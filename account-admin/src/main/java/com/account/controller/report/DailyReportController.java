package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.StringUtils;
import com.account.system.domain.search.ReportSearch;
import com.account.system.service.BetService;
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
@RequestMapping("sys/report")
@Api(tags = "客户日报表")
public class DailyReportController extends BaseController {
    @Autowired
    BetService betService;

    /**
     * 客户日报表列表
     * @return
     */
    @PreAuthorize("@ss.hasPermi('sys:report:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询客户日报表列表")
    public TableDataInfo list(ReportSearch reportSearch ){
        if (StringUtils.isNull(reportSearch.getCard())){
            return new TableDataInfo();
        }
        startPage();
        List<Map> list = betService.selectDailyReportList(reportSearch);
        return getDataTable(list);
    }


    @PreAuthorize("@ss.hasPermi('sys:report:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    public AjaxResult total(ReportSearch reportSearch){
        Map map = betService.selectDailyReportTotal(reportSearch);
        return AjaxResult.success(map);
    }

}
