package com.account.controller.report;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.framework.manager.AsyncManager;
import com.account.system.domain.BetRepair;
import com.account.system.domain.BetUpdate;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
import com.account.system.service.BetService;
import com.account.system.service.SysBetUpdateRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/bet/update")
@Api(tags = "注单修改记录")
public class BetUpdateReportController extends BaseController {
    @Autowired
    SysBetUpdateRecordService sysBetUpdateRecordService;
    /**
     * 注单记录
     * @return
     */
    @PreAuthorize("@ss.hasPermi('bet:update:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询注单修改记录列表")
    public TableDataInfo list(BetSearch betSearch,@RequestHeader("language") String language){
        startPage();
        return getDataTable(sysBetUpdateRecordService.selectBetUpdateList(betSearch,language));
    }
}
