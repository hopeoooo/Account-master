package com.account.system.service.impl;

import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysTableManagement;
import com.account.system.mapper.BetMpper;
import com.account.system.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hope
 * @since 2022/4/22
 */
@Service
public class BetServiceImpl implements BetService {

    @Autowired
    BetMpper betMpper;
    @Override
    public SysTableManagement getTableByIp(String ip) {
        return betMpper.getTableByIp(ip);
    }

    @Override
    public void saveGameResult(SysGameResult sysGameResult) {
        betMpper.saveGameResult(sysGameResult);
    }

    @Override
    public void updateGameNum(Long id) {
        betMpper.updateGameNum(id);
    }
}
