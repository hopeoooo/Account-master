package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.search.SysAccessCodeDetailedSearch;
import com.account.system.domain.vo.SysAccessCodeDetailedVo;
import com.account.system.service.SysAccessCodeDetailedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/accessCodeDetailed")
@Api(tags = "存取码明细")
public class SysAccessCodeDetailedController extends BaseController {
    @Autowired
    private SysAccessCodeDetailedService accessCodeDetailedService;

    @PreAuthorize("@ss.hasPermi('system:accessCodeDetailed:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询存取码明细列表")
    public TableDataInfo list(SysAccessCodeDetailedSearch accessCodeDetailedSearch){
        startPage();
        List<SysAccessCodeDetailedVo> sysAccessCodeVos = accessCodeDetailedService.selectAccessDetailedCodeList(accessCodeDetailedSearch);
        return getDataTable(sysAccessCodeVos);
    }


}
