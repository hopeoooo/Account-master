package com.account.system.mapper;


import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysAccessCodeSearch;

import java.util.List;
import java.util.Map;

/**
 * 存取码 数据层
 */
public interface SysAccessCodeMapper {

    List<Map> selectAccessCodeList(SysAccessCodeSearch accessCodeSearch);

    Map selectAccessCodeTotal(SysAccessCodeSearch accessCodeSearch);

    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    SysAccessCode selectAccessCodeInfo(Long userId);

    int insertAccessCode(SysAccessCodeSearch accessCode) ;

    int updateAccessCode(SysAccessCodeSearch accessCode) ;

}
