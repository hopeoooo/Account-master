package com.account.system.service;

import com.account.system.domain.*;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.search.ReportSearch;
import com.account.system.domain.search.WinLoseReportSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
import com.account.system.domain.vo.DailyReportVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author hope
 * @since 2022/4/22
 */
public interface BetService {

    SysTableManagement getTableByIp(String ip, Long gameId);

    void saveBet(SysTableManagement sysTableManagement, String gameResult, JSONArray bets,String dealer);

    List<Map> getGameResults(SysTableManagement sysTableManagement);

    BigDecimal getPayOut(JSONObject bet, String gameResult, Long gameId);

    Map pointChip(Reckon reckon, SysTableManagement sysTableManagement);

    Map receiptChip(Reckon reckon, SysTableManagement sysTableManagement);

    List<BetInfoVo> selectBetInfoList(BetSearch betSearch);

    Map<Long, List<BetInfoOptionVo>> selectBetOptionList(List<Long> betIds);

    Map selectBetTotal(BetSearch betSearch);

    void editChip(Reckon reckon, SysTableManagement sysTableManagement);

    void receiptEditChip(Reckon reckon, SysTableManagement sysTableManagement);

    void updateGameResult(SysGameResult sysGameResult, Long betId);

    /**
     * 查询客户日报表列表
     *
     * @return
     */
    List<Map> selectDailyReportList(ReportSearch reportSearch);

    Map selectDailyReportTotal(ReportSearch reportSearch);

    void repairBet(BetRepair betRepair);

    void updateBet(BetUpdate betUpdate);

    /**
     * 查询输赢报表
     *
     * @param reportSearch
     * @return
     */
    List<Map> selectWinLoseList(WinLoseReportSearch reportSearch);

    Map selectWinLoseTotal(WinLoseReportSearch reportSearch) ;

    /**
     * 台面上下水报表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> selectTablePlumbingList(String startTime, String endTime,String timeType);

    Map selectTablePlumbingTotal(String startTime, String endTime,String timeType);

    void nextGameNum(SysTableManagement sysTableManagement);
}
