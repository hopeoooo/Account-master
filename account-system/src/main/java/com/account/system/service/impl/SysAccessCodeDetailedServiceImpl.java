package com.account.system.service.impl;

import com.account.common.enums.AccessType;
import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysAccessCodeDetailed;
import com.account.system.domain.SysAccessCodeDetailedSearch;
import com.account.system.domain.SysAccessCodeSearch;
import com.account.system.domain.vo.SysAccessCodeDetailedVo;
import com.account.system.mapper.SysAccessCodeDetailedMapper;
import com.account.system.service.SysAccessCodeDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 存取码明细 服务层实现
 */
@Service
public class SysAccessCodeDetailedServiceImpl implements SysAccessCodeDetailedService {
    @Autowired
    private SysAccessCodeDetailedMapper accessCodeDetailedMapper;

    @Override
    public int insertAccessCodeDetailed(SysAccessCodeDetailed accessCodeDetailed) {
        return accessCodeDetailedMapper.insertAccessCodeDetailed(accessCodeDetailed);
    }


    /**
     * 查询存取码明细列表
     * @return
     */
    @Override
    public List<SysAccessCodeDetailedVo> selectAccessDetailedCodeList(SysAccessCodeDetailedSearch accessCodeDetailedSearch) {
        List<SysAccessCodeDetailedVo> sysAccessCodeDetaileds = accessCodeDetailedMapper.selectAccessDetailedCodeList(accessCodeDetailedSearch);
        return sysAccessCodeDetaileds;
    }
}
