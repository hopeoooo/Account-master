package com.account.system.mapper;

import com.account.system.domain.search.InputErrorSearch;
import com.account.system.domain.vo.SysInputErrorVo;

import java.util.List;

/**
 * @author hope
 * @since 2022/4/30
 */
public interface SysInputErrorMapper {

    List<SysInputErrorVo> selectInputErrorList(InputErrorSearch errorSearch);
}
