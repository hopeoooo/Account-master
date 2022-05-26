package com.account.system.service.impl;


import com.account.common.core.domain.entity.SysDealer;
import com.account.system.domain.search.SysDealerSearch;
import com.account.system.domain.search.SysUserSearch;
import com.account.system.mapper.SysDealerMapper;
import com.account.system.service.SysDealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SysDealerServiceImpl implements SysDealerService {

    @Autowired
    private SysDealerMapper dealerMapper;


    @Override
    public List<SysDealer> selectDealerList(SysDealerSearch dealerSearch) {
        return dealerMapper.selectDealerList(dealerSearch);
    }

    @Override
    public List<Map> selectDealerStatisticsList(SysDealerSearch dealerSearch) {
        return dealerMapper.selectDealerStatisticsList(dealerSearch);
    }

    @Override
    public void addDealer(SysDealer dealer) {
         dealerMapper.addDealer(dealer);
    }

    @Override
    public void editDealer(SysDealer dealer) {
         dealerMapper.editDealer(dealer);
    }

    @Override
    public SysDealer selectDealerByUserName(String userName) {
        return dealerMapper.selectDealerByUserName(userName);
    }

    @Override
    public void deleteDealer(Long userId) {
        dealerMapper.deleteDealer(userId);
    }
}
