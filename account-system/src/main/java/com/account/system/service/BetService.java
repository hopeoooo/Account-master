package com.account.system.service;

import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysTableManagement;
import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;

/**
 * @author hope
 * @since 2022/4/22
 */
public interface BetService {

    SysTableManagement getTableByIp(String ip);

    void saveGameResult(SysGameResult sysGameResult);

    void updateGameNum(Long id);

    BigDecimal selectMembersChip(String card);

    String selectGameResult(SysTableManagement sysTableManagement);

    void saveBet(String gameResult, JSONArray bets);
}
