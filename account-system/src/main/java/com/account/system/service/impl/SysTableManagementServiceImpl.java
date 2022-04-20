package com.account.system.service.impl;

import com.account.system.domain.SysTableManagement;
import com.account.system.mapper.SysTableManagementMapper;
import com.account.system.service.ISysTableManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 桌台设置服务层实现
 */
@Service
public class SysTableManagementServiceImpl implements ISysTableManagementService {
    @Autowired
    private SysTableManagementMapper tableManagementMapper;

    @Override
    public  List<SysTableManagement> selectTableList(SysTableManagement sysTableManagement) {
        return tableManagementMapper.selectTableList(sysTableManagement);
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
}
