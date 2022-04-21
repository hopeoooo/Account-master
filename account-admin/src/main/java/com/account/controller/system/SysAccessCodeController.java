package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.AccessType;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysAccessCode;
import com.account.system.domain.search.SysAccessCodeAddSearch;
import com.account.system.domain.search.SysAccessCodeSearch;
import com.account.system.domain.vo.SysAccessCodeVo;
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
    public TableDataInfo list(SysAccessCodeSearch accessCodeSearch){
        startPage();
        startOrderBy();
        List<SysAccessCodeVo> sysAccessCodeVos = accessCodeService.selectAccessCodeList(accessCodeSearch);
        return getDataTable(sysAccessCodeVos);
    }


    @PreAuthorize("@ss.hasPermi('system:accessCode:total')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    public AjaxResult total(SysAccessCodeSearch accessCodeSearch){
        Map map = accessCodeService.selectAccessCodeTotal(accessCodeSearch);
        return AjaxResult.success(map);
    }


    @PreAuthorize("@ss.hasPermi('system:accessCode:saveCode')")
    @PostMapping("saveCode")
    @ApiOperation(value = "存码")
    public AjaxResult saveCode(@Validated @RequestBody SysAccessCodeAddSearch accessCode)
    {
        accessCode.setMark(AccessType.STORAGE_CODE.getCode());
        //存码
        if (StringUtils.isNotNull(accessCode.getId()) && accessCode.getId() > 0) {
            //判断id是否存在
            SysAccessCode sysAccessCode = accessCodeService.selectAccessCodeInfo(accessCode.getId(), accessCode.getUserId());
            if (sysAccessCode==null){
                return AjaxResult.error("存码失败!");
            }
            //修改:金额累加
            accessCode.setCreateBy(SecurityUtils.getUsername());
            accessCode.setUpdateBy(SecurityUtils.getUsername());
            accessCodeService.updateAccessCode(accessCode);
        } else {
            //判断id是否存在
            SysAccessCode sysAccessCode = accessCodeService.selectAccessCodeInfo(null,accessCode.getUserId());
            if (sysAccessCode!=null){
                return AjaxResult.error("存码失败!");
            }
            //添加
            accessCode.setCreateBy(SecurityUtils.getUsername());
            accessCodeService.insertAccessCode(accessCode);
        }
        return AjaxResult.success("存码成功!");
    }

    @PreAuthorize("@ss.hasPermi('system:accessCode:updateCodeFetching')")
    @PostMapping("/updateCodeFetching")
    @ApiOperation(value = "取码")
    public AjaxResult updateCodeFetching(@Validated @RequestBody SysAccessCodeAddSearch accessCode){
        //判断金额是否足够
        SysAccessCode sysAccessCode = accessCodeService.selectAccessCodeInfo(accessCode.getId(),accessCode.getUserId());
        accessCode.setMark(AccessType.CODE_FETCHING.getCode());
        if (sysAccessCode==null){
            return AjaxResult.error("取码失败!");
        }
        if (accessCode.getChipBalance().compareTo(sysAccessCode.getChipBalance())>0){
            return AjaxResult.error("余额不足!");
        }

        if (accessCode.getCashBalance().compareTo(sysAccessCode.getCashBalance())>0){
            return AjaxResult.error("余额不足!");
        }
        //取码:金额减
        accessCode.setUpdateBy(SecurityUtils.getUsername());
        accessCode.setCreateBy(SecurityUtils.getUsername());
        accessCodeService.updateAccessCode(accessCode);
        return AjaxResult.success("取码成功!");
    }
}
