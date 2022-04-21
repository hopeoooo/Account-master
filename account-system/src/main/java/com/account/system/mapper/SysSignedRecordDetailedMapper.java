package com.account.system.mapper;

import com.account.system.domain.SysSignedRecordDetailed;
import com.account.system.domain.search.SysSignedRecordDetailedSearch;
import com.account.system.domain.vo.SysSignedRecordDetailedVo;

import java.util.List;

/**
 * 签单明细 数据层
 */
public interface SysSignedRecordDetailedMapper {

    int insertSignedRecordDetailed(SysSignedRecordDetailed signedRecordDetailed);

    List<SysSignedRecordDetailedVo> selectSignedRecordDetailedList(SysSignedRecordDetailedSearch signedRecordDetailedSearch);

}
