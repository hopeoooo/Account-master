package com.account.system.service.impl;

import com.account.system.domain.SysMembers;
import com.account.system.domain.SysMembersSearch;
import com.account.system.mapper.SysMembersMapper;
import com.account.system.service.SysMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 参数配置 服务层实现
 *
 * @author hope
 */
@Service
public class SysMembersServiceImpl implements SysMembersService {

    @Autowired
    SysMembersMapper sysMembersMapper;
    @Override
    public List<Map> selectMembersList(SysMembersSearch sysMembers) {
        return sysMembersMapper.selectMembersList(sysMembers);
    }

    @Override
    public void addMembers(SysMembers sysMembers) {
        sysMembersMapper.addMembers(sysMembers);
    }

    @Override
    public Map selectMembersInfo(Long id) {
        return sysMembersMapper.selectMembersInfo(id);
    }

    @Override
    public void editMembers(SysMembers sysMembers) {
        sysMembersMapper.editMembers(sysMembers);
    }

}
