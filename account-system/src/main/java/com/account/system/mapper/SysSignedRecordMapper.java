package com.account.system.mapper;


import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysSignedRecord;
import com.account.system.domain.SysTableManagement;
import com.account.system.domain.search.SysSignedRecordSearch;
import com.account.system.domain.vo.SysSignedRecordVo;
import org.apache.ibatis.annotations.Param;

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
    List<SysSignedRecordVo> selectSignedRecordList(@Param("card")String card, @Param("isAdmin")Integer isAdmin);

    Map selectSignedRecordTotal(@Param("card")String card, @Param("isAdmin")Integer isAdmin);


    SysSignedRecord selectSignedRecordInfo(@Param("id")Long id, @Param("userId")Long userId);


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
