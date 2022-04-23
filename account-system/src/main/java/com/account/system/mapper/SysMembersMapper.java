package com.account.system.mapper;

import com.account.system.domain.SysMembers;
import com.account.system.domain.search.SysMembersSearch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 参数配置 数据层
 *
 * @author hope
 */
public interface SysMembersMapper {
    /**
     * 查询参数配置列表
     */
    List<Map> selectMembersList(SysMembersSearch sysMembers);

    List<Map> selectBusinessCashChipList(@Param("card")String card, @Param("isAdmin")Integer isAdmin);

    Map selectBusinessCashChipTotal(@Param("card")String card, @Param("isAdmin")Integer isAdmin);

    void addMembers(SysMembers sysMembers);

    Map selectMembersInfo(@Param("id") Long id);

    void editMembers(SysMembers sysMembers);

    void deleteMembers(@Param("ids") String ids);

    int updateChipAmount(@Param("userId") Long userId, @Param("chipAmount") BigDecimal chipAmount,@Param("type")Integer type);

    SysMembers selectmembersByCard(@Param("card") String card);

    @Select("SELECT baccarat_rolling_ratio_chip baccaratRollingRatioChip, " +
            "baccarat_rolling_ratio_cash baccaratRollingRatioCash, " +
            "dragon_tiger_ratio_chip dragonTigerRatioChip, " +
            "dragon_tiger_ratio_cash dragonTigerRatioCash " +
            "FROM sys_odds_configure limit 0,1")
    Map getOddsConfig();

    @Update(" update sys_members set chip = chip + #{chip} where card = #{card}")
    int updateMembersChip(@Param("card") String card, @Param("chip") BigDecimal chip);

}
