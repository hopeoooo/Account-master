package com.account.controller.bet;

import com.account.common.core.domain.AjaxResult;
import com.account.common.enums.ResultEnum;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.ServletUtils;
import com.account.common.utils.StringUtils;
import com.account.common.utils.ip.IpUtils;
import com.account.framework.manager.AsyncManager;
import com.account.system.domain.Reckon;
import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysTableManagement;
import com.account.system.service.BetService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author hope
 * @since 2022/4/21
 */
@RestController
@RequestMapping("/bet/baccarat")
@Api(tags = "百家乐注单录入")
public class BetBaccaratController {

    @Autowired
    BetService betService;

    @PreAuthorize("@ss.hasPermi('bet:baccarat:list')")
    @PostMapping("/info")
    @ApiOperation(value = "桌台信息")
    public AjaxResult info() {
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        Map map = new HashMap();
        map.put("tableId", sysTableManagement.getTableId());
        map.put("bootNum", sysTableManagement.getBootNum());
        map.put("gameNum", sysTableManagement.getGameNum());
        map.put("chip", sysTableManagement.getChipPointBase().add(sysTableManagement.getChip()));
        map.put("cash", sysTableManagement.getCashPointBase().add(sysTableManagement.getCash()));
        map.put("total", sysTableManagement.getChipPointBase().add(sysTableManagement.getChip())
                .add(sysTableManagement.getCashPointBase().add(sysTableManagement.getCash())));
        return AjaxResult.success(map);
    }

    @PreAuthorize("@ss.hasPermi('bet:baccarat:list')")
    @PostMapping("/game")
    @ApiOperation(value = "赛果列表")
    public AjaxResult game() {
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        List<Map> list = betService.getGameResults(sysTableManagement);
        return AjaxResult.success(list);
    }


    @PreAuthorize("@ss.hasPermi('bet:baccarat:list')")
    @PostMapping("/open")
    @ApiOperation(value = "开牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "json格式字符串 eq:{\"gameResult\":\"159\",\"bet\":[{\"card\":\"123456\",\"type\":0,\"1\":300,\"5\":200}]}",
                    dataType = "string", required = true, paramType = "path")
    })
    public AjaxResult open(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        String gameResult = jsonObject.getString("gameResult");
        if (StringUtils.isEmpty(ResultEnum.getResultEnum(gameResult))) {
            return AjaxResult.error("赛果格式错误");
        }
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        //注单计算
        JSONArray bets = jsonObject.getJSONArray("bet");
        bets.forEach(b -> {
            JSONObject bet = (JSONObject) b;
            String card = bet.getString("card");
            BigDecimal chip = betService.selectMembersChip(card);
            BigDecimal payout = betService.getPayOut(bet,gameResult);//派彩
            if (0 == bet.getInteger("type")) chip = chip.add(payout);
            bet.put("chip", chip);
            bet.put("payout", payout);
        });
        return AjaxResult.success(jsonObject);
    }

    @PreAuthorize("@ss.hasPermi('bet:baccarat:list')")
    @GetMapping("/input")
    @ApiOperation(value = "录入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "json格式字符串 eq:{\"gameResult\":\"159\",\"bet\":[{\"card\":\"123456\",\"type\":0,\"1\":300,\"5\":200}]}",
                    dataType = "string", required = true, paramType = "path")
    })
    public AjaxResult input(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        String gameResult = jsonObject.getString("gameResult");
        if (StringUtils.isEmpty(ResultEnum.getResultEnum(gameResult))) {
            return AjaxResult.error("赛果格式错误");
        }
        //注单录入
        JSONArray bets = jsonObject.getJSONArray("bet");
        sysTableManagement.setCreateBy(SecurityUtils.getUsername());
        SysGameResult sysGameResult = new SysGameResult(sysTableManagement);
        sysGameResult.setGameResult(gameResult);
        sysGameResult.setCreateBy(SecurityUtils.getUsername());
        AsyncManager.me().execute(new TimerTask() {
            @Override
            public void run() {
                betService.saveBet(sysTableManagement, gameResult, bets);
                betService.saveGameResult(sysGameResult);
                betService.updateGameNum(sysTableManagement.getId());
            }
        });
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('bet:baccarat:list')")
    @GetMapping("/reckon")
    @ApiOperation(value = "点码||收码 计算差距")
    public AjaxResult reckon(Reckon reckon) {
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        Map map;
        if(reckon.getType()==0){
            map = betService.pointChip(reckon,sysTableManagement);
        }else {
            map = betService.receiptChip(reckon,sysTableManagement);
        }
        return AjaxResult.success(map);
    }

    @PreAuthorize("@ss.hasPermi('bet:baccarat:list')")
    @GetMapping("/edit")
    @ApiOperation(value = "点码||收码 确认修改")
    public AjaxResult edit(Reckon reckon) {
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        if(reckon.getType()==0){
            betService.editChip(reckon,sysTableManagement);
        }else {
            betService.receiptEditChip(reckon,sysTableManagement);
        }
        return AjaxResult.success();
    }
}
