package com.account.system.service;

import com.account.system.domain.*;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.search.ReportSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
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

    void saveBet(SysTableManagement sysTableManagement, String gameResult, JSONArray bets);

    List<Map> getGameResults(SysTableManagement sysTableManagement);

    BigDecimal getPayOut(JSONObject bet, String gameResult);

    Map pointChip(Reckon reckon, SysTableManagement sysTableManagement);

    Map receiptChip(Reckon reckon, SysTableManagement sysTableManagement);

    List<BetInfoVo> selectBetInfoList(BetSearch betSearch);

    Map<Long, List<BetInfoOptionVo>> selectBetOptionList(List<Long> betIds);

    Map selectBetTotal(BetSearch betSearch);

    void editChip(Reckon reckon, SysTableManagement sysTableManagement);

    void receiptEditChip(Reckon reckon, SysTableManagement sysTableManagement);

    void updateGameResult(SysGameResult sysGameResult);

    /**
     * 查询客户日报表列表
     * @return
     */
    List<Map> selectDailyReportList(ReportSearch reportSearch );

    Map selectDailyReportTotal(ReportSearch reportSearch );

    void repairBet(BetRepair betRepair);

    void updateBet(BetUpdate betUpdate);
}
