package com.account.system.mapper;

import com.account.system.domain.SysBetInfo;
import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysTableManagement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 赛果
 * @author hope
 * @since 2022/4/29
 */
public interface SysGameResultMapper {

    @Insert("insert into sys_game_result (game_id,table_id,version,boot_num,game_num,game_result,create_time,create_by) " +
            "values (#{gameId},#{tableId},#{version},#{bootNum},#{gameNum},#{gameResult},sysdate(),#{createBy}) ")
    void saveGameResult(SysGameResult sysGameResult);

    @Select("select id,table_id tableId,version,boot_num bootNum,game_num gameNum, game_result gameResult,version,game_id gameId from sys_game_result " +
            "where id=#{id} order by create_time desc limit 0,1")
    SysGameResult selectGameResult(SysGameResult sysGameResult);

    @Select("select id, table_id tableId,version,boot_num bootNum,game_num gameNum, game_result gameResult,version from sys_game_result " +
            "where game_id = #{gameId} and table_id = #{tableId} and boot_num = #{bootNum} and version = #{version}")
    List<Map> getGameResults(SysTableManagement sysTableManagement);


    @Update("update sys_game_result set game_result = #{gameResult},update_time = sysdate(),update_By = #{updateBy} where id = #{id}")
    void updateGameResult(SysGameResult sysGameResult);

}
