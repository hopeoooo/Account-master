package com.account.system.mapper;


import com.account.system.domain.SysAccessCode;
import com.account.system.domain.SysAccessCodeAddSearch;
import com.account.system.domain.SysAccessCodeSearch;
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
     * @param userId
     * @return
     */
    SysAccessCode selectAccessCodeInfo(@Param("id")Long id, @Param("userId")Long userId);

    int insertAccessCode(SysAccessCodeAddSearch accessCode) ;

    int updateAccessCode(SysAccessCodeAddSearch accessCode) ;

}
