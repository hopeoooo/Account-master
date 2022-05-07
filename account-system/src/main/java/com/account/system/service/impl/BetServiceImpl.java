package com.account.system.service.impl;

import com.account.common.enums.ChipChangeEnum;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.*;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.search.ReportSearch;
import com.account.system.domain.search.WinLoseReportSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
import com.account.system.mapper.*;
import com.account.system.service.BetService;
import com.account.system.service.SysBetUpdateRecordService;
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
    BetUtilService betUtilService;

    @Autowired
    SysBetUpdateRecordService sysBetUpdateRecordService;

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
    public SysTableManagement getTableByIp(String ip, Long gameId) {
        return sysTableManagementMapper.getTableByIp(ip, gameId);
    }

    /**
     * 修改路珠
     */
    @Transactional
    public void updateGameResult(SysGameResult sysGameResult, Long betId) {
        SysGameResult gameResult = sysGameResultMapper.getGameResultInfo(sysGameResult);
        if (sysGameResult.getGameResult().equals(gameResult.getGameResult())) return;
        List<SysBet> sysBets = betMapper.getBetsByResult(gameResult);
        Map chipRecord = new HashMap();
        sysBets.forEach(sysBet -> {
            List<SysBetInfo> sysBetInfos = betMapper.getBetInfos(sysBet.getBetId());
            sysBet.setGameResult(sysGameResult.getGameResult());
            final BigDecimal[] newWin = {BigDecimal.ZERO};
            sysBetInfos.forEach(sysBetInfo -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(sysBetInfo.getBetOption(), sysBetInfo.getBetMoney());

                //修改 注单明细
                Map map = betUtilService.getBetInfos(jsonObject, sysBet, gameResult.getGameId());
                List<SysBetInfo> betInfos = (List) map.get("list");
                betMapper.updateBetInfos(betInfos.get(0));
                newWin[0] = newWin[0].add(betInfos.get(0).getWinLose());
                //修改 洗码
                BigDecimal water = (BigDecimal) map.get("water");
                BigDecimal oldWater = sysBetInfo.getWater();
                BigDecimal oldWaterAmount = sysBetInfo.getWaterAmount();
                BigDecimal newWater = checkDecimal(betInfos.get(0).getWater());
                BigDecimal newWaterAmount = checkDecimal(betInfos.get(0).getWaterAmount());
                if (sysBet.getType() == 0 || sysBet.getType() == 1) {
                    sysWaterMapper.saveMembersWater(sysBetInfo.getCard(), newWater.subtract(oldWater), newWaterAmount.subtract(oldWaterAmount)
                            , BigDecimal.ZERO, BigDecimal.ZERO);
                } else {
                    sysWaterMapper.saveMembersWater(sysBetInfo.getCard(), BigDecimal.ZERO, BigDecimal.ZERO
                            , newWater.subtract(oldWater), newWaterAmount.subtract(oldWaterAmount));
                }

                //修改会员现有筹码
                BigDecimal membersChip = (BigDecimal) map.get("membersChip");
                if (sysBetInfo.getType() == 0 || sysBetInfo.getType() == 2) {
                    membersChip = membersChip.subtract(sysBetInfo.getWinLose());
                }
                if (membersChip.compareTo(BigDecimal.ZERO) != 0) {
                    if (sysBet.getType() == 0 || sysBet.getType() == 1) {
                        //修改会员现有筹码
                        sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip, BigDecimal.ZERO);
                    } else {
                        //修改会员现有筹码
                        sysMembersMapper.updateMembersChip(sysBet.getCard(), BigDecimal.ZERO, membersChip);
                    }
                }

                //记录 帐变
                chipRecord.put(sysBetInfo.getBetId(), checkDecimal((BigDecimal) chipRecord.get(sysBetInfo.getBetId())).add((BigDecimal) map.get("membersChip")));

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
                        porintMapper.updatePorint(sysPorint.getId(), tableChip, tableCash, tableInsurance, newWater.subtract(oldWater));
                    }
                    if (sysReceipt != null) {
                        receiptMapper.updateReceipt(sysReceipt.getId(), tableChip, tableCash, tableInsurance, newWater.subtract(oldWater));
                    } else {
                        //修改 桌台 累计
                        sysTableManagementMapper.addTableMoney(new SysTableManagement(gameResult.getTableId(), tableChip, tableCash, tableInsurance));
                    }
                }
            });

            //修改 筹码帐变记录
            SysChipRecord sysChipRecord = sysChipRecordMapper.selectChipRecord(sysBet.getCard(), sysBet.getBetId());
            BigDecimal change = (BigDecimal) chipRecord.get(sysBet.getBetId());
            if (change.compareTo(new BigDecimal(0)) == 0) {
                sysChipRecordMapper.deleteChipRecord(sysChipRecord.getCard(), sysChipRecord.getBetId());
            } else {
                if (change.compareTo(new BigDecimal(0)) > 0) {//赢
                    sysChipRecord.setType(ChipChangeEnum.WIN_CHIP.getCode());
                } else if (change.compareTo(new BigDecimal(0)) < 0) {
                    sysChipRecord.setType(ChipChangeEnum.LOSE_CHIP.getCode());
                }
                if (sysBet.getType() == 0 || sysBet.getType() == 1) {
                    sysChipRecord.setChange(change.abs());
                    sysChipRecord.setAfter(sysChipRecord.getBefore().add(change));
                    sysChipRecord.setChangeTh(BigDecimal.ZERO);
                    sysChipRecord.setAfterTh(BigDecimal.ZERO);
                } else {
                    sysChipRecord.setChange(BigDecimal.ZERO);
                    sysChipRecord.setAfter(BigDecimal.ZERO);
                    sysChipRecord.setChangeTh(change.abs());
                    sysChipRecord.setAfterTh(sysChipRecord.getBefore().add(change));
                }
                if (!StringUtils.isNull(sysChipRecord)) {
                    sysChipRecordMapper.updateChipRecord(sysChipRecord);
                } else {
                    sysChipRecordMapper.addChipRecord(sysChipRecord);
                }
            }

            //生成注单修改记录
            sysBetUpdateRecordService.saveResultRecord(sysBet, gameResult.getGameResult(), betId, sysBetInfos, newWin[0]);
            //修改注单
            sysBet.setUpdateBy(sysGameResult.getUpdateBy());
            betMapper.updateBet(sysBet);
        });

        sysGameResultMapper.updateGameResult(sysGameResult);
    }

    @Transactional
    public void saveBet(SysTableManagement sysTableManagement, String gameResult, JSONArray bets) {
        final BigDecimal[] tableChip = {BigDecimal.ZERO};
        final BigDecimal[] tableCash = {BigDecimal.ZERO};
        final BigDecimal[] tableInsurance = {BigDecimal.ZERO};

        final BigDecimal[] tableChipTh = {BigDecimal.ZERO};
        final BigDecimal[] tableCashTh = {BigDecimal.ZERO};
        final BigDecimal[] tableInsuranceTh = {BigDecimal.ZERO};
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
            if (gameResult == null) {
                if (bet.getBigDecimal("输") == null) {
                    sysBet.setGameResult("赢");
                } else {
                    sysBet.setGameResult("输");
                }
            }
            betMapper.saveBet(sysBet);

            Map map = betUtilService.getBetInfos(bet, sysBet, sysTableManagement.getGameId());

            //桌台 累计
            if (sysBet.getType() == 0 || sysBet.getType() == 1) {
                tableChip[0] = tableChip[0].add((BigDecimal) map.get("tableChip"));
                tableCash[0] = tableCash[0].add((BigDecimal) map.get("tableCash"));
                tableInsurance[0] = tableInsurance[0].add((BigDecimal) map.get("tableInsurance"));
            } else {
                tableChipTh[0] = tableChipTh[0].add((BigDecimal) map.get("tableChip"));
                tableCashTh[0] = tableCashTh[0].add((BigDecimal) map.get("tableCash"));
                tableInsuranceTh[0] = tableInsuranceTh[0].add((BigDecimal) map.get("tableInsurance"));
            }

            //添加 注单明细
            List list = (List) map.get("list");
            if (list.size() > 0) {
                betMapper.saveBetInfos(list);
            }

            BigDecimal membersChip = (BigDecimal) map.get("membersChip");
            if (membersChip.compareTo(BigDecimal.ZERO) != 0) {
                //生成 筹码帐变记录
                SysMembers sysMembers = sysMembersMapper.selectmembersByCard(sysBet.getCard());
                SysChipRecord sysChipRecord;
                if (sysBet.getType() == 0 || sysBet.getType() == 1) {
                    sysChipRecord = new SysChipRecord(sysBet.getCard(), sysMembers.getChip(), membersChip.abs(), sysMembers.getChip().add(membersChip)
                            , BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, sysBet.getBetId());
                    //修改会员现有筹码
                    sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip, BigDecimal.ZERO);
                } else {
                    sysChipRecord = new SysChipRecord(sysBet.getCard(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                            sysMembers.getChip(), membersChip.abs(), sysMembers.getChip().add(membersChip), sysBet.getBetId());
                    //修改会员现有筹码
                    sysMembersMapper.updateMembersChip(sysBet.getCard(), BigDecimal.ZERO, membersChip);
                }
                if (membersChip.compareTo(new BigDecimal(0)) > 0) {//赢
                    sysChipRecord.setType(ChipChangeEnum.WIN_CHIP.getCode());
                } else {
                    sysChipRecord.setType(ChipChangeEnum.LOSE_CHIP.getCode());
                }
                sysChipRecord.setCreateBy(sysTableManagement.getCreateBy());
                sysChipRecordMapper.addChipRecord(sysChipRecord);
            }
            //计算打码量
            BigDecimal water = (BigDecimal) map.get("water");
            if (water.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal waterAmount = (BigDecimal) map.get("waterAmount");
                if (sysBet.getType() == 0 || sysBet.getType() == 1) {
                    sysWaterMapper.saveMembersWater(sysBet.getCard(), water, waterAmount, BigDecimal.ZERO, BigDecimal.ZERO);
                } else {
                    sysWaterMapper.saveMembersWater(sysBet.getCard(), BigDecimal.ZERO, BigDecimal.ZERO, water, waterAmount);
                }
            }
        });

        //修改桌台累计
        if (tableChip[0].compareTo(BigDecimal.ZERO) != 0 || tableCash[0].compareTo(BigDecimal.ZERO) != 0
                || tableInsurance[0].compareTo(BigDecimal.ZERO) != 0) {
            sysTableManagementMapper.addTableMoney(new SysTableManagement(sysTableManagement.getTableId(), tableChip[0], tableCash[0], tableInsurance[0]
                    , tableChipTh[0], tableCashTh[0], tableInsuranceTh[0]));
        }
        //记录赛果
        if (gameResult != null) {
            SysGameResult sysGameResult = new SysGameResult(sysTableManagement);
            sysGameResult.setGameResult(gameResult);
            sysGameResult.setCreateBy(sysTableManagement.getCreateBy());
            sysGameResultMapper.saveGameResult(sysGameResult);
        }
        //修改局号
        sysTableManagementMapper.updateGameNum(sysTableManagement.getId());
    }

    public List<Map> getGameResults(SysTableManagement sysTableManagement) {
        return sysGameResultMapper.getGameResults(sysTableManagement);
    }

    /**
     * 开牌 计算赔码数
     */
    public BigDecimal getPayOut(JSONObject bet, String gameResult, Long gameId) {
        SysBet sysBet = new SysBet();
        sysBet.setCard(bet.getString("card"));
        sysBet.setGameResult(gameResult);
        sysBet.setType(bet.getInteger("type"));
        sysBet.setCreateBy(SecurityUtils.getUsername());
        Map map = betUtilService.getBetInfos(bet, sysBet, gameId);
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
        if (sysTableManagement.getGameId() == 2) {//龙虎 计算和钱
            BigDecimal tie = checkDecimal(betMapper.selectTie(sysTableManagement));
            map.put("tie", tie);
        }
        return map;
    }

    /**
     * 收码
     */
    public Map receiptChip(Reckon reckon, SysTableManagement sysTableManagement) {
        sysTableManagement.setBootNum(null);
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
        sysPorint.setPersonChip(checkDecimal(reckon.getChip()));
        sysPorint.setChipGap(sysPorint.getPersonChip().subtract(sysPorint.getSysChip()));
        sysPorint.setChipAdd(checkDecimal(reckon.getChipAdd()).subtract(checkDecimal(reckon.getChipSub())));

        sysPorint.setSysCash(sysTableManagement.getCashPointBase().add(sysTableManagement.getCash())
                .add(sysTableManagement.getCashAdd()).add(checkDecimal(reckon.getCashAdd()))
                .subtract(checkDecimal(reckon.getCashSub())));
        sysPorint.setPersonCash(checkDecimal(reckon.getCash()));
        sysPorint.setCashGap(sysPorint.getPersonCash().subtract(sysPorint.getSysCash()));
        sysPorint.setCashAdd(checkDecimal(reckon.getCashAdd()).subtract(checkDecimal(reckon.getCashSub())));

        sysPorint.setSysInsurance(sysTableManagement.getInsurancePointBase().add(sysTableManagement.getInsurance())
                .add(sysTableManagement.getInsuranceAdd()).add(checkDecimal(reckon.getInsuranceAdd()))
                .subtract(checkDecimal(reckon.getInsuranceSub())));
        sysPorint.setPersonInsurance(checkDecimal(reckon.getInsurance()));
        sysPorint.setInsuranceGap(sysPorint.getPersonInsurance().subtract(sysPorint.getSysInsurance()));
        sysPorint.setInsuranceAdd(checkDecimal(reckon.getInsuranceAdd()).subtract(checkDecimal(reckon.getInsuranceSub())));

        sysPorint.setWater(checkDecimal(betMapper.getWater(sysTableManagement)));
        sysPorint.setChipWin(checkDecimal(betMapper.getWinLose(sysTableManagement)));
        sysPorint.setInsuranceWin(checkDecimal(betMapper.getInsuranceWin(sysTableManagement)));
        sysPorint.setRemark(reckon.getRemark());
        porintMapper.savePorint(sysPorint);
        sysTableManagementMapper.addTableMoney(new SysTableManagement(sysTableManagement.getTableId(), 1l, chipAdd, cashAdd, insuranceAdd));
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

        sysReceipt.setChip(checkDecimal(reckon.getChip()).subtract(sysTableManagement.getChipPointBase())
                .subtract(checkDecimal(reckon.getChipAdd()))
                .add(checkDecimal(reckon.getChipSub()))
                .subtract(sysTableManagement.getChipAdd()));
        sysReceipt.setChipAdd((sysTableManagement.getChipAdd()).add(checkDecimal(reckon.getChipAdd()))
                .subtract(checkDecimal(reckon.getChipSub())));

        sysReceipt.setCash(checkDecimal(reckon.getCash()).subtract(sysTableManagement.getCashPointBase()));

        sysReceipt.setInsurance(checkDecimal(reckon.getInsurance()).subtract(sysTableManagement.getInsurancePointBase())
                .subtract(checkDecimal(reckon.getInsuranceAdd()))
                .add(checkDecimal(reckon.getInsuranceSub()))
                .subtract(sysTableManagement.getInsuranceAdd()));
        sysReceipt.setInsuranceAdd((sysTableManagement.getInsuranceAdd()).add(checkDecimal(reckon.getInsuranceAdd()))
                .subtract(checkDecimal(reckon.getInsuranceSub())));

        sysReceipt.setWater(checkDecimal(betMapper.getReceiptWater(sysTableManagement)));
        sysReceipt.setWin(checkDecimal(betMapper.getReceiptWinLose(sysTableManagement)));
        sysReceipt.setInsuranceWin(checkDecimal(betMapper.getReceiptInsuranceWin(sysTableManagement)));
        sysReceipt.setRemark(reckon.getRemark());
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
        //判断赛果是否修改
        SysGameResult sysGameResult = sysGameResultMapper.selectGameResult(betRepair);
        if (sysGameResult != null && !betRepair.getGameResult().equals(sysGameResult.getGameResult())) {
            sysGameResult.setGameResult(betRepair.getGameResult());
            updateGameResult(sysGameResult, null);
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
        Map map = betUtilService.getBetInfos(bet, sysBet, betRepair.getGameId());

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
            BigDecimal chip = BigDecimal.ZERO;//sysMembersMapper.selectMembersChip(sysBet.getCard());
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
            sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip, BigDecimal.ZERO);
        }
        //计算打码量
        BigDecimal water = (BigDecimal) map.get("water");
        if (water.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal waterAmount = (BigDecimal) map.get("waterAmount");
            sysWaterMapper.saveMembersWater(sysBet.getCard(), water, waterAmount,BigDecimal.ZERO,BigDecimal.ZERO);
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

    /**
     * 注单修改
     */
    public void updateBet(BetUpdate betUpdate) {
        //判断赛果是否修改
        SysGameResult sysGameResult = sysGameResultMapper.selectGameResultByBetId(betUpdate.getBetId());
        String oldGameResult = sysGameResult != null ? sysGameResult.getGameResult() : "";
        if (sysGameResult != null && !betUpdate.getGameResult().equals(sysGameResult.getGameResult())) {
            oldGameResult = sysGameResult.getGameResult();
            sysGameResult.setGameResult(betUpdate.getGameResult());
            updateGameResult(sysGameResult, betUpdate.getBetId());
        }

        List<SysBetInfo> list = betMapper.getBetInfos(betUpdate.getBetId());

        BigDecimal[] tableChip = {BigDecimal.ZERO};
        BigDecimal[] tableCash = {BigDecimal.ZERO};
        BigDecimal[] tableInsurance = {BigDecimal.ZERO};
        BigDecimal[] water = {BigDecimal.ZERO};
        list.forEach(oldBetInfo -> {
            //修改 洗码
            if (oldBetInfo.getWater().compareTo(BigDecimal.ZERO) != 0) {
                sysWaterMapper.saveMembersWater(oldBetInfo.getCard(), BigDecimal.ZERO.subtract(oldBetInfo.getWater())
                        , BigDecimal.ZERO.subtract(oldBetInfo.getWaterAmount()),BigDecimal.ZERO,BigDecimal.ZERO);
                water[0] = water[0].add(oldBetInfo.getWater());
            }
            //修改会员现有筹码
            if (oldBetInfo.getType() != 1 && oldBetInfo.getWinLose().compareTo(BigDecimal.ZERO) != 0) {
                sysMembersMapper.updateMembersChip(oldBetInfo.getCard(), BigDecimal.ZERO.subtract(oldBetInfo.getWinLose()), BigDecimal.ZERO);
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

        SysBet sysBet = betMapper.getBet(betUpdate.getBetId());
        sysBet.setUpdateBy(betUpdate.getUpdateBy());
        sysBet.setCard(betUpdate.getCard());
        sysBet.setType(betUpdate.getType());
        sysBet.setGameResult(betUpdate.getGameResult());

        JSONObject jsonObject = JSON.parseObject(betUpdate.getOption());

        //注单明细
        Map map = betUtilService.getBetInfos(jsonObject, sysBet, sysBet.getGameId());
        List<SysBetInfo> betInfos = (List) map.get("list");

        betMapper.deleteBetInfo(betUpdate.getBetId());
        betMapper.saveBetInfos(betInfos);

        BigDecimal membersChip = (BigDecimal) map.get("membersChip");
        if (membersChip.compareTo(BigDecimal.ZERO) != 0) {
            //生成 筹码帐变记录
            BigDecimal chip = BigDecimal.ZERO;// sysMembersMapper.selectMembersChip(sysBet.getCard());
            SysChipRecord sysChipRecord = new SysChipRecord(sysBet.getCard(), chip, membersChip.abs(), chip.add(membersChip), null, null, null, sysBet.getBetId());
            if (membersChip.compareTo(new BigDecimal(0)) > 0) {//赢
                sysChipRecord.setType(ChipChangeEnum.WIN_CHIP.getCode());
            } else {
                sysChipRecord.setType(ChipChangeEnum.LOSE_CHIP.getCode());
            }
            sysChipRecord.setCreateBy(sysBet.getCreateBy());
            sysChipRecordMapper.addChipRecord(sysChipRecord);
            //修改会员现有筹码
            sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip, BigDecimal.ZERO);
        }
        //计算打码量
        BigDecimal newWater = (BigDecimal) map.get("water");
        if (newWater.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal waterAmount = (BigDecimal) map.get("waterAmount");
            sysWaterMapper.saveMembersWater(sysBet.getCard(), newWater, waterAmount,BigDecimal.ZERO,BigDecimal.ZERO);
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
        sysBetUpdateRecordService.saveBetRecord(betUpdate, list, betInfos, oldGameResult);
        betMapper.updateBet(sysBet);
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

    private BigDecimal checkDecimal(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal : BigDecimal.ZERO;
    }
}
