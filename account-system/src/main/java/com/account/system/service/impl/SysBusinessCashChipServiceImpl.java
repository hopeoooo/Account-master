package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.ChipChangeEnum;
import com.account.system.domain.SysChipRecord;
import com.account.system.domain.SysMembers;
import com.account.system.domain.search.SysBusinessCashChipAddSearch;
import com.account.system.mapper.SysChipRecordMapper;
import com.account.system.service.SysBusinessCashChipService;
import com.account.system.service.SysMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * 买码换现服务层实现
 */
@Service
public class SysBusinessCashChipServiceImpl implements SysBusinessCashChipService {

    @Autowired
    private SysChipRecordMapper chipRecordMapper;

    @Autowired
    private SysMembersService membersService;


    @Override
    @Transactional
    public int addBuyCode(SysBusinessCashChipAddSearch businessCashChipAddSearch) {
        int mark = businessCashChipAddSearch.getMark() == ChipChangeEnum.BUY_CODE.getCode() ? CommonConst.NUMBER_1 : CommonConst.NUMBER_0;
        int i = membersService.updateChipAmount(businessCashChipAddSearch.getCard(), businessCashChipAddSearch.getChipAmount(),businessCashChipAddSearch.getChipAmountTh(), mark);
        if (i>0){
            addChipRecord(businessCashChipAddSearch);
        }
        return 0;
    }
    /**
     * 组装筹码明细变动数据
     * @param businessCashChipAddSearch
     */
    public void addChipRecord( SysBusinessCashChipAddSearch businessCashChipAddSearch){
        SysMembers sysMembers = membersService.selectmembersByCard(businessCashChipAddSearch.getCard());
        SysChipRecord chipRecord=new SysChipRecord();
        chipRecord.setCard(businessCashChipAddSearch.getCard());
        chipRecord.setType(businessCashChipAddSearch.getMark());
        BigDecimal chip = sysMembers!=null && sysMembers.getChip() != null ? sysMembers.getChip() :  BigDecimal.ZERO;
        BigDecimal chipTh = sysMembers!=null && sysMembers.getChipTh() != null ? sysMembers.getChipTh() :  BigDecimal.ZERO;

        BigDecimal before=BigDecimal.ZERO;
        BigDecimal beforeTh=BigDecimal.ZERO;

        if (businessCashChipAddSearch.getMark() == ChipChangeEnum.BUY_CODE.getCode()){
            before=chip.subtract(businessCashChipAddSearch.getChipAmount()==null ?BigDecimal.ZERO:businessCashChipAddSearch.getChipAmount());
            beforeTh=chipTh.subtract(businessCashChipAddSearch.getChipAmountTh()==null ?BigDecimal.ZERO:businessCashChipAddSearch.getChipAmountTh());
        }else {
            before=chip.add(businessCashChipAddSearch.getChipAmount()==null ?BigDecimal.ZERO:businessCashChipAddSearch.getChipAmount());
            beforeTh=chipTh.add(businessCashChipAddSearch.getChipAmountTh()==null ?BigDecimal.ZERO:businessCashChipAddSearch.getChipAmountTh());
        }
        if(businessCashChipAddSearch.getChipAmount()!=null && businessCashChipAddSearch.getChipAmount().compareTo(BigDecimal.ZERO)>0) {
            chipRecord.setBefore(before);
            chipRecord.setChange(businessCashChipAddSearch.getChipAmount());
            chipRecord.setAfter(chip);
        }
        if(businessCashChipAddSearch.getChipAmountTh()!=null && businessCashChipAddSearch.getChipAmountTh().compareTo(BigDecimal.ZERO)>0){
            chipRecord.setBeforeTh(beforeTh);
            chipRecord.setChangeTh(businessCashChipAddSearch.getChipAmountTh());
            chipRecord.setAfterTh(chipTh);

        }
        chipRecord.setCreateBy(businessCashChipAddSearch.getCreateBy());
        chipRecordMapper.addChipRecord(chipRecord);
    }

}
