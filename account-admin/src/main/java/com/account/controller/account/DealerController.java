package com.account.controller.account;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.domain.entity.SysDealer;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.search.SysDealerSearch;
import com.account.system.service.SysDealerService;
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
@RequestMapping("/account/dealer")
@Api(tags = "荷官管理")
public class DealerController extends BaseController {
    @Autowired
    private SysDealerService dealerService;


    @PreAuthorize("@ss.hasPermi('account:dealer:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取荷官列表")
    public TableDataInfo list(SysDealerSearch dealerSearch) {
        startPage();
        List<SysDealer> list = dealerService.selectDealerList(dealerSearch);
        return getDataTable(list);
    }

    @GetMapping("/checkUserName")
    @ApiOperation(value = "校验荷官工号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "工号", dataType = "String", required = true, paramType = "path")
    })
    public AjaxResult checkUserName(String userName) {
        SysDealer sysDealer = dealerService.selectDealerByUserName(userName);
        if (sysDealer==null || StringUtils.isEmpty(sysDealer.getUserName())) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('account:dealer:list')")
    @GetMapping("/add")
    @ApiOperation(value = "新增荷官")
    public AjaxResult add(SysDealer dealer) {
        SysDealer sysDealer = dealerService.selectDealerByUserName(dealer.getUserName());
        if (StringUtils.isNotNull(sysDealer.getUserName())) {
            return AjaxResult.error("工号已存在");
        }
        dealer.setCreateBy(SecurityUtils.getUsername());
        dealerService.addDealer(dealer);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('account:dealer:list')")
    @GetMapping("/edit")
    @ApiOperation(value = "编辑荷官")
    public AjaxResult edit(SysDealer dealer) {
        dealer.setUpdateBy(SecurityUtils.getUsername());
        dealerService.editDealer(dealer);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('account:dealer:list')")
    @GetMapping("/delete")
    @ApiOperation(value = "删除荷官")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", dataType = "Long", required = true, paramType = "path")
    })
    public AjaxResult delete(Long userId) {
        dealerService.deleteDealer(userId);
        return AjaxResult.success();
    }

}
