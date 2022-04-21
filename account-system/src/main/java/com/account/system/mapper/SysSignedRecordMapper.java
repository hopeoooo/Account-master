package com.account.system.mapper;


import com.account.system.domain.vo.SysSignedRecordVo;

import java.util.List;
import java.util.Map;

/**
 * 签单 数据层
 */
public interface SysSignedRecordMapper {

    /**
     * 查询签单列表
     * @return
     */
    List<SysSignedRecordVo> selectSignedRecordList();

}
