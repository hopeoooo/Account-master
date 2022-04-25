package com.account.system.mapper;

import com.account.system.domain.SysChipRecord;
import com.account.system.domain.search.SysChipRecordSearch;
import org.apache.ibatis.annotations.Insert;

import java.util.List;
import java.util.Map;

/**
 * 用户表 数据层
 *
 * @author hope
 */
public interface SysChipRecordMapper {

    @Insert("insert into sys_chip_record (card,type,`before`,`change`,`after`,create_by,create_time) " +
            "values (#{card},#{type},#{before},#{change},#{after},#{createBy},sysdate()) ")
    void addChipRecord(SysChipRecord sysChipRecord);

    List<Map> selectChipRecordList(SysChipRecordSearch chipRecordSearch);

}
