package com.account.system.mapper;

import com.account.system.domain.SysMembers;
import com.account.system.domain.SysMembersSearch;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
    List<Map> selectMembersList(SysMembersSearch sysMembers);

    void addMembers(SysMembers sysMembers);

    Map selectMembersInfo(@Param("id") Long id);

    void editMembers(SysMembers sysMembers);

    void deleteMembers(@Param("ids") String ids);

    int updateChipAmount(@Param("userId") Long userId, @Param("chipAmount") BigDecimal chipAmount,@Param("type")Integer type);
}
