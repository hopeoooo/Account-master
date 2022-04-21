package com.account.system.service;

import com.account.system.domain.vo.SysSignedRecordVo;

import java.util.List;
import java.util.Map;

/**
 * 签单 服务层
 */
public interface SysSignedRecordService
{
    /**
     * 查询签单列表
     * @return
     */
    List<SysSignedRecordVo> selectSignedRecordList();

}
