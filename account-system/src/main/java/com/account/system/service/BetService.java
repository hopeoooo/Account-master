package com.account.system.service;

import com.account.system.domain.SysGameResult;
import com.account.system.domain.SysTableManagement;

/**
 * @author hope
 * @since 2022/4/22
 */
public interface BetService {

    SysTableManagement getTableByIp(String ip);

    void saveGameResult(SysGameResult sysGameResult);

    void updateGameNum(Long id);
}
