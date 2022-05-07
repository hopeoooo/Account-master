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
        //美金-筹码
        if (accessCode.getChipAmount()!=null && accessCode.getChipAmount().compareTo(BigDecimal.ZERO)>0){
            BigDecimal chipBalance = sysAccessCode!=null && sysAccessCode.getChipBalance() != null ? sysAccessCode.getChipBalance() :  BigDecimal.ZERO;
            if (accessCode.getMark()== ChipChangeEnum.STORE_CHIP.getCode()){
                accessCodeDetailed.setChipAmountBefore(chipBalance.subtract(accessCode.getChipAmount()==null ?BigDecimal.ZERO:accessCode.getChipAmount()));
            }else {
                accessCodeDetailed.setChipAmountBefore(chipBalance.add(accessCode.getChipAmount()==null ?BigDecimal.ZERO:accessCode.getChipAmount()));
            }
            accessCodeDetailed.setChipAmount(accessCode.getChipAmount());
            accessCodeDetailed.setChipAmountAfter(chipBalance);
        }
        //美金-现金
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

        //泰铢-筹码
        if (accessCode.getChipAmountTh()!=null && accessCode.getChipAmountTh().compareTo(BigDecimal.ZERO)>0){
            BigDecimal chipBalanceTh = sysAccessCode!=null && sysAccessCode.getChipBalanceTh() != null ? sysAccessCode.getChipBalanceTh() :  BigDecimal.ZERO;
            if (accessCode.getMark()== ChipChangeEnum.STORE_CHIP.getCode()){
                accessCodeDetailed.setChipAmountBeforeTh(chipBalanceTh.subtract(accessCode.getChipAmountTh()==null ?BigDecimal.ZERO:accessCode.getChipAmountTh()));
            }else {
                accessCodeDetailed.setChipAmountBeforeTh(chipBalanceTh.add(accessCode.getChipAmountTh()==null ?BigDecimal.ZERO:accessCode.getChipAmountTh()));
            }
            accessCodeDetailed.setChipAmountTh(accessCode.getChipAmount());
            accessCodeDetailed.setChipAmountAfterTh(chipBalanceTh);

        }

        //泰铢-现金
        if (accessCode.getCashAmountTh()!=null && accessCode.getCashAmountTh().compareTo(BigDecimal.ZERO)>0) {
            BigDecimal cashBalanceTh = sysAccessCode!=null &&  sysAccessCode.getCashBalanceTh() != null ? sysAccessCode.getCashBalanceTh() : BigDecimal.ZERO;
            if (accessCode.getMark()==ChipChangeEnum.STORE_CHIP.getCode()){
                accessCodeDetailed.setCashAmountBeforeTh(cashBalanceTh.subtract(accessCode.getCashAmountTh()==null ?BigDecimal.ZERO:accessCode.getCashAmountTh()));
            }else {
                accessCodeDetailed.setCashAmountBeforeTh(cashBalanceTh.add(accessCode.getCashAmountTh()==null ?BigDecimal.ZERO:accessCode.getCashAmountTh()));
            }
            accessCodeDetailed.setCashAmountTh(accessCode.getCashAmountTh());
            accessCodeDetailed.setCashAmountAfterTh(cashBalanceTh);
        }

        accessCodeDetailedMapper.insertAccessCodeDetailed(accessCodeDetailed);


        if ((accessCode.getChipAmount()!=null && accessCode.getChipAmount().compareTo(BigDecimal.ZERO)>0)
                || (accessCode.getChipAmountTh()!=null && accessCode.getChipAmountTh().compareTo(BigDecimal.ZERO)>0)){
            //添加筹码变动明细表
            addChipRecord(accessCodeDetailed);
        }
    }


    /**
     * 组装筹码明细变动数据
     * @param accessCodeDetailed
     */
    public void addChipRecord(SysAccessCodeDetailed accessCodeDetailed){
        SysChipRecord chipRecord=new SysChipRecord();
        chipRecord.setCard(accessCodeDetailed.getCard());
        chipRecord.setType(accessCodeDetailed.getType());
        chipRecord.setBefore(accessCodeDetailed.getChipAmountBefore()==null ? BigDecimal.ZERO : accessCodeDetailed.getChipAmountBefore());
        chipRecord.setChange(accessCodeDetailed.getChipAmount()==null ? BigDecimal.ZERO :accessCodeDetailed.getChipAmount() );
        chipRecord.setAfter(accessCodeDetailed.getChipAmountAfter()==null ? BigDecimal.ZERO : accessCodeDetailed.getChipAmountAfter());
        chipRecord.setBeforeTh(accessCodeDetailed.getChipAmountBeforeTh()==null ? BigDecimal.ZERO : accessCodeDetailed.getChipAmountBeforeTh());
        chipRecord.setChangeTh(accessCodeDetailed.getChipAmountTh()==null ? BigDecimal.ZERO :accessCodeDetailed.getChipAmountTh() );
        chipRecord.setAfterTh(accessCodeDetailed.getChipAmountAfterTh()==null ? BigDecimal.ZERO : accessCodeDetailed.getChipAmountAfterTh());
        chipRecord.setCreateBy(accessCodeDetailed.getCreateBy());
        chipRecordMapper.addChipRecord(chipRecord);
    }
}
