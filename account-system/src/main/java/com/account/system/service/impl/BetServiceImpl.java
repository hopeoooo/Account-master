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
        BigDecimal zero = new BigDecimal(0);
        final BigDecimal[] tableChip = {zero};
        final BigDecimal[] tableCash = {zero};
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
            if (membersChip.compareTo(zero) != 0) {
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
        if (tableChip[0].compareTo(zero) != 0 || tableCash[0].compareTo(zero) != 0) {
            //修改 桌台 累计
            betMpper.updateTableManagement(sysTableManagement.getId(), tableChip[0], tableCash[0]);
        }

    }

    @Override
    public List<Map> getGameResults(SysTableManagement sysTableManagement) {
        return betMpper.getGameResults(sysTableManagement);
    }

    //计算注单
    private Map getBetInfos(JSONObject bet, SysBet sysBet, BigDecimal tableChip, BigDecimal tableCash) {
        Map map = new HashMap();
        List<SysBetInfo> list = new ArrayList<>();

        BigDecimal membersChip = new BigDecimal(0);//会员总筹码输赢
        boolean isChip = 0 == sysBet.getType();
        SysOddsConfigure sysOddsConfigure = oddsConfigureMapper.selectConfigInfo();
        // 1：闲 4：庄 7：和
        // 5：闲对 8：庄对
        // 9：大 6：小
        // 0: 闲保险 3:庄保险
        String[] betOption = {"1", "4", "7", "5", "8", "9", "6", "3", "0"};
        String[] odds = {sysOddsConfigure.getBaccaratPlayerWin(),
                sysOddsConfigure.getBaccaratBankerWin(),
                sysOddsConfigure.getBaccaratTieWin(),
                sysOddsConfigure.getBaccaratPlayerPair(),
                sysOddsConfigure.getBaccaratBankerPair(),
                sysOddsConfigure.getBaccaratLarge(),
                sysOddsConfigure.getBaccaratSmall(),
        };
        for (int i = 0; i < odds.length; i++) {
            BigDecimal amount = bet.getBigDecimal(betOption[i]);
            if (amount != null) {
                SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet), SysBetInfo.class);
                sysBetInfo.setBetOption(betOption[i]);
                sysBetInfo.setBetMoney(amount);
                if (sysBet.getGameResult().contains(betOption[i])) {
                    sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])));
                } else {
                    sysBetInfo.setWinLose(new BigDecimal(0).subtract(amount));
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
        for (int i = odds.length; i < betOption.length; i++) {
            BigDecimal amount = bet.getBigDecimal(betOption[i]);
            if (amount != null) {
                SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet), SysBetInfo.class);
                sysBetInfo.setBetOption(betOption[i]);
                sysBetInfo.setBetMoney(amount);
                sysBetInfo.setWinLose(amount);
                list.add(sysBetInfo);
                if (isChip) {
                    membersChip = membersChip.add(sysBetInfo.getWinLose());
                    tableChip = tableChip.subtract(sysBetInfo.getWinLose());
                } else {
                    tableCash = tableCash.subtract(sysBetInfo.getWinLose());
                }
            }
        }
        map.put("list", list);
        map.put("membersChip", membersChip);
        map.put("tableChip", tableChip);
        map.put("tableCash", tableCash);
        return map;
    }
}
