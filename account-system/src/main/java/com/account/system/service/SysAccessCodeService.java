package com.account.system.service;

import com.account.system.domain.SysAccessCode;
import com.account.system.domain.search.SysAccessCodeAddSearch;
import com.account.system.domain.search.SysAccessCodeSearch;
import com.account.system.domain.vo.SysAccessCodeVo;

import java.util.List;
import java.util.Map;

/**
 * 存取码 服务层
 */
public interface SysAccessCodeService
{

    List<SysAccessCodeVo> selectAccessCodeList(SysAccessCodeSearch accessCodeSearch);

    Map selectAccessCodeTotal(SysAccessCodeSearch accessCodeSearch) ;

    SysAccessCode selectAccessCodeInfo(Long id ,String card);

     int insertAccessCode(SysAccessCodeAddSearch accessCode) ;
     int updateAccessCode(SysAccessCodeAddSearch accessCode) ;

}
