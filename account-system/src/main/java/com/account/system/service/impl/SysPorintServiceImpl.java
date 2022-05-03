package com.account.system.service.impl;


import com.account.system.domain.PorintUpdate;
import com.account.system.domain.SysPorint;
import com.account.system.domain.SysReceipt;
import com.account.system.domain.search.ReceiptReportSearch;
import com.account.system.mapper.PorintMapper;
import com.account.system.service.SysPorintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysPorintServiceImpl implements SysPorintService {

    @Autowired
    PorintMapper porintMapper;

    public List<SysReceipt> selectPorintList(ReceiptReportSearch receiptReportSearch) {
        return porintMapper.selectPorintList(receiptReportSearch);
    }

    @Override
    public Map selectPorintCount(ReceiptReportSearch receiptReportSearch) {
        return porintMapper.selectPorintCount(receiptReportSearch);
    }

    @Override
    public Map porintReckon(PorintUpdate porintUpdate) {
        SysPorint sysPorint = porintMapper.selectPorint(porintUpdate.getId());
        Map map = new HashMap();
        BigDecimal chipGap = checkDecimal(porintUpdate.getChip()).subtract(sysPorint.getSysChip())
                .subtract(checkDecimal(porintUpdate.getChipAdd()))
                .add(checkDecimal(porintUpdate.getChipSub()));

        BigDecimal cashGap = checkDecimal(porintUpdate.getCash()).subtract(sysPorint.getSysChip())
                .subtract(checkDecimal(porintUpdate.getCashAdd()))
                .add(checkDecimal(porintUpdate.getCashSub()));

        BigDecimal insuranceGap = checkDecimal(porintUpdate.getInsurance()).subtract(sysPorint.getSysInsurance())
                .subtract(checkDecimal(porintUpdate.getInsuranceAdd()))
                .add(checkDecimal(porintUpdate.getInsuranceSub()));

        map.put("chipGap", chipGap);
        map.put("cashGap", cashGap);
        map.put("insuranceGap", insuranceGap);
        return map;
    }

    private BigDecimal checkDecimal(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal : BigDecimal.ZERO;
    }
}
