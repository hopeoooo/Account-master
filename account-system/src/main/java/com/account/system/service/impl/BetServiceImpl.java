package com.account.system.service.impl;

import com.account.common.utils.SecurityUtils;
import com.account.system.domain.*;
import com.account.system.mapper.BetMpper;
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
import java.util.List;

/**
 * @author hope
 * @since 2022/4/22
 */
@Service
public class BetServiceImpl implements BetService {

    @Autowired
    BetMpper betMpper;

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
        bets.forEach(b -> {
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
            List<SysBetInfo> list = getBetInfos(bet, sysBet);
            if (list.size() > 0) {
                betMpper.saveBetInfos(list);
            }
        });

    }

    //计算注单
    private List<SysBetInfo> getBetInfos(JSONObject bet, SysBet sysBet) {
        List<SysBetInfo> list = new ArrayList<>();

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
                SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet),SysBetInfo.class);
                sysBetInfo.setBetOption(betOption[i]);
                sysBetInfo.setBetMoney(amount);
                if (sysBet.getGameResult().contains(betOption[i])) {
                    sysBetInfo.setWinLose(amount.multiply(new BigDecimal(odds[i])));
                } else {
                    sysBetInfo.setWinLose(new BigDecimal(0).subtract(amount));
                }
                list.add(sysBetInfo);
            }
        }
        for (int i = odds.length; i < betOption.length; i++) {
            BigDecimal amount = bet.getBigDecimal(betOption[i]);
            if (amount != null) {
                SysBetInfo sysBetInfo = JSON.parseObject(JSONObject.toJSONString(sysBet),SysBetInfo.class);
                sysBetInfo.setBetOption(betOption[i]);
                sysBetInfo.setBetMoney(amount);
                sysBetInfo.setWinLose(amount);
                list.add(sysBetInfo);
            }
        }

        return list;
    }
}
