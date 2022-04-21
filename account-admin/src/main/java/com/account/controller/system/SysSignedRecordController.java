package com.account.controller.system;

import com.account.common.core.controller.BaseController;
import com.account.common.core.page.TableDataInfo;
import com.account.system.domain.vo.SysSignedRecordVo;
import com.account.system.service.SysSignedRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/signed")
@Api(tags = "签单管理")
public class SysSignedRecordController extends BaseController {
    @Autowired
    private SysSignedRecordService signedRecordService;

    @PreAuthorize("@ss.hasPermi('system:signed:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询签单列表")
    public TableDataInfo list(){
        startPage();
        List<SysSignedRecordVo> list = signedRecordService.selectSignedRecordList();
        return getDataTable(list);
    }

}
