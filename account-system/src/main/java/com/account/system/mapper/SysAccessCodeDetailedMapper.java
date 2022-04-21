package com.account.system.mapper;


import com.account.system.domain.SysAccessCodeDetailed;
import com.account.system.domain.SysAccessCodeDetailedSearch;
import com.account.system.domain.vo.SysAccessCodeDetailedVo;

import java.util.List;
/**
 * 存取码明细 数据层
 */
public interface SysAccessCodeDetailedMapper {

    int insertAccessCodeDetailed(SysAccessCodeDetailed accessCodeDetailed);

    List<SysAccessCodeDetailedVo> selectAccessDetailedCodeList(SysAccessCodeDetailedSearch accessCodeDetailedSearch);

}
