package com.account.system.service.impl;

import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;
import com.account.system.mapper.ReceiptMapper;
import com.account.system.service.SysReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 筹码变动明细 服务层实现
 */
@Service
public class SysReceiptServiceImpl implements SysReceiptService {
    @Autowired
    private ReceiptMapper receiptMapper;

    @Override
    public List<SysReceipt> selectReceiptList(ReceiptReportSearch receiptReportSearch) {
        return receiptMapper.selectReceiptList(receiptReportSearch);
    }
}
