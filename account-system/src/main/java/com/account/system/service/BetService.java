package com.account.system.service;

import com.account.system.domain.Reckon;
import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysTableManagement;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    void saveBet(SysTableManagement sysTableManagement, String gameResult, JSONArray bets);

    List<Map> getGameResults(SysTableManagement sysTableManagement);

    BigDecimal getPayOut(JSONObject bet, String gameResult);

    Map pointChip(Reckon reckon, SysTableManagement sysTableManagement);

    Map receiptChip(Reckon reckon, SysTableManagement sysTableManagement);
}
