package com.account.controller.bet;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.BetRepair;
import com.account.system.domain.BetUpdate;
import com.account.system.domain.SysMembers;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
import com.account.system.service.BetService;
import com.account.system.service.SysMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hope
 * @since 2022/4/21
 */
@RestController
@RequestMapping("/bet/record")
@Api(tags = "注单记录")
public class BetRecordController extends BaseController {
    @Autowired
    BetService betService;

    @Autowired
    SysMembersService sysMembersService;
    /**
     * 注单记录
     * @return
     */
    @PreAuthorize("@ss.hasPermi('bet:record:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询注单记录列表")
    public TableDataInfo list(BetSearch betSearch){
        startPage();
        List<BetInfoVo> sysAccessCodeVos = betService.selectBetInfoList(betSearch);
        if(StringUtils.isNotEmpty(sysAccessCodeVos)){
            List<Long> betId = sysAccessCodeVos.stream().map(BetInfoVo::getBetId).collect(Collectors.toList());
            //玩法
            Map<Long, List<BetInfoOptionVo>> betOptionList = betService.selectBetOptionList(betId);

            sysAccessCodeVos.forEach(info ->{
                List<BetInfoOptionVo> betInfoOptionVos = betOptionList.get(info.getBetId());
                if (betInfoOptionVos!=null){
                    info.setOption(betInfoOptionVos);
                }

            });
        }

        return getDataTable(sysAccessCodeVos);
    }

    @PreAuthorize("@ss.hasPermi('bet:record:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    public AjaxResult total(BetSearch betSearch){
        Map map = betService.selectBetTotal(betSearch);
        return AjaxResult.success(map);
    }

    @PreAuthorize("@ss.hasPermi('bet:record:list')")
    @PostMapping("/repair")
    @ApiOperation(value = "注单补录")
    public AjaxResult repair(BetRepair betRepair){
        SysMembers sysMembers = sysMembersService.selectmembersByCard(betRepair.getCard());
        if(StringUtils.isNull(sysMembers)){
            return AjaxResult.error("卡号："+betRepair.getCard()+" 不存在");
        }
        betService.repairBet(betRepair);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('bet:record:list')")
    @PostMapping("/edit")
    @ApiOperation(value = "注单修改")
    public AjaxResult edit(BetUpdate betUpdate){
        SysMembers sysMembers = sysMembersService.selectmembersByCard(betUpdate.getCard());
        if(StringUtils.isNull(sysMembers)){
            return AjaxResult.error("卡号："+betUpdate.getCard()+" 不存在");
        }
        betUpdate.setUpdateBy(SecurityUtils.getUsername());
        betService.updateBet(betUpdate);
        return AjaxResult.success();
    }
}
