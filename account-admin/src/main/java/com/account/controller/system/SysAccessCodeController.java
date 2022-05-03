package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.ChipChangeEnum;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysMembers;
import com.account.system.domain.search.SysAccessCodeAddSearch;
import com.account.system.domain.search.SysAccessCodeSearch;
import com.account.system.domain.vo.SysAccessCodeVo;
import com.account.system.service.SysAccessCodeService;
import com.account.system.service.SysMembersService;
import io.swagger.annotations.Api;
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

    @Autowired
    private SysMembersService membersService;

    @PreAuthorize("@ss.hasPermi('system:accessCode:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询存取码列表")
    public TableDataInfo list(SysAccessCodeSearch accessCodeSearch){
        startPage();
        startOrderBy();
        List<SysAccessCodeVo> sysAccessCodeVos = accessCodeService.selectAccessCodeList(accessCodeSearch);
        return getDataTable(sysAccessCodeVos);
    }


    @PreAuthorize("@ss.hasPermi('system:accessCode:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    public AjaxResult total(SysAccessCodeSearch accessCodeSearch){
        Map map = accessCodeService.selectAccessCodeTotal(accessCodeSearch);
        return AjaxResult.success(map);
    }


    @PreAuthorize("@ss.hasPermi('system:accessCode:list')")
    @PostMapping("saveCode")
    @ApiOperation(value = "存码")
    public AjaxResult saveCode(@Validated @RequestBody SysAccessCodeAddSearch accessCode)
    {
        if (StringUtils.isNull(accessCode.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }
        //判断该卡号是否存在
        SysMembers sysMembers = membersService.selectmembersByCard(accessCode.getCard());
        if (sysMembers==null){
            return AjaxResult.success("当前卡号不存在!");
        }
        accessCode.setMark(ChipChangeEnum.STORE_CHIP.getCode());
        SysAccessCode sysAccessCode = accessCodeService.selectAccessCodeInfo(null, accessCode.getCard());
        if (sysAccessCode==null){
            //添加
            accessCode.setCreateBy(SecurityUtils.getUsername());
            accessCodeService.insertAccessCode(accessCode);
        }else {
            //修改:金额累加
            accessCode.setCreateBy(SecurityUtils.getUsername());
            accessCode.setUpdateBy(SecurityUtils.getUsername());
            accessCodeService.updateAccessCode(accessCode);
        }
        return AjaxResult.success("存码成功!");
    }

    @PreAuthorize("@ss.hasPermi('system:accessCode:list')")
    @PostMapping("/updateCodeFetching")
    @ApiOperation(value = "取码")
    public AjaxResult updateCodeFetching(@Validated @RequestBody SysAccessCodeAddSearch accessCode){
        if (StringUtils.isNull(accessCode.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }
        //判断该卡号是否存在
        SysMembers sysMembers = membersService.selectmembersByCard(accessCode.getCard());
        if (sysMembers==null){
            return AjaxResult.success("当前卡号不存在!");
        }
        //判断金额是否足够
        SysAccessCode sysAccessCode = accessCodeService.selectAccessCodeInfo(accessCode.getId(),accessCode.getCard());
        accessCode.setMark(ChipChangeEnum.TAKE_CHIP.getCode());
        if (sysAccessCode==null){
            return AjaxResult.error("取码失败!");
        }
        if (accessCode.getChipAmount() !=null && accessCode.getChipAmount().compareTo(sysAccessCode.getChipBalance())>0){
            return AjaxResult.error("余额不足!");
        }

        if (accessCode.getCashAmount() !=null && accessCode.getCashAmount().compareTo(sysAccessCode.getCashBalance())>0){
            return AjaxResult.error("余额不足!");
        }
        //取码:金额减
        accessCode.setUpdateBy(SecurityUtils.getUsername());
        accessCode.setCreateBy(SecurityUtils.getUsername());
        accessCodeService.updateAccessCode(accessCode);
        return AjaxResult.success("取码成功!");
    }
}
