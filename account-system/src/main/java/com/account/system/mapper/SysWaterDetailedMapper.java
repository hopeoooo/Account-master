package com.account.system.mapper;


import com.account.system.domain.SysWaterDetailed;
import com.account.system.domain.search.SysWaterDetailedSearch;
import com.account.system.domain.vo.SysWaterDetailedVo;

import java.util.List;
import java.util.Map;

/**
 * 洗码量明细
 *
 * @author hope
 */
public interface SysWaterDetailedMapper {

    List<SysWaterDetailedVo> selectWaterDetailedList(SysWaterDetailedSearch waterDetailedSearch);

    Map selectWaterDetailedTotal(SysWaterDetailedSearch waterDetailedSearch);


    int insertWaterDetailed(SysWaterDetailed waterDetailed);

    void insertWaterDetailedList(List<SysWaterDetailed> waterDetailed);

}
