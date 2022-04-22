package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.AccessType;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysSignedRecord;
import com.account.system.domain.search.SysSignedRecordSearch;
import com.account.system.domain.vo.SysSignedRecordVo;
import com.account.system.service.SysMembersService;
import com.account.system.service.SysSignedRecordService;
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
@RequestMapping("/system/signed")
@Api(tags = "签单管理")
public class SysSignedRecordController extends BaseController {
    @Autowired
    private SysSignedRecordService signedRecordService;

    @PreAuthorize("@ss.hasPermi('system:signed:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询签单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false)
    })
    public TableDataInfo list(String card, Integer isAdmin) {
        startPage();
        List<SysSignedRecordVo> list = signedRecordService.selectSignedRecordList(card, isAdmin);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:signed:total')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false)
    })
    public AjaxResult total(String card, Integer isAdmin) {
        Map map = signedRecordService.selectSignedRecordTotal(card, isAdmin);
        return AjaxResult.success(map);
    }


    @PreAuthorize("@ss.hasPermi('system:signed:addSigned')")
    @PostMapping("/addSigned")
    @ApiOperation(value = "签单")
    public AjaxResult addSigned(@Validated @RequestBody SysSignedRecordSearch signedRecordSearch) {
        signedRecordSearch.setMark(AccessType.SIGNED.getCode());
        //修改
        if (StringUtils.isNotNull(signedRecordSearch.getId()) && signedRecordSearch.getId() > 0) {
            SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(signedRecordSearch.getId(), signedRecordSearch.getUserId());
            if (sysSignedRecord == null) {
                return AjaxResult.error("签单失败!");
            }
            signedRecordSearch.setUpdateBy(SecurityUtils.getUsername());
            signedRecordSearch.setCreateBy(SecurityUtils.getUsername());
            signedRecordService.update(signedRecordSearch);
        } else {
            SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(null, signedRecordSearch.getUserId());
            if (sysSignedRecord != null) {
                return AjaxResult.error("签单失败!");
            }
            //添加
            signedRecordSearch.setCreateBy(SecurityUtils.getUsername());
            signedRecordService.insertSigned(signedRecordSearch);
        }
        return AjaxResult.success("签单成功!");
    }


    @PreAuthorize("@ss.hasPermi('system:signed:addReturnOrder')")
    @PostMapping("/addReturnOrder")
    @ApiOperation(value = "还单")
    public AjaxResult addReturnOrder(@Validated @RequestBody SysSignedRecordSearch signedRecordSearch) {
        SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(signedRecordSearch.getId(), signedRecordSearch.getUserId());
        if (sysSignedRecord == null) {
            return AjaxResult.error("还单失败!");
        }
        if (signedRecordSearch.getAmount().compareTo(sysSignedRecord.getSignedAmount()) > 0) {
            return AjaxResult.error("请输入正确的金额!");
        }

        signedRecordSearch.setMark(AccessType.RETURN_ORDER.getCode());
        signedRecordSearch.setUpdateBy(SecurityUtils.getUsername());
        //用于记录明细操作人
        signedRecordSearch.setCreateBy(SecurityUtils.getUsername());
        signedRecordService.update(signedRecordSearch);
        return AjaxResult.success("还单成功!");
    }
}
