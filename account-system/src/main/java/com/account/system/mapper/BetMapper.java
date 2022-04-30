package com.account.system.mapper;

import com.account.system.domain.*;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.search.ReportSearch;
import com.account.system.domain.search.WinLoseReportSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 注单
 * @author hope
 * @since 2022/4/22
 */
public interface BetMapper {

    void saveBet(SysBet sysBet);

    void saveBetInfos(List<SysBetInfo> list);

    List<BetInfoVo> selectBetInfoList(BetSearch betSearch);

    List<BetInfoOptionVo> selectBetOptionList(@Param("betIds") List<Long> betIds);

    Map selectBetTotal(BetSearch betSearch);

    @Select("select SUM(water) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and version = #{version} ")
    BigDecimal getWater(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and type != 2 and version = #{version}")
    BigDecimal getWinLose(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and type = 2 and version = #{version}")
    BigDecimal getInsuranceWin(SysTableManagement sysTableManagement);

    @Select("select SUM(water) from sys_bet_info where table_id = #{tableId} and version = #{version} ")
    BigDecimal getReceiptWater(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and type != 2 and version = #{version}")
    BigDecimal getReceiptWinLose(SysTableManagement sysTableManagement);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and type = 2 and version = #{version}")
    BigDecimal getReceiptInsuranceWin(SysTableManagement sysTableManagement);

    @Select("select bet_id betId,card,bet_option betOption,type,bet_money betMoney,water,water_amount waterAmount from sys_bet_info " +
            "where bet_id = #{betId}")
    List<SysBetInfo> getBets(@Param("betId") Long betId);

    @Update("update sys_bet set game_result = #{gameResult},type = #{type},update_time = sysdate(),update_by = #{updateBy} where bet_id = #{betId}")
    void updateBet(SysBet sysBet);

    void updateBetInfos(SysBetInfo betInfos);

    void saveReceipt(SysReceipt sysReceipt);

    List<Map> selectDailyReportList(ReportSearch reportSearch);

    Map selectDailyReportTotal(ReportSearch reportSearch);

    @Select("select id from sys_porint where table_id = #{tableId} and boot_num = #{bootNum} and version = #{version} limit 0,1")
    SysPorint getPorint(@Param("tableId") Long tableId, @Param("bootNum") Long bootNum, @Param("version") Long version);

    @Select("select id from sys_receipt where table_id = #{tableId} and version = #{version} limit 0,1")
    SysReceipt getReceipt(@Param("tableId") Long tableId, @Param("version") Long version);

    @Select("select bet_id betId,table_id tableId,version,boot_num bootNum,game_num gameNum, game_result gameResult,version,game_id gameId from sys_bet_info " +
            "where table_id=#{tableId} and version=#{version} and boot_num = #{bootNum} and game_num = #{gameNum}" +
            " order by create_time desc limit 0,1")
    List<SysBetInfo> getBetsByResult(SysGameResult gameResult);
    @Update("update sys_porint set sys_chip = sys_chip + #{chip},chip_gap = chip_gap + #{chip}," +
            " sys_insurance = sys_insurance + #{insurance},insurance_gap = insurance_gap + #{insurance}," +
            " chip_win = chip_win - #{cash},insurance_win = insurance_win - #{insurance}," +
            " water = water + #{water},update_time = sysdate()" +
            " where id = #{id}")
    void updatePorint(@Param("id") int id, @Param("chip") BigDecimal tableChip,
                      @Param("cash") BigDecimal tableCash, @Param("insurance") BigDecimal tableInsurance, @Param("water") BigDecimal water);

    @Update("update sys_receipt set chip = chip + #{chip},cash = cash + #{cash}," +
            " insurance = insurance + #{insurance},win = win - #{cash}," +
            " insurance_win = insurance_win - #{insurance},water = water + #{water},update_time = sysdate()" +
            " where id = #{id}")
    void updateReceipt(@Param("id") int id, @Param("chip") BigDecimal tableChip,
                       @Param("cash") BigDecimal tableCash, @Param("insurance") BigDecimal tableInsurance, @Param("water") BigDecimal water);


    List<Map> selectWinLoseList(WinLoseReportSearch reportSearch);

    List<Map> selectTablePlumbingList(@Param("startTime") String startTime,@Param("endTime") String endTime);

    Map selectTablePlumbingTotal(@Param("startTime") String startTime,@Param("endTime") String endTime);
}
