package com.account.system.service;


import com.account.system.domain.SysAccessCodeDetailed;
import com.account.system.domain.search.SysAccessCodeDetailedSearch;
import com.account.system.domain.vo.SysAccessCodeDetailedVo;

import java.util.List;

/**
 * 存取码明细 服务层
 */
public interface SysAccessCodeDetailedService
{
    /**
     * 添加明细
     * @param accessCodeDetailed
     * @return
     */
    int insertAccessCodeDetailed(SysAccessCodeDetailed accessCodeDetailed);

    /**
     * 查询存取码明细列表
     * @return
     */
    List<SysAccessCodeDetailedVo> selectAccessDetailedCodeList(SysAccessCodeDetailedSearch accessCodeDetailedSearch);

}
