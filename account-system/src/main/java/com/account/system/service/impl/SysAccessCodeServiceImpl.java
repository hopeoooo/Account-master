package com.account.system.service.impl;

import com.account.common.enums.AccessType;
import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysAccessCodeAddSearch;
import com.account.system.domain.SysAccessCodeDetailed;
import com.account.system.domain.SysAccessCodeSearch;
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
    public List<Map> selectAccessCodeList(SysAccessCodeSearch accessCodeSearch) {
        return accessCodeMapper.selectAccessCodeList(accessCodeSearch);
    }

    @Override
    public Map selectAccessCodeTotal(SysAccessCodeSearch accessCodeSearch)  {
        return accessCodeMapper.selectAccessCodeTotal(accessCodeSearch);
    }

    @Override
    public SysAccessCode selectAccessCodeInfo(Long id,Long userId) {
        return accessCodeMapper.selectAccessCodeInfo(id,userId);
    }

    @Override
    @Transactional
    public int insertAccessCode(SysAccessCodeAddSearch accessCode) {
        BigDecimal cashBalance = accessCode.getCashBalance() == null ? BigDecimal.ZERO : accessCode.getCashBalance();
        BigDecimal chipBalance = accessCode.getChipBalance() == null ? BigDecimal.ZERO : accessCode.getChipBalance();
        accessCode.setTotalBalance(cashBalance.add(chipBalance));
        int i = accessCodeMapper.insertAccessCode(accessCode);
        //保存存取码明细
        saveAccessCodeDetailed(accessCode);
        return i;
    }

    @Override
    @Transactional
    public int updateAccessCode(SysAccessCodeAddSearch accessCode) {
        int i = accessCodeMapper.updateAccessCode(accessCode);
        //保存存取码明细
        saveAccessCodeDetailed(accessCode);
        return i;
    }


    /**
     * 组装存取码明细数据
     * @param accessCode
     * @return
     */
    public int saveAccessCodeDetailed(SysAccessCodeAddSearch accessCode){
        //查询用户信息
        Map map = membersMapper.selectMembersInfo(accessCode.getUserId());
        SysAccessCode sysAccessCode = accessCodeMapper.selectAccessCodeInfo(accessCode.getId(),accessCode.getUserId());
        SysAccessCodeDetailed accessCodeDetailed=new SysAccessCodeDetailed();
        accessCodeDetailed.setUserId(accessCode.getUserId());
        accessCodeDetailed.setType(accessCode.getMark());
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
        accessCodeDetailed.setCreateBy(accessCode.getCreateBy());
        accessCodeDetailed.setRemark(accessCode.getRemark());
       return  accessCodeDetailedMapper.insertAccessCodeDetailed(accessCodeDetailed);
    }
}
