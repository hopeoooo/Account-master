package com.account.controller.account;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysMembers;
import com.account.system.domain.search.SysMembersSearch;
import com.account.system.service.SysMembersService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author hope
 * @since 2022/4/19
 * 会员管理
 */
@RestController
@RequestMapping("/account/mambers")
@Api(tags = "会员管理")
public class MembersController extends BaseController {

    @Autowired
    SysMembersService sysMembersService;

    /**
     * 获取会员列表
     */
    @PreAuthorize("@ss.hasPermi('account:mambers:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取会员列表")
    public AjaxResult list(SysMembersSearch sysMembersSearch) {
        startPage();
        List<Map> list = sysMembersService.selectMembersList(sysMembersSearch);
        Map map = sysMembersService.selectMembersCount(sysMembersSearch);
        return AjaxResult.success().put("list",getDataTable(list)).put("count",map);
    }

    /**
     * 会员详情
     */
    @PreAuthorize("@ss.hasPermi('account:mambers:list')")
    @GetMapping("/info")
    @ApiOperation(value = "会员详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "Long", required = true, paramType = "path")
    })
    public AjaxResult info(Long id) {
        return AjaxResult.success(sysMembersService.selectMembersInfo(id));
    }

    /**
     * 新增会员
     */
    @PreAuthorize("@ss.hasPermi('account:mambers:list')")
    @GetMapping("/add")
    @ApiOperation(value = "新增会员")
    public AjaxResult add(SysMembers sysMembers) {
        if (StringUtils.isNotNull(sysMembersService.selectmembersByCard(sysMembers.getCard()))) {
            return AjaxResult.error("新增会员'" + sysMembers.getCard() + "'失败，卡号已存在");
        }
        sysMembers.setCreateBy(SecurityUtils.getUsername());
        sysMembersService.addMembers(sysMembers);
        return AjaxResult.success();
    }

    /**
     * 编辑会员
     */
    @PreAuthorize("@ss.hasPermi('account:mambers:list')")
    @GetMapping("/edit")
    @ApiOperation(value = "编辑会员")
    public AjaxResult edit(SysMembers sysMembers) {
        SysMembers oldMembers = sysMembersService.selectmembersByCard(sysMembers.getCard());
        if (StringUtils.isNotNull(oldMembers) && sysMembers.getId() != oldMembers.getId()) {
            return AjaxResult.error("修改会员'" + sysMembers.getCard() + "'失败，卡号已存在");
        }
        sysMembers.setUpdateBy(SecurityUtils.getUsername());
        sysMembersService.editMembers(sysMembers);
        return AjaxResult.success();
    }

    /**
     * 删除会员
     */
    @PreAuthorize("@ss.hasPermi('account:mambers:list')")
    @GetMapping("/delete")
    @ApiOperation(value = "删除会员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户id，多个用逗号拼接", dataType = "Long", required = true, paramType = "path")
    })
    public AjaxResult delete(String ids) {
        sysMembersService.deleteMembers(ids);
        return AjaxResult.success();
    }

    /**
     * 获取默认洗码比例
     */
    @PreAuthorize("@ss.hasPermi('account:mambers:list')")
    @GetMapping("/odds")
    @ApiOperation(value = "获取默认洗码比例")
    public AjaxResult odds() {
        return AjaxResult.success(sysMembersService.getOddsConfig());
    }

}
