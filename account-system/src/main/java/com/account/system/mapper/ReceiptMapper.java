package com.account.system.mapper;

import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;
import com.account.system.domain.search.ReportSearch;
import com.account.system.domain.search.WinLoseReportSearch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 收码
 */
public interface ReceiptMapper {

    void updateReceipt(@Param("id") int id, @Param("chip") BigDecimal tableChip, @Param("cash") BigDecimal tableCash
            , @Param("insurance") BigDecimal tableInsurance, @Param("water") BigDecimal water);

    void updateReceiptTh(@Param("id") int id, @Param("chip") BigDecimal tableChip, @Param("cash") BigDecimal tableCash
            , @Param("insurance") BigDecimal tableInsurance, @Param("water") BigDecimal water);

    @Select("select id from sys_receipt where table_id = #{tableId} and version = #{version} limit 0,1")
    SysReceipt getReceipt(@Param("tableId") Long tableId, @Param("version") Long version);

    List<SysReceipt> selectReceiptList(ReceiptReportSearch receiptReportSearch);

    List selectReceiptTimes(WinLoseReportSearch reportSearch);

    List selectReceiptTablePlumbingTimes(WinLoseReportSearch reportSearch);

    List<Map> selectWinLoseListByReceiptTimes(@Param("reportSearch") WinLoseReportSearch reportSearch, @Param("list") List receiptTimes);

    Map selectWinLoseTotalByReceiptTimes(@Param("reportSearch") WinLoseReportSearch reportSearch, @Param("list") List receiptTimes);

    List selectDailyReportListByReceiptTimes(@Param("reportSearch") ReportSearch reportSearch, @Param("list") List receiptTimes);

    List selectTodayReceiptTimes();

    Map selectDailyReportTotalByReceiptTimes(@Param("reportSearch") ReportSearch reportSearch, @Param("list") List receiptTimes);
}
