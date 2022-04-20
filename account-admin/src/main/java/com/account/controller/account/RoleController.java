package com.account.controller.account;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.domain.entity.SysRole;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/list")
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
    @GetMapping("/add")
    @ApiOperation(value = "新增角色")
    public AjaxResult add(SysRole sysRole) {
        sysRole.setCreateBy(SecurityUtils.getUsername());
        return toAjax(roleService.addRole(sysRole));
    }
}
