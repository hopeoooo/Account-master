package com.account.system.service;


import com.account.system.domain.PorintUpdate;
import com.account.system.domain.SysPorint;
import com.account.system.domain.search.PorintUpdateSearch;
import com.account.system.domain.search.ReceiptReportSearch;

import java.util.List;
import java.util.Map;


public interface SysPorintService {

    List<SysPorint> selectPorintList(ReceiptReportSearch receiptReportSearch);

    Map selectPorintCount(ReceiptReportSearch receiptReportSearch);

    Map porintReckon(PorintUpdate porintUpdate);

    void editPorint(PorintUpdate porintUpdate);

    List<Map> selectPorintUpdateList(PorintUpdateSearch porintUpdateSearch);
}
