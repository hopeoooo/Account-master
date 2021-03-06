package com.account.system.service.impl;

import com.account.system.domain.SysMembers;
import com.account.system.domain.search.SysMembersSearch;
import com.account.system.domain.vo.SysMaterVo;
import com.account.system.mapper.SysMembersMapper;
import com.account.system.service.SysMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 参数配置 服务层实现
 *
 * @author hope
 */
@Service
public class SysMembersServiceImpl implements SysMembersService {

    @Autowired
    SysMembersMapper sysMembersMapper;

    @Override
    public List<Map> selectMembersList(SysMembersSearch sysMembers) {
        return sysMembersMapper.selectMembersList(sysMembers);
    }

    @Override
    public void addMembers(SysMembers sysMembers) {
        sysMembersMapper.addMembers(sysMembers);
    }

    @Override
    public Map selectMembersInfo(Long id) {
        return sysMembersMapper.selectMembersInfo(id);
    }

    @Override
    public void editMembers(SysMembers sysMembers) {
        sysMembersMapper.editMembers(sysMembers);
    }

    @Override
    public void deleteMembers(String ids) {
        sysMembersMapper.deleteMembers(ids);
    }

    @Override
    public SysMembers selectmembersByCard(String card) {
        return sysMembersMapper.selectmembersByCard(card);
    }

    @Override
    public List<Map> selectBusinessCashChipList(String card, Integer isAdmin) {
        return sysMembersMapper.selectBusinessCashChipList(card, isAdmin);
    }

    @Override
    public Map selectBusinessCashChipTotal(String card, Integer isAdmin) {
        return sysMembersMapper.selectBusinessCashChipTotal(card, isAdmin);
    }

    /**
     * @param card
     * @param chipAmount 操作金额
     * @param type       0:减,1:加
     * @return
     */
    @Override
    public int updateChipAmount(String card, BigDecimal chipAmount,BigDecimal chipAmountTh, Integer type) {
        return sysMembersMapper.updateChipAmount(card, chipAmount,chipAmountTh, type);
    }

    @Override
    public Map getOddsConfig() {
        return sysMembersMapper.getOddsConfig();
    }

    @Override
    public List<SysMaterVo> selectWaterList(String card, Integer isAdmin, Integer cardType) {
        return sysMembersMapper.selectWaterList(card,isAdmin,cardType);
    }

    @Override
    public Map selectWaterTotal(String card, Integer isAdmin,Integer cardType) {
        return sysMembersMapper.selectWaterTotal(card,isAdmin,cardType);
    }

    @Override
    public Map selectMembersCount(SysMembersSearch sysMembersSearch) {
        return sysMembersMapper.selectMembersCount(sysMembersSearch);
    }

}
