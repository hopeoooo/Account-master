package com.account.controller.account;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.domain.entity.SysUser;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.framework.web.service.TokenService;
import com.account.system.domain.search.SysUserSearch;
import com.account.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户信息
 *
 * @author hope
 */
@RestController
@RequestMapping("/account/user")
@Api(tags = "员工管理")
public class UserController extends BaseController {
    @Autowired
    private SysUserService userService;

    /**
     * 获取员工列表
     */
    @PreAuthorize("@ss.hasPermi('account:user:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取员工列表")
    public TableDataInfo list(SysUserSearch user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 新增员工
     */
    @PreAuthorize("@ss.hasPermi('account:user:list')")
    @GetMapping("/add")
    @ApiOperation(value = "新增员工")
    public AjaxResult add(SysUser sysUser) {
        if (StringUtils.isNotNull(userService.selectUserByUserName(sysUser.getUserName()))) {
            return AjaxResult.error("新增会员'" + sysUser.getUserName() + "'失败，工号已存在");
        }
        sysUser.setCreateBy(SecurityUtils.getUsername());
        userService.addUser(sysUser);
        return AjaxResult.success();
    }

    /**
     * 编辑员工
     */
    @PreAuthorize("@ss.hasPermi('account:user:list')")
    @GetMapping("/edit")
    @ApiOperation(value = "编辑员工")
    public AjaxResult edit(SysUser sysUser) {
        sysUser.setUpdateBy(SecurityUtils.getUsername());
        userService.editUser(sysUser);
        return AjaxResult.success();
    }

    @Autowired
    TokenService tokenService;
    /**
     * 删除员工
     */
    @PreAuthorize("@ss.hasPermi('account:user:list')")
    @GetMapping("/delete")
    @ApiOperation(value = "删除员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "员工id", dataType = "Long", required = true, paramType = "path")
    })
    public AjaxResult delete(Long userId) {
        userService.deleteUser(userId);
        tokenService.deleteToken(userId);
        return AjaxResult.success();
    }
}
