package com.account.controller.bet;

import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.ServletUtils;
import com.account.common.utils.StringUtils;
import com.account.common.utils.ip.IpUtils;
import com.account.framework.manager.AsyncManager;
import com.account.system.domain.Reckon;
import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysMembers;
import com.account.system.domain.SysTableManagement;
import com.account.system.domain.search.BetSearch;
import com.account.system.domain.vo.BetInfoOptionVo;
import com.account.system.domain.vo.BetInfoVo;
import com.account.system.service.BetService;
import com.account.system.service.SysMembersService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * @author hope
 * @since 2022/5/2
 */
@RestController
@RequestMapping("/bet/sangong")
@Api(tags = "三公注单录入")
public class BetSanGongController extends BaseController {

    @Autowired
    BetService betService;

    @Autowired
    SysMembersService sysMembersService;

    @PreAuthorize("@ss.hasPermi('bet:sangong:list')")
    @PostMapping("/info")
    @ApiOperation(value = "桌台信息")
    public AjaxResult info() {
        //根据ip获取台桌信息
        String ip = IpUtils.checkIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,4l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误 ip:" + ip);
        }
        Map map = new HashMap();
        map.put("tableId", sysTableManagement.getTableId());
        map.put("version", sysTableManagement.getVersion());
        map.put("bootNum", sysTableManagement.getBootNum());
        map.put("gameNum", sysTableManagement.getGameNum());

        map.put("chip", sysTableManagement.getChip());
        map.put("cash", sysTableManagement.getCash());
        map.put("total", sysTableManagement.getChip()
                .add(sysTableManagement.getCash()));
        map.put("chipTh", sysTableManagement.getChipTh());
        map.put("cashTh", sysTableManagement.getCashTh());
        map.put("totalTh", sysTableManagement.getChipTh()
                .add(sysTableManagement.getCashTh()));

        return AjaxResult.success(map);
    }

    @PreAuthorize("@ss.hasPermi('bet:sangong:list')")
    @PostMapping("/record")
    @ApiOperation(value = "注单记录")
    public Object record() {
        //根据ip获取台桌信息
        String ip = IpUtils.checkIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,4l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        startPage();
        BetSearch betSearch = new BetSearch(sysTableManagement.getTableId(),
                sysTableManagement.getGameId(),
                sysTableManagement.getBootNum(),
                sysTableManagement.getVersion());
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

    @PreAuthorize("@ss.hasPermi('bet:sangong:list')")
    @PostMapping("/open")
    @ApiOperation(value = "开牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "json格式字符串 eq:{\"bet\":[{\"card\":\"123456\",\"type\":0,\"输\":300}]}",
                    dataType = "string", required = true, paramType = "path")
    })
    public AjaxResult open(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        //根据ip获取台桌信息
        String ip = IpUtils.checkIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,4l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        //注单计算
        JSONArray bets = jsonObject.getJSONArray("bet");
        for (int i = 0; i < bets.size(); i++) {
            JSONObject bet = bets.getJSONObject(i);
            String card = bet.getString("card");
            if(StringUtils.isEmpty(card)){
                continue;
            }
            SysMembers sysMembers = sysMembersService.selectmembersByCard(card);
            if(StringUtils.isNull(sysMembers)){
                return AjaxResult.error("卡号："+card+" 不存在");
            }
            BigDecimal payout = betService.getPayOut(bet,null,sysTableManagement.getGameId());//派彩
            bet.put("payout", payout);
        }
        return AjaxResult.success(jsonObject);
    }

    @PreAuthorize("@ss.hasPermi('bet:sangong:list')")
    @PostMapping("/input")
    @ApiOperation(value = "录入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "json格式字符串 eq:{\"bet\":[{\"card\":\"123456\",\"type\":0,\"输\":300}]}",
                    dataType = "string", required = true, paramType = "path")
    })
    public AjaxResult input(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        //根据ip获取台桌信息
        String ip = IpUtils.checkIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,4l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        //注单录入
        JSONArray bets = jsonObject.getJSONArray("bet");
        sysTableManagement.setCreateBy(SecurityUtils.getUsername());
        AsyncManager.me().execute(new TimerTask() {
            public void run() {
                betService.saveBet(sysTableManagement, null, bets);
            }
        });
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('bet:sangong:list')")
    @PostMapping("/reckon")
    @ApiOperation(value = "点码||收码 计算差距")
    public AjaxResult reckon(Reckon reckon) {
        String ip = IpUtils.checkIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,4l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        Map map;
        if (reckon.getType() == 0) {
            map = betService.pointChip(reckon, sysTableManagement);
        } else {
            map = betService.receiptChip(reckon, sysTableManagement);
        }
        return AjaxResult.success(map);
    }

    @PreAuthorize("@ss.hasPermi('bet:sangong:edit')")
    @PostMapping("/edit")
    @ApiOperation(value = "点码||收码 确认修改")
    public AjaxResult edit(Reckon reckon) {
        String ip = IpUtils.checkIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,4l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        if (reckon.getType() == 0) {
            betService.editChip(reckon, sysTableManagement);
        } else {
            betService.receiptEditChip(reckon, sysTableManagement);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('bet:sangong:list')")
    @PostMapping("/next")
    @ApiOperation(value = "下一局")
    public AjaxResult next() {
        String ip = IpUtils.checkIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,4l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        betService.nextGameNum(sysTableManagement);
        return AjaxResult.success();
    }
}
