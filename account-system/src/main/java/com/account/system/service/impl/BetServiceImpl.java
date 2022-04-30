package com.account.system.service.impl;

import com.account.common.enums.ChipChangeEnum;
import com.account.common.utils.SecurityUtils;
import com.account.system.domain.*;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.search.ReportSearch;
import com.account.system.domain.search.WinLoseReportSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
import com.account.system.mapper.*;
import com.account.system.service.BetService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hope
 * @since 2022/4/22
 */
@Service
public class BetServiceImpl implements BetService {

    @Autowired
    BetMapper betMapper;

    @Autowired
    SysMembersMapper sysMembersMapper;

    @Autowired
    SysChipRecordMapper sysChipRecordMapper;

    @Autowired
    SysOddsConfigureMapper oddsConfigureMapper;

    @Autowired
    SysWaterMapper sysWaterMapper;

    @Autowired
    SysTableManagementMapper sysTableManagementMapper;

    @Autowired
    PorintMapper porintMapper;

    @Autowired
    ReceiptMapper receiptMapper;

    @Autowired
    SysGameResultMapper sysGameResultMapper;

    @Override
    public SysTableManagement getTableByIp(String ip) {
        return sysTableManagementMapper.getTableByIp(ip);
    }

    @Override
    public BigDecimal selectMembersChip(String card) {
        return sysMembersMapper.selectMembersChip(card);
    }

    /**
     * 修改路珠
     */
    @Transactional
    public void updateGameResult(SysGameResult sysGameResult) {
        SysGameResult gameResult = sysGameResultMapper.getGameResultInfo(sysGameResult);
        List<SysBetInfo> list = betMapper.getBetsByResult(gameResult);
        SysBet sysBet = new SysBet(gameResult.getTableId(), gameResult.getBootNum(), gameResult.getGameNum(), sysGameResult.getGameResult());
        sysBet.setUpdateBy(sysGameResult.getUpdateBy());
        Map chipRecord = new HashMap();
        list.forEach(sysBetInfo -> {
            sysBet.setBetId(sysBetInfo.getBetId());
            sysBet.setCard(sysBetInfo.getCard());
            sysBet.setType(sysBetInfo.getType());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(sysBetInfo.getBetOption(), sysBetInfo.getBetMoney());

            //修改注单
            betMapper.updateBet(sysBet);

            //修改 注单明细
            Map map = getBaccaratBetInfos(jsonObject, sysBet);
            List<SysBetInfo> betInfos = (List) map.get("list");
            betMapper.updateBetInfos(betInfos.get(0));

            //修改 洗码
            BigDecimal water = (BigDecimal) map.get("water");
            BigDecimal oldWater = sysBetInfo.getWater();
            BigDecimal oldWaterAmount = sysBetInfo.getWaterAmount();
            BigDecimal newWater = checkDecimal(betInfos.get(0).getWater());
            BigDecimal newWaterAmount = checkDecimal(betInfos.get(0).getWaterAmount());
            sysWaterMapper.saveMembersWater(sysBetInfo.getCard(), newWater.subtract(oldWater), newWaterAmount.subtract(oldWaterAmount));

            //修改会员现有筹码
            BigDecimal membersChip = (BigDecimal) map.get("membersChip");
            if (sysBetInfo.getType() != 1) {
                membersChip = membersChip.subtract(sysBetInfo.getWinLose());
            }
            sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip);

            //修改 筹码帐变记录
            SysChipRecord sysChipRecord = sysChipRecordMapper.selectChipRecord(sysBet.getCard(), sysBet.getBetId());
            BigDecimal change = checkDecimal((BigDecimal) chipRecord.get(sysBetInfo.getBetId()));
            change = change.add((BigDecimal) map.get("membersChip"));
            chipRecord.put(sysBetInfo.getBetId(), change);
            if (change.compareTo(new BigDecimal(0)) > 0) {//赢
                sysChipRecord.setType(ChipChangeEnum.WIN_CHIP.getCode());
            } else {
                sysChipRecord.setType(ChipChangeEnum.LOSE_CHIP.getCode());
            }
            sysChipRecord.setChange(change.abs());
            sysChipRecord.setAfter(sysChipRecord.getBefore().add(change));
            sysChipRecordMapper.updateChipRecord(sysChipRecord);

            //修改 签单
            BigDecimal tableChip = (BigDecimal) map.get("tableChip");
            BigDecimal tableCash = (BigDecimal) map.get("tableCash");
            BigDecimal tableInsurance = (BigDecimal) map.get("tableInsurance");
            if (sysBetInfo.getType() == 0) {
                tableChip = tableChip.add(sysBetInfo.getWinLose());
            } else if (sysBetInfo.getType() == 1) {
                tableCash = tableCash.add(sysBetInfo.getWinLose());
            } else if (sysBetInfo.getType() == 2) {
                tableInsurance = tableInsurance.add(sysBetInfo.getWinLose());
            }
            if (tableChip.compareTo(BigDecimal.ZERO) != 0
                    || tableCash.compareTo(BigDecimal.ZERO) != 0
                    || tableInsurance.compareTo(BigDecimal.ZERO) != 0
                    || water.compareTo(BigDecimal.ZERO) != 0) {
                SysPorint sysPorint = porintMapper.getPorint(gameResult.getTableId(), gameResult.getBootNum(), gameResult.getVersion());
                SysReceipt sysReceipt = receiptMapper.getReceipt(gameResult.getTableId(), gameResult.getVersion());
                if (sysPorint != null) {
                    porintMapper.updatePorint(sysPorint.getId(), tableChip, tableCash, tableInsurance, water);
                }
                if (sysReceipt != null) {
                    receiptMapper.updateReceipt(sysReceipt.getId(), tableChip, tableCash, tableInsurance, water);
                } else {
                    //修改 桌台 累计
                    sysTableManagementMapper.addTableMoney(new SysTableManagement(gameResult.getTableId(), tableChip, tableCash, tableInsurance));
                }
            }
        });

