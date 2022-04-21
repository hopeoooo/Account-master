package com.account.system.service.impl;

import com.account.common.core.domain.entity.SysUser;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysUserSearch;
import com.account.system.mapper.SysUserMapper;
import com.account.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author hope
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;


    @Override
    public List<SysUser> selectUserList(SysUserSearch user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 重置用户密码
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    @Override
    public void addUser(SysUser sysUser) {
        sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        userMapper.addUser(sysUser);
    }

    @Override
    public void editUser(SysUser sysUser) {
        if(!StringUtils.isEmpty(sysUser.getPassword())){
            sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        }
        userMapper.editUser(sysUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }
}
