package com.account.system.service.impl;


import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.*;
import com.account.system.domain.search.PorintUpdateSearch;
import com.account.system.domain.search.ReceiptReportSearch;
import com.account.system.mapper.PorintMapper;
import com.account.system.mapper.ReceiptMapper;
import com.account.system.mapper.SysTableManagementMapper;
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

    @Autowired
    ReceiptMapper receiptMapper;

    @Autowired
    SysTableManagementMapper sysTableManagementMapper;

    public List<SysPorint> selectPorintList(ReceiptReportSearch receiptReportSearch) {
        return porintMapper.selectPorintList(receiptReportSearch);
    }

    @Override
    public Map selectPorintCount(ReceiptReportSearch receiptReportSearch) {
        return porintMapper.selectPorintCount(receiptReportSearch);
    }

    /**
     * 修改点码 计算差距
     */
    public Map porintReckon(PorintUpdate porintUpdate) {
        SysPorint sysPorint = porintMapper.selectPorint(porintUpdate.getId());
        Map map = new HashMap();
        BigDecimal chipGap = checkDecimal(porintUpdate.getChip()).subtract(sysPorint.getSysChip())
                .subtract(checkDecimal(porintUpdate.getChipAdd()))
                .add(checkDecimal(porintUpdate.getChipSub()));

        BigDecimal cashGap = checkDecimal(porintUpdate.getCash()).subtract(sysPorint.getSysCash())
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

    /**
     * 修改点码 确认修改
     */
    public void editPorint(PorintUpdate porintUpdate) {
        SysPorint sysPorint = porintMapper.selectPorint(porintUpdate.getId());
        SysPorint porint = sysPorint.clone();
        //修改点码 人工点码 增减 差距
        porint.setPersonChip(checkDecimal(porintUpdate.getChip()));
        porint.setPersonCash(checkDecimal(porintUpdate.getCash()));
        porint.setPersonInsurance(checkDecimal(porintUpdate.getInsurance()));

        porint.setChipAdd(checkDecimal(porintUpdate.getChipAdd()).subtract(checkDecimal(porintUpdate.getChipSub())));
        porint.setCashAdd(checkDecimal(porintUpdate.getCashAdd()).subtract(checkDecimal(porintUpdate.getCashSub())));
        porint.setInsuranceAdd(checkDecimal(porintUpdate.getInsuranceAdd()).subtract(checkDecimal(porintUpdate.getInsuranceSub())));

        porint.setChipGap(porint.getPersonChip().subtract(sysPorint.getSysChip()).subtract(porint.getChipAdd()));
        porint.setCashGap(porint.getPersonCash().subtract(sysPorint.getSysCash()).subtract(porint.getCashAdd()));
        porint.setInsuranceGap(porint.getPersonInsurance().subtract(sysPorint.getSysInsurance()).subtract(porint.getInsuranceAdd()));
        porint.setUpdateBy(SecurityUtils.getUsername());
        porint.setRemark(porintUpdate.getRemark());
        porintMapper.editPorint(porint);
        //记录修改记录
        recordPorintUpdate(sysPorint,porint);

        if (sysPorint.getChipAdd().compareTo(porint.getChipAdd()) != 0
                || sysPorint.getCashAdd().compareTo(porint.getCashAdd()) != 0
                || sysPorint.getInsuranceAdd().compareTo(porint.getInsuranceAdd()) != 0) {

            List<SysPorint> list = porintMapper.getPorints(sysPorint);
            if(StringUtils.isNotEmpty(list)){
                //修改后续点码 增减 差距
                porintMapper.editPorints(list,porint.getChipAdd(),porint.getCashAdd(),porint.getInsuranceAdd());
            }
        }
        // 修改 收码
        SysReceipt sysReceipt = receiptMapper.getReceipt(sysPorint.getTableId(), sysPorint.getVersion());
        if (sysReceipt != null) {
            receiptMapper.updateReceipt(sysReceipt.getId(), porint.getChipAdd(), porint.getCashAdd(), porint.getInsuranceAdd(), BigDecimal.ZERO);
        } else {
            //修改 桌台 累计
            SysTableManagement sysTableManagement = new SysTableManagement();
            sysTableManagement.setTableId(sysPorint.getTableId());
            sysTableManagement.setChipAdd(porint.getChipAdd());
            sysTableManagement.setCashAdd(porint.getCashAdd());
            sysTableManagement.setInsuranceAdd(porint.getInsuranceAdd());
            sysTableManagementMapper.addTableMoney(sysTableManagement);
        }

    }

    @Override
    public List<Map> selectPorintUpdateList(PorintUpdateSearch porintUpdateSearch) {
        return porintMapper.selectPorintUpdateList(porintUpdateSearch);
    }

    private BigDecimal checkDecimal(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal : BigDecimal.ZERO;
    }

    private void recordPorintUpdate(SysPorint oldPorint,SysPorint newPorint){
        SysPorintUpdate sysPorintUpdate = new SysPorintUpdate(oldPorint);

        sysPorintUpdate.setPersonChip(compare(oldPorint.getPersonChip().add(oldPorint.getPersonCash()),
                newPorint.getPersonChip().add(newPorint.getPersonCash())));

        sysPorintUpdate.setChipGap(compare(oldPorint.getChipGap(),newPorint.getChipGap()));
        sysPorintUpdate.setChipAdd(compare(oldPorint.getChipAdd(),newPorint.getChipAdd()));

        sysPorintUpdate.setCashGap(compare(oldPorint.getCashGap(),newPorint.getCashGap()));
        sysPorintUpdate.setCashAdd(compare(oldPorint.getCashAdd(),newPorint.getCashAdd()));

        sysPorintUpdate.setPersonInsurance(compare(oldPorint.getPersonInsurance(),newPorint.getPersonInsurance()));
        sysPorintUpdate.setInsuranceGap(compare(oldPorint.getInsuranceGap(),newPorint.getInsuranceGap()));
        sysPorintUpdate.setInsuranceAdd(compare(oldPorint.getInsuranceAdd(),newPorint.getInsuranceAdd()));
        sysPorintUpdate.setCreateBy(newPorint.getUpdateBy());
        sysPorintUpdate.setRemark(newPorint.getRemark());
        porintMapper.savePorintUpdate(sysPorintUpdate);
    }

    private String compare(Object a,Object b){
        if(a instanceof BigDecimal){
            a = ((BigDecimal)a).setScale(4);
        }
        if(b instanceof BigDecimal){
            b = ((BigDecimal)b).setScale(4);
        }
        if(a.equals(b)){
            return a.toString();
        }else {
            return  a+"->"+b;
        }
    }
}
