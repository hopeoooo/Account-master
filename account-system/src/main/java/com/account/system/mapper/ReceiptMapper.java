package com.account.system.mapper;

import com.account.system.domain.SysReceipt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * 收码
 */
public interface ReceiptMapper {

    void updateReceipt(@Param("id") int id, @Param("chip") BigDecimal tableChip, @Param("cash") BigDecimal tableCash
            , @Param("insurance") BigDecimal tableInsurance, @Param("water") BigDecimal water);

    @Select("select id from sys_receipt where table_id = #{tableId} and version = #{version} limit 0,1")
    SysReceipt getReceipt(@Param("tableId") Long tableId, @Param("version") Long version);
}
