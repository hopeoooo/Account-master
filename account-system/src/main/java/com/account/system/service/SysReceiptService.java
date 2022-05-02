package com.account.system.service;


import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;

import java.util.List;

/**
 * 收码报表 服务层
 */
public interface SysReceiptService
{
    List<SysReceipt> selectReceiptList(ReceiptReportSearch receiptReportSearch);
}
