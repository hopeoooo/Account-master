package com.account.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.account.common.core.domain.entity.SysUser;

/**
 * 用户表 数据层
 *
 * @author hope
 */
public interface SysUserMapper {
    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
     List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
     SysUser selectUserByUserName(String userName);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
     int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

}
