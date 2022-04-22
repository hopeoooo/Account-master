package com.account.controller.system;

import com.account.common.constant.CommonConst;
import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.AccessType;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysBusinessCashChip;
import com.account.system.domain.search.SysBusinessCashChipAddSearch;
import com.account.system.domain.vo.SysBusinessCashChipVo;
import com.account.system.service.SysBusinessCashChipService;
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
@RequestMapping("/system/businessCashChip")
@Api(tags = "买码换现管理")
public class SysBusinessCashChipController extends BaseController {
    @Autowired
    private SysBusinessCashChipService businessCashChipService;

    @PreAuthorize("@ss.hasPermi('system:businessCashChip:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询买码换现列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false)
    })
    public TableDataInfo list(String card, Integer isAdmin) {
        startPage();
        List<SysBusinessCashChipVo> list = businessCashChipService.selectBusinessCashChipList(card, isAdmin);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:businessCashChip:total')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false)
    })
    public AjaxResult total(String card, Integer isAdmin) {
        Map map = businessCashChipService.selectBusinessCashChipTotal(card, isAdmin);
        return AjaxResult.success(map);
    }


    @PreAuthorize("@ss.hasPermi('system:businessCashChip:addBuyCode')")
    @PostMapping("/addBuyCode")
    @ApiOperation(value = "买码")
    public AjaxResult addBuyCode(@Validated @RequestBody SysBusinessCashChipAddSearch businessCashChipAddSearch) {
        businessCashChipAddSearch.setMark(AccessType.BUY_CODE.getCode());
        //修改
        if (StringUtils.isNotNull(businessCashChipAddSearch.getId()) && businessCashChipAddSearch.getId() > 0) {
            SysBusinessCashChip sysBusinessCashChip = businessCashChipService.selectBusinessCashChipInfo(businessCashChipAddSearch.getId(),businessCashChipAddSearch.getUserId());
            if (sysBusinessCashChip==null){
                return AjaxResult.error("买码失败!");
            }
            businessCashChipAddSearch.setCreateBy(SecurityUtils.getUsername());
            businessCashChipAddSearch.setUpdateBy(SecurityUtils.getUsername());
            businessCashChipService.update(businessCashChipAddSearch);
        }else {
            SysBusinessCashChip sysBusinessCashChip = businessCashChipService.selectBusinessCashChipInfo(null,businessCashChipAddSearch.getUserId());
            if (sysBusinessCashChip!=null){
                return AjaxResult.error("买码失败!");
            }
            //添加
            businessCashChipAddSearch.setCreateBy(SecurityUtils.getUsername());
            businessCashChipService.insert(businessCashChipAddSearch);
        }
        return AjaxResult.success("买码成功!");
    }

    @PreAuthorize("@ss.hasPermi('system:businessCashChip:addCashExchange')")
    @PostMapping("/addCashExchange")
    @ApiOperation(value = "换现")
    public AjaxResult addCashExchange(@Validated @RequestBody SysBusinessCashChipAddSearch businessCashChipAddSearch) {

        if (StringUtils.isNotNull(businessCashChipAddSearch.getIsCash()) && businessCashChipAddSearch.getIsCash()== CommonConst.NUMBER_0){
            return AjaxResult.error("当前会员不可换现!");
        }
        businessCashChipAddSearch.setMark(AccessType.CASH_EXCHANGE.getCode());
        SysBusinessCashChip sysBusinessCashChip = businessCashChipService.selectBusinessCashChipInfo(null,businessCashChipAddSearch.getUserId());
        if (sysBusinessCashChip == null) {
            return AjaxResult.error("换现失败!");
        }
        if (businessCashChipAddSearch.getChipAmount().compareTo(sysBusinessCashChip.getChipAmount()) > 0) {
            return AjaxResult.error("余额不足!");
        }

        businessCashChipAddSearch.setCreateBy(SecurityUtils.getUsername());
        businessCashChipAddSearch.setUpdateBy(SecurityUtils.getUsername());
        businessCashChipService.update(businessCashChipAddSearch);
        return AjaxResult.success("换现成功!");
    }
}
