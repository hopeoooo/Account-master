package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.ChipChangeEnum;
import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysChipRecord;
import com.account.system.domain.SysMembers;
import com.account.system.domain.search.SysAccessCodeAddSearch;
import com.account.system.domain.SysAccessCodeDetailed;
import com.account.system.domain.search.SysAccessCodeSearch;
import com.account.system.domain.vo.SysAccessCodeVo;
import com.account.system.mapper.SysAccessCodeDetailedMapper;
import com.account.system.mapper.SysAccessCodeMapper;
import com.account.system.mapper.SysChipRecordMapper;
import com.account.system.mapper.SysMembersMapper;
import com.account.system.service.SysAccessCodeService;
import com.account.system.service.SysMembersService;
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
    @Autowired
    private SysMembersMapper membersMapper;


    @Autowired
    private SysMembersService membersService;


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
        if (accessCode.getChipAmount()!=null && accessCode.getChipAmount().compareTo(BigDecimal.ZERO)>0
                || accessCode.getChipAmountTh()!=null && accessCode.getChipAmountTh().compareTo(BigDecimal.ZERO)>0) {
            int mark = accessCode.getMark() == ChipChangeEnum.STORE_CHIP.getCode() ? CommonConst.NUMBER_0 : CommonConst.NUMBER_1;
            //修改筹码余额
            membersService.updateChipAmount(accessCode.getCard(), accessCode.getChipAmount(), accessCode.getChipAmountTh(), mark);
        }

        if(i>0){
            //保存存取码明细
            saveAccessCodeDetailed(accessCode);
        }
        return i;
    }

    @Override
    @Transactional
    public int updateAccessCode(SysAccessCodeAddSearch accessCode) {
        //存取码
        int i =  accessCodeMapper.updateAccessCode(accessCode);
        if (accessCode.getChipAmount()!=null && accessCode.getChipAmount().compareTo(BigDecimal.ZERO)>0
                || accessCode.getChipAmountTh()!=null && accessCode.getChipAmountTh().compareTo(BigDecimal.ZERO)>0) {
            int mark = accessCode.getMark() == ChipChangeEnum.STORE_CHIP.getCode() ? CommonConst.NUMBER_0 : CommonConst.NUMBER_1;
            //修改筹码余额
            membersService.updateChipAmount(accessCode.getCard(), accessCode.getChipAmount(), accessCode.getChipAmountTh(), mark);
        }

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

        //查询总筹码余额
        SysMembers sysMembers = membersMapper.selectmembersByCard(accessCodeDetailed.getCard());
        SysChipRecord chipRecord=new SysChipRecord();
        chipRecord.setCard(accessCodeDetailed.getCard());
        chipRecord.setType(accessCodeDetailed.getType());
        chipRecord.setRemark(accessCode.getRemark());
        chipRecord.setCreateBy(accessCodeDetailed.getCreateBy());

        //美金-筹码
        if (accessCode.getChipAmount()!=null && accessCode.getChipAmount().compareTo(BigDecimal.ZERO)>0){
            BigDecimal chipBalance = sysAccessCode!=null && sysAccessCode.getChipBalance() != null ? sysAccessCode.getChipBalance() :  BigDecimal.ZERO;
            BigDecimal chipAmount =accessCode.getChipAmount()==null ?BigDecimal.ZERO:accessCode.getChipAmount();
            if (accessCode.getMark()== ChipChangeEnum.STORE_CHIP.getCode()){
                accessCodeDetailed.setChipAmountBefore(chipBalance.subtract(chipAmount));
                chipRecord.setBefore(sysMembers.getChip().add(chipAmount));
            }else {
                accessCodeDetailed.setChipAmountBefore(chipBalance.add(chipAmount));
                chipRecord.setBefore(sysMembers.getChip().subtract(chipAmount));
            }
            accessCodeDetailed.setChipAmount(accessCode.getChipAmount());
            accessCodeDetailed.setChipAmountAfter(chipBalance);
            chipRecord.setChange(accessCode.getChipAmount());
            chipRecord.setAfter(sysMembers.getChip());
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
            BigDecimal chipAmountTh=  accessCode.getChipAmountTh()==null ?BigDecimal.ZERO:accessCode.getChipAmountTh();
            if (accessCode.getMark()== ChipChangeEnum.STORE_CHIP.getCode()){
                accessCodeDetailed.setChipAmountBeforeTh(chipBalanceTh.subtract(chipAmountTh));
                chipRecord.setBeforeTh(sysMembers.getChipTh().add(chipAmountTh));
            }else {
                accessCodeDetailed.setChipAmountBeforeTh(chipBalanceTh.add(chipAmountTh));
                chipRecord.setBeforeTh(sysMembers.getChipTh().subtract(chipAmountTh));
            }
            accessCodeDetailed.setChipAmountTh(accessCode.getChipAmountTh());
            accessCodeDetailed.setChipAmountAfterTh(chipBalanceTh);

            chipRecord.setChangeTh(accessCode.getChipAmountTh());
            chipRecord.setAfterTh(sysMembers.getChipTh());

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
            addChipRecord(chipRecord);
        }
    }




    /**
     * 组装筹码明细变动数据
     * @param chipRecord
     */
    public void addChipRecord(SysChipRecord chipRecord){
        chipRecord.setBefore(chipRecord.getBefore()==null ? BigDecimal.ZERO : chipRecord.getBefore());
        chipRecord.setChange(chipRecord.getChange()==null ? BigDecimal.ZERO :chipRecord.getChange() );
        chipRecord.setAfter(chipRecord.getAfter()==null ? BigDecimal.ZERO : chipRecord.getAfter());
        chipRecord.setBeforeTh(chipRecord.getBeforeTh()==null ? BigDecimal.ZERO : chipRecord.getBeforeTh());
        chipRecord.setChangeTh(chipRecord.getChangeTh()==null ? BigDecimal.ZERO :chipRecord.getChangeTh() );
        chipRecord.setAfterTh(chipRecord.getAfterTh()==null ? BigDecimal.ZERO : chipRecord.getAfterTh());
        chipRecordMapper.addChipRecord(chipRecord);
    }
}
