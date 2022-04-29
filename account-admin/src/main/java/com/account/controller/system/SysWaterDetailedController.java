package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.search.SysWaterDetailedSearch;
import com.account.system.domain.vo.SysWaterDetailedVo;
import com.account.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/waterDetailed")
@Api(tags = "洗码费结算明细")
public class SysWaterDetailedController extends BaseController {
    @Autowired
    private SysWaterDetailedService waterDetailedService;


    @PreAuthorize("@ss.hasPermi('system:waterDetailed:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询洗码费结算明细列表")
    public TableDataInfo list(SysWaterDetailedSearch waterDetailedSearch) {
        startPage();
        List<SysWaterDetailedVo> list = waterDetailedService.selectWaterDetailedList(waterDetailedSearch);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:waterDetailed:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    public AjaxResult total(SysWaterDetailedSearch waterDetailedSearch) {
        Map map = waterDetailedService.selectWaterDetailedTotal(waterDetailedSearch);
        return AjaxResult.success(map);
    }


}
