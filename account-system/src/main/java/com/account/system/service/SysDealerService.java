package com.account.system.service;


import com.account.common.core.domain.entity.SysDealer;
import com.account.system.domain.search.SysDealerSearch;
import com.account.system.domain.search.SysUserSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysDealerService {


    List<SysDealer> selectDealerList(SysDealerSearch dealerSearch);

    List<Map> selectDealerStatisticsList(SysDealerSearch dealerSearch);

    void addDealer(SysDealer dealer);

    void editDealer(SysDealer dealer);


    SysDealer selectDealerByUserName(String userName);

    void deleteDealer( Long userId);
}
