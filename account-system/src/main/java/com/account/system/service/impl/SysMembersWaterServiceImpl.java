package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.ChipChangeEnum;
import com.account.system.domain.SysChipRecord;
import com.account.system.domain.SysMembers;
import com.account.system.domain.SysSignedRecordDetailed;
import com.account.system.domain.SysWaterDetailed;
import com.account.system.domain.search.SysWaterSearch;
import com.account.system.mapper.SysChipRecordMapper;
import com.account.system.mapper.SysMembersMapper;
import com.account.system.mapper.SysWaterDetailedMapper;
import com.account.system.mapper.SysWaterMapper;
import com.account.system.service.SysMembersService;
import com.account.system.service.SysMembersWaterService;
import com.account.system.service.SysWaterDetailedService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class SysMembersWaterServiceImpl implements SysMembersWaterService {

    @Autowired
    SysWaterMapper waterMapper;

    @Autowired
    SysWaterDetailedMapper waterDetailedMapper;

    @Autowired
    private SysChipRecordMapper chipRecordMapper;

    @Autowired
    private SysMembersWaterService membersWaterService;
    @Autowired
    SysMembersMapper sysMembersMapper;

    @Override
    @Transactional
    public int updateMembersWater(SysWaterSearch waterSearch) {
         int i=waterMapper.updateMembersWater(waterSearch);
        if (i>0){
            if(waterSearch.getOperationType()== CommonConst.NUMBER_0){
                sysMembersMapper.updateChipAmount(waterSearch.getCard(), waterSearch.getActualWaterAmount(),waterSearch.getActualWaterAmountTh(), CommonConst.NUMBER_1);
                //添加筹码变动明细
                chipRecordMapper.addChipRecord(addChipRecord(waterSearch));
            }
            addWaterDetailed(waterSearch);
        }
        return i;
    }

    @Override
    @Transactional
    public void updateMembersWaterList(List<SysWaterSearch> waterSearch) {
         waterMapper.updateMembersWaterList( waterSearch);
         //批量洗码明细
        addWaterDetailedList(waterSearch);
    }

    @Override
    public Map selectMembersWaterInfo(String card) {
        return waterMapper.selectMembersWaterInfo(card);
    }


    public void addWaterDetailed(SysWaterSearch waterSearch){
        SysWaterDetailed waterDetailed=new SysWaterDetailed();
        waterDetailed.setCard(waterSearch.getCard());
        waterDetailed.setOperationType(waterSearch.getOperationType());
        waterDetailed.setWater(waterSearch.getWater());
        waterDetailed.setWaterAmount(waterSearch.getWaterAmount());
        waterDetailed.setActualWaterAmount(waterSearch.getActualWaterAmount());

        waterDetailed.setWaterTh(waterSearch.getWaterTh());
        waterDetailed.setWaterAmountTh(waterSearch.getWaterAmountTh());
        waterDetailed.setActualWaterAmountTh(waterSearch.getActualWaterAmountTh());
        waterDetailed.setRemark(waterSearch.getRemark());
        waterDetailed.setCreateBy(waterSearch.getCreateBy());
        //添加洗码明细
        waterDetailedMapper.insertWaterDetailed(waterDetailed);
    }


    public void addWaterDetailedList(List<SysWaterSearch> waterSearch){
        List<SysWaterDetailed> list =new ArrayList<>();
        List<SysChipRecord> chipList =new ArrayList<>();
        waterSearch.forEach(info ->{
            SysWaterDetailed waterDetailed=new SysWaterDetailed();
            waterDetailed.setCard(info.getCard());
            waterDetailed.setOperationType(info.getOperationType());
            waterDetailed.setWater(info.getWater());
            waterDetailed.setWaterAmount(info.getWaterAmount());
            waterDetailed.setActualWaterAmount(info.getActualWaterAmount());

            waterDetailed.setWaterTh(info.getWaterTh());
            waterDetailed.setWaterAmountTh(info.getWaterAmountTh());
            waterDetailed.setActualWaterAmountTh(info.getActualWaterAmountTh());
            waterDetailed.setRemark("批量结算");
            waterDetailed.setCreateBy(info.getCreateBy());
            list.add(waterDetailed);
            if (info.getOperationType()== CommonConst.NUMBER_0){
                info.setRemark("批量结算");
                SysChipRecord sysChipRecord = addChipRecord(info);
                chipList.add(sysChipRecord);
            }
        });
        //添加洗码明细
        waterDetailedMapper.insertWaterDetailedList(list);

        if (waterSearch.get(0).getOperationType()== CommonConst.NUMBER_0){
            //添加筹码变动明细
            chipRecordMapper.addChipRecordList(chipList);
        }

    }


    /**
     * 组装筹码明细变动数据
     * @param waterSearch
     */
    public SysChipRecord addChipRecord(SysWaterSearch waterSearch){

        SysMembers sysMembers = sysMembersMapper.selectmembersByCard(waterSearch.getCard());

        BigDecimal waterAmount = sysMembers.getChip();
        BigDecimal waterAmountTh = sysMembers.getChipTh();

        SysChipRecord chipRecord=new SysChipRecord();
        chipRecord.setCard(waterSearch.getCard());
        chipRecord.setType(ChipChangeEnum.SETTLEMENT_CHIP.getCode());



        chipRecord.setBefore(waterAmount.subtract(waterSearch.getActualWaterAmount()));
        chipRecord.setChange(waterSearch.getWaterAmount());
        chipRecord.setAfter(waterAmount);


        chipRecord.setBeforeTh(waterAmountTh.subtract(waterSearch.getActualWaterAmountTh()));
        chipRecord.setChangeTh(waterSearch.getWaterAmountTh());
        chipRecord.setAfterTh(waterAmountTh);


        chipRecord.setRemark(waterSearch.getRemark());
        chipRecord.setCreateBy(waterSearch.getCreateBy());
        return  chipRecord;
    }
}
