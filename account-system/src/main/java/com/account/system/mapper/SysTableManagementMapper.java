package com.account.system.mapper;


import com.account.system.domain.SysTableManagement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 桌台管理设置数据层
 */
public interface SysTableManagementMapper {

    List<SysTableManagement> selectTableList(SysTableManagement sysTableManagement);


    Map selectTableTotal(SysTableManagement sysTableManagement);

    SysTableManagement selectTableInfo(@Param("tableId") Long tableId, @Param("ip") String ip, @Param("id") Long id);

    int insertTable(SysTableManagement sysTableManagement);

    int updateTable(SysTableManagement sysTableManagement);

    int deleteUserByIds(Long id);

    int addTableMoney(SysTableManagement sysTableManagement);

    void resetTableMoney(@Param("id") Long id);

    @Update("update sys_table_management set game_num=game_num+1 where id = #{id}")
    void updateGameNum(@Param("id") Long id);

    @Update("update sys_table_management set game_num=game_num+1 where table_id = #{tableId} and is_delete = 0 ")
    void updateGameNumByTableId(@Param("tableId") Long tableId);

    SysTableManagement getTableByIp(@Param("ip") String ip, @Param("gameId") Long gameId);

    List<Map> selectTableIdInfo();
}
