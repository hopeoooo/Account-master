package com.account.system.service;

import com.account.system.domain.SysBet;

/**
 * @author hope
 * @since 2022/5/2
 */
public interface SysBetUpdateRecordServicce {

    void saveUpdateResultRecord(SysBet sysBet, String gameResult, Long betId);

}
