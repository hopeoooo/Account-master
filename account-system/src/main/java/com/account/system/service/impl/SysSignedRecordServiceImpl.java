package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.ChipChangeEnum;
import com.account.system.domain.SysChipRecord;
import com.account.system.domain.SysSignedRecord;
import com.account.system.domain.SysSignedRecordDetailed;
import com.account.system.domain.search.SysSignedRecordSearch;
import com.account.system.domain.vo.SysSignedRecordVo;
import com.account.system.mapper.SysChipRecordMapper;
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

    @Autowired
    private SysChipRecordMapper chipRecordMapper;

    @Override
    public List<SysSignedRecordVo> selectSignedRecordList(String card, Integer isAdmin) {
        return signedRecordMapper.selectSignedRecordList(card, isAdmin);
    }

    @Override
    public Map selectSignedRecordTotal(String card, Integer isAdmin) {
        return signedRecordMapper.selectSignedRecordTotal(card, isAdmin);
    }

    @Override
    public SysSignedRecord selectSignedRecordInfo(Long id, String  card) {
        return signedRecordMapper.selectSignedRecordInfo(id, card);
    }

    @Override
    @Transactional
    public int insertSigned(SysSignedRecordSearch signedRecordSearch) {
        int i = signedRecordMapper.insertSigned(signedRecordSearch);
        if(i>0){
            //保存签单明细
            saveSignedRecordDetailed(signedRecordSearch);

            //更新用户筹码金额
            if (signedRecordSearch.getAmount().compareTo(BigDecimal.ZERO)>0){
                updateUserChipAmount(signedRecordSearch.getAmount(), signedRecordSearch.getAmountTh(), CommonConst.NUMBER_1,signedRecordSearch.getCard());
            }
        }
        return i;
    }

    @Override
    @Transactional
    public int update(SysSignedRecordSearch signedRecordSearch) {
        int i = signedRecordMapper.update(signedRecordSearch);
        if(i>0) {
            //保存签单明细
            saveSignedRecordDetailed(signedRecordSearch);
            //更新用户筹码金额
            if (signedRecordSearch.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                int number1 = signedRecordSearch.getMark() == ChipChangeEnum.BORROW_CHIP.getCode() ? CommonConst.NUMBER_1 : CommonConst.NUMBER_0;
                updateUserChipAmount(signedRecordSearch.getAmount(),signedRecordSearch.getAmountTh(), number1, signedRecordSearch.getCard());
            }
        }
        return i;
    }

    /**
     * 更新用户筹码金额
     * @param chipAmount
     * @param type 0:减、1:加
     * @return
     */
    public int updateUserChipAmount(BigDecimal chipAmount,BigDecimal chipAmountTh,int type,String userId){
        return  membersMapper.updateChipAmount(userId, chipAmount,chipAmountTh, type);
    }

    /**
     * 组装签单明细数据
     * @param signedRecordSearch
     * @return
     */
    public void saveSignedRecordDetailed(SysSignedRecordSearch signedRecordSearch){
        //查询签单数据
        SysSignedRecord sysSignedRecord = signedRecordMapper.selectSignedRecordInfo(signedRecordSearch.getId(), signedRecordSearch.getCard());

        SysSignedRecordDetailed signedRecordDetailed=new SysSignedRecordDetailed();
        signedRecordDetailed.setCard(signedRecordSearch.getCard());
        signedRecordDetailed.setType(signedRecordSearch.getMark());
        //美金
        BigDecimal signedAmount = sysSignedRecord!=null && sysSignedRecord.getSignedAmount() != null ? sysSignedRecord.getSignedAmount() :  BigDecimal.ZERO;
        //泰铢
        BigDecimal signedAmountTh = sysSignedRecord!=null && sysSignedRecord.getSignedAmountTh() != null ? sysSignedRecord.getSignedAmountTh() :  BigDecimal.ZERO;

        BigDecimal amountBefore=BigDecimal.ZERO;
        BigDecimal amountBeforeTh=BigDecimal.ZERO;
        if (signedRecordSearch.getMark() == ChipChangeEnum.BORROW_CHIP.getCode()){
            amountBefore=signedAmount.subtract(signedRecordSearch.getAmount()==null ?BigDecimal.ZERO:signedRecordSearch.getAmount());
            amountBeforeTh=signedAmountTh.subtract(signedRecordSearch.getAmountTh()==null ?BigDecimal.ZERO:signedRecordSearch.getAmountTh());
        }else {
            amountBefore= signedAmount.add(signedRecordSearch.getAmount()==null ? BigDecimal.ZERO:signedRecordSearch.getAmount());
            amountBeforeTh= signedAmountTh.add(signedRecordSearch.getAmountTh()==null ?BigDecimal.ZERO:signedRecordSearch.getAmountTh());
        }

        if(signedRecordSearch.getAmount()!=null && signedRecordSearch.getAmount().compareTo(BigDecimal.ZERO)>0) {
            signedRecordDetailed.setAmountBefore(amountBefore);
            signedRecordDetailed.setAmount(signedRecordSearch.getAmount());
            signedRecordDetailed.setAmountAfter(signedAmount);
        }

        if(signedRecordSearch.getAmountTh()!=null && signedRecordSearch.getAmountTh().compareTo(BigDecimal.ZERO)>0){
            signedRecordDetailed.setAmountBeforeTh(amountBeforeTh);
            signedRecordDetailed.setAmountTh(signedRecordSearch.getAmountTh());
            signedRecordDetailed.setAmountAfterTh(signedAmountTh);

        }

        signedRecordDetailed.setCreateBy(signedRecordSearch.getCreateBy());
        signedRecordDetailed.setRemark(signedRecordSearch.getRemark());
        signedRecordDetailedMapper.insertSignedRecordDetailed(signedRecordDetailed);
        //添加筹码变动明细表
        addChipRecord(signedRecordDetailed);
    }



    /**
     * 组装筹码明细变动数据
     * @param signedRecordDetailed
     */
    public void addChipRecord( SysSignedRecordDetailed signedRecordDetailed){
        SysChipRecord chipRecord=new SysChipRecord();
        chipRecord.setCard(signedRecordDetailed.getCard());
        chipRecord.setType(signedRecordDetailed.getType());
        chipRecord.setBefore(signedRecordDetailed.getAmountBefore()==null ? BigDecimal.ZERO:signedRecordDetailed.getAmountBefore());
        chipRecord.setChange(signedRecordDetailed.getAmount()==null ? BigDecimal.ZERO:signedRecordDetailed.getAmount());
        chipRecord.setAfter(signedRecordDetailed.getAmountAfter()==null ? BigDecimal.ZERO:signedRecordDetailed.getAmountAfter());
        chipRecord.setBeforeTh(signedRecordDetailed.getAmountBeforeTh()==null ? BigDecimal.ZERO:signedRecordDetailed.getAmountBeforeTh());
        chipRecord.setChangeTh(signedRecordDetailed.getAmountTh()==null ? BigDecimal.ZERO:signedRecordDetailed.getAmountTh());
        chipRecord.setAfterTh(signedRecordDetailed.getAmountAfterTh()==null ? BigDecimal.ZERO:signedRecordDetailed.getAmountAfterTh());
        chipRecord.setCreateBy(signedRecordDetailed.getCreateBy());
        chipRecord.setRemark(signedRecordDetailed.getRemark());
        chipRecordMapper.addChipRecord(chipRecord);
    }

}
