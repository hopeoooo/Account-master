package com.account.system.mapper;

import com.account.system.domain.SysPorint;
import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 点码
 */
public interface PorintMapper {

    void savePorint(SysPorint sysPorint);

    void updatePorint(@Param("id") int id, @Param("chip") BigDecimal tableChip, @Param("cash") BigDecimal tableCash
            , @Param("insurance") BigDecimal tableInsurance, @Param("water") BigDecimal water);

    @Select("select id from sys_porint where table_id = #{tableId} and boot_num = #{bootNum} and version = #{version} limit 0,1")
    SysPorint getPorint(@Param("tableId") Long tableId, @Param("bootNum") Long bootNum, @Param("version") Long version);

    List<SysReceipt> selectPorintList(ReceiptReportSearch receiptReportSearch);

    Map selectPorintCount(ReceiptReportSearch receiptReportSearch);

    SysPorint selectPorint(@Param("id") Long id);
}
