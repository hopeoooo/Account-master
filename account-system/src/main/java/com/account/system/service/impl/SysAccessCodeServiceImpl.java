package com.account.system.service.impl;

import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysAccessCodeSearch;
import com.account.system.mapper.SysAccessCodeMapper;
import com.account.system.service.SysAccessCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 存取码 服务层实现
 */
@Service
public class SysAccessCodeServiceImpl implements SysAccessCodeService {
    @Autowired
    private SysAccessCodeMapper accessCodeMapper;

    @Override
    public List<Map> selectAccessCodeList(SysAccessCodeSearch accessCodeSearch) {
        return accessCodeMapper.selectAccessCodeList(accessCodeSearch);
    }

    @Override
    public Map selectAccessCodeTotal(SysAccessCodeSearch accessCodeSearch)  {
        return accessCodeMapper.selectAccessCodeTotal(accessCodeSearch);
    }

    @Override
    public SysAccessCode selectAccessCodeInfo(Long userId) {
        return accessCodeMapper.selectAccessCodeInfo(userId);
    }

    @Override
    public int insertAccessCode(SysAccessCodeSearch accessCode) {
        BigDecimal cashBalance = accessCode.getCashBalance() == null ? BigDecimal.ZERO : accessCode.getCashBalance();
        BigDecimal chipBalance = accessCode.getChipBalance() == null ? BigDecimal.ZERO : accessCode.getChipBalance();
        accessCode.setTotalBalance(cashBalance.add(chipBalance));
        return accessCodeMapper.insertAccessCode(accessCode);
    }

    @Override
    public int updateAccessCode(SysAccessCodeSearch accessCode) {
        return accessCodeMapper.updateAccessCode(accessCode);
    }
}
