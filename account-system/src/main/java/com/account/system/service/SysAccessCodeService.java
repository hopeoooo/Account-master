package com.account.system.service;

import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysAccessCodeAddSearch;
import com.account.system.domain.SysAccessCodeSearch;

import java.util.List;
import java.util.Map;

/**
 * 存取码 服务层
 */
public interface SysAccessCodeService
{

    List<Map> selectAccessCodeList(SysAccessCodeSearch accessCodeSearch);

    Map selectAccessCodeTotal(SysAccessCodeSearch accessCodeSearch) ;

    SysAccessCode selectAccessCodeInfo(Long id ,Long userId);

     int insertAccessCode(SysAccessCodeAddSearch accessCode) ;
     int updateAccessCode(SysAccessCodeAddSearch accessCode) ;

}
