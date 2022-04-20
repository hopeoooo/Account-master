package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysAccessCodeSearch;
import com.account.system.service.SysAccessCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/accessCode")
@Api(tags = "存取码管理")
public class SysAccessCodeController extends BaseController {
    @Autowired
    private SysAccessCodeService accessCodeService;


    @PreAuthorize("@ss.hasPermi('system:accessCode:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询存取码列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "会员账号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0否 1是)", required = false)
    })
    public TableDataInfo list(SysAccessCodeSearch accessCodeSearch){
        startPage();
        startOrderBy();
        List<Map> sysAccessCodeVos = accessCodeService.selectAccessCodeList(accessCodeSearch);
        return getDataTable(sysAccessCodeVos);
    }


    @PreAuthorize("@ss.hasPermi('system:accessCode:total')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    public AjaxResult total(SysAccessCodeSearch accessCodeSearch){
        Map map = accessCodeService.selectAccessCodeTotal(accessCodeSearch);
        return AjaxResult.success(map);
    }


    /**
     * 新增/修改桌台
     */
    @PreAuthorize("@ss.hasPermi('system:accessCode:saveCode')")
    @PostMapping
    @ApiOperation(value = "存码")
    public AjaxResult saveCode(@Validated @RequestBody SysAccessCodeSearch accessCode)
    {
        int i = 0;
        //存码
        if (StringUtils.isNotNull(accessCode.getId()) && accessCode.getId() > 0) {
            //修改:金额累加
            accessCode.setUpdateBy(getUsername());
            return toAjax( accessCodeService.updateAccessCode(accessCode));
        } else {
            //添加
            accessCode.setCreateBy(getUsername());
            return toAjax( accessCodeService.insertAccessCode(accessCode));
        }
    }

    @PreAuthorize("@ss.hasPermi('system:accessCode:updateCodeFetching')")
    @PostMapping("/updateCodeFetching")
    @ApiOperation(value = "取码")
    public AjaxResult updateCodeFetching(@Validated @RequestBody SysAccessCodeSearch accessCode){
        //判断金额是否足够
        SysAccessCode sysAccessCode = accessCodeService.selectAccessCodeInfo(accessCode.getUserId());
        if (sysAccessCode==null){
            return AjaxResult.error();
        }
        if (accessCode.getChipBalance().compareTo(sysAccessCode.getChipBalance())>0){
            return AjaxResult.error("余额不足");
        }

        if (accessCode.getCashBalance().compareTo(sysAccessCode.getCashBalance())>0){
            return AjaxResult.error("余额不足");
        }
        //取码:金额减
        accessCode.setUpdateBy(getUsername());
        return toAjax(accessCodeService.updateAccessCode(accessCode));
    }
}
