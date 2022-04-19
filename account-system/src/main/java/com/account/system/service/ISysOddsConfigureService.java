package com.account.system.service;

import com.account.system.domain.SysOddsConfigure;


/**
 * 赔率设置服务层
 */
public interface ISysOddsConfigureService
{
    /**
     * 查询列表
     * @return
     */
    SysOddsConfigure selectConfigInfo();

    int updateOddsConfig(SysOddsConfigure oddsConfigure);


}
