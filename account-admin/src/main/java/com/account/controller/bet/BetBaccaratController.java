package com.account.controller.bet;

import com.account.common.core.domain.AjaxResult;
import com.account.common.enums.ResultEnum;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.ServletUtils;
import com.account.common.utils.StringUtils;
import com.account.common.utils.ip.IpUtils;
import com.account.framework.manager.AsyncManager;
import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysOddsConfigure;
import com.account.system.domain.SysTableManagement;
import com.account.system.service.BetService;
import com.account.system.service.SysOddsConfigureService;
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

    @Autowired
    SysOddsConfigureService sysOddsConfigureService;

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
            BigDecimal payout = payout(bet, gameResult);//派彩
            if (0 == bet.getInteger("type")) chip = chip.add(payout);
            bet.put("chip", chip);
            bet.put("payout", payout);
        });
        SysGameResult sysGameResult = new SysGameResult(sysTableManagement);
        sysGameResult.setGameResult(gameResult);
        sysGameResult.setCreateBy(SecurityUtils.getUsername());
        AsyncManager.me().execute(new TimerTask() {
            @Override
            public void run() {
                betService.saveGameResult(sysGameResult);
                betService.updateGameNum(sysTableManagement.getId());
            }
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
        String gameResult = betService.selectGameResult(sysTableManagement);
        //注单录入
        JSONArray bets = jsonObject.getJSONArray("bet");
        betService.saveBet(sysTableManagement, gameResult, bets);
        return AjaxResult.success();
    }

    //计算派奖
    private BigDecimal payout(JSONObject bet, String gameResult) {
        BigDecimal payout = new BigDecimal(0);//派彩
        SysOddsConfigure sysOddsConfigure = sysOddsConfigureService.selectConfigInfo();
        // 1：闲 4：庄 7：和
        // 5：闲对 8：庄对
        // 9：大 6：小
        // 0: 闲保险 3:庄保险
        String[] betOption = {"1", "4", "7", "5", "8", "9", "6"};
        String[] odds = {sysOddsConfigure.getBaccaratPlayerWin(),
                sysOddsConfigure.getBaccaratBankerWin(),
                sysOddsConfigure.getBaccaratTieWin(),
                sysOddsConfigure.getBaccaratPlayerPair(),
                sysOddsConfigure.getBaccaratBankerPair(),
                sysOddsConfigure.getBaccaratLarge(),
                sysOddsConfigure.getBaccaratSmall(),
        };
        for (int i = 0; i < odds.length; i++) {
            BigDecimal amount = bet.getBigDecimal(betOption[i]);
            if (amount != null) {
                if (gameResult.contains(betOption[i])) {
                    payout = payout.add(amount.multiply(new BigDecimal(odds[i])));
                } else {
                    payout = payout.subtract(amount);
                }
            }
        }

        //3:庄保险
        BigDecimal banker = bet.getBigDecimal("3");
        if (banker != null) {
            if (gameResult.contains("1")) {
                payout = payout.add(banker);
            } else {
                payout = payout.subtract(banker);
            }
        }

        //0: 闲保险
        BigDecimal player = bet.getBigDecimal("0");
        if (player != null) {
            if (gameResult.contains("4")) {
                payout = payout.add(player);
            } else {
                payout = payout.subtract(player);
            }
        }

        return payout;
    }
}
