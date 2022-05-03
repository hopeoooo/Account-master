package com.account.system.mapper;

import com.account.system.domain.SysBetUpdateRecord;
import com.account.system.domain.search.BetSearch;

import java.util.List;

/**
 * @author hope
 * @since 2022/5/2
 */
public interface SysBetUpdateRecordMapper {

    void saveUpdateRecord(SysBetUpdateRecord sysBetUpdateRecord);

    List selectBetUpdateList(BetSearch betSearch);
}
