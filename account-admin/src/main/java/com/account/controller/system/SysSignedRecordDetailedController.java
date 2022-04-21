package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.search.SysSignedRecordDetailedSearch;
import com.account.system.domain.vo.SysSignedRecordDetailedVo;
import com.account.system.service.SysSignedRecordDetailedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/signedDetailed")
@Api(tags = "签单明细")
public class SysSignedRecordDetailedController extends BaseController {
    @Autowired
    private SysSignedRecordDetailedService signedRecordDetailedService;

    @PreAuthorize("@ss.hasPermi('system:signedDetailed:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询签单明细列表")
    public TableDataInfo list(SysSignedRecordDetailedSearch signedRecordDetailedSearch){
        startPage();
        List<SysSignedRecordDetailedVo> sysSignedRecordDetailedVos = signedRecordDetailedService.selectSignedRecordDetailedList(signedRecordDetailedSearch);
        return getDataTable(sysSignedRecordDetailedVos);
    }


}
