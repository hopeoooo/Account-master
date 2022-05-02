package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;
import com.account.system.domain.search.WinLoseReportSearch;
import com.account.system.service.SysReceiptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("sys/receipt")
@Api(tags = "收码报表")
public class ReceiptReportController extends BaseController {
    @Autowired
    SysReceiptService receiptService;

    @PreAuthorize("@ss.hasPermi('sys:receipt:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询收码报表")
    public TableDataInfo list(ReceiptReportSearch receiptReportSearch){
        startPage();
        List<SysReceipt> list = receiptService.selectReceiptList(receiptReportSearch);
        return getDataTable(list);
    }

}
