package com.account.system.service;

import com.account.system.domain.SysSignedRecord;
import com.account.system.domain.search.SysSignedRecordSearch;
import com.account.system.domain.vo.SysSignedRecordVo;
import org.apache.ibatis.annotations.Param;

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
    List<SysSignedRecordVo> selectSignedRecordList(String card, Integer isAdmin);

    Map selectSignedRecordTotal(String card, Integer isAdmin);


    SysSignedRecord selectSignedRecordInfo(Long id, Long userId);
    /**
     * 添加
     * @return
     */
    int insertSigned(SysSignedRecordSearch signedRecordSearch);

    /**
     * 修改
     * @param signedRecordSearch
     * @return
     */
    int update(SysSignedRecordSearch signedRecordSearch);

}
