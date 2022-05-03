package com.account.system.service.impl;


import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;
import com.account.system.mapper.PorintMapper;
import com.account.system.service.SysPorintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPorintServiceImpl implements SysPorintService {

    @Autowired
    PorintMapper porintMapper;

    public List<SysReceipt> selectPorintList(ReceiptReportSearch receiptReportSearch) {
        return porintMapper.selectPorintList(receiptReportSearch);
    }
}
