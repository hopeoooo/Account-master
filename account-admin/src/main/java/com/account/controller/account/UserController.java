package com.account.controller.account;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.entity.SysUser;
import com.account.common.core.page.TableDataInfo;
import com.account.system.service.SysUserService;
import io.swagger.annotations.Api;
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
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

}
