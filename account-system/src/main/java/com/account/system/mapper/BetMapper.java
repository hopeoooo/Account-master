package com.account.system.mapper;

import com.account.system.domain.*;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.search.ReportSearch;
import com.account.system.domain.search.WinLoseReportSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 注单
 *
 * @author hope
 * @since 2022/4/22
 */
public interface BetMapper {

    void saveBet(SysBet sysBet);

    void saveBetInfos(List<SysBetInfo> list);

    List<BetInfoVo> selectBetInfoList(BetSearch betSearch);

    List<BetInfoOptionVo> selectBetOptionList(@Param("betIds") List<Long> betIds);

    Map selectBetTotal(BetSearch betSearch);

    @Select("select SUM(water) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and version = #{version} and type in(0,1,4)")
    BigDecimal getWater(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and type != 2 and version = #{version} and type in(0,1,4)")
    BigDecimal getWinLose(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and type = 2 and version = #{version} and type in(0,1,4)")
    BigDecimal getInsuranceWin(SysTableManagement sysTableManagement);

    @Select("select SUM(water) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and version = #{version} and type in(2,3,5)")
    BigDecimal getWaterTh(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and type != 2 and version = #{version} and type in(2,3,5)")
    BigDecimal getWinLoseTh(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and type = 2 and version = #{version} and type in(2,3,5)")
    BigDecimal getInsuranceWinTh(SysTableManagement sysTableManagement);

    @Select("select SUM(water) from sys_bet_info where table_id = #{tableId} and version = #{version} and type in(0,1,4) ")
    BigDecimal getReceiptWater(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and type != 2 and version = #{version} and type in(0,1,4)")
    BigDecimal getReceiptWinLose(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and type = 2 and version = #{version} and type in(0,1,4)")
    BigDecimal getReceiptInsuranceWin(SysTableManagement sysTableManagement);

    @Select("select SUM(water) from sys_bet_info where table_id = #{tableId} and version = #{version} and type in(2,3,5) ")
    BigDecimal getReceiptWaterTh(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and type != 2 and version = #{version} and type in(2,3,5)")
    BigDecimal getReceiptWinLoseTh(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and type = 2 and version = #{version} and type in(2,3,5)")
    BigDecimal getReceiptInsuranceWinTh(SysTableManagement sysTableManagement);

    List<SysBetInfo> getBetInfos(@Param("betId") Long betId);

    SysBet getBet(@Param("betId") Long betId);

    @Update("update sys_bet set game_result = #{gameResult},type = #{type},card = #{card},update_time = sysdate(),update_by = #{updateBy} where bet_id = #{betId}")
    void updateBet(SysBet sysBet);

    void updateBetInfos(SysBetInfo betInfos);

    void saveReceipt(SysReceipt sysReceipt);

    List<Map> selectDailyReportList(ReportSearch reportSearch);

    Map selectDailyReportTotal(ReportSearch reportSearch);

    List<SysBet> getBetsByResult(SysGameResult gameResult);

    List<Map> selectWinLoseList(WinLoseReportSearch reportSearch);

    List<Map> selectTablePlumbingList(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Map selectTablePlumbingTotal(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Delete("delete from sys_bet_info where bet_id = #{betId}")
    void deleteBetInfo(@Param("betId") Long betId);

    @Select("<script>select sum(tie) from sys_bet_info where table_id = #{tableId} and version = #{version} and type in(0,1,4) " +
            "<if test='bootNum!=null'> and boot_num = #{bootNum} </if> </script>")
    BigDecimal selectTie(SysTableManagement sysTableManagement);

    @Select("<script>select sum(tie) from sys_bet_info where table_id = #{tableId} and version = #{version} and type in(2,3,5) " +
            "<if test='bootNum!=null'> and boot_num = #{bootNum} </if> </script>")
    BigDecimal selectTieTh(SysTableManagement sysTableManagement);
}