        sysGameResultMapper.updateGameResult(sysGameResult);
    }

    @Transactional
    public void saveBet(SysTableManagement sysTableManagement, String gameResult, JSONArray bets) {
        final BigDecimal[] tableChip = {BigDecimal.ZERO};
        final BigDecimal[] tableCash = {BigDecimal.ZERO};
        final BigDecimal[] tableInsurance = {BigDecimal.ZERO};
        bets.forEach(b -> {
            //添加注单
            JSONObject bet = (JSONObject) b;
            SysBet sysBet = new SysBet();
            sysBet.setCard(bet.getString("card"));
            sysBet.setGameId(sysTableManagement.getGameId());
            sysBet.setTableId(sysTableManagement.getTableId());
            sysBet.setBootNum(sysTableManagement.getBootNum());
            sysBet.setGameNum(sysTableManagement.getGameNum() + 1);
            sysBet.setGameResult(gameResult);
            sysBet.setType(bet.getInteger("type"));
            sysBet.setCreateBy(sysTableManagement.getCreateBy());
            sysBet.setVersion(sysTableManagement.getVersion());
            betMapper.saveBet(sysBet);

            Map map = getBaccaratBetInfos(bet, sysBet);

            //桌台 累计
            tableChip[0] = tableChip[0].add((BigDecimal) map.get("tableChip"));
            tableCash[0] = tableCash[0].add((BigDecimal) map.get("tableCash"));
            tableInsurance[0] = tableInsurance[0].add((BigDecimal) map.get("tableInsurance"));

            //添加 注单明细
            List list = (List) map.get("list");
            if (list.size() > 0) {
                betMapper.saveBetInfos(list);
            }

            BigDecimal membersChip = (BigDecimal) map.get("membersChip");
            if (membersChip.compareTo(BigDecimal.ZERO) != 0) {
                //生成 筹码帐变记录
                BigDecimal chip = sysMembersMapper.selectMembersChip(sysBet.getCard());
                SysChipRecord sysChipRecord = new SysChipRecord(sysBet.getCard(), chip, membersChip.abs(), chip.add(membersChip), sysBet.getBetId());
                if (membersChip.compareTo(new BigDecimal(0)) > 0) {//赢
                    sysChipRecord.setType(ChipChangeEnum.WIN_CHIP.getCode());
                } else {
                    sysChipRecord.setType(ChipChangeEnum.LOSE_CHIP.getCode());
                }
                sysChipRecord.setCreateBy(sysTableManagement.getCreateBy());
                sysChipRecordMapper.addChipRecord(sysChipRecord);
                //修改会员现有筹码
                sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip);
            }
            //计算打码量
            BigDecimal water = (BigDecimal) map.get("water");
            if (water.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal waterAmount = (BigDecimal) map.get("waterAmount");
                sysWaterMapper.saveMembersWater(sysBet.getCard(), water, waterAmount);
            }

        });

        //修改桌台累计
        if (tableChip[0].compareTo(BigDecimal.ZERO) != 0 || tableCash[0].compareTo(BigDecimal.ZERO) != 0
                || tableInsurance[0].compareTo(BigDecimal.ZERO) != 0) {
            sysTableManagementMapper.addTableMoney(new SysTableManagement(sysTableManagement.getId(), tableChip[0], tableCash[0], tableInsurance[0]));
        }
        //记录赛果
        SysGameResult sysGameResult = new SysGameResult(sysTableManagement);
        sysGameResult.setGameResult(gameResult);
        sysGameResult.setCreateBy(sysTableManagement.getCreateBy());
        sysGameResultMapper.saveGameResult(sysGameResult);
        //修改局号
        sysTableManagementMapper.updateGameNum(sysTableManagement.getId());
    }

    public List<Map> getGameResults(SysTableManagement sysTableManagement) {
        return sysGameResultMapper.getGameResults(sysTableManagement);
    }

    /**
     * 开牌 计算赔码数
     */
    public BigDecimal getPayOut(JSONObject bet, String gameResult) {
        SysBet sysBet = new SysBet();
        sysBet.setCard(bet.getString("card"));
        sysBet.setGameResult(gameResult);
        sysBet.setType(bet.getInteger("type"));
        sysBet.setCreateBy(SecurityUtils.getUsername());
        Map map = getBaccaratBetInfos(bet, sysBet);
        BigDecimal payout = (BigDecimal) map.get("tableChip");
        if (payout.compareTo(BigDecimal.ZERO) != 0) {
            return payout.add((BigDecimal) map.get("tableInsurance"));
        } else {
            payout = (BigDecimal) map.get("tableCash");
        }
        return payout;
    }

    /**
     * 点码
     */
    public Map pointChip(Reckon reckon, SysTableManagement sysTableManagement) {
        Map map = new HashMap();
        BigDecimal chipGap = checkDecimal(reckon.getChip()).subtract(sysTableManagement.getChipPointBase()).subtract(sysTableManagement.getChip())
                .subtract(sysTableManagement.getChipAdd())
                .subtract(checkDecimal(reckon.getChipAdd()))
                .add(checkDecimal(reckon.getChipSub()));

        BigDecimal cashGap = checkDecimal(reckon.getCash()).subtract(sysTableManagement.getCashPointBase()).subtract(sysTableManagement.getCash())
                .subtract(sysTableManagement.getCashAdd())
                .subtract(checkDecimal(reckon.getCashAdd()))
                .add(checkDecimal(reckon.getCashSub()));


        BigDecimal insuranceGap = checkDecimal(reckon.getInsurance()).subtract(sysTableManagement.getInsurancePointBase())
                .subtract(sysTableManagement.getInsuranceAdd())
                .subtract(sysTableManagement.getInsurance())
                .subtract(checkDecimal(reckon.getInsuranceAdd()))
                .add(checkDecimal(reckon.getInsuranceSub()));

        map.put("chipGap", chipGap);
        map.put("cashGap", cashGap);
        map.put("insuranceGap", insuranceGap);
        return map;
    }

    /**
     * 收码
     */
    public Map receiptChip(Reckon reckon, SysTableManagement sysTableManagement) {
        Map map = pointChip(reckon, sysTableManagement);
        BigDecimal chipReceipt = checkDecimal(reckon.getChip()).subtract(sysTableManagement.getChipPointBase())
                .subtract(checkDecimal(reckon.getChipAdd()))
                .add(checkDecimal(reckon.getChipSub()))
                .subtract(sysTableManagement.getChipAdd());

        BigDecimal cashReceipt = checkDecimal(reckon.getCash()).subtract(sysTableManagement.getCashPointBase())
                .subtract(checkDecimal(reckon.getCashAdd()))
                .add(checkDecimal(reckon.getCashSub()))
                .subtract(sysTableManagement.getCashAdd());

        map.put("chipReceipt", chipReceipt);
        map.put("cashReceipt", cashReceipt);
        return map;
    }

    /**
     * 点码 确认修改
     */
    @Transactional
    public void editChip(Reckon reckon, SysTableManagement sysTableManagement) {
        BigDecimal chipAdd = checkDecimal(reckon.getChipAdd()).subtract(checkDecimal(reckon.getChipSub()));
        BigDecimal cashAdd = checkDecimal(reckon.getCashAdd()).subtract(checkDecimal(reckon.getCashSub()));
        BigDecimal insuranceAdd = checkDecimal(reckon.getInsuranceAdd()).subtract(checkDecimal(reckon.getInsuranceSub()));

        SysPorint sysPorint = new SysPorint();
        sysPorint.setCreateBy(SecurityUtils.getUsername());
        sysPorint.setTableId(sysTableManagement.getTableId());
        sysPorint.setVersion(sysTableManagement.getVersion());
        sysPorint.setBootNum(sysTableManagement.getBootNum());

        sysPorint.setSysChip(sysTableManagement.getChipPointBase().add(sysTableManagement.getChip())
                .add(sysTableManagement.getChipAdd()).add(checkDecimal(reckon.getChipAdd()))
                .subtract(checkDecimal(reckon.getChipSub())));
        sysPorint.setPersonChip(reckon.getChip());
        sysPorint.setChipGap(sysPorint.getPersonChip().subtract(sysPorint.getSysChip()));
        sysPorint.setChipAdd((sysTableManagement.getChipAdd()).add(checkDecimal(reckon.getChipAdd()))
                .subtract(checkDecimal(reckon.getChipSub())));

        sysPorint.setSysInsurance(sysTableManagement.getInsurancePointBase().add(sysTableManagement.getInsurance())
                .add(sysTableManagement.getInsuranceAdd()).add(checkDecimal(reckon.getInsuranceAdd()))
                .subtract(checkDecimal(reckon.getInsuranceSub())));
        sysPorint.setPersonInsurance(reckon.getInsurance());
        sysPorint.setInsuranceGap(sysPorint.getPersonInsurance().subtract(sysPorint.getSysInsurance()));
        sysPorint.setInsuranceAdd((sysTableManagement.getInsuranceAdd())
                .add(checkDecimal(reckon.getInsuranceAdd())).subtract(checkDecimal(reckon.getInsuranceSub())));

        sysPorint.setWater(betMapper.getWater(sysTableManagement));
        sysPorint.setChipWin(betMapper.getWinLose(sysTableManagement));
        sysPorint.setInsuranceWin(betMapper.getInsuranceWin(sysTableManagement));
        porintMapper.savePorint(sysPorint);
        sysTableManagementMapper.addTableMoney(new SysTableManagement(sysTableManagement.getId(), 1l, chipAdd, cashAdd, insuranceAdd));
    }

    /**
     * 收码 确认修改
     */
    @Transactional
    public void receiptEditChip(Reckon reckon, SysTableManagement sysTableManagement) {
        SysReceipt sysReceipt = new SysReceipt();
        sysReceipt.setCreateBy(SecurityUtils.getUsername());
        sysReceipt.setTableId(sysTableManagement.getTableId());
        sysReceipt.setVersion(sysTableManagement.getVersion());

        sysReceipt.setChip(reckon.getChip().subtract(sysTableManagement.getChipPointBase())
                .subtract(reckon.getChipAdd() != null ? reckon.getChipAdd() : BigDecimal.ZERO)
                .add(reckon.getChipSub() != null ? reckon.getChipSub() : BigDecimal.ZERO)
                .subtract(sysTableManagement.getChipAdd()));
        sysReceipt.setChipAdd((sysTableManagement.getChipAdd()).add(reckon.getChipAdd() != null ? reckon.getChipAdd() : BigDecimal.ZERO)
                .subtract(reckon.getChipSub() != null ? reckon.getChipSub() : BigDecimal.ZERO));

        sysReceipt.setCash(reckon.getCash().subtract(sysTableManagement.getCashPointBase()));

        sysReceipt.setInsurance(reckon.getInsurance().subtract(sysTableManagement.getInsurancePointBase())
                .subtract(reckon.getInsuranceAdd() != null ? reckon.getInsuranceAdd() : BigDecimal.ZERO)
                .add(reckon.getInsuranceSub() != null ? reckon.getInsuranceSub() : BigDecimal.ZERO)
                .subtract(sysTableManagement.getInsuranceAdd()));
        sysReceipt.setInsuranceAdd((sysTableManagement.getInsuranceAdd()).add(reckon.getInsuranceAdd() != null ? reckon.getInsuranceAdd() : BigDecimal.ZERO)
                .subtract(reckon.getInsuranceSub() != null ? reckon.getInsuranceSub() : BigDecimal.ZERO));

        sysReceipt.setWater(betMapper.getReceiptWater(sysTableManagement));
        sysReceipt.setWin(betMapper.getReceiptWinLose(sysTableManagement));
        sysReceipt.setInsuranceWin(betMapper.getReceiptInsuranceWin(sysTableManagement));

        betMapper.saveReceipt(sysReceipt);
        sysTableManagementMapper.resetTableMoney(sysTableManagement.getId());
    }

    @Override
    public List<Map> selectDailyReportList(ReportSearch reportSearch) {
        return betMapper.selectDailyReportList(reportSearch);
    }

    @Override
    public Map selectDailyReportTotal(ReportSearch reportSearch) {
        return betMapper.selectDailyReportTotal(reportSearch);
    }

    /**
     * 注单补录
     */
    @Transactional
    public void repairBet(BetRepair betRepair) {
        if (betRepair.getGameId() == 1) {
            //判断赛果是否修改
            SysGameResult sysGameResult = sysGameResultMapper.selectGameResult(betRepair);
            if (sysGameResult != null && !betRepair.getGameResult().equals(sysGameResult.getGameResult())) {
                sysGameResult.setGameResult(betRepair.getGameResult());
                updateGameResult(sysGameResult);
            }

            BigDecimal tableChip = BigDecimal.ZERO;
            BigDecimal tableCash = BigDecimal.ZERO;
            BigDecimal tableInsurance = BigDecimal.ZERO;

            //添加注单
            SysBet sysBet = new SysBet(betRepair.getTableId(), betRepair.getBootNum(), betRepair.getGameNum(), betRepair.getGameResult(), betRepair.getVersion());
            sysBet.setCard(betRepair.getCard());
            sysBet.setGameId(betRepair.getGameId());
            sysBet.setType(betRepair.getType());
            sysBet.setCreateBy(SecurityUtils.getUsername());
            try {
                sysBet.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(betRepair.getOptionTime()));
            } catch (ParseException e) {
                sysBet.setCreateTime(new Date());
            }
            betMapper.saveBet(sysBet);

            JSONObject bet = JSONObject.parseObject(betRepair.getOption());
            Map map = getBaccaratBetInfos(bet, sysBet);

            //添加 注单明细
            List list = (List) map.get("list");
            if (list.size() > 0) {
                betMapper.saveBetInfos(list);
            }

            //桌台 累计
            tableChip = tableChip.add((BigDecimal) map.get("tableChip"));
            tableCash = tableCash.add((BigDecimal) map.get("tableCash"));
            tableInsurance = tableInsurance.add((BigDecimal) map.get("tableInsurance"));

            BigDecimal membersChip = (BigDecimal) map.get("membersChip");
            if (membersChip.compareTo(BigDecimal.ZERO) != 0) {
                //生成 筹码帐变记录
                BigDecimal chip = sysMembersMapper.selectMembersChip(sysBet.getCard());
                SysChipRecord sysChipRecord = new SysChipRecord();
                if (membersChip.compareTo(new BigDecimal(0)) > 0) {//赢
                    sysChipRecord.setType(ChipChangeEnum.WIN_CHIP.getCode());
                } else {
                    sysChipRecord.setType(ChipChangeEnum.LOSE_CHIP.getCode());
                }
                sysChipRecord.setCard(sysBet.getCard());
                sysChipRecord.setBefore(chip);
                sysChipRecord.setChange(membersChip.abs());
                sysChipRecord.setAfter(chip.add(membersChip));
                sysChipRecord.setCreateBy(SecurityUtils.getUsername());
                sysChipRecord.setBetId(sysBet.getBetId());
                sysChipRecordMapper.addChipRecord(sysChipRecord);
                //修改会员现有筹码
                sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip);
            }
            //计算打码量
            BigDecimal water = (BigDecimal) map.get("water");
            if (water.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal waterAmount = (BigDecimal) map.get("waterAmount");
                sysWaterMapper.saveMembersWater(sysBet.getCard(), water, waterAmount);
            }
            //查询签单
            if (tableChip.compareTo(BigDecimal.ZERO) != 0
                    || tableCash.compareTo(BigDecimal.ZERO) != 0
                    || tableInsurance.compareTo(BigDecimal.ZERO) != 0
                    || water.compareTo(BigDecimal.ZERO) != 0) {
                SysPorint sysPorint = porintMapper.getPorint(betRepair.getTableId(), betRepair.getBootNum(), betRepair.getVersion());
                SysReceipt sysReceipt = receiptMapper.getReceipt(betRepair.getTableId(), betRepair.getVersion());
                if (sysPorint != null) {
                    porintMapper.updatePorint(sysPorint.getId(), tableChip, tableCash, tableInsurance, water);
                }
                if (sysReceipt != null) {
                    receiptMapper.updateReceipt(sysReceipt.getId(), tableChip, tableCash, tableInsurance, water);
                } else {
                    //修改 桌台 累计
                    sysTableManagementMapper.addTableMoney(new SysTableManagement(betRepair.getTableId(), tableChip, tableCash, tableInsurance));
                }
            }
        }
    }

    /**
     * 注单修改
     * 会员洗码 筹码余额 筹码变动明细 点码明细 收码明细
     */
    public void updateBet(BetUpdate betUpdate) {
        if (betUpdate.getGameId() == 1) {
            //判断赛果是否修改
            SysGameResult sysGameResult = sysGameResultMapper.selectGameResultByBetId(betUpdate.getBetId());
            if (sysGameResult != null && !betUpdate.getGameResult().equals(sysGameResult.getGameResult())) {
                sysGameResult.setGameResult(betUpdate.getGameResult());
                updateGameResult(sysGameResult);
            }

            List<SysBetInfo> list = betMapper.getBets(betUpdate.getBetId());


            BigDecimal[] tableChip = {BigDecimal.ZERO};
            BigDecimal[] tableCash = {BigDecimal.ZERO};
            BigDecimal[] tableInsurance = {BigDecimal.ZERO};
            BigDecimal[] water = {BigDecimal.ZERO};
            list.forEach(oldBetInfo -> {
                //修改 洗码
                if (oldBetInfo.getWater().compareTo(BigDecimal.ZERO) != 0) {
                    sysWaterMapper.saveMembersWater(oldBetInfo.getCard(), BigDecimal.ZERO.subtract(oldBetInfo.getWater())
                            , BigDecimal.ZERO.subtract(oldBetInfo.getWaterAmount()));
                    water[0] = water[0].add(oldBetInfo.getWater());
                }
                //修改会员现有筹码
                if (oldBetInfo.getType() != 1 && oldBetInfo.getWinLose().compareTo(BigDecimal.ZERO) != 0) {
                    sysMembersMapper.updateMembersChip(oldBetInfo.getCard(), BigDecimal.ZERO.subtract(oldBetInfo.getWinLose()));
                }
                //删除 筹码帐变记录
                sysChipRecordMapper.deleteChipRecord(oldBetInfo.getCard(), oldBetInfo.getBetId());
                //记录桌台累计
                if (oldBetInfo.getType() == 0) {
                    tableChip[0] = tableChip[0].add(oldBetInfo.getWinLose());
                } else if (oldBetInfo.getType() == 1) {
                    tableCash[0] = tableCash[0].add(oldBetInfo.getWinLose());
                } else if (oldBetInfo.getType() == 2) {
                    tableInsurance[0] = tableInsurance[0].add(oldBetInfo.getWinLose());
                }
            });


            SysBetInfo betInfo = list.get(0);
            SysBet sysBet = new SysBet();
            sysBet.setBetId(betUpdate.getBetId());
            sysBet.setUpdateBy(betUpdate.getUpdateBy());
            sysBet.setCard(betUpdate.getCard());
            sysBet.setType(betUpdate.getType());
            sysBet.setGameResult(betUpdate.getGameResult());
            sysBet.setCreateTime(betInfo.getCreateTime());
            sysBet.setCreateBy(betInfo.getCreateBy());
            sysBet.setVersion(betInfo.getVersion());
            sysBet.setTableId(betInfo.getTableId());
            sysBet.setBootNum(betInfo.getBootNum());
            sysBet.setGameNum(betInfo.getGameNum());
            sysBet.setGameId(betInfo.getGameId());

            JSONObject jsonObject = JSON.parseObject(betUpdate.getOption());;

            //注单明细
            Map map = getBaccaratBetInfos(jsonObject, sysBet);
            List<SysBetInfo> betInfos = (List) map.get("list");

            betMapper.deleteBetInfo(betUpdate.getBetId());
            betMapper.saveBetInfos(betInfos);

            BigDecimal membersChip = (BigDecimal) map.get("membersChip");
            if (membersChip.compareTo(BigDecimal.ZERO) != 0) {
                //生成 筹码帐变记录
                BigDecimal chip = sysMembersMapper.selectMembersChip(sysBet.getCard());
                SysChipRecord sysChipRecord = new SysChipRecord(sysBet.getCard(), chip, membersChip.abs(), chip.add(membersChip), sysBet.getBetId());
                if (membersChip.compareTo(new BigDecimal(0)) > 0) {//赢
                    sysChipRecord.setType(ChipChangeEnum.WIN_CHIP.getCode());
                } else {
                    sysChipRecord.setType(ChipChangeEnum.LOSE_CHIP.getCode());
                }
                sysChipRecord.setCreateBy(sysBet.getCreateBy());
                sysChipRecordMapper.addChipRecord(sysChipRecord);
                //修改会员现有筹码
                sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip);
            }
            //计算打码量
            BigDecimal newWater = (BigDecimal) map.get("water");
            if (newWater.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal waterAmount = (BigDecimal) map.get("waterAmount");
                sysWaterMapper.saveMembersWater(sysBet.getCard(), newWater, waterAmount);
            }

            //修改 签单
            tableChip[0] = tableChip[0].subtract(((BigDecimal) map.get("tableChip")));
            tableCash[0] = tableCash[0].subtract(((BigDecimal) map.get("tableCash")));
            tableInsurance[0] = tableInsurance[0].subtract(((BigDecimal) map.get("tableInsurance")));
            water[0] = water[0].subtract(newWater);
            if (tableChip[0].compareTo(BigDecimal.ZERO) != 0
                    || tableCash[0].compareTo(BigDecimal.ZERO) != 0
                    || tableInsurance[0].compareTo(BigDecimal.ZERO) != 0
                    || water[0].compareTo(BigDecimal.ZERO) != 0) {
                SysPorint sysPorint = porintMapper.getPorint(sysBet.getTableId(), sysBet.getBootNum(), sysBet.getVersion());
                SysReceipt sysReceipt = receiptMapper.getReceipt(sysBet.getTableId(), sysBet.getVersion());
                if (sysPorint != null) {
                    porintMapper.updatePorint(sysPorint.getId(), tableChip[0], tableCash[0], tableInsurance[0], water[0]);
                }
                if (sysReceipt != null) {
                    receiptMapper.updateReceipt(sysReceipt.getId(), tableChip[0], tableCash[0], tableInsurance[0], water[0]);
                } else {
                    //修改 桌台 累计
                    sysTableManagementMapper.addTableMoney(new SysTableManagement(sysBet.getTableId(), tableChip[0], tableCash[0], tableInsurance[0]));
                }
            }
            betMapper.updateBet(sysBet);
        }
    }

    @Override
    public List<Map> selectWinLoseList(WinLoseReportSearch reportSearch) {
        return betMapper.selectWinLoseList(reportSearch);
    }

    @Override
    public List<Map> selectTablePlumbingList(String startTime, String endTime) {
        return betMapper.selectTablePlumbingList(startTime, endTime);
    }

    @Override
    public Map selectTablePlumbingTotal(String startTime, String endTime) {
        return betMapper.selectTablePlumbingTotal(startTime, endTime);
    }

    /**
     * 注单记录
     *
     * @return
     */
    @Override
    public List<BetInfoVo> selectBetInfoList(BetSearch betSearch) {
        List<BetInfoVo> betInfoVos = betMapper.selectBetInfoList(betSearch);
        return betInfoVos;
    }

    @Override
    public Map<Long, List<BetInfoOptionVo>> selectBetOptionList(List<Long> betIds) {
        List<BetInfoOptionVo> betInfos = betMapper.selectBetOptionList(betIds);
        Map<Long, List<BetInfoOptionVo>> map = betInfos.stream().collect(Collectors.groupingBy(BetInfoOptionVo::getBetId));
        return map;
    }

    @Override
    public Map selectBetTotal(BetSearch betSearch) {
        return betMapper.selectBetTotal(betSearch);
    }

    /**
     * 计算百家乐 注单明细
     * 1：闲 4：庄 7：和 || 5：闲对 8：庄对 || 9：大 6：小 || 0: 闲保险 3:庄保险
     */
    private Map getBaccaratBetInfos(JSONObject bet, SysBet sysBet) {
        Map map = new HashMap();
        List<SysBetInfo> list = new ArrayList<>();//注单明细
        BigDecimal membersChip = BigDecimal.ZERO;//会员总筹码输赢

        BigDecimal water = BigDecimal.ZERO;//会员洗码量
        BigDecimal waterAmount;//会员洗码费

        BigDecimal tableChip = BigDecimal.ZERO;//桌台筹码输赢
        BigDecimal tableCash = BigDecimal.ZERO;//桌台现金输赢
        BigDecimal tableInsurance = BigDecimal.ZERO;//桌台保险输赢

        boolean isChip = (0 == sysBet.getType() || 2 == sysBet.getType()); //是否为筹码下注
        SysOddsConfigure sysOddsConfigure = oddsConfigureMapper.selectConfigInfo();
        String[] betOption = {"1", "4", "7", "5", "8", "9", "6", "0", "3"};
        String[] odds = {sysOddsConfigure.getBaccaratPlayerWin(),
                sysOddsConfigure.getBaccaratBankerWin(),
                sysOddsConfigure.getBaccaratTieWin(),
                sysOddsConfigure.getBaccaratPlayerPair(),
                sysOddsConfigure.getBaccaratBankerPair(),
                sysOddsConfigure.getBaccaratLarge(),
                sysOddsConfigure.getBaccaratSmall(),
        };
        String gameResult = sysBet.getGameResult();
        for (int i = 0; i < betOption.length; i++) {
            String option = betOption[i];
            BigDecimal amount = bet.getBigDecimal(option);
            if (amount != null) {
                SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet), SysBetInfo.class);
                sysBetInfo.setBetOption(betOption[i]);
                sysBetInfo.setBetMoney(amount);
                if ("1".equals(option)) {//闲
                    if (gameResult.contains("1")) {
                        sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])));
                    } else if (gameResult.contains("7")) {
                        sysBetInfo.setWinLose(BigDecimal.ZERO);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                        water = water.add(amount);
                        sysBetInfo.setWater(amount);
                        if (isChip) {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioChip()).divide(new BigDecimal(100)));
                        } else {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioCash()).divide(new BigDecimal(100)));
                        }
                    }
                } else if ("4".equals(option)) {//庄
                    if (gameResult.contains("4")) {
                        sysBetInfo.setPump(amount.multiply(sysOddsConfigure.getBaccaratPump()).divide(new BigDecimal(100)));
                        sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])).subtract(sysBetInfo.getPump()));
                    } else if (gameResult.contains("7")) {
                        sysBetInfo.setWinLose(BigDecimal.ZERO);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                        water = water.add(amount);
                        sysBetInfo.setWater(amount);
                        if (isChip) {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioChip()).divide(new BigDecimal(100)));
                        } else {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioCash()).divide(new BigDecimal(100)));
                        }
                    }
                } else if ("0".equals(option)) { //闲保险
                    sysBetInfo.setType(2);
                    if (gameResult.contains("4")) {
                        sysBetInfo.setWinLose(amount);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                        water = water.add(amount);
                        sysBetInfo.setWater(amount);
                        if (isChip) {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioChip()).divide(new BigDecimal(100)));
                        } else {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioCash()).divide(new BigDecimal(100)));
                        }
                    }
                    tableInsurance = tableInsurance.subtract(sysBetInfo.getWinLose());
                } else if ("3".equals(option)) { //庄保险
                    sysBetInfo.setType(2);
                    if (gameResult.contains("1")) {
                        sysBetInfo.setWinLose(amount);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                        water = water.add(amount);
                        sysBetInfo.setWater(amount);
                        if (isChip) {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioChip()).divide(new BigDecimal(100)));
                        } else {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioCash()).divide(new BigDecimal(100)));
                        }
                    }
                    tableInsurance = tableInsurance.subtract(sysBetInfo.getWinLose());
                } else {
                    if (gameResult.contains(option)) {
                        sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])));
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                        water = water.add(amount);
                        sysBetInfo.setWater(amount);
                        if (isChip) {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioChip()).divide(new BigDecimal(100)));
                        } else {
                            sysBetInfo.setWaterAmount(amount.multiply(sysOddsConfigure.getBaccaratRollingRatioCash()).divide(new BigDecimal(100)));
                        }
                    }
                }
                list.add(sysBetInfo);
                if (isChip) {
                    membersChip = membersChip.add(sysBetInfo.getWinLose());
                    tableChip = tableChip.subtract(sysBetInfo.getWinLose()).subtract(tableInsurance);
                } else {
                    tableCash = tableCash.subtract(sysBetInfo.getWinLose());
                }
            }
        }
        if (isChip) {
            waterAmount = water.multiply(sysOddsConfigure.getBaccaratRollingRatioChip()).divide(new BigDecimal(100));
        } else {
            waterAmount = water.multiply(sysOddsConfigure.getBaccaratRollingRatioCash()).divide(new BigDecimal(100));
        }

        map.put("list", list);
        map.put("membersChip", membersChip);
        map.put("tableChip", tableChip);
        map.put("tableCash", tableCash);
        map.put("tableInsurance", tableInsurance);
        map.put("water", water);
        map.put("waterAmount", waterAmount);
        return map;
    }

    private BigDecimal checkDecimal(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal : BigDecimal.ZERO;
    }
}
