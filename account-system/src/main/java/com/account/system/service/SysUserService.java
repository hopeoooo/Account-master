package com.account.system.service;

import java.util.List;

import com.account.common.core.domain.entity.SysUser;

/**
 * 用户 业务层
 *
 * @author hope
 */
public interface SysUserService {
    /**
     * 根据条件分页查询用户列表
     */
     List<SysUser> selectUserList(SysUser user);

    /**
     * 通过用户名查询用户
     */
     SysUser selectUserByUserName(String userName);

    /**
     * 重置用户密码
     */
     int resetUserPwd(String userName, String password);


}
