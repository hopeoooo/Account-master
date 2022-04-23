package com.account.system.mapper;


import com.account.system.domain.SysAccessCode;
import com.account.system.domain.search.SysAccessCodeAddSearch;
import com.account.system.domain.search.SysAccessCodeSearch;
import com.account.system.domain.vo.SysAccessCodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 存取码 数据层
 */
public interface SysAccessCodeMapper {

    List<SysAccessCodeVo> selectAccessCodeList(SysAccessCodeSearch accessCodeSearch);

    Map selectAccessCodeTotal(SysAccessCodeSearch accessCodeSearch);

    /**
     * 根据唯一id、用户id查询
     * @param card
     * @return
     */
    SysAccessCode selectAccessCodeInfo(@Param("id")Long id, @Param("card")String card);

    int insertAccessCode(SysAccessCodeAddSearch accessCode) ;

    int updateAccessCode(SysAccessCodeAddSearch accessCode) ;

}
