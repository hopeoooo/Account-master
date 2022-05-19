package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysTableManagement;
import com.account.system.service.SysTableManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

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

    @PreAuthorize("@ss.hasPermi('system:table:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    public AjaxResult total(SysTableManagement sysTableManagement){
        Map map = tableManagementService.selectTableTotal(sysTableManagement);
        return AjaxResult.success(map);
    }

    @GetMapping("/tableIdComboBoxInfo")
    @ApiOperation(value = "桌台下拉框")
    public TableDataInfo tableIdComboBoxInfo(SysTableManagement sysTableManagement){
        List<Map> list = tableManagementService.selectTableIdInfo(sysTableManagement);
        return getDataTable(list);
    }


    /**
     * 新增/修改桌台
     */
    @PreAuthorize("@ss.hasPermi('system:table:list')")
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "新增/编辑桌台")
    public AjaxResult add(@Validated @RequestBody SysTableManagement sysTableManagement) throws IOException {
        //校验ip是否是正确得
      /*  boolean b = tableManagementService.pingIp(sysTableManagement.getIp());
        if (!b){
            return AjaxResult.error("请输入正确得ip地址!");
        }*/
        SysTableManagement sysTableManagement1 = tableManagementService.selectTableInfo(null,sysTableManagement.getIp(),sysTableManagement.getId());
        if  (sysTableManagement1!=null){
            return AjaxResult.error("桌台ip已存在!");
        }
        SysTableManagement tableManagement = tableManagementService.selectTableInfo(sysTableManagement.getTableId(),null,sysTableManagement.getId());
        if  (tableManagement!=null){
            return AjaxResult.error("桌台编号已存在!");
        }
        //修改
        if(StringUtils.isNotNull(sysTableManagement.getId()) && sysTableManagement.getId()>0){
            sysTableManagement.setUpdateBy(SecurityUtils.getUsername());
            return toAjax(tableManagementService.updateTable(sysTableManagement));
        }else {
            //添加
            sysTableManagement.setCreateBy(SecurityUtils.getUsername());
            return toAjax(tableManagementService.insertTable(sysTableManagement));
        }
    }



    /**
     * 删除桌台
     */
    @PreAuthorize("@ss.hasPermi('system:table:list')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除桌台")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(tableManagementService.deleteUserByIds(id));
    }


}
