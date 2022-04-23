package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.search.SysRemittanceDetailedSearch;
import com.account.system.domain.vo.SysRemittanceDetailedVo;
import com.account.system.service.SysRemittanceDetailedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/remittanceDetailed")
@Api(tags = "汇款明细管理")
public class SysRemittanceDetailedController extends BaseController {
    @Autowired
    private SysRemittanceDetailedService remittanceDetailedService;

    @PreAuthorize("@ss.hasPermi('system:remittanceDetailed:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询汇款明细列表")
    public TableDataInfo list(SysRemittanceDetailedSearch remittanceDetailedSearch) {
        startPage();
        List<SysRemittanceDetailedVo> list = remittanceDetailedService.selectRemittanceDetailedList(remittanceDetailedSearch);
        return getDataTable(list);
    }

}
