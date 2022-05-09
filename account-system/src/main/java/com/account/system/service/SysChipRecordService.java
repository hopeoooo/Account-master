package com.account.system.service;


import com.account.system.domain.search.SysChipRecordSearch;
import com.account.system.domain.vo.SysChipRecordVo;

import java.util.List;
import java.util.Map;

/**
 * 筹码变动明细 服务层
 */
public interface SysChipRecordService
{

    List<SysChipRecordVo> selectChipRecordList(SysChipRecordSearch chipRecordSearch);
}
