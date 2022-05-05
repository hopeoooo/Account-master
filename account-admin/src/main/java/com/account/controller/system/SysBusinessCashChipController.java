package com.account.controller.system;

import com.account.common.constant.CommonConst;
import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.ChipChangeEnum;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysMembers;
import com.account.system.domain.SysSignedRecord;
import com.account.system.domain.search.SysBusinessCashChipAddSearch;
import com.account.system.service.SysBusinessCashChipService;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/businessCashChip")
@Api(tags = "买码换现管理")
public class SysBusinessCashChipController extends BaseController {
    @Autowired
    private SysMembersService membersService;
    @Autowired
    private SysBusinessCashChipService businessCashChipService;
    @Autowired
    private SysSignedRecordService signedRecordService;


    @PreAuthorize("@ss.hasPermi('system:businessCashChip:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询买码换现列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false)
    })
    public TableDataInfo list(String card, Integer isAdmin) {
        startPage();
        startOrderBy();
        List<Map>list = membersService.selectBusinessCashChipList(card, isAdmin);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:businessCashChip:list')")
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


    @PreAuthorize("@ss.hasPermi('system:businessCashChip:list')")
    @PostMapping("/addBuyCode")
    @ApiOperation(value = "买码")
    public AjaxResult addBuyCode(@Validated @RequestBody SysBusinessCashChipAddSearch businessCashChipAddSearch) {
        if (StringUtils.isNull(businessCashChipAddSearch.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }
        businessCashChipAddSearch.setMark(ChipChangeEnum.BUY_CODE.getCode());
        businessCashChipAddSearch.setCreateBy(SecurityUtils.getUsername());
        businessCashChipService.addBuyCode(businessCashChipAddSearch);
        return AjaxResult.success("买码成功!");
    }

    @PreAuthorize("@ss.hasPermi('system:businessCashChip:list')")
    @PostMapping("/addCashExchange")
    @ApiOperation(value = "换现")
    public AjaxResult addCashExchange(@Validated @RequestBody SysBusinessCashChipAddSearch businessCashChipAddSearch) {
        if (StringUtils.isNull(businessCashChipAddSearch.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }
        businessCashChipAddSearch.setMark(ChipChangeEnum.CASH_OUT_CHIP.getCode());
        //判断该会员是否可以换现
        SysMembers sysMembers = membersService.selectmembersByCard(businessCashChipAddSearch.getCard());
        if (sysMembers==null){
            return AjaxResult.success("当前卡号不存在!");
        }
        if (sysMembers.getStatus()== CommonConst.NUMBER_1){
            return AjaxResult.success("该卡号已停用!");
        }
        int isCash = sysMembers.getIsCash();
        if (isCash==CommonConst.NUMBER_0){
            return AjaxResult.success("当前用户不可换现!");
        }
        //判断会员是否有欠钱
 /*       SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(null, businessCashChipAddSearch.getCard());
        if (sysSignedRecord!=null && sysSignedRecord.getSignedAmount().compareTo(BigDecimal.ZERO)>0){
            return AjaxResult.success("当前用户不可换现!");
        }*/
        BigDecimal chip = sysMembers.getChip();
        if (businessCashChipAddSearch.getChipAmount().compareTo(chip)>0){
            return AjaxResult.success("余额不足!");
        }
        businessCashChipAddSearch.setCreateBy(SecurityUtils.getUsername());
        businessCashChipService.addBuyCode(businessCashChipAddSearch);
        return AjaxResult.success("换现成功!");
    }
}
