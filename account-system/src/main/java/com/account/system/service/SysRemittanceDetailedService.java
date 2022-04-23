package com.account.system.service;

import com.account.system.domain.search.SysRemittanceDetailedSearch;
import com.account.system.domain.search.SysRemittanceSearch;
import com.account.system.domain.vo.SysRemittanceDetailedVo;

import java.util.List;
import java.util.Map;

/**
 * 汇款 服务层
 */
public interface SysRemittanceDetailedService
{

    List<SysRemittanceDetailedVo> selectRemittanceDetailedList(SysRemittanceDetailedSearch remittanceDetailedSearch);

    Map selectRemittanceDetailedTotal(SysRemittanceDetailedSearch remittanceDetailedSearch);

    int insertRemittanceDetailed(SysRemittanceSearch remittanceSearch);
}
