package com.account.system.service;

import com.account.system.domain.SysMembers;

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
    List<Map> selectMembersList(SysMembers sysMembers);


}
