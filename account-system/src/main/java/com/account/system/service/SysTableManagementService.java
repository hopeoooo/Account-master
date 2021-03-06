package com.account.system.service;

import com.account.system.domain.SysTableManagement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 桌台设置服务层
 */
public interface SysTableManagementService
{
    List<SysTableManagement> selectTableList(SysTableManagement sysTableManagement);

    Map selectTableTotal(SysTableManagement sysTableManagement);

    SysTableManagement selectTableInfo(Long tableId,String ip,Long id);

    int insertTable(SysTableManagement sysTableManagement);

    int updateTable(SysTableManagement sysTableManagement);

    int deleteUserByIds(Long id);

    boolean pingIp(String ip) throws IOException;

    List<Map> selectTableIdInfo(SysTableManagement sysTableManagement);
}
