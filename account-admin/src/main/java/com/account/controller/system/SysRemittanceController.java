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
import com.account.system.domain.search.SysRemittanceSearch;
import com.account.system.service.SysMembersService;
import com.account.system.service.SysRemittanceDetailedService;
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
@RequestMapping("/system/remittance")
@Api(tags = "汇款管理")
public class SysRemittanceController extends BaseController {
    @Autowired
    private SysMembersService membersService;
    @Autowired
    private SysRemittanceDetailedService remittanceDetailedService;

    @Autowired
    private SysSignedRecordService signedRecordService;


    @PreAuthorize("@ss.hasPermi('system:remittance:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询汇款列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = true),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false)
    })
    public TableDataInfo list(String card, Integer isAdmin) {
        startPage();
        List<Map>list = membersService.selectBusinessCashChipList(card, isAdmin);
        return getDataTable(list);
    }

/*
    @PreAuthorize("@ss.hasPermi('system:remittance:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = true),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false)
    })
    public AjaxResult total(String card, Integer isAdmin) {
        Map map = membersService.selectBusinessCashChipTotal(card, isAdmin);
        return AjaxResult.success(map);
    }
*/


    @PreAuthorize("@ss.hasPermi('system:remittance:list')")
    @PostMapping("/addImport")
    @ApiOperation(value = "汇入")
    public AjaxResult addImport(@Validated @RequestBody SysRemittanceSearch remittanceSearch) {
        if (StringUtils.isNull(remittanceSearch.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }
        //判断该会员是否可以汇入
        SysMembers sysMembers = membersService.selectmembersByCard(remittanceSearch.getCard());
        if (sysMembers==null){
            return AjaxResult.success("汇入失败!");
        }
        remittanceSearch.setType(ChipChangeEnum.IMPORT_CHIP.getCode());
        remittanceSearch.setCreateBy(SecurityUtils.getUsername());
        remittanceDetailedService.insertRemittanceDetailed(remittanceSearch);
        return AjaxResult.success("汇入成功!");
    }

    @PreAuthorize("@ss.hasPermi('system:remittance:list')")
    @PostMapping("/addRemit")
    @ApiOperation(value = "汇出")
    public AjaxResult addRemit(@Validated @RequestBody SysRemittanceSearch remittanceSearch) {
        if (StringUtils.isNull(remittanceSearch.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }
        remittanceSearch.setType(ChipChangeEnum.OUT_EXCHANGE.getCode());
        //判断该会员是否可以汇出
        SysMembers sysMembers = membersService.selectmembersByCard(remittanceSearch.getCard());
        if (sysMembers==null){
            return AjaxResult.success("当前卡号不存在!");
        }
        if (sysMembers.getStatus()== CommonConst.NUMBER_1){
            return AjaxResult.success("该卡号已停用!");
        }
        int isOut = sysMembers.getIsOut();
        if (isOut==CommonConst.NUMBER_0){
            return AjaxResult.success("当前用户不可汇出!");
        }
        //判断会员是否有欠钱
/*        SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(null, remittanceSearch.getCard());
        if (sysSignedRecord!=null && sysSignedRecord.getSignedAmount().compareTo(BigDecimal.ZERO)>0){
            return AjaxResult.success("当前用户不可汇出!");
        }*/
        remittanceSearch.setCreateBy(SecurityUtils.getUsername());
        //汇出为筹码则校验,减用户筹码余额
        if (remittanceSearch.getOperationType()==CommonConst.NUMBER_1){
            BigDecimal chip = sysMembers.getChip();
            if (remittanceSearch.getAmount().compareTo(chip)>0){
                return AjaxResult.success("余额不足!");
            }
        }
        remittanceDetailedService.insertRemittanceDetailed(remittanceSearch);
        return AjaxResult.success("汇出成功!");
    }

}
