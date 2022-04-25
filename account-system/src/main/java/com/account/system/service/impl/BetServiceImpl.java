package com.account.system.service.impl;

import com.account.common.enums.ChipChangeEnum;
import com.account.common.utils.SecurityUtils;
import com.account.system.domain.*;
import com.account.system.mapper.BetMpper;
import com.account.system.mapper.SysChipRecordMapper;
import com.account.system.mapper.SysMembersMapper;
import com.account.system.mapper.SysOddsConfigureMapper;
import com.account.system.service.BetService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hope
 * @since 2022/4/22
 */
@Service
public class BetServiceImpl implements BetService {

    @Autowired
    BetMpper betMpper;

    @Autowired
    SysMembersMapper sysMembersMapper;

    @Autowired
    SysChipRecordMapper sysChipRecordMapper;

    @Autowired
    private SysOddsConfigureMapper oddsConfigureMapper;

    @Override
    public SysTableManagement getTableByIp(String ip) {
        return betMpper.getTableByIp(ip);
    }

    @Override
    public void saveGameResult(SysGameResult sysGameResult) {
        betMpper.saveGameResult(sysGameResult);
    }

    @Override
    public void updateGameNum(Long id) {
        betMpper.updateGameNum(id);
    }

    @Override
    public BigDecimal selectMembersChip(String card) {
        return betMpper.selectMembersChip(card);
    }

    @Override
    public String selectGameResult(SysTableManagement sysTableManagement) {
        return betMpper.selectGameResult(sysTableManagement);
    }

    @Override
    @Transactional
    public void saveBet(SysTableManagement sysTableManagement, String gameResult, JSONArray bets) {
        final BigDecimal[] tableChip = {BigDecimal.ZERO};
        final BigDecimal[] tableCash = {BigDecimal.ZERO};
        bets.forEach(b -> {
            //添加注单
            JSONObject bet = (JSONObject) b;
            SysBet sysBet = new SysBet();
            sysBet.setCard(bet.getString("card"));
            sysBet.setGameId(sysTableManagement.getGameId());
            sysBet.setTableId(sysTableManagement.getTableId());
            sysBet.setBootNum(sysTableManagement.getBootNum());
            sysBet.setGameNum(sysTableManagement.getGameNum());
            sysBet.setGameResult(gameResult);
            sysBet.setType(bet.getInteger("type"));
            sysBet.setCreateBy(SecurityUtils.getUsername());
            betMpper.saveBet(sysBet);

            Map map = getBetInfos(bet, sysBet, tableChip[0], tableCash[0]);

            //桌台 累计
            tableChip[0] = tableChip[0].add((BigDecimal) map.get("tableChip"));
            tableCash[0] = tableCash[0].add((BigDecimal) map.get("tableCash"));

            //添加 注单明细
            List list = (List) map.get("list");
            if (list.size() > 0) {
                betMpper.saveBetInfos(list);
            }

            BigDecimal membersChip = (BigDecimal) map.get("membersChip");
            if (membersChip.compareTo(BigDecimal.ZERO) != 0) {
                //生成 筹码帐变记录
                BigDecimal chip = betMpper.selectMembersChip(sysBet.getCard());
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
                sysChipRecordMapper.addChipRecord(sysChipRecord);
                //修改会员现有筹码
                sysMembersMapper.updateMembersChip(sysBet.getCard(), membersChip);
            }
        });
        if (tableChip[0].compareTo(BigDecimal.ZERO) != 0 || tableCash[0].compareTo(BigDecimal.ZERO) != 0) {
            //修改 桌台 累计
            betMpper.updateTableManagement(sysTableManagement.getId(), tableChip[0], tableCash[0]);
        }

    }

    @Override
    public List<Map> getGameResults(SysTableManagement sysTableManagement) {
        return betMpper.getGameResults(sysTableManagement);
    }

    @Override
    public BigDecimal getPayOut(JSONObject bet, String gameResult) {
        SysBet sysBet = new SysBet();
        sysBet.setCard(bet.getString("card"));
        sysBet.setGameResult(gameResult);
        sysBet.setType(bet.getInteger("type"));
        sysBet.setCreateBy(SecurityUtils.getUsername());
        Map map = getBetInfos(bet, sysBet, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal payout = (BigDecimal) map.get("tableChip");
        if (payout.compareTo(BigDecimal.ZERO) != 0) {
            return payout;
        } else {
            payout = (BigDecimal) map.get("tableCash");
        }
        return payout;
    }

    /**
     * 1：闲 4：庄 7：和 || 5：闲对 8：庄对 || 9：大 6：小 || 0: 闲保险 3:庄保险
     */
    private Map getBetInfos(JSONObject bet, SysBet sysBet, BigDecimal tableChip, BigDecimal tableCash) {
        Map map = new HashMap();
        List<SysBetInfo> list = new ArrayList<>();//注单明细
        BigDecimal membersChip = BigDecimal.ZERO;//会员总筹码输赢

        boolean isChip = 0 == sysBet.getType(); //是否为筹码下注
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
                if ("1".equals(option) || "4".equals(option)) {//庄 闲
                    if (gameResult.contains(option)) {
                        sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])));
                    } else if (gameResult.contains(betOption[3])) {
                        sysBetInfo.setWinLose(BigDecimal.ZERO);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                    }
                } else if ("0".equals(option)) { //闲保险
                    if (gameResult.contains("4")) {
                        sysBetInfo.setWinLose(amount);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                    }
                } else if ("3".equals(option)) { //庄保险
                    if (gameResult.contains("1")) {
                        sysBetInfo.setWinLose(amount);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                    }
                } else {
                    if (gameResult.contains(option)) {
                        sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])));
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                    }
                }
                list.add(sysBetInfo);
                if (isChip) {
                    membersChip = membersChip.add(sysBetInfo.getWinLose());
                    tableChip = tableChip.subtract(sysBetInfo.getWinLose());
                } else {
                    tableCash = tableCash.subtract(sysBetInfo.getWinLose());
                }
            }
        }

        //3:庄保险
        BigDecimal banker = bet.getBigDecimal("3");
        if (banker != null) {
            SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet), SysBetInfo.class);
            sysBetInfo.setBetOption("3");
            sysBetInfo.setBetMoney(banker);
            if (gameResult.contains("1")) {
                sysBetInfo.setWinLose(banker);
            } else {
                sysBetInfo.setWinLose(new BigDecimal(0).subtract(banker));
            }
            list.add(sysBetInfo);
            if (isChip) {
                membersChip = membersChip.add(sysBetInfo.getWinLose());
                tableChip = tableChip.subtract(sysBetInfo.getWinLose());
            } else {
                tableCash = tableCash.subtract(sysBetInfo.getWinLose());
            }
        }

        //0: 闲保险
        BigDecimal player = bet.getBigDecimal("0");
        if (player != null) {
            SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet), SysBetInfo.class);
            sysBetInfo.setBetOption("0");
            sysBetInfo.setBetMoney(player);
            if (sysBet.getGameResult().contains("4")) {
                sysBetInfo.setWinLose(player);
            } else {
                sysBetInfo.setWinLose(new BigDecimal(0).subtract(player));
            }
            list.add(sysBetInfo);
            if (isChip) {
                membersChip = membersChip.add(sysBetInfo.getWinLose());
                tableChip = tableChip.subtract(sysBetInfo.getWinLose());
            } else {
                tableCash = tableCash.subtract(sysBetInfo.getWinLose());
            }
        }

        map.put("list", list);
        map.put("membersChip", membersChip);
        map.put("tableChip", tableChip);
        map.put("tableCash", tableCash);
        return map;
    }
}
