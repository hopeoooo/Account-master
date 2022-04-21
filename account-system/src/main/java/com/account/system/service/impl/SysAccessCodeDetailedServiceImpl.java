package com.account.system.service.impl;

import com.account.system.domain.SysAccessCodeDetailed;
import com.account.system.domain.search.SysAccessCodeDetailedSearch;
import com.account.system.domain.vo.SysAccessCodeDetailedVo;
import com.account.system.mapper.SysAccessCodeDetailedMapper;
import com.account.system.service.SysAccessCodeDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
