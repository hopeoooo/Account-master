package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.ChipChangeEnum;
import com.account.common.enums.CurrencyTypeEnum;
import com.account.common.enums.RemittanceTypeEnum;
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
        List<SysRemittanceDetailedVo> sysRemittanceDetailedVos = remittanceDetailedMapper.selectRemittanceDetailedList(remittanceDetailedSearch);

        //渲染展示汇款类型,货币类型
        sysRemittanceDetailedVos.stream().forEach(vo -> {
            //汇款类型
            String remittanceTypeDisplay = RemittanceTypeEnum.getByValue(vo.getType()).getDisplay();
            vo.setTypeDisplay(remittanceTypeDisplay);

            //货币类型
            String currencyTypeEnumDisplay = CurrencyTypeEnum.getByValue(vo.getOperationType()).getDisplay();
            vo.setOperationTypeDisplay(currencyTypeEnumDisplay);
        });
        return sysRemittanceDetailedVos;
    }

    @Override
    public Map selectRemittanceDetailedTotal(SysRemittanceDetailedSearch remittanceDetailedSearch) {
        return remittanceDetailedMapper.selectRemittanceDetailedTotal(remittanceDetailedSearch);
    }

    @Override
    @Transactional
    public int insertRemittanceDetailed(SysRemittanceSearch remittanceSearch) {
        //筹码
        if (remittanceSearch.getOperationType() == CommonConst.NUMBER_0) {
            int type = remittanceSearch.getType() == ChipChangeEnum.IMPORT_CHIP.getCode() ? CommonConst.NUMBER_1 : CommonConst.NUMBER_0;
            int i = membersService.updateChipAmount(remittanceSearch.getCard(), remittanceSearch.getAmount(), remittanceSearch.getAmountTh(),type);
            if (i > 0) {
                //汇款明细
                remittanceDetailedMapper.insertRemittanceDetailed(remittanceSearch);
                //添加筹码变动明细表
                addChipRecord(remittanceSearch);
            }
        } else {
            //汇款明细
            remittanceDetailedMapper.insertRemittanceDetailed(remittanceSearch);
        }
        return 0;
    }

    /**
     * 组装筹码明细变动数据
     *
     * @param remittanceSearch
     */
    public void addChipRecord(SysRemittanceSearch remittanceSearch) {
        SysMembers sysMembers = membersService.selectmembersByCard(remittanceSearch.getCard());
        BigDecimal chip = sysMembers != null && sysMembers.getChip() != null ? sysMembers.getChip() : BigDecimal.ZERO;
        BigDecimal chipTh = sysMembers != null && sysMembers.getChipTh() != null ? sysMembers.getChipTh() : BigDecimal.ZERO;
        SysChipRecord chipRecord = new SysChipRecord();
        chipRecord.setCard(remittanceSearch.getCard());
        chipRecord.setType(remittanceSearch.getType());


        BigDecimal before=BigDecimal.ZERO;
        BigDecimal beforeTh=BigDecimal.ZERO;

        if (remittanceSearch.getType() == ChipChangeEnum.IMPORT_CHIP.getCode()) {
            before=chip.subtract(remittanceSearch.getAmount() == null ? BigDecimal.ZERO : remittanceSearch.getAmount());
            beforeTh=chipTh.subtract(remittanceSearch.getAmountTh() == null ? BigDecimal.ZERO : remittanceSearch.getAmountTh());
        } else {
            before=chip.add(remittanceSearch.getAmount() == null ? BigDecimal.ZERO : remittanceSearch.getAmount());
            beforeTh=chipTh.add(remittanceSearch.getAmountTh() == null ? BigDecimal.ZERO : remittanceSearch.getAmountTh());
        }
        if(remittanceSearch.getAmount()!=null && remittanceSearch.getAmount().compareTo(BigDecimal.ZERO)>0) {
            chipRecord.setBefore(before);
            chipRecord.setChange(remittanceSearch.getAmount());
            chipRecord.setAfter(chip);
        }

        if(remittanceSearch.getAmountTh()!=null && remittanceSearch.getAmountTh().compareTo(BigDecimal.ZERO)>0) {
            chipRecord.setBeforeTh(beforeTh);
            chipRecord.setChangeTh(remittanceSearch.getAmountTh());
            chipRecord.setAfterTh(chipTh);
        }
        chipRecord.setCreateBy(remittanceSearch.getCreateBy());
        chipRecordMapper.addChipRecord(chipRecord);
    }
}
