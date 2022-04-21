package com.account.framework.manager;

import com.account.common.utils.spring.SpringUtils;
import com.account.system.domain.SysChipRecord;
import com.account.system.mapper.SysChipRecordMapper;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author hope
 */
public class AsyncFactory {

    /**
     * 筹码变动记录
     */
    public static TimerTask recordChip(SysChipRecord sysChipRecord) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(SysChipRecordMapper.class).addChipRecord(sysChipRecord);
            }
        };
    }
}
