package com.account.system.mapper;


import com.account.system.domain.SysTableManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 桌台管理设置数据层
 */
public interface SysTableManagementMapper {

    List<SysTableManagement> selectTableList(SysTableManagement sysTableManagement);

    SysTableManagement selectTableInfo(@Param("tableId")Long tableId,@Param("ip")String ip,@Param("gameId")Long gameId);

     int insertTable(SysTableManagement sysTableManagement) ;

     int updateTable(SysTableManagement sysTableManagement) ;

    int deleteUserByIds(Long id);
}
