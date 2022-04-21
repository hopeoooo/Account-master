package com.account.system.service.impl;

import com.account.system.domain.search.SysSignedRecordDetailedSearch;
import com.account.system.domain.vo.SysSignedRecordDetailedVo;
import com.account.system.mapper.SysSignedRecordDetailedMapper;
import com.account.system.service.SysSignedRecordDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 签单明细 服务层实现
 */
@Service
public class SysSignedRecordDetailedServiceImpl implements SysSignedRecordDetailedService {
    @Autowired
    private SysSignedRecordDetailedMapper signedRecordDetailedMapper;


    @Override
    public List<SysSignedRecordDetailedVo> selectSignedRecordDetailedList(SysSignedRecordDetailedSearch signedRecordDetailedSearch) {
        return signedRecordDetailedMapper.selectSignedRecordDetailedList(signedRecordDetailedSearch);
    }
}
