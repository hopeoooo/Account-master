package com.account.controller.system;

import com.account.common.constant.CommonConst;
import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.AccessType;
import com.account.system.domain.search.SysBusinessCashChipAddSearch;
import com.account.system.domain.vo.SysBusinessCashChipVo;
import com.account.system.service.SysMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/businessCashChip")
@Api(tags = "买码换现管理")
public class SysBusinessCashChipController extends BaseController {
    @Autowired
    private SysMembersService membersService;

    @PreAuthorize("@ss.hasPermi('system:businessCashChip:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询买码换现列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false)
    })
    public TableDataInfo list(String card, Integer isAdmin) {
        startPage();
        List<Map>list = membersService.selectBusinessCashChipList(card, isAdmin);
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
        Map map = membersService.selectBusinessCashChipTotal(card, isAdmin);
        return AjaxResult.success(map);
    }


    @PreAuthorize("@ss.hasPermi('system:businessCashChip:addBuyCode')")
    @PostMapping("/addBuyCode")
    @ApiOperation(value = "买码")
    public AjaxResult addBuyCode(@Validated @RequestBody SysBusinessCashChipAddSearch businessCashChipAddSearch) {
        businessCashChipAddSearch.setMark(AccessType.BUY_CODE.getCode());
        membersService.updateChipAmount(businessCashChipAddSearch.getId(), businessCashChipAddSearch.getChipAmount(), CommonConst.NUMBER_1);
        return AjaxResult.success("买码成功!");
    }

    @PreAuthorize("@ss.hasPermi('system:businessCashChip:addCashExchange')")
    @PostMapping("/addCashExchange")
    @ApiOperation(value = "换现")
    public AjaxResult addCashExchange(@Validated @RequestBody SysBusinessCashChipAddSearch businessCashChipAddSearch) {
        businessCashChipAddSearch.setMark(AccessType.CASH_EXCHANGE.getCode());
        //判断该会员是否可以换现
        Map map = membersService.selectMembersInfo(businessCashChipAddSearch.getId());
        int isCash = Integer.parseInt(map.get("isCash").toString());
        if (isCash==CommonConst.NUMBER_0){
            return AjaxResult.success("当前会员不可换现!");
        }
        BigDecimal chip = new BigDecimal(map.get("chip").toString());
        if (businessCashChipAddSearch.getChipAmount().compareTo(chip)>0){
            return AjaxResult.success("余额不足!");
        }
        membersService.updateChipAmount(businessCashChipAddSearch.getId(), businessCashChipAddSearch.getChipAmount(), CommonConst.NUMBER_0);
        return AjaxResult.success("换现成功!");
    }
}