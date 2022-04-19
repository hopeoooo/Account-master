package com.account.controller.account;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.SysConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hope
 * @since 2022/4/19
 * 会员管理
 */
//@RestController
//@RequestMapping("/account/mambers")
//@Api("会员管理")
public class MembersController extends BaseController {

    /**
     * 获取会员列表
     */
    @PreAuthorize("@ss.hasPermi('account:mambers:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取会员列表")
    public TableDataInfo list() {
        startPage();
        List<SysConfig> list = null;
        return getDataTable(list);
    }
}
