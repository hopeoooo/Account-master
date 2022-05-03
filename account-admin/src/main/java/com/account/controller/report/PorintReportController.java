package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.SysReceipt;
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
    public TableDataInfo list(ReceiptReportSearch receiptReportSearch){
        startPage();
        return getDataTable(sysPorintService.selectPorintList(receiptReportSearch));
    }

}
