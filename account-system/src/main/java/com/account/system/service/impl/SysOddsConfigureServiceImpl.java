package com.account.system.service.impl;

import com.account.system.domain.SysOddsConfigure;
import com.account.system.mapper.SysOddsConfigureMapper;
import com.account.system.service.ISysOddsConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 赔率设置服务层实现
 */
@Service
public class SysOddsConfigureServiceImpl implements ISysOddsConfigureService {
    @Autowired
    private SysOddsConfigureMapper oddsConfigureMapper;

    /**
     * 查询赔率列表
     * @return
     */
    @Override
    public SysOddsConfigure selectConfigInfo() {
        return oddsConfigureMapper.selectConfigInfo();
    }

    @Override
    @Transactional
    public int updateOddsConfig(SysOddsConfigure oddsConfigure){
        return oddsConfigureMapper.updateOddsConfig(oddsConfigure);
    }
}
