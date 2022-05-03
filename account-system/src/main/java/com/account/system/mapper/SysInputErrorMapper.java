package com.account.system.mapper;

import com.account.system.domain.SysInputError;
import com.account.system.domain.search.InputErrorSearch;
import com.account.system.domain.vo.SysInputErrorVo;

import java.util.List;
import java.util.Map;

/**
 * @author hope
 * @since 2022/4/30
 */
public interface SysInputErrorMapper {

    void saveInputError(SysInputError sysInputError);

    List<SysInputErrorVo> selectInputErrorList(InputErrorSearch errorSearch);
}
