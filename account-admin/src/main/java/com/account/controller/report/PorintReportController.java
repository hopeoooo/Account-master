package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.PorintUpdate;
import com.account.system.domain.search.PorintUpdateSearch;
import com.account.system.domain.search.ReceiptReportSearch;
import com.account.system.service.SysPorintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("sys/porint")
@Api(tags = "点码报表")
public class PorintReportController extends BaseController {
    @Autowired
    SysPorintService sysPorintService;

    @PreAuthorize("@ss.hasPermi('sys:porint:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询点码列表")
    public AjaxResult list(ReceiptReportSearch receiptReportSearch){
        startPage();
        AjaxResult ajaxResult = AjaxResult.success();
        List list = sysPorintService.selectPorintList(receiptReportSearch);
        ajaxResult.put("list",list);
        ajaxResult.put("count",sysPorintService.selectPorintCount(receiptReportSearch));
        return ajaxResult;
    }

    @PreAuthorize("@ss.hasPermi('sys:porint:list')")
    @GetMapping("/reckon")
    @ApiOperation(value = "点码修改 计算差距")
    public AjaxResult reckon(PorintUpdate porintUpdate){
        return AjaxResult.success(sysPorintService.porintReckon(porintUpdate));
    }

    @PreAuthorize("@ss.hasPermi('sys:porint:list')")
    @GetMapping("/edit")
    @ApiOperation(value = "点码修改 确认修改")
    public AjaxResult edit(PorintUpdate porintUpdate){
        sysPorintService.editPorint(porintUpdate);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('porint:update:list')")
    @GetMapping("/update/list")
    @ApiOperation(value = "点码修改列表")
    public TableDataInfo updateList(PorintUpdateSearch porintUpdateSearch){
        startPage();
        return getDataTable(sysPorintService.selectPorintUpdateList(porintUpdateSearch));
    }
}
