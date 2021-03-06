package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.search.WinLoseReportSearch;
import com.account.system.service.BetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("sys/winLose")
@Api(tags = "输赢报表")
public class WinLoseReportController extends BaseController {
    @Autowired
    BetService betService;

    @PreAuthorize("@ss.hasPermi('sys:winLose:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询输赢报表列表")
    public TableDataInfo list(WinLoseReportSearch reportSearch ){
        startPage();
        startOrderByNew();
        List<Map> list = betService.selectWinLoseList(reportSearch);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('sys:winLose:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    public AjaxResult total(WinLoseReportSearch reportSearch ){
        Map map = betService.selectWinLoseTotal(reportSearch);
        return AjaxResult.success(map);
    }


}
