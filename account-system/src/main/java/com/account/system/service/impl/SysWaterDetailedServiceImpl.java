package com.account.system.service.impl;

import com.account.system.domain.SysWaterDetailed;
import com.account.system.domain.search.SysWaterDetailedSearch;
import com.account.system.domain.vo.SysWaterDetailedVo;
import com.account.system.mapper.SysWaterDetailedMapper;
import com.account.system.mapper.SysWaterMapper;
import com.account.system.service.SysWaterDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SysWaterDetailedServiceImpl implements SysWaterDetailedService {

    @Autowired
    SysWaterDetailedMapper waterDetailedMapper;

    @Override
    public int insertWaterDetailed(SysWaterDetailed waterDetailed) {
        return waterDetailedMapper.insertWaterDetailed(waterDetailed);
    }

    @Override
    public List<SysWaterDetailedVo> selectWaterDetailedList(SysWaterDetailedSearch waterDetailedSearch) {
        return waterDetailedMapper.selectWaterDetailedList( waterDetailedSearch);
    }

    @Override
    public Map selectWaterDetailedTotal(SysWaterDetailedSearch waterDetailedSearch) {
        return waterDetailedMapper.selectWaterDetailedTotal( waterDetailedSearch);
    }
}
