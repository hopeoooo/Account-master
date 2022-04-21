package com.account.system.service.impl;

import com.account.system.domain.vo.SysSignedRecordVo;
import com.account.system.mapper.SysSignedRecordMapper;
import com.account.system.service.SysSignedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 签单 服务层实现
 */
@Service
public class SysSignedRecordServiceImpl implements SysSignedRecordService {
    @Autowired
    private SysSignedRecordMapper signedRecordMapper;

    @Override
    public List<SysSignedRecordVo> selectSignedRecordList() {
        return signedRecordMapper.selectSignedRecordList();
    }
}
