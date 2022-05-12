package com.account.system.service.impl;

import com.account.system.domain.SysBet;
import com.account.system.domain.SysBetInfo;
import com.account.system.domain.SysOddsConfigure;
import com.account.system.mapper.SysOddsConfigureMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BetUtilService {

    @Autowired
    SysOddsConfigureMapper oddsConfigureMapper;

    public Map getBetInfos(JSONObject bet, SysBet sysBet, Long gameId) {
        if (gameId == 1) {
            return getBaccaratBetInfos(bet, sysBet);
        } else if (gameId == 2) {
            return getDragonTigerBetInfos(bet, sysBet);
        }
        return getOtherBetInfos(bet, sysBet);
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

        Integer type = sysBet.getType();
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
                        sysBetInfo.setWaterAmount(getBaccaratWaterAmount(amount, sysOddsConfigure, type));
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
                        sysBetInfo.setWaterAmount(getBaccaratWaterAmount(amount, sysOddsConfigure, type));
                    }
                } else if ("0".equals(option)) { //闲保险
                    if (type == 0 || type == 1) {
                        sysBetInfo.setType(4);
                    } else if (type == 2 || type == 3) {
                        sysBetInfo.setType(5);
                    }
                    if (gameResult.contains("4")) {
                        sysBetInfo.setWinLose(amount);
                    } else if (gameResult.contains("7")) {
                        sysBetInfo.setWinLose(BigDecimal.ZERO);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                    }
                    tableInsurance = tableInsurance.subtract(sysBetInfo.getWinLose());
                } else if ("3".equals(option)) { //庄保险
                    if (type == 0 || type == 1) {
                        sysBetInfo.setType(4);
                    } else if (type == 2 || type == 3) {
                        sysBetInfo.setType(5);
                    }
                    if (gameResult.contains("1")) {
                        sysBetInfo.setWinLose(amount);
                    } else if (gameResult.contains("7")) {
                        sysBetInfo.setWinLose(BigDecimal.ZERO);
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                    }
                    tableInsurance = tableInsurance.subtract(sysBetInfo.getWinLose());
                } else {
                    if (gameResult.contains(option)) {
                        sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])));
                    } else {
                        sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                        water = water.add(amount);
                        sysBetInfo.setWater(amount);
                        sysBetInfo.setWaterAmount(getBaccaratWaterAmount(amount, sysOddsConfigure, type));
                    }
                }
                list.add(sysBetInfo);
                if (type == 0 || type == 2) {
                    membersChip = membersChip.add(sysBetInfo.getWinLose());
                }
                if (sysBetInfo.getType() == 0 || sysBetInfo.getType() == 2) {
                    tableChip = tableChip.subtract(sysBetInfo.getWinLose());
                } else if (sysBetInfo.getType() == 1 || sysBetInfo.getType() == 3) {
                    tableCash = tableCash.subtract(sysBetInfo.getWinLose());
                }
            }
        }

        waterAmount = getBaccaratWaterAmount(water, sysOddsConfigure, type);

        map.put("list", list);
        map.put("membersChip", membersChip);
        map.put("tableChip", tableChip);
        map.put("tableCash", tableCash);
        map.put("tableInsurance", tableInsurance);
        map.put("water", water);
        map.put("waterAmount", waterAmount);
        return map;
    }

    /**
     * 计算龙虎 注单明细
     */
    private Map getDragonTigerBetInfos(JSONObject bet, SysBet sysBet) {
        Map map = new HashMap();
        List<SysBetInfo> list = new ArrayList<>();//注单明细
        BigDecimal membersChip = BigDecimal.ZERO;//会员总筹码输赢

        BigDecimal water = BigDecimal.ZERO;//会员洗码量
        BigDecimal waterAmount;//会员洗码费

        BigDecimal tableChip = BigDecimal.ZERO;//桌台筹码输赢
        BigDecimal tableCash = BigDecimal.ZERO;//桌台现金输赢
        BigDecimal tableInsurance = BigDecimal.ZERO;//桌台保险输赢

        Integer type = sysBet.getType();
        SysOddsConfigure sysOddsConfigure = oddsConfigureMapper.selectConfigInfo();
        String[] betOption = {"龙", "虎", "和"};
        String[] odds = {sysOddsConfigure.getDragonWin(), sysOddsConfigure.getTigerWin(), sysOddsConfigure.getTieWin(),};
        String gameResult = sysBet.getGameResult();
        for (int i = 0; i < betOption.length; i++) {
            String option = betOption[i];
            BigDecimal amount = bet.getBigDecimal(option);
            if (amount != null) {
                SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet), SysBetInfo.class);
                sysBetInfo.setBetOption(betOption[i]);
                sysBetInfo.setBetMoney(amount);
                if (gameResult.contains(option)) {
                    sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])));
                } else {
                    sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
                    water = water.add(amount);
                    sysBetInfo.setWater(amount);
                    sysBetInfo.setWaterAmount(getDragonTigerWaterAmount(amount, sysOddsConfigure, type));
                    if ("和".contains(gameResult)) sysBetInfo.setTie(amount);
                }
                list.add(sysBetInfo);
                if (type == 0 || type == 2) {
                    membersChip = membersChip.add(sysBetInfo.getWinLose());
                    tableChip = tableChip.subtract(sysBetInfo.getWinLose());
                } else {
                    tableCash = tableCash.subtract(sysBetInfo.getWinLose());
                }
            }
        }

        waterAmount = getDragonTigerWaterAmount(water, sysOddsConfigure, type);

        map.put("list", list);
        map.put("membersChip", membersChip);
        map.put("tableChip", tableChip);
        map.put("tableCash", tableCash);
        map.put("tableInsurance", tableInsurance);
        map.put("water", water);
        map.put("waterAmount", waterAmount);
        return map;
    }

    /**
     * 计算 三公 牛牛 筒子 注单明细
     */
    private Map getOtherBetInfos(JSONObject bet, SysBet sysBet) {
        Map map = new HashMap();
        List<SysBetInfo> list = new ArrayList<>();//注单明细
        BigDecimal membersChip = BigDecimal.ZERO;//会员总筹码输赢

        BigDecimal tableChip = BigDecimal.ZERO;//桌台筹码输赢
        BigDecimal tableCash = BigDecimal.ZERO;//桌台现金输赢

        boolean isChip = 0 == sysBet.getType() || 2 == sysBet.getType(); //是否为筹码下注
        SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet), SysBetInfo.class);
        BigDecimal amount = bet.getBigDecimal("输");
        if (amount != null) {
            sysBetInfo.setBetOption("-");
            sysBetInfo.setBetMoney(amount);
            sysBetInfo.setWinLose(BigDecimal.ZERO.subtract(amount));
        } else {
            amount = bet.getBigDecimal("赢");
            sysBetInfo.setBetOption("-");
            sysBetInfo.setBetMoney(amount);
            sysBetInfo.setWinLose(amount);
        }
        list.add(sysBetInfo);
        if (isChip) {
            membersChip = membersChip.add(sysBetInfo.getWinLose());
            tableChip = tableChip.subtract(sysBetInfo.getWinLose());
        } else {
            tableCash = tableCash.subtract(sysBetInfo.getWinLose());
        }
        map.put("list", list);
        map.put("membersChip", membersChip);
        map.put("tableChip", tableChip);
        map.put("tableCash", tableCash);
        map.put("tableInsurance", BigDecimal.ZERO);
        map.put("water", BigDecimal.ZERO);
        map.put("waterAmount", BigDecimal.ZERO);
        return map;
    }

    private BigDecimal getBaccaratWaterAmount(BigDecimal water, SysOddsConfigure sysOddsConfigure, Integer type) {
        BigDecimal waterAmount = BigDecimal.ZERO;
        if (type == 0) {
            waterAmount = water.multiply(sysOddsConfigure.getBaccaratRollingRatioChip()).divide(new BigDecimal(100));
        } else if (type == 1) {
            waterAmount = water.multiply(sysOddsConfigure.getBaccaratRollingRatioCash()).divide(new BigDecimal(100));
        } else if (type == 2) {
            waterAmount = water.multiply(sysOddsConfigure.getBaccaratRollingRatioChipTh()).divide(new BigDecimal(100));
        } else if (type == 3) {
            waterAmount = water.multiply(sysOddsConfigure.getBaccaratRollingRatioCashTh()).divide(new BigDecimal(100));
        }
        return waterAmount;
    }

    private BigDecimal getDragonTigerWaterAmount(BigDecimal water, SysOddsConfigure sysOddsConfigure, Integer type) {
        BigDecimal waterAmount = BigDecimal.ZERO;
        if (type == 0) {
            waterAmount = water.multiply(sysOddsConfigure.getDragonTigerRatioChip()).divide(new BigDecimal(100));
        } else if (type == 1) {
            waterAmount = water.multiply(sysOddsConfigure.getDragonTigerRatioCash()).divide(new BigDecimal(100));
        } else if (type == 2) {
            waterAmount = water.multiply(sysOddsConfigure.getDragonTigerRatioChipTh()).divide(new BigDecimal(100));
        } else if (type == 3) {
            waterAmount = water.multiply(sysOddsConfigure.getDragonTigerRatioCashTh()).divide(new BigDecimal(100));
        }
        return waterAmount;
    }
}
