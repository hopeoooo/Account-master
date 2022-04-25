package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.search.SysChipRecordSearch;
import com.account.system.domain.search.SysSignedRecordDetailedSearch;
import com.account.system.domain.vo.SysSignedRecordDetailedVo;
import com.account.system.service.SysChipRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/chipRecord")
@Api(tags = "客户筹码变动明细")
public class SysChipRecordController extends BaseController {
    @Autowired
    private SysChipRecordService chipRecordService;

    @PreAuthorize("@ss.hasPermi('system:chipRecord:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询签单明细列表")
    public TableDataInfo list(SysChipRecordSearch chipRecordSearch){
        startPage();
        List<Map> maps = chipRecordService.selectChipRecordList(chipRecordSearch);
        return getDataTable(maps);
    }
}
