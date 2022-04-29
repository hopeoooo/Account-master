package com.account.system.service.impl;

import com.account.system.domain.SysWaterDetailed;
import com.account.system.domain.search.SysWaterSearch;
import com.account.system.mapper.SysWaterDetailedMapper;
import com.account.system.mapper.SysWaterMapper;
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

    @Override
    @Transactional
    public int updateMembersWater(SysWaterSearch waterSearch) {
        int i = waterMapper.updateMembersWater(waterSearch);
        if (i>0){
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
        waterDetailed.setRemark(waterSearch.getRemark());
        waterDetailed.setCreateBy(waterSearch.getCreateBy());
        //添加洗码明细
        waterDetailedMapper.insertWaterDetailed(waterDetailed);
    }


    public void addWaterDetailedList(List<SysWaterSearch> waterSearch){
        List<SysWaterDetailed> list =new ArrayList<>();
        waterSearch.forEach(info ->{
            SysWaterDetailed waterDetailed=new SysWaterDetailed();
            waterDetailed.setCard(info.getCard());
            waterDetailed.setOperationType(info.getOperationType());
            waterDetailed.setWater(info.getWater());
            waterDetailed.setWaterAmount(info.getWaterAmount());
            waterDetailed.setActualWaterAmount(info.getActualWaterAmount());
            waterDetailed.setRemark(info.getRemark());
            waterDetailed.setCreateBy(info.getCreateBy());
            list.add(waterDetailed);
        });
        //添加洗码明细
        waterDetailedMapper.insertWaterDetailedList(list);
    }
}
