package com.account.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 收码
 */
public interface ReceiptMapper {

    void updateReceipt(@Param("id") int id, @Param("chip") BigDecimal tableChip, @Param("cash") BigDecimal tableCash
            , @Param("insurance") BigDecimal tableInsurance, @Param("water") BigDecimal water);
}
