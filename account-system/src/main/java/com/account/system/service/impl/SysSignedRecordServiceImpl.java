package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.AccessType;
import com.account.system.domain.SysSignedRecord;
import com.account.system.domain.SysSignedRecordDetailed;
import com.account.system.domain.search.SysSignedRecordSearch;
import com.account.system.domain.vo.SysSignedRecordVo;
import com.account.system.mapper.SysMembersMapper;
import com.account.system.mapper.SysSignedRecordDetailedMapper;
import com.account.system.mapper.SysSignedRecordMapper;
import com.account.system.service.SysSignedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 签单 服务层实现
 */
@Service
public class SysSignedRecordServiceImpl implements SysSignedRecordService {
    @Autowired
    private SysSignedRecordMapper signedRecordMapper;
    @Autowired
    private SysSignedRecordDetailedMapper signedRecordDetailedMapper;

    @Autowired
    private SysMembersMapper membersMapper;

    @Override
    public List<SysSignedRecordVo> selectSignedRecordList(String card, Integer isAdmin) {
        return signedRecordMapper.selectSignedRecordList(card, isAdmin);
    }

    @Override
    public Map selectSignedRecordTotal(String card, Integer isAdmin) {
        return signedRecordMapper.selectSignedRecordTotal(card, isAdmin);
    }

    @Override
    public SysSignedRecord selectSignedRecordInfo(Long id, Long userId) {
        return signedRecordMapper.selectSignedRecordInfo(id, userId);
    }

    @Override
    @Transactional
    public int insertSigned(SysSignedRecordSearch signedRecordSearch) {
        int i = signedRecordMapper.insertSigned(signedRecordSearch);
        //保存签单明细
        saveSignedRecordDetailed(signedRecordSearch);

        //更新用户筹码金额
        if (signedRecordSearch.getAmount().compareTo(BigDecimal.ZERO)>0){
            updateUserChipAmount(signedRecordSearch.getAmount(), CommonConst.NUMBER_1,signedRecordSearch.getUserId());
        }
        return i;
    }

    @Override
    @Transactional
    public int update(SysSignedRecordSearch signedRecordSearch) {
        int update = signedRecordMapper.update(signedRecordSearch);
        //保存签单明细
        saveSignedRecordDetailed(signedRecordSearch);
        //更新用户筹码金额
        if (signedRecordSearch.getAmount().compareTo(BigDecimal.ZERO)>0){
            int number1 =signedRecordSearch.getMark()==AccessType.SIGNED.getCode() ? CommonConst.NUMBER_1: CommonConst.NUMBER_0;
            updateUserChipAmount(signedRecordSearch.getAmount(), number1,signedRecordSearch.getUserId());
        }
        return update;
    }

    /**
     * 更新用户筹码金额
     * @param chipAmount
     * @param type 0:减、1:加
     * @return
     */
    public int updateUserChipAmount(BigDecimal chipAmount,int type,Long userId){
        return  membersMapper.updateChipAmount(userId, chipAmount, type);
    }

    /**
     * 组装签单明细数据
     * @param signedRecordSearch
     * @return
     */
    public int saveSignedRecordDetailed(SysSignedRecordSearch signedRecordSearch){
        //查询签单数据
        SysSignedRecord sysSignedRecord = signedRecordMapper.selectSignedRecordInfo(signedRecordSearch.getId(), signedRecordSearch.getUserId());

        SysSignedRecordDetailed signedRecordDetailed=new SysSignedRecordDetailed();
        signedRecordDetailed.setUserId(signedRecordSearch.getUserId());
        signedRecordDetailed.setOperationType(signedRecordSearch.getMark());
        BigDecimal signedAmount = sysSignedRecord!=null && sysSignedRecord.getSignedAmount() != null ? sysSignedRecord.getSignedAmount() :  BigDecimal.ZERO;
        if (signedRecordSearch.getMark() == AccessType.SIGNED.getCode()){
            signedRecordDetailed.setAmountBefore(signedAmount.subtract(signedRecordSearch.getAmount()==null ?BigDecimal.ZERO:signedRecordSearch.getAmount()));
        }else {
            signedRecordDetailed.setAmountBefore(signedAmount.add(signedRecordSearch.getAmount()==null ?BigDecimal.ZERO:signedRecordSearch.getAmount()));
        }
        signedRecordDetailed.setAmount(signedRecordSearch.getAmount());
        signedRecordDetailed.setAmountAfter(signedAmount);

        signedRecordDetailed.setCreateBy(signedRecordSearch.getCreateBy());
        signedRecordDetailed.setRemark(signedRecordSearch.getRemark());
        return  signedRecordDetailedMapper.insertSignedRecordDetailed(signedRecordDetailed);
    }

}
