package com.account.system.mapper;

import com.account.system.domain.SysChipRecord;
import org.apache.ibatis.annotations.Insert;

/**
 * 用户表 数据层
 *
 * @author hope
 */
public interface SysChipRecordMapper {

    @Insert("insert into (card,type,before,change,after,create_time,create_by) " +
            "values (#{card},#{type},#{before},#{change},#{after},sysdate(),#{createBy}) ")
    void addChipRecord(SysChipRecord sysChipRecord);

}
