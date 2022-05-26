package com.account.system.mapper;


import com.account.common.core.domain.entity.SysDealer;
import com.account.system.domain.search.SysDealerSearch;
import com.account.system.domain.search.SysUserSearch;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysDealerMapper {


    List<SysDealer> selectDealerList(SysDealerSearch dealerSearch);


    List<Map> selectDealerStatisticsList(SysDealerSearch dealerSearch);

    void addDealer(SysDealer dealer);

    void editDealer(SysDealer dealer);

    SysDealer selectDealerByUserName(String userName);

    @Delete("delete from sys_dealer where user_id = #{userId}")
    void deleteDealer(@Param("userId") Long userId);
}
