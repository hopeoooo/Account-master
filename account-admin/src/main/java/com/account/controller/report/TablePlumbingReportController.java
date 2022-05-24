package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.system.service.BetService;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
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
@RequestMapping("sys/tablePlumbing")
@Api(tags = "台面上下水报表")
public class TablePlumbingReportController extends BaseController {
    @Autowired
    BetService betService;

    @PreAuthorize("@ss.hasPermi('sys:tablePlumbing:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询台面上下水报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false),
            @ApiImplicitParam(name = "timeType", value = "0:收码时间,1:普通时间,2:今日报表", required = false)
    })
    public TableDataInfo list(String startTime, String endTime,String timeType){
        startPage();
        if (StrUtil.isBlank(timeType)){
            timeType="1";
        }
        List<Map> list = betService.selectTablePlumbingList(startTime,endTime,timeType);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('sys:tablePlumbing:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false),
            @ApiImplicitParam(name = "timeType", value = "0:收码时间,1:普通时间,2:今日报表", required = false)
    })
    public AjaxResult total(String startTime, String endTime,String timeType){
        Map map = betService.selectTablePlumbingTotal(startTime,endTime,timeType);
        return AjaxResult.success(map);
    }


}
