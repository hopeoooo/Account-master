package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.AccessType;
import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysChipRecord;
import com.account.system.domain.search.SysAccessCodeAddSearch;
import com.account.system.domain.SysAccessCodeDetailed;
import com.account.system.domain.search.SysAccessCodeSearch;
import com.account.system.domain.vo.SysAccessCodeVo;
import com.account.system.mapper.SysAccessCodeDetailedMapper;
import com.account.system.mapper.SysAccessCodeMapper;
import com.account.system.mapper.SysMembersMapper;
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
    private SysMembersMapper membersMapper;

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
        BigDecimal cashBalance = accessCode.getCashBalance() == null ? BigDecimal.ZERO : accessCode.getCashBalance();
        BigDecimal chipBalance = accessCode.getChipBalance() == null ? BigDecimal.ZERO : accessCode.getChipBalance();
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
    public int saveAccessCodeDetailed(SysAccessCodeAddSearch accessCode){
        //存取码明细
        SysAccessCode sysAccessCode = accessCodeMapper.selectAccessCodeInfo(accessCode.getId(),accessCode.getCard());
        SysAccessCodeDetailed accessCodeDetailed=new SysAccessCodeDetailed();
        accessCodeDetailed.setCard(accessCode.getCard());
        accessCodeDetailed.setType(accessCode.getMark());
        accessCodeDetailed.setCreateBy(accessCode.getCreateBy());
        accessCodeDetailed.setRemark(accessCode.getRemark());
        //筹码
        if (accessCode.getChipBalance()!=null && accessCode.getChipBalance().compareTo(BigDecimal.ZERO)>0){
            BigDecimal chipBalance = sysAccessCode!=null && sysAccessCode.getChipBalance() != null ? sysAccessCode.getChipBalance() :  BigDecimal.ZERO;
            if (accessCode.getMark()==AccessType.STORAGE_CODE.getCode()){
                accessCodeDetailed.setChipAmountBefore(chipBalance.subtract(accessCode.getChipBalance()==null ?BigDecimal.ZERO:accessCode.getChipBalance()));
            }else {
                accessCodeDetailed.setChipAmountBefore(chipBalance.add(accessCode.getChipBalance()==null ?BigDecimal.ZERO:accessCode.getChipBalance()));
            }
            accessCodeDetailed.setChipAmount(accessCode.getChipBalance());
            accessCodeDetailed.setChipAmountAfter(chipBalance);
            //异步添加筹码变动明细表
            //addChipRecord(accessCodeDetailed,sysAccessCode.getC);
        }
        //现金
        if (accessCode.getCashBalance()!=null && accessCode.getCashBalance().compareTo(BigDecimal.ZERO)>0) {
            BigDecimal cashBalance = sysAccessCode!=null &&  sysAccessCode.getCashBalance() != null ? sysAccessCode.getCashBalance() : BigDecimal.ZERO;
            if (accessCode.getMark()==AccessType.STORAGE_CODE.getCode()){
                accessCodeDetailed.setCashAmountBefore(cashBalance.subtract(accessCode.getCashBalance()==null ?BigDecimal.ZERO:accessCode.getCashBalance()));
            }else {
                accessCodeDetailed.setCashAmountBefore(cashBalance.add(accessCode.getCashBalance()==null ?BigDecimal.ZERO:accessCode.getCashBalance()));
            }
            accessCodeDetailed.setCashAmount(accessCode.getCashBalance());
            accessCodeDetailed.setCashAmountAfter(cashBalance);
        }
       return  accessCodeDetailedMapper.insertAccessCodeDetailed(accessCodeDetailed);
    }


    public void addChipRecord(SysAccessCodeDetailed accessCodeDetailed){
        SysChipRecord chipRecord=new SysChipRecord();
        chipRecord.setCard(accessCodeDetailed.getCard());
        chipRecord.setType(accessCodeDetailed.getType());
        chipRecord.setBefore(accessCodeDetailed.getChipAmountBefore());
        chipRecord.setChange(accessCodeDetailed.getChipAmount());
        chipRecord.setAfter(accessCodeDetailed.getChipAmountAfter());
        chipRecord.setCreateBy(accessCodeDetailed.getCreateBy());
    }
}
