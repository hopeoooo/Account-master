package com.account.system.service.impl;

import com.account.system.domain.search.SysRemittanceDetailedSearch;
import com.account.system.domain.search.SysRemittanceSearch;
import com.account.system.domain.vo.SysRemittanceDetailedVo;
import com.account.system.mapper.SysRemittanceDetailedMapper;
import com.account.system.service.SysRemittanceDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 汇款服务层实现
 */
@Service
public class SysRemittanceDetailedServiceImpl implements SysRemittanceDetailedService {
    @Autowired
    private SysRemittanceDetailedMapper remittanceDetailedMapper;

    @Override
    public List<SysRemittanceDetailedVo> selectRemittanceDetailedList(SysRemittanceDetailedSearch remittanceDetailedSearch) {
        return remittanceDetailedMapper.selectRemittanceDetailedList(remittanceDetailedSearch);
    }

    @Override
    public int insertRemittanceDetailed(SysRemittanceSearch remittanceSearch) {
        return remittanceDetailedMapper.insertRemittanceDetailed(remittanceSearch);
    }
}
