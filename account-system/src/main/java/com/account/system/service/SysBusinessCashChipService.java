package com.account.system.service;


import com.account.system.domain.SysBusinessCashChip;
import com.account.system.domain.search.SysBusinessCashChipAddSearch;
import com.account.system.domain.vo.SysBusinessCashChipVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 买码换现 服务层
 */
public interface SysBusinessCashChipService
{
    List<SysBusinessCashChipVo> selectBusinessCashChipList(String card, Integer isAdmin);

    SysBusinessCashChip selectBusinessCashChipInfo(Long id, Long userId);

    Map selectBusinessCashChipTotal(String card,Integer isAdmin);

    int insert(SysBusinessCashChipAddSearch businessCashChipAddSearch);

    int update(SysBusinessCashChipAddSearch businessCashChipAddSearch);

}
