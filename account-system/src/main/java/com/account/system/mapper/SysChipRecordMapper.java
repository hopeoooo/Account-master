package com.account.system.mapper;

import com.account.system.domain.SysChipRecord;
import com.account.system.domain.search.SysChipRecordSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 筹码变动明显
 */
public interface SysChipRecordMapper {

    void addChipRecord(SysChipRecord sysChipRecord);

    void addChipRecordList(List<SysChipRecord> sysChipRecord);

    List<Map> selectChipRecordList(SysChipRecordSearch chipRecordSearch);

    SysChipRecord selectChipRecord(@Param("card") String card,@Param("betId") Long betId);

    void updateChipRecord(SysChipRecord sysChipRecord);
}
