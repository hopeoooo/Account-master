package com.account.system.service.impl;


import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.*;
import com.account.system.domain.search.PorintUpdateSearch;
import com.account.system.domain.search.ReceiptReportSearch;
import com.account.system.mapper.BetMapper;
import com.account.system.mapper.PorintMapper;
import com.account.system.mapper.ReceiptMapper;
import com.account.system.mapper.SysTableManagementMapper;
import com.account.system.service.SysPorintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    BetMapper betMapper;

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

        BigDecimal chipGap = sysPorint.getSysChip().subtract(checkDecimal(porintUpdate.getChip()))
                .add(checkDecimal(porintUpdate.getChipAdd()))
                .subtract(checkDecimal(porintUpdate.getChipSub()))
                .subtract(sysPorint.getChipAdd());

        BigDecimal cashGap = sysPorint.getSysCash().subtract(checkDecimal(porintUpdate.getCash()))
                .add(checkDecimal(porintUpdate.getCashAdd()))
                .subtract(checkDecimal(porintUpdate.getCashSub()))
                .subtract(sysPorint.getCashAdd());

        BigDecimal insuranceGap = sysPorint.getSysInsurance().subtract(checkDecimal(porintUpdate.getInsurance()))
                .add(checkDecimal(porintUpdate.getInsuranceAdd()))
                .subtract(checkDecimal(porintUpdate.getInsuranceSub()))
                .subtract(sysPorint.getInsuranceAdd());

        BigDecimal chipGapTh = sysPorint.getSysChipTh().subtract(checkDecimal(porintUpdate.getChipTh()))
                .add(checkDecimal(porintUpdate.getChipAddTh()))
                .subtract(checkDecimal(porintUpdate.getChipSubTh()))
                .subtract(sysPorint.getChipAddTh());

        BigDecimal cashGapTh = sysPorint.getSysCashTh().subtract(checkDecimal(porintUpdate.getCashTh()))
                .add(checkDecimal(porintUpdate.getCashAddTh()))
                .subtract(checkDecimal(porintUpdate.getCashSubTh()))
                .subtract(sysPorint.getCashAddTh());

        BigDecimal insuranceGapTh = sysPorint.getSysInsuranceTh().subtract(checkDecimal(porintUpdate.getInsuranceTh()))
                .add(checkDecimal(porintUpdate.getInsuranceAddTh()))
                .subtract(checkDecimal(porintUpdate.getInsuranceSubTh()))
                .subtract(sysPorint.getInsuranceAddTh());

        map.put("chipGap", chipGap);
        map.put("cashGap", cashGap);
        map.put("totalGap", chipGap.add(cashGap));
        map.put("insuranceGap", insuranceGap);
        map.put("chipGapTh", chipGapTh);
        map.put("cashGapTh", cashGapTh);
        map.put("totalGapTh", chipGapTh.add(cashGapTh));
        map.put("insuranceGapTh", insuranceGapTh);
        if (porintUpdate.getGameId() == 2) {//龙虎 计算和钱
            SysTableManagement sysTableManagement = new SysTableManagement(sysPorint.getTableId(),sysPorint.getVersion(),sysPorint.getBootNum());
            BigDecimal tie = checkDecimal(betMapper.selectTie(sysTableManagement));
            BigDecimal tieTh = checkDecimal(betMapper.selectTieTh(sysTableManagement));
            map.put("tie", tie);
            map.put("tieTh", tieTh);
        }
        return map;
    }

    /**
     * 修改点码 确认修改
     */
    @Transactional
    public void editPorint(PorintUpdate porintUpdate) {
        SysPorint sysPorint = porintMapper.selectPorint(porintUpdate.getId());
        SysPorint porint = sysPorint.clone();
        //修改点码 人工点码 增减 差距 系统点码
        porint.setInfo(porintUpdate.getInfo());
        porint.setInfoTh(porintUpdate.getInfoTh());

        porint.setPersonChip(checkDecimal(porintUpdate.getChip()));
        porint.setPersonCash(checkDecimal(porintUpdate.getCash()));
        porint.setPersonInsurance(checkDecimal(porintUpdate.getInsurance()));

        porint.setChipAdd(checkDecimal(porintUpdate.getChipAdd()).subtract(checkDecimal(porintUpdate.getChipSub())));
        porint.setCashAdd(checkDecimal(porintUpdate.getCashAdd()).subtract(checkDecimal(porintUpdate.getCashSub())));
        porint.setInsuranceAdd(checkDecimal(porintUpdate.getInsuranceAdd()).subtract(checkDecimal(porintUpdate.getInsuranceSub())));

        porint.setPersonChipTh(checkDecimal(porintUpdate.getChipTh()));
        porint.setPersonCashTh(checkDecimal(porintUpdate.getCashTh()));
        porint.setPersonInsuranceTh(checkDecimal(porintUpdate.getInsuranceTh()));

        porint.setChipAddTh(checkDecimal(porintUpdate.getChipAddTh()).subtract(checkDecimal(porintUpdate.getChipSubTh())));
        porint.setCashAddTh(checkDecimal(porintUpdate.getCashAddTh()).subtract(checkDecimal(porintUpdate.getCashSubTh())));
        porint.setInsuranceAddTh(checkDecimal(porintUpdate.getInsuranceAddTh()).subtract(checkDecimal(porintUpdate.getInsuranceSubTh())));

        porint.setSysChip(sysPorint.getSysChip().subtract(sysPorint.getChipAdd()).add(porint.getChipAdd()));
        porint.setSysCash(sysPorint.getSysCash().subtract(sysPorint.getCashAdd()).add(porint.getCashAdd()));
        porint.setSysInsurance(sysPorint.getSysInsurance().subtract(sysPorint.getInsuranceAdd()).add(porint.getInsuranceAdd()));

        porint.setSysChipTh(sysPorint.getSysChipTh().subtract(sysPorint.getChipAddTh()).add(porint.getChipAddTh()));
        porint.setSysCashTh(sysPorint.getSysCashTh().subtract(sysPorint.getCashAddTh()).add(porint.getCashAddTh()));
        porint.setSysInsuranceTh(sysPorint.getSysInsuranceTh().subtract(sysPorint.getInsuranceAddTh()).add(porint.getInsuranceAddTh()));

        porint.setChipGap(porint.getSysChip().subtract(porint.getPersonChip()));
        porint.setCashGap(porint.getSysCash().subtract(porint.getPersonCash()));
        porint.setInsuranceGap(porint.getSysInsurance().subtract(porint.getPersonInsurance()));

        porint.setChipGapTh(porint.getSysChipTh().subtract(porint.getPersonChipTh()));
        porint.setCashGapTh(porint.getSysCashTh().subtract(porint.getPersonCashTh()));
        porint.setInsuranceGapTh(porint.getSysInsuranceTh().subtract(porint.getPersonInsuranceTh()));

        porint.setUpdateBy(SecurityUtils.getUsername());
        porint.setRemark(porintUpdate.getRemark());
        porintMapper.editPorint(porint);
        //记录修改记录
        recordPorintUpdate(sysPorint,porint);

        if (sysPorint.getChipAdd().compareTo(porint.getChipAdd()) != 0
                || sysPorint.getCashAdd().compareTo(porint.getCashAdd()) != 0
                || sysPorint.getInsuranceAdd().compareTo(porint.getInsuranceAdd()) != 0
                || sysPorint.getChipAddTh().compareTo(porint.getChipAddTh()) != 0
                || sysPorint.getCashAddTh().compareTo(porint.getCashAddTh()) != 0
                || sysPorint.getInsuranceAddTh().compareTo(porint.getInsuranceAddTh()) != 0) {

            List<SysPorint> list = porintMapper.getPorints(sysPorint);
            if (StringUtils.isNotEmpty(list)) {
                //修改后续点码 增减 差距
                porintMapper.editPorints(list, porint.getChipAdd().subtract(sysPorint.getChipAdd())
                        , porint.getCashAdd().subtract(sysPorint.getCashAdd())
                        , porint.getInsuranceAdd().subtract(sysPorint.getInsuranceAdd())
                        , porint.getChipAddTh().subtract(sysPorint.getChipAddTh())
                        , porint.getCashAddTh().subtract(sysPorint.getCashAddTh())
                        , porint.getInsuranceAddTh().subtract(sysPorint.getInsuranceAddTh()));
            }
            // 修改 收码
            SysReceipt sysReceipt = receiptMapper.getReceipt(sysPorint.getTableId(), sysPorint.getVersion());
            if (sysReceipt != null) {
                receiptMapper.updateReceipt(sysReceipt.getId()
                        , porint.getChipAdd().subtract(sysPorint.getChipAdd())
                        , porint.getCashAdd().subtract(sysPorint.getCashAdd())
                        , porint.getInsuranceAdd().subtract(sysPorint.getInsuranceAdd())
                        , BigDecimal.ZERO);
                receiptMapper.updateReceiptTh(sysReceipt.getId()
                        , porint.getChipAddTh().subtract(sysPorint.getChipAddTh())
                        , porint.getCashAddTh().subtract(sysPorint.getCashAddTh())
                        , porint.getInsuranceAddTh().subtract(sysPorint.getInsuranceAddTh())
                        , BigDecimal.ZERO);
            } else {
                //修改 桌台 累计
                sysTableManagementMapper.addTableMoney(new SysTableManagement(sysPorint.getTableId()
                        , porint.getChipAdd().subtract(sysPorint.getChipAdd())
                        , porint.getCashAdd().subtract(sysPorint.getCashAdd())
                        , porint.getInsuranceAdd().subtract(sysPorint.getInsuranceAdd())
                        , porint.getChipAddTh().subtract(sysPorint.getChipAddTh())
                        , porint.getCashAddTh().subtract(sysPorint.getCashAddTh())
                        , porint.getInsuranceAddTh().subtract(sysPorint.getInsuranceAddTh())));
            }
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

        sysPorintUpdate.setPersonChipTh(compare(oldPorint.getPersonChipTh().add(oldPorint.getPersonCashTh()),
                newPorint.getPersonChipTh().add(newPorint.getPersonCashTh())));
        sysPorintUpdate.setChipGapTh(compare(oldPorint.getChipGapTh(),newPorint.getChipGapTh()));
        sysPorintUpdate.setChipAddTh(compare(oldPorint.getChipAddTh(),newPorint.getChipAddTh()));

        sysPorintUpdate.setCashGapTh(compare(oldPorint.getCashGapTh(),newPorint.getCashGapTh()));
        sysPorintUpdate.setCashAddTh(compare(oldPorint.getCashAddTh(),newPorint.getCashAddTh()));

        sysPorintUpdate.setPersonInsuranceTh(compare(oldPorint.getPersonInsuranceTh(),newPorint.getPersonInsuranceTh()));
        sysPorintUpdate.setInsuranceGapTh(compare(oldPorint.getInsuranceGapTh(),newPorint.getInsuranceGapTh()));
        sysPorintUpdate.setInsuranceAddTh(compare(oldPorint.getInsuranceAddTh(),newPorint.getInsuranceAddTh()));

        sysPorintUpdate.setCreateBy(newPorint.getUpdateBy());
        sysPorintUpdate.setRemark(newPorint.getRemark());
        porintMapper.savePorintUpdate(sysPorintUpdate);
    }

    private String compare(Object a,Object b){
        if(a instanceof BigDecimal){
            a = ((BigDecimal)a).setScale(2);
        }
        if(b instanceof BigDecimal){
            b = ((BigDecimal)b).setScale(2);
        }
        if(a.equals(b)){
            return a.toString();
        }else {
            return  a+"->"+b;
        }
    }
}
