package com.account.system.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.account.common.core.domain.entity.SysMenu;
import com.account.common.core.domain.entity.SysRole;
import com.account.system.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Delete;
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
    SysRole selectRoleByName(@Param("roleName") String roleName);

    Set<String> selectMenuPermsByUserId(@Param("userId") Long userId);

    Set<String> selectRolePermissionByUserId(@Param("userId") Long userId);

    List<SysMenu> selectMenuTreeAll();

    List<SysMenu> selectMenuTreeByUserId(@Param("userId") Long userId);

    @Select("select user_id userId,user_name userName,nick_name nickName,phonenumber,password,sex,role_id roleId " +
            "from sys_user where role_id = #{roleId}")
    List<Map> selectUserByRoleId(@Param("roleId") Long roleId);

    @Delete("delete from sys_role where role_id = #{roleId}")
    int deleteRole(@Param("roleId") Long roleId);

    List<SysMenu> selectMenuList();

    void editRole(SysRole sysRole);

    void deleteRoleMenu(@Param("roleId") Long roleId);

    int addRoleMenu(List<SysRoleMenu> list);

    List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId);
}
