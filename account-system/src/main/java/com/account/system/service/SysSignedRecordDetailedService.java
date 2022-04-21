package com.account.system.service;


import com.account.system.domain.search.SysSignedRecordDetailedSearch;
import com.account.system.domain.vo.SysSignedRecordDetailedVo;

import java.util.List;

/**
 * 签单明细 服务层
 */
public interface SysSignedRecordDetailedService
{
    List<SysSignedRecordDetailedVo> selectSignedRecordDetailedList(SysSignedRecordDetailedSearch signedRecordDetailedSearch);

}
