package com.account.system.mapper;

import com.account.system.domain.SysChipRecord;
import org.apache.ibatis.annotations.Insert;

/**
 * 用户表 数据层
 *
 * @author hope
 */
public interface SysChipRecordMapper {

    @Insert("insert into sys_chip_record (card,type,`before`,`change`,`after`,create_by,create_time) " +
            "values (#{card},#{type},#{before},#{change},#{after},#{createBy},sysdate()) ")
    void addChipRecord(SysChipRecord sysChipRecord);

}
