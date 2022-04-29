package com.account.system.service;


import com.account.system.domain.SysWaterDetailed;
import com.account.system.domain.search.SysWaterDetailedSearch;
import com.account.system.domain.vo.SysWaterDetailedVo;

import java.util.List;
import java.util.Map;

public interface SysWaterDetailedService {


    int insertWaterDetailed(SysWaterDetailed waterDetailed);



    List<SysWaterDetailedVo> selectWaterDetailedList(SysWaterDetailedSearch waterDetailedSearch);

    Map selectWaterDetailedTotal(SysWaterDetailedSearch waterDetailedSearch);
}
