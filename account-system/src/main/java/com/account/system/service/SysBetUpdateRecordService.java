package com.account.system.service;

import com.account.system.domain.BetUpdate;
import com.account.system.domain.SysBet;
import com.account.system.domain.SysBetInfo;
import com.account.system.domain.search.BetSearch;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hope
 * @since 2022/5/2
 */
public interface SysBetUpdateRecordService {

    void saveResultRecord(SysBet sysBet, String gameResult, Long betId, List<SysBetInfo> sysBetInfos, BigDecimal win);


    void saveBetRecord(BetUpdate betUpdate, List<SysBetInfo> oldBetInfos, List<SysBetInfo> newBetInfos,String gameResult);

    List selectBetUpdateList(BetSearch betSearch,String language);
}
