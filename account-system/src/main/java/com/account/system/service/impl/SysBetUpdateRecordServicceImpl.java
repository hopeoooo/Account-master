package com.account.system.service.impl;

import com.account.system.domain.SysBet;
import com.account.system.service.SysBetUpdateRecordServicce;
import org.springframework.stereotype.Service;

/**
 * @author hope
 * @since 2022/4/22
 */
@Service
public class SysBetUpdateRecordServicceImpl implements SysBetUpdateRecordServicce {

    /**
     * 修改赛果
     */
    public void saveUpdateResultRecord(SysBet sysBet, String gameResult, Long betId) {
        if(!sysBet.getBetId().equals(betId)){

        }
    }
}
