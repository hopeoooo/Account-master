package com.account.system.mapper;

import com.account.system.domain.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
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
    SysGameResult getGameResultInfo(SysGameResult sysGameResult);

    @Select("select id, table_id tableId,version,boot_num bootNum,game_num gameNum, game_result gameResult,version from sys_game_result " +
            "where game_id = #{gameId} and table_id = #{tableId} and boot_num = #{bootNum} and version = #{version}")
    List<Map> getGameResults(SysTableManagement sysTableManagement);

    @Update("update sys_game_result set game_result = #{gameResult},update_time = sysdate(),update_By = #{updateBy} where id = #{id}")
    void updateGameResult(SysGameResult sysGameResult);

    @Select("select game_result gameResult,id from sys_game_result where game_id = #{gameId} and table_id = #{tableId} " +
            "and boot_num = #{bootNum} and version = #{version} and game_num = #{gameNum} order by create_by desc limit 0,1")
    SysGameResult selectGameResult(BetRepair betRepair);

    @Select("select g.game_result gameResult,id " +
            " from sys_game_result g" +
            " left join sys_bet b" +
            " on g.table_id=b.table_id " +
            " and g.game_id=b.game_id " +
            " and g.boot_num=b.boot_num " +
            " and g.version=b.version " +
            " and g.game_num=b.game_num" +
            " where b.bet_id = #{betId}")
    SysGameResult selectGameResultByBetId(@Param("betId") Long betId);
}
