package com.account.system.mapper;

import com.account.system.domain.SysPorint;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 点码
 */
public interface PorintMapper {

    void savePorint(SysPorint sysPorint);

    void updatePorint(@Param("id") int id, @Param("chip") BigDecimal tableChip,
                      @Param("cash") BigDecimal tableCash, @Param("insurance") BigDecimal tableInsurance, @Param("water") BigDecimal water);
}
