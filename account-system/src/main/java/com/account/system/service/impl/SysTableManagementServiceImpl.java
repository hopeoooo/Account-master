package com.account.system.service.impl;

import com.account.system.domain.SysTableManagement;
import com.account.system.mapper.SysTableManagementMapper;
import com.account.system.service.SysTableManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


/**
 * 桌台设置服务层实现
 */
@Service
public class SysTableManagementServiceImpl implements SysTableManagementService {
    @Autowired
    private SysTableManagementMapper tableManagementMapper;

    @Override
    public  List<SysTableManagement> selectTableList(SysTableManagement sysTableManagement) {
        return tableManagementMapper.selectTableList(sysTableManagement);
    }

    @Override
    public   SysTableManagement selectTableInfo(Long tableId,String ip,Long gameId){
        return tableManagementMapper.selectTableInfo(tableId,ip,gameId);
    }
    @Override
    public int insertTable(SysTableManagement sysTableManagement) {
        return tableManagementMapper.insertTable(sysTableManagement);
    }

    @Override
    public int updateTable(SysTableManagement sysTableManagement) {
        return tableManagementMapper.updateTable(sysTableManagement);
    }

    @Override
    public int deleteUserByIds(Long id){
        return tableManagementMapper.deleteUserByIds(id);
    }


    @Override
    public boolean pingIp(String ip) throws IOException {
        boolean doip = ip.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])" +
                "(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
        if (!doip){
            return false;
        }
        InetAddress address = InetAddress.getByName(ip);
        return address.isReachable(3000);
    }
}
