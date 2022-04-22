package com.account.system.mapper;

import com.account.system.domain.SysBusinessCashChip;
import com.account.system.domain.search.SysBusinessCashChipAddSearch;
import com.account.system.domain.vo.SysBusinessCashChipVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 买码换现 数据层
 */
public interface SysBusinessCashChipMapper {

   List<SysBusinessCashChipVo> selectBusinessCashChipList(@Param("card")String card, @Param("isAdmin")Integer isAdmin);

   SysBusinessCashChip selectBusinessCashChipInfo(@Param("id")Long id, @Param("userId")Long userId);

   Map selectBusinessCashChipTotal(@Param("card")String card, @Param("isAdmin")Integer isAdmin);

   int insert(SysBusinessCashChipAddSearch businessCashChipAddSearch);

   int update(SysBusinessCashChipAddSearch businessCashChipAddSearch);
}
