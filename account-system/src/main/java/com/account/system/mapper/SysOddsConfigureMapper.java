package com.account.system.mapper;


import com.account.system.domain.SysOddsConfigure;


/**
 * 赔率设置数据层
 */
public interface SysOddsConfigureMapper{

    SysOddsConfigure selectConfigInfo();

    int updateOddsConfig(SysOddsConfigure oddsConfigure);
}
