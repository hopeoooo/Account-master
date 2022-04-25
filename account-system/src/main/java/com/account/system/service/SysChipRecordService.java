package com.account.system.service;


import com.account.system.domain.search.SysChipRecordSearch;

import java.util.List;
import java.util.Map;

/**
 * 筹码变动明细 服务层
 */
public interface SysChipRecordService
{

    List<Map> selectChipRecordList(SysChipRecordSearch chipRecordSearch);
}
