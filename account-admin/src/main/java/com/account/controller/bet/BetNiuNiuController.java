package com.account.controller.bet;

import com.account.common.core.domain.AjaxResult;
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
 * @since 2022/5/2
 */
@RestController
@RequestMapping("/bet/niuniu")
@Api(tags = "牛牛注单录入")
public class BetNiuNiuController {

    @Autowired
    BetService betService;

    @PreAuthorize("@ss.hasPermi('bet:niuniu:list')")
    @PostMapping("/info")
    @ApiOperation(value = "桌台信息")
    public AjaxResult info() {
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,3l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误 ip:" + ip);
        }
        Map map = new HashMap();
        map.put("tableId", sysTableManagement.getTableId());
        map.put("version", sysTableManagement.getVersion());
        map.put("bootNum", sysTableManagement.getBootNum());
        map.put("gameNum", sysTableManagement.getGameNum());
        map.put("chip", sysTableManagement.getChipPointBase().add(sysTableManagement.getChip()));
        map.put("cash", sysTableManagement.getCashPointBase().add(sysTableManagement.getCash()));
        map.put("total", sysTableManagement.getChipPointBase().add(sysTableManagement.getChip())
                .add(sysTableManagement.getCashPointBase().add(sysTableManagement.getCash())));
        return AjaxResult.success(map);
    }

    @PreAuthorize("@ss.hasPermi('bet:niuniu:list')")
    @PostMapping("/game")
    @ApiOperation(value = "赛果列表")
    public AjaxResult game() {
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,3l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        List<Map> list = betService.getGameResults(sysTableManagement);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('bet:niuniu:list')")
    @PostMapping("/update")
    @ApiOperation(value = "路珠修改")
    public AjaxResult update(SysGameResult sysGameResult) {
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,3l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        sysGameResult.setUpdateBy(SecurityUtils.getUsername());
        AsyncManager.me().execute(new TimerTask() {
            public void run() {
                betService.updateGameResult(sysGameResult);
            }
        });
        return AjaxResult.success();
    }


    @PreAuthorize("@ss.hasPermi('bet:niuniu:list')")
    @PostMapping("/open")
    @ApiOperation(value = "开牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "json格式字符串 eq:{\"bet\":[{\"card\":\"123456\",\"type\":0,\"输\":300}]}",
                    dataType = "string", required = true, paramType = "path")
    })
    public AjaxResult open(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,3l);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }
        //注单计算
        JSONArray bets = jsonObject.getJSONArray("bet");
        bets.forEach(b -> {
            JSONObject bet = (JSONObject) b;
            String card = bet.getString("card");
            BigDecimal chip = betService.selectMembersChip(card);
            BigDecimal payout = betService.getPayOut(bet, null,sysTableManagement.getGameId());//派彩
            if (0 == bet.getInteger("type")) chip = chip.add(payout);
            bet.put("chip", chip);
            bet.put("payout", payout);
        });
        return AjaxResult.success(jsonObject);
    }

    @PreAuthorize("@ss.hasPermi('bet:niuniu:list')")
    @PostMapping("/input")
    @ApiOperation(value = "录入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "json格式字符串 eq:{\"bet\":[{\"card\":\"123456\",\"type\":0,\"输\":300}]}",
                    dataType = "string", required = true, paramType = "path")
    })
    public AjaxResult input(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,3l);
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

    @PreAuthorize("@ss.hasPermi('bet:niuniu:list')")
    @PostMapping("/reckon")
    @ApiOperation(value = "点码||收码 计算差距")
    public AjaxResult reckon(Reckon reckon) {
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,3l);
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

    @PreAuthorize("@ss.hasPermi('bet:niuniu:list')")
    @PostMapping("/edit")
    @ApiOperation(value = "点码||收码 确认修改")
    public AjaxResult edit(Reckon reckon) {
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,3l);
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
}
