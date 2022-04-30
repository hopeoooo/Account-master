package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.ChipChangeEnum;
import com.account.system.domain.SysChipRecord;
import com.account.system.domain.SysMembers;
import com.account.system.domain.search.SysRemittanceDetailedSearch;
import com.account.system.domain.search.SysRemittanceSearch;
import com.account.system.domain.vo.SysRemittanceDetailedVo;
import com.account.system.mapper.SysChipRecordMapper;
import com.account.system.mapper.SysRemittanceDetailedMapper;
import com.account.system.service.SysMembersService;
import com.account.system.service.SysRemittanceDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 汇款服务层实现
 */
@Service
public class SysRemittanceDetailedServiceImpl implements SysRemittanceDetailedService {
    @Autowired
    private SysRemittanceDetailedMapper remittanceDetailedMapper;
    @Autowired
    private SysMembersService membersService;

    @Autowired
    private SysChipRecordMapper chipRecordMapper;

    @Override
    public List<SysRemittanceDetailedVo> selectRemittanceDetailedList(SysRemittanceDetailedSearch remittanceDetailedSearch) {
        return remittanceDetailedMapper.selectRemittanceDetailedList(remittanceDetailedSearch);
    }

    @Override
    public Map selectRemittanceDetailedTotal(SysRemittanceDetailedSearch remittanceDetailedSearch) {
        return remittanceDetailedMapper.selectRemittanceDetailedTotal(remittanceDetailedSearch);
    }

    @Override
    @Transactional
    public int insertRemittanceDetailed(SysRemittanceSearch remittanceSearch) {
        int type = remittanceSearch.getType() == ChipChangeEnum.IMPORT_CHIP.getCode() ? CommonConst.NUMBER_1 : CommonConst.NUMBER_0;
        int i = membersService.updateChipAmount(remittanceSearch.getCard(), remittanceSearch.getAmount(), type);
        if (i>0){
            //汇款明细
            remittanceDetailedMapper.insertRemittanceDetailed(remittanceSearch);
            //汇入为筹码则累加用户筹码余额
            if (remittanceSearch.getOperationType()==CommonConst.NUMBER_0){
                //添加筹码变动明细表
                addChipRecord(remittanceSearch);
            }
        }
        return i;
    }

    /**
     * 组装筹码明细变动数据
     * @param remittanceSearch
     */
    public void addChipRecord( SysRemittanceSearch remittanceSearch){
        SysMembers sysMembers = membersService.selectmembersByCard(remittanceSearch.getCard());
        BigDecimal chip = sysMembers!=null && sysMembers.getChip() != null ? sysMembers.getChip() :  BigDecimal.ZERO;
        SysChipRecord chipRecord=new SysChipRecord();
        chipRecord.setCard(remittanceSearch.getCard());
        chipRecord.setType(remittanceSearch.getType());
        if (remittanceSearch.getType() == ChipChangeEnum.IMPORT_CHIP.getCode()){
            chipRecord.setBefore(chip.subtract(remittanceSearch.getAmount()==null ?BigDecimal.ZERO:remittanceSearch.getAmount()));
        }else {
            chipRecord.setBefore(chip.add(remittanceSearch.getAmount()==null ?BigDecimal.ZERO:remittanceSearch.getAmount()));
        }
        chipRecord.setChange(remittanceSearch.getAmount());
        chipRecord.setAfter(chip);
        chipRecord.setCreateBy(remittanceSearch.getCreateBy());
        chipRecordMapper.addChipRecord(chipRecord);
    }
}
