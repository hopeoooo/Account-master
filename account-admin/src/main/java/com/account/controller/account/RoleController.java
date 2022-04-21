package com.account.controller.account;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.domain.entity.SysRole;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色信息
 *
 * @author hope
 */
@RestController
@RequestMapping("/account/role")
@Api(tags = "角色管理")
public class RoleController extends BaseController {

    @Autowired
    private SysRoleService roleService;


    @PreAuthorize("@ss.hasPermi('account:role:list')")
    @PostMapping("/list")
    @ApiOperation(value = "获取角色列表")
    public TableDataInfo list() {
        startPage();
        List<SysRole> list = roleService.selectRoleList();
        return getDataTable(list);
    }

    /**
     * 新增角色
     */
    @PreAuthorize("@ss.hasPermi('account:role:list')")
    @PostMapping("/add")
    @ApiOperation(value = "新增角色")
    public AjaxResult add(SysRole sysRole) {
        if(StringUtils.isNotEmpty(roleService.selectRoleByName(sysRole.getRoleName()))){
            return AjaxResult.error("新增角色'" + sysRole.getRoleName() + "'失败，角色名称已存在");
        }
        sysRole.setCreateBy(SecurityUtils.getUsername());
        return toAjax(roleService.addRole(sysRole));
    }

    /**
     * 删除角色
     */
    @PreAuthorize("@ss.hasPermi('account:role:list')")
    @GetMapping("/delete")
    @ApiOperation(value = "删除角色")
    public AjaxResult delete(Long roleId) {
        if(!StringUtils.isEmpty(roleService.selectUserByRoleId(roleId))){
            return AjaxResult.error("角色已分配,不能删除");
        }
        return toAjax(roleService.deleteRole(roleId));
    }

    /**
     * 编辑角色
     */
    @PreAuthorize("@ss.hasPermi('account:role:list')")
    @PostMapping("/edit")
    @ApiOperation(value = "编辑角色")
    public AjaxResult edit(SysRole sysRole) {
        if(StringUtils.isNotEmpty(roleService.selectRoleByName(sysRole.getRoleName()))){
            return AjaxResult.error("修改角色'" + sysRole.getRoleName() + "'失败，角色名称已存在");
        }
        sysRole.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(roleService.editRole(sysRole));
    }

    /**
     * 获取菜单下拉树列表
     */
    @PreAuthorize("@ss.hasPermi('account:role:list')")
    @PostMapping("/tree")
    @ApiOperation(value = "获取菜单列表")
    public AjaxResult treeselect() {
        return AjaxResult.success(roleService.buildMenuTreeSelect());
    }

}
