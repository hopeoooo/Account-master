package com.account.system.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.account.common.core.domain.entity.SysMenu;
import com.account.common.core.domain.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 角色表 数据层
 *
 * @author hope
 */
public interface SysRoleMapper {

    List<SysRole> selectRoleList();

    int addRole(SysRole sysRole);

    @Select("select role_id roleId,role_name roleName,create_time createTime,remark from sys_role where role_name = #{roleName}")
    Map selectRoleByName(@Param("roleName") String roleName);

    Set<String> selectMenuPermsByUserId(@Param("userId") Long userId);

    Set<String> selectRolePermissionByUserId(@Param("userId") Long userId);

    List<SysMenu> selectMenuTreeAll();

    List<SysMenu> selectMenuTreeByUserId(@Param("userId") Long userId);
}
