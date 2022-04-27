package com.account.system.mapper;

import com.account.system.domain.*;
import com.account.system.domain.search.BetSearch;
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
 * @author hope
 * @since 2022/4/22
 */
public interface BetMapper {


    @Select("select id, table_id tableId,game_id gameId,chip_point_base chipPointBase,cash_point_base cashPointBase " +
            ",insurance_point_base insurancePointBase,boot_num bootNum" +
            ",game_num gameNum,chip,cash,insurance,chip_add chipAdd,insurance_add insuranceAdd from sys_table_management where ip = #{ip} limit 0,1")
    SysTableManagement getTableByIp(@Param("ip") String ip);

    @Insert("insert into sys_game_result (game_id,table_id,boot_num,game_num,game_result,create_time,create_by) " +
            "values (#{gameId},#{tableId},#{bootNum},#{gameNum},#{gameResult},sysdate(),#{createBy}) ")
    void saveGameResult(SysGameResult sysGameResult);

    @Update("update sys_table_management set game_num=game_num+1 where id = #{id}")
    void updateGameNum(@Param("id") Long id);

    @Select("select chip from sys_members where card = #{card}")
    BigDecimal selectMembersChip(@Param("card") String card);

    @Select("select game_result from sys_game_result where game_id = #{gameId} " +
            "and table_id = #{tableId} and boot_num = #{bootNum} and game_num = #{gameNum} order by create_time desc limit 0,1")
    String selectGameResult(SysTableManagement sysTableManagement);

    void saveBet(SysBet sysBet);

    void saveBetInfos(List<SysBetInfo> list);

    @Select("select id, table_id tableId,boot_num bootNum,game_num gameNum, game_result gameResult from sys_game_result " +
            "where game_id = #{gameId} and table_id = #{tableId} and boot_num = #{bootNum} ")
    List<Map> getGameResults(SysTableManagement sysTableManagement);


    List<BetInfoVo> selectBetInfoList(BetSearch betSearch);

    List<BetInfoOptionVo>selectBetOptionList(@Param("betIds")List<Long> betIds);

    Map selectBetTotal(BetSearch betSearch);

    @Update("update sys_table_management set chip = chip + #{chip} ,cash = cash + #{cash},insurance = insurance + #{insurance} where id = #{id} ")
    void updateTableManagement(@Param("id") Long id, @Param("chip") BigDecimal tableChip, @Param("cash") BigDecimal tableCash, @Param("insurance") BigDecimal tableInsurance);


    @Update("update sys_table_management set game_num=0,boot_num = boot_num+1" +
            ",chip_add= chip_add+#{chipAdd},insurance_add= insurance_add+#{insuranceAdd} where id = #{id}")
    void updateAdd(@Param("id") Long id, @Param("chipAdd") BigDecimal chipAdd, @Param("insuranceAdd") BigDecimal insuranceAdd);

    @Update("update sys_table_management set chip_add= 0,insurance_add= 0,chip=0,cash=0,insurance=0 where id = #{id}")
    void receiptEditChip(@Param("id") Long id);

    void savePorint(SysPorint sysPorint);

    @Select("select SUM(water) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} ")
    BigDecimal getWater(@Param("tableId") Long tableId, @Param("bootNum") Long bootNum);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and type = 0")
    BigDecimal getChipWin(@Param("tableId") Long tableId, @Param("bootNum") Long bootNum);

    @Select("select 0-SUM(win_lose) from sys_bet_info where table_id = #{tableId} and boot_num = #{bootNum} and type = 2")
    BigDecimal getInsuranceWin(@Param("tableId") Long tableId, @Param("bootNum") Long bootNum);

}
