package com.account.system.service.impl;

import com.account.common.enums.ChipChangeEnum;
import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysChipRecord;
import com.account.system.domain.search.SysAccessCodeAddSearch;
import com.account.system.domain.SysAccessCodeDetailed;
import com.account.system.domain.search.SysAccessCodeSearch;
import com.account.system.domain.vo.SysAccessCodeVo;
import com.account.system.mapper.SysAccessCodeDetailedMapper;
import com.account.system.mapper.SysAccessCodeMapper;
import com.account.system.mapper.SysChipRecordMapper;
import com.account.system.service.SysAccessCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 存取码 服务层实现
 */
@Service
public class SysAccessCodeServiceImpl implements SysAccessCodeService {
    @Autowired
    private SysAccessCodeMapper accessCodeMapper;
    @Autowired
    private SysAccessCodeDetailedMapper accessCodeDetailedMapper;
    @Autowired
    private SysChipRecordMapper chipRecordMapper;

    @Override
    public List<SysAccessCodeVo> selectAccessCodeList(SysAccessCodeSearch accessCodeSearch) {
        return accessCodeMapper.selectAccessCodeList(accessCodeSearch);
    }

    @Override
    public Map selectAccessCodeTotal(SysAccessCodeSearch accessCodeSearch)  {
        return accessCodeMapper.selectAccessCodeTotal(accessCodeSearch);
    }

    @Override
    public SysAccessCode selectAccessCodeInfo(Long id,String card) {
        return accessCodeMapper.selectAccessCodeInfo(id,card);
    }

    @Override
    @Transactional
    public int insertAccessCode(SysAccessCodeAddSearch accessCode) {
        BigDecimal cashBalance = accessCode.getCashAmount() == null ? BigDecimal.ZERO : accessCode.getCashAmount();
        BigDecimal chipBalance = accessCode.getChipAmount() == null ? BigDecimal.ZERO : accessCode.getChipAmount();
        accessCode.setTotalBalance(cashBalance.add(chipBalance));
        int i = accessCodeMapper.insertAccessCode(accessCode);
        if(i>0){
            //保存存取码明细
            saveAccessCodeDetailed(accessCode);
        }
        return i;
    }

    @Override
    @Transactional
    public int updateAccessCode(SysAccessCodeAddSearch accessCode) {
        int i = accessCodeMapper.updateAccessCode(accessCode);
        if(i>0){
            //保存存取码明细
            saveAccessCodeDetailed(accessCode);
        }
        return i;
    }


    /**
     * 组装存取码明细数据
     * @param accessCode
     * @return
     */
    public void saveAccessCodeDetailed(SysAccessCodeAddSearch accessCode){
        //存取码明细
        SysAccessCode sysAccessCode = accessCodeMapper.selectAccessCodeInfo(accessCode.getId(),accessCode.getCard());
        SysAccessCodeDetailed accessCodeDetailed=new SysAccessCodeDetailed();
        accessCodeDetailed.setCard(accessCode.getCard());
        accessCodeDetailed.setType(accessCode.getMark());
        accessCodeDetailed.setCreateBy(accessCode.getCreateBy());
        accessCodeDetailed.setRemark(accessCode.getRemark());
        //筹码
        if (accessCode.getChipAmount()!=null && accessCode.getChipAmount().compareTo(BigDecimal.ZERO)>0){
            BigDecimal chipBalance = sysAccessCode!=null && sysAccessCode.getChipBalance() != null ? sysAccessCode.getChipBalance() :  BigDecimal.ZERO;
            if (accessCode.getMark()== ChipChangeEnum.STORE_CHIP.getCode()){
                accessCodeDetailed.setChipAmountBefore(chipBalance.subtract(accessCode.getChipAmount()==null ?BigDecimal.ZERO:accessCode.getChipAmount()));
            }else {
                accessCodeDetailed.setChipAmountBefore(chipBalance.add(accessCode.getChipAmount()==null ?BigDecimal.ZERO:accessCode.getChipAmount()));
            }
            accessCodeDetailed.setChipAmount(accessCode.getChipAmount());
            accessCodeDetailed.setChipAmountAfter(chipBalance);
            //添加筹码变动明细表
            addChipRecord(accessCodeDetailed);
        }
        //现金
        if (accessCode.getCashAmount()!=null && accessCode.getCashAmount().compareTo(BigDecimal.ZERO)>0) {
            BigDecimal cashBalance = sysAccessCode!=null &&  sysAccessCode.getCashBalance() != null ? sysAccessCode.getCashBalance() : BigDecimal.ZERO;
            if (accessCode.getMark()==ChipChangeEnum.STORE_CHIP.getCode()){
                accessCodeDetailed.setCashAmountBefore(cashBalance.subtract(accessCode.getCashAmount()==null ?BigDecimal.ZERO:accessCode.getCashAmount()));
            }else {
                accessCodeDetailed.setCashAmountBefore(cashBalance.add(accessCode.getCashAmount()==null ?BigDecimal.ZERO:accessCode.getCashAmount()));
            }
            accessCodeDetailed.setCashAmount(accessCode.getCashAmount());
            accessCodeDetailed.setCashAmountAfter(cashBalance);
        }
        accessCodeDetailedMapper.insertAccessCodeDetailed(accessCodeDetailed);
    }


    /**
     * 组装筹码明细变动数据
     * @param accessCodeDetailed
     */
    public void addChipRecord(SysAccessCodeDetailed accessCodeDetailed){
        SysChipRecord chipRecord=new SysChipRecord();
        chipRecord.setCard(accessCodeDetailed.getCard());
        chipRecord.setType(accessCodeDetailed.getType());
        chipRecord.setBefore(accessCodeDetailed.getChipAmountBefore());
        chipRecord.setChange(accessCodeDetailed.getChipAmount());
        chipRecord.setAfter(accessCodeDetailed.getChipAmountAfter());
        chipRecord.setCreateBy(accessCodeDetailed.getCreateBy());
        chipRecordMapper.addChipRecord(chipRecord);
    }
}
