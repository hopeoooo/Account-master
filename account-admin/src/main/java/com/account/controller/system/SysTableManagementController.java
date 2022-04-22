package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysTableManagement;
import com.account.system.service.SysTableManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/table")
@Api(tags = "桌台管理")
public class SysTableManagementController extends BaseController {
    @Autowired
    private SysTableManagementService tableManagementService;


    @PreAuthorize("@ss.hasPermi('system:table:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询桌台列表")
    public TableDataInfo list(SysTableManagement sysTableManagement){
        startPage();
        List<SysTableManagement> list = tableManagementService.selectTableList(sysTableManagement);
        return getDataTable(list);
    }


    /**
     * 新增/修改桌台
     */
    @PreAuthorize("@ss.hasPermi('system:table:addOrUpdate')")
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "新增/编辑桌台")
    public AjaxResult add(@Validated @RequestBody SysTableManagement sysTableManagement)
    {
        //修改
        if(StringUtils.isNotNull(sysTableManagement.getId()) && sysTableManagement.getId()>0){
            sysTableManagement.setUpdateBy(SecurityUtils.getUsername());
            return toAjax(tableManagementService.updateTable(sysTableManagement));
        }else {
            SysTableManagement sysTableManagement1 = tableManagementService.selectTableInfo(sysTableManagement.getTableId(),sysTableManagement.getGameId());
            if  (sysTableManagement1!=null){
                return AjaxResult.error("添加失败,桌台已存在!");
            }
            //添加
            sysTableManagement.setCreateBy(SecurityUtils.getUsername());
            return toAjax(tableManagementService.insertTable(sysTableManagement));
        }
    }


    /**
     * 删除桌台
     */
    @PreAuthorize("@ss.hasPermi('system:table:remove')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除桌台")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(tableManagementService.deleteUserByIds(id));
    }


}
