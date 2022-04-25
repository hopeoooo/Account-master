package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.ChipChangeEnum;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysMembers;
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
    @Autowired
    private SysMembersService membersService;

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

    @PreAuthorize("@ss.hasPermi('system:signed:list')")
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


    @PreAuthorize("@ss.hasPermi('system:signed:list')")
    @PostMapping("/addSigned")
    @ApiOperation(value = "签单")
    public AjaxResult addSigned(@Validated @RequestBody SysSignedRecordSearch signedRecordSearch) {
        if (StringUtils.isNull(signedRecordSearch.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }
        //判断该卡号是否存在
        SysMembers sysMembers = membersService.selectmembersByCard(signedRecordSearch.getCard());
        if (sysMembers==null){
            return AjaxResult.success("当前卡号不存在!");
        }
        signedRecordSearch.setMark(ChipChangeEnum.BORROW_CHIP.getCode());
        SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(null, signedRecordSearch.getCard());
        if (sysSignedRecord==null){
            //添加
            signedRecordSearch.setCreateBy(SecurityUtils.getUsername());
            signedRecordService.insertSigned(signedRecordSearch);
        }else {
            //修改
            signedRecordSearch.setUpdateBy(SecurityUtils.getUsername());
            signedRecordSearch.setCreateBy(SecurityUtils.getUsername());
            signedRecordService.update(signedRecordSearch);
        }
        return AjaxResult.success("签单成功!");
    }


    @PreAuthorize("@ss.hasPermi('system:signed:list')")
    @PostMapping("/addReturnOrder")
    @ApiOperation(value = "还单")
    public AjaxResult addReturnOrder(@Validated @RequestBody SysSignedRecordSearch signedRecordSearch) {
        if (StringUtils.isNull(signedRecordSearch.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }
        //判断该卡号是否存在
        SysMembers sysMembers = membersService.selectmembersByCard(signedRecordSearch.getCard());
        if (sysMembers==null){
            return AjaxResult.success("当前卡号不存在!");
        }
        SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(null, signedRecordSearch.getCard());
        if (sysSignedRecord == null) {
            return AjaxResult.error("还单失败!");
        }
        if (signedRecordSearch.getAmount().compareTo(sysSignedRecord.getSignedAmount()) > 0) {
            return AjaxResult.error("请输入正确的金额!");
        }

        signedRecordSearch.setMark(ChipChangeEnum.RETURN_CHIP.getCode());
        signedRecordSearch.setUpdateBy(SecurityUtils.getUsername());
        //用于记录明细操作人
        signedRecordSearch.setCreateBy(SecurityUtils.getUsername());
        signedRecordService.update(signedRecordSearch);
        return AjaxResult.success("还单成功!");
    }
}
