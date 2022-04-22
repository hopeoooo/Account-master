package com.account.controller.bet;

import com.account.common.core.domain.AjaxResult;
import com.account.common.enums.ResultEnum;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.ServletUtils;
import com.account.common.utils.StringUtils;
import com.account.common.utils.ip.IpUtils;
import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysTableManagement;
import com.account.system.service.BetService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/open")
    @ApiOperation(value = "开牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameResult", value = "赛果 1：闲 4：庄 7：和; 2：庄闲对 5：闲对 8：庄对; 9：大 6：小",
                    dataType = "string", required = true, paramType = "path")
    })
    public AjaxResult open(JSONObject jsonObject) {
        String gameResult = jsonObject.getString("gameResult");
        if(StringUtils.isEmpty(ResultEnum.getResultEnum(gameResult))){
            return AjaxResult.error("赛果格式错误");
        }
        //根据ip获取台桌信息
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip);
        if(StringUtils.isNull(sysTableManagement)){
            return AjaxResult.error("ip地址错误");
        }
        SysGameResult sysGameResult = new SysGameResult();
        sysGameResult.setGameResult(gameResult);
        sysGameResult.setGameId(sysTableManagement.getGameId());
        sysGameResult.setTableId(sysTableManagement.getTableId());
        sysGameResult.setBootNum(sysTableManagement.getBootNum());
        sysGameResult.setGameNum(sysTableManagement.getGameNum()+1);
        sysGameResult.setCreateBy(SecurityUtils.getUsername());
        //保存赛果
        betService.saveGameResult(sysGameResult);
        //注单
        JSONArray bets = jsonObject.getJSONArray("bet");

        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('bet:baccarat:list')")
    @GetMapping("/input")
    @ApiOperation(value = "录入")
    public AjaxResult input(String gameResult) {

        return AjaxResult.success();
    }
}
