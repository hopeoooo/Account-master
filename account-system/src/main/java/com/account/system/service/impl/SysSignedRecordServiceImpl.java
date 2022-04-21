package com.account.system.service.impl;

import com.account.system.domain.SysSignedRecord;
import com.account.system.domain.search.SysSignedRecordSearch;
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
    public List<SysSignedRecordVo> selectSignedRecordList(String card, Integer isAdmin) {
        return signedRecordMapper.selectSignedRecordList(card, isAdmin);
    }

    @Override
    public Map selectSignedRecordTotal(String card, Integer isAdmin) {
        return signedRecordMapper.selectSignedRecordTotal(card, isAdmin);
    }

    @Override
    public SysSignedRecord selectSignedRecordInfo(Long id, Long userId) {
        return signedRecordMapper.selectSignedRecordInfo(id, userId);
    }

    @Override
    public int insertSigned(SysSignedRecordSearch signedRecordSearch) {
        return signedRecordMapper.insertSigned(signedRecordSearch);
    }

    @Override
    public int update(SysSignedRecordSearch signedRecordSearch) {
        return signedRecordMapper.update(signedRecordSearch);
    }
}
