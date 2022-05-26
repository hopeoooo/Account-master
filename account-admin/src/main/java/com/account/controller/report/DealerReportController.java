package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.search.SysDealerSearch;
import com.account.system.service.SysDealerService;
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
@RequestMapping("/sys/dealer")
@Api(tags = "荷官上下水")
public class DealerReportController extends BaseController {
    @Autowired
    private SysDealerService dealerService;


    @PreAuthorize("@ss.hasPermi('sys:dealer:list')")
    @GetMapping("/selectDealerStatisticsList")
    @ApiOperation(value = "荷官上下水")
    public TableDataInfo selectDealerStatisticsList(SysDealerSearch dealerSearch) {
        startPage();
        startOrderByNew();
        List<Map> list = dealerService.selectDealerStatisticsList(dealerSearch);
        return getDataTable(list);
    }

}
