package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.system.domain.SysOddsConfigure;
import com.account.system.service.ISysOddsConfigureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/system/oddsConfig")
@Api(tags = "赔率设置")
public class SysOddsConfigureController extends BaseController {
    @Autowired
    private ISysOddsConfigureService oddsConfigureService;


    @PreAuthorize("@ss.hasPermi('system:oddsConfig:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询赔率设置")
    public AjaxResult list(SysOddsConfigure oddsConfigure) {
        SysOddsConfigure list = oddsConfigureService.selectConfigInfo();
        return AjaxResult.success(list);
    }


    /**
     * 修改赔率设置
     */
    @PreAuthorize("@ss.hasPermi('system:oddsConfig:update')")
    @PostMapping
    @ApiOperation(value = "修改赔率设置")
    public AjaxResult update(@Validated @RequestBody SysOddsConfigure oddsConfigure)
    {
        return toAjax(oddsConfigureService.updateOddsConfig(oddsConfigure));
    }

}
