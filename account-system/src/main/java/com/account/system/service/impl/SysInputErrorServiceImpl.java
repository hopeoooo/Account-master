package com.account.system.service.impl;

import com.account.common.utils.StringUtils;
import com.account.system.domain.search.InputErrorSearch;
import com.account.system.domain.vo.SysInputErrorVo;
import com.account.system.mapper.SysInputErrorMapper;
import com.account.system.service.SysInputErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 员工录入错帐报表 服务层实现
 */
@Service
public class SysInputErrorServiceImpl implements SysInputErrorService {
    @Autowired
    private SysInputErrorMapper inputErrorMapper;

    @Override
    public List<SysInputErrorVo> selectInputErrorList(InputErrorSearch errorSearch) {
        List<SysInputErrorVo> list = inputErrorMapper.selectInputErrorList(errorSearch);
        list.stream().forEach(info ->{
            if (StringUtils.isNotNull(info.getErrorRate())){
                info.setErrorRate(info.getErrorRate()+"%");
            }
        });
        return list;
    }
}
