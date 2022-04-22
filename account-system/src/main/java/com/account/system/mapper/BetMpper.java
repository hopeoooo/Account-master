package com.account.system.mapper;

import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysTableManagement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author hope
 * @since 2022/4/22
 */
public interface BetMpper {


    @Select("select id, table_id tableId,game_id gameId,chip_point_base chipPointBase,cash_point_base cashPointBase " +
            ",insurance_point_base insurancePointBase,boot_num bootNum" +
            ",game_num gameNum,chip,cash from sys_table_management where ip = #{ip} limit 0,1")
    SysTableManagement getTableByIp(@Param("ip") String ip);

    @Insert("insert into sys_game_result (game_id,table_id,boot_num,game_num,game_result,create_time,create_by) " +
            "values (#{gameId},#{tableId},#{bootNum},#{gameNum},#{gameResult},sysdate(),#{createBy}) ")
    void saveGameResult(SysGameResult sysGameResult);

    @Update("update sys_table_management set game_num=game_num+1 where id = #{id}")
    void updateGameNum(@Param("id") Long id);
}
