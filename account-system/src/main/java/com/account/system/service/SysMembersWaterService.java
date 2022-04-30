package com.account.system.service;


import com.account.system.domain.search.SysWaterSearch;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 洗码 服务层
 *
 * @author hope
 */
public interface SysMembersWaterService {
    int updateMembersWater( SysWaterSearch waterSearch);

    Map selectMembersWaterInfo( String card);

    void updateMembersWaterList(List<SysWaterSearch> waterSearch);
}
