package com.account.system.mapper;

import com.account.system.domain.SysMembers;

import java.util.List;
import java.util.Map;

/**
 * 参数配置 数据层
 *
 * @author hope
 */
public interface SysMembersMapper {
    /**
     * 查询参数配置列表
     */
    List<Map> selectMembersList(SysMembers sysMembers);

}
