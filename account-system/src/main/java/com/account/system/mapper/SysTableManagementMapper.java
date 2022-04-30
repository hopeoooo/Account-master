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

    SysTableManagement selectTableInfo(@Param("tableId")Long tableId,@Param("ip")String ip,@Param("id")Long id);

    int insertTable(SysTableManagement sysTableManagement);

    int updateTable(SysTableManagement sysTableManagement);

    int deleteUserByIds(Long id);

    int addTableMoney(SysTableManagement sysTableManagement);

    void resetTableMoney(@Param("id") Long id);

    @Update("update sys_table_management set game_num=game_num+1 where id = #{id}")
    void updateGameNum(@Param("id") Long id);

    @Select("select id, table_id tableId,game_id gameId,chip_point_base chipPointBase,cash_point_base cashPointBase " +
            ",insurance_point_base insurancePointBase,boot_num bootNum" +
            ",game_num gameNum,chip,cash,insurance,chip_add chipAdd,insurance_add insuranceAdd ,version " +
            "from sys_table_management where ip = #{ip} and is_delete = 0 limit 0,1")
    SysTableManagement getTableByIp(@Param("ip") String ip);

    List<Map> selectTableIdInfo();
}
