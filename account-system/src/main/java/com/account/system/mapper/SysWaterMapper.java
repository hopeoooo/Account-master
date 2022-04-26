package com.account.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 洗码量
 *
 * @author hope
 */
public interface SysWaterMapper {

    void saveMembersWater(@Param("card") String card, @Param("water") BigDecimal water, @Param("waterAmount") BigDecimal waterAmount);

}
