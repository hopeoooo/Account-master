package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.search.InputErrorSearch;
import com.account.system.domain.vo.SysInputErrorVo;
import com.account.system.service.SysInputErrorService;
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
@RequestMapping("sys/inputError")
@Api(tags = "员工录入错账报表")
public class InputErrorReportController extends BaseController {
    @Autowired
    SysInputErrorService inputErrorService;

    @PreAuthorize("@ss.hasPermi('sys:inputError:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询员工录入错账报表")
    public TableDataInfo list(InputErrorSearch errorSearch){
        startPage();
        List<SysInputErrorVo> list = inputErrorService.selectInputErrorList(errorSearch);
        return getDataTable(list);
    }

}
