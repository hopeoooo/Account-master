package com.account.system.service;


import com.account.system.domain.PorintUpdate;
import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;

import java.util.List;
import java.util.Map;


public interface SysPorintService {

    List<SysReceipt> selectPorintList(ReceiptReportSearch receiptReportSearch);

    Map selectPorintCount(ReceiptReportSearch receiptReportSearch);

    Map porintReckon(PorintUpdate porintUpdate);
}
