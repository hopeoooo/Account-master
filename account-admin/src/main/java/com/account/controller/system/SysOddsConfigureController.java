package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.system.domain.SysOddsConfigure;
import com.account.system.service.SysOddsConfigureService;
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
    private SysOddsConfigureService oddsConfigureService;


    @PreAuthorize("@ss.hasPermi('system:oddsConfig:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询赔率设置")
    public AjaxResult list() {
        SysOddsConfigure list = oddsConfigureService.selectConfigInfo();
        return AjaxResult.success(list);
    }


    /**
     * 修改赔率设置
     */
    @PreAuthorize("@ss.hasPermi('system:oddsConfig:list')")
    @PostMapping
    @ApiOperation(value = "修改赔率设置")
    public AjaxResult update(@Validated @RequestBody SysOddsConfigure oddsConfigure)
    {
        if (oddsConfigure.getBaccaratBankerWin().length()>10 ||
                oddsConfigure.getBaccaratPlayerWin().length()>10||
                oddsConfigure.getBaccaratTieWin().length()>10||
                oddsConfigure.getBaccaratBankerPair().length()>10||
                oddsConfigure.getBaccaratPlayerPair().length()>10||
                oddsConfigure.getBaccaratLarge().length()>10||
                oddsConfigure.getBaccaratSmall().length()>10||
                oddsConfigure.getDragonWin().length()>10||
                oddsConfigure.getTigerWin().length()>10||
                oddsConfigure.getTieWin().length()>10
        ){
            return AjaxResult.error("请输入正确得配置数据");
        }

        return toAjax(oddsConfigureService.updateOddsConfig(oddsConfigure));
    }

}
