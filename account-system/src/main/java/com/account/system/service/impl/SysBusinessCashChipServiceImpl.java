package com.account.system.service.impl;

import com.account.common.constant.CommonConst;
import com.account.common.enums.AccessType;
import com.account.system.domain.SysBusinessCashChip;
import com.account.system.domain.search.SysBusinessCashChipAddSearch;
import com.account.system.domain.vo.SysBusinessCashChipVo;
import com.account.system.mapper.SysBusinessCashChipMapper;
import com.account.system.mapper.SysMembersMapper;
import com.account.system.service.SysBusinessCashChipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 买码换现 服务层实现
 */
@Service
public class SysBusinessCashChipServiceImpl implements SysBusinessCashChipService {
    @Autowired
    private SysBusinessCashChipMapper businessCashChipMapper;

    @Autowired
    private SysMembersMapper membersMapper;


    @Override
    public List<SysBusinessCashChipVo> selectBusinessCashChipList(String card, Integer isAdmin) {
        return businessCashChipMapper.selectBusinessCashChipList(card,isAdmin);
    }

    @Override
    public SysBusinessCashChip selectBusinessCashChipInfo(Long id, Long userId) {
        return businessCashChipMapper.selectBusinessCashChipInfo(id,userId);
    }

    @Override
    public Map selectBusinessCashChipTotal(String card, Integer isAdmin) {
        return businessCashChipMapper.selectBusinessCashChipTotal(card,isAdmin);
    }

    @Override
    @Transactional
    public int insert(SysBusinessCashChipAddSearch businessCashChipAddSearch) {
        //更新用户筹码金额
        if (businessCashChipAddSearch.getChipAmount().compareTo(BigDecimal.ZERO)>0){
            updateUserChipAmount(businessCashChipAddSearch.getChipAmount(), CommonConst.NUMBER_1,businessCashChipAddSearch.getUserId());
        }
        return businessCashChipMapper.insert(businessCashChipAddSearch);
    }

    @Override
    @Transactional
    public int update(SysBusinessCashChipAddSearch businessCashChipAddSearch){
        //更新用户筹码金额
        if (businessCashChipAddSearch.getChipAmount().compareTo(BigDecimal.ZERO)>0){
            int number1 =businessCashChipAddSearch.getMark()== AccessType.BUY_CODE.getCode() ? CommonConst.NUMBER_1: CommonConst.NUMBER_0;
            updateUserChipAmount(businessCashChipAddSearch.getChipAmount(), number1,businessCashChipAddSearch.getUserId());
        }
        return businessCashChipMapper.update(businessCashChipAddSearch);
    }

    /**
     * 更新用户筹码金额
     * @param chipAmount
     * @param type 0:减、1:加
     * @return
     */
    public int updateUserChipAmount(BigDecimal chipAmount,int type,Long userId){
        return  membersMapper.updateChipAmount(userId, chipAmount, type);
    }

}
