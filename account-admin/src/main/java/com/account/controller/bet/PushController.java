package com.account.controller.bet;

import com.account.common.core.domain.AjaxResult;
import com.account.common.core.redis.RedisCache;
import com.account.common.utils.ServletUtils;
import com.account.common.utils.StringUtils;
import com.account.common.utils.ip.IpUtils;
import com.account.system.domain.SysTableManagement;
import com.account.system.service.BetService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hope
 * @since 2022/5/4
 */
@RestController
@RequestMapping("/bet/push")
@Api(tags = "注单投屏")
public class PushController {

    @Autowired
    BetService betService;

    @Autowired
    RedisCache redisCache;

    @PostMapping("/save")
    @ApiOperation(value = "注单保存")
    public AjaxResult save(@RequestBody JSONObject jsonObject) {
        //根据ip获取台桌信息
        String ip = IpUtils.checkIpAddr(ServletUtils.getRequest());
        SysTableManagement sysTableManagement = betService.getTableByIp(ip,null);
        if (StringUtils.isNull(sysTableManagement)) {
            return AjaxResult.error("ip地址错误");
        }

        redisCache.setCacheObject("bet:push:"+sysTableManagement.getTableId(),jsonObject);
        return AjaxResult.success();
    }

    @PostMapping("/get")
    @ApiOperation(value = "注单查询")
    public AjaxResult get(Long tableId) {
        return AjaxResult.success("OJBK",redisCache.getCacheObject("bet:push:"+tableId));
    }
}
