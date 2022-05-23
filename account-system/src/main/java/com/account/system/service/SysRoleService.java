package com.account.system.service;

import com.account.common.core.domain.TreeSelect;
import com.account.common.core.domain.entity.SysMenu;
import com.account.common.core.domain.entity.SysRole;
import com.account.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hope
 * @since 2022/4/20
 */
public interface SysRoleService {

    List<SysRole> selectRoleList();

    SysRole selectRoleByName(String roleName);

    int addRole(SysRole sysRole);

    Set<String> selectMenuPermsByUserId(Long userId);

    List<SysMenu> selectMenuTreeByUserId(Long userId);

    List<RouterVo> buildMenus(List<SysMenu> menus);

    List selectUserByRoleId(Long roleId);

    int deleteRole(Long roleId);

    List<TreeSelect> buildMenuTreeSelect(String language);

    int editRole(SysRole sysRole);

    List<Long> selectMenuListByRoleId(Long roleId);
}
