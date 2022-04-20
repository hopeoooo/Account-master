package com.account.system.service;

import com.account.common.core.domain.entity.SysMenu;
import com.account.common.core.domain.entity.SysRole;
import com.account.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * @author hope
 * @since 2022/4/20
 */
public interface SysRoleService {

    List<SysRole> selectRoleList();

    int addRole(SysRole sysRole);

    Set<String> selectMenuPermsByUserId(Long userId);

    Set<String> selectRolePermissionByUserId(Long userId);

    List<SysMenu> selectMenuTreeByUserId(Long userId);

    List<RouterVo> buildMenus(List<SysMenu> menus);
}
