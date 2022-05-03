package com.account.system.service;


import com.account.system.domain.search.InputErrorSearch;
import com.account.system.domain.vo.SysInputErrorVo;

import java.util.List;
import java.util.Map;

/**
 * 员工录入错帐报表 服务层
 */
public interface SysInputErrorService
{
    List<SysInputErrorVo> selectInputErrorList(InputErrorSearch errorSearch);

}
