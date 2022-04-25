package com.account.system.service.impl;

import com.account.system.domain.search.SysChipRecordSearch;
import com.account.system.mapper.SysChipRecordMapper;
import com.account.system.service.SysChipRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 筹码变动明细 服务层实现
 */
@Service
public class SysChipRecordServiceImpl implements SysChipRecordService {
    @Autowired
    private SysChipRecordMapper chipRecordMapper;


    @Override
    public List<Map> selectChipRecordList(SysChipRecordSearch chipRecordSearch) {
        return chipRecordMapper.selectChipRecordList( chipRecordSearch);
    }
}
