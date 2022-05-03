package com.account.system.service;


import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;

import java.util.List;


public interface SysPorintService {

    List<SysReceipt> selectPorintList(ReceiptReportSearch receiptReportSearch);

}
