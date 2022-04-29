package com.account.system.mapper;

import com.account.system.domain.search.SysWaterSearch;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 洗码量
 *
 * @author hope
 */
public interface SysWaterMapper {

    void saveMembersWater(@Param("card") String card, @Param("water") BigDecimal water, @Param("waterAmount") BigDecimal waterAmount);


    int updateMembersWater(SysWaterSearch waterSearch);

    int updateMembersWaterList(List<SysWaterSearch> waterSearch);

    Map selectMembersWaterInfo(@Param("card") String card);

}
