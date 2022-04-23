package com.account.system.mapper;


import com.account.system.domain.search.SysRemittanceDetailedSearch;
import com.account.system.domain.search.SysRemittanceSearch;
import com.account.system.domain.vo.SysRemittanceDetailedVo;

import java.util.List;
import java.util.Map;

/**
 * 汇款 数据层
 */
public interface SysRemittanceDetailedMapper {

    List<SysRemittanceDetailedVo> selectRemittanceDetailedList(SysRemittanceDetailedSearch remittanceDetailedSearch);

    Map selectRemittanceDetailedTotal(SysRemittanceDetailedSearch remittanceDetailedSearch);

    int insertRemittanceDetailed(SysRemittanceSearch remittanceSearch);

}
