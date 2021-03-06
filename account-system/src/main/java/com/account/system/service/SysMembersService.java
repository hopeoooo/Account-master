package com.account.system.service;

import com.account.system.domain.SysMembers;
import com.account.system.domain.search.SysMembersSearch;
import com.account.system.domain.vo.SysMaterVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 会员管理 服务层
 *
 * @author hope
 */
public interface SysMembersService {

    /**
     * 查询参数配置列表
     */
    List<Map> selectMembersList(SysMembersSearch sysMembers);


    void addMembers(SysMembers sysMembers);

    Map selectMembersInfo(Long id);

    void editMembers(SysMembers sysMembers);

    void deleteMembers(String ids);

    SysMembers selectmembersByCard(String card);

    List<Map> selectBusinessCashChipList(String card, Integer isAdmin);

    Map selectBusinessCashChipTotal(String card, Integer isAdmin);

    int updateChipAmount( String card,  BigDecimal chipAmount,BigDecimal chipAmountTh, Integer type);

    Map getOddsConfig();

    List<SysMaterVo> selectWaterList(String card, Integer isAdmin, Integer cardType);

    Map selectWaterTotal(String card, Integer isAdmin,Integer cardType);

    Map selectMembersCount(SysMembersSearch sysMembersSearch);
}
