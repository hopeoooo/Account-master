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

    SysTableManagement selectTableInfo(Long tableId,String ip,Long gameId);

    int insertTable(SysTableManagement sysTableManagement);

    int updateTable(SysTableManagement sysTableManagement);

    int deleteUserByIds(Long id);

    boolean pingIp(String ip) throws IOException;
}
