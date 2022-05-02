package com.account.system.service.impl;

import com.account.common.utils.StringUtils;
import com.account.system.domain.SysBet;
import com.account.system.domain.SysBetInfo;
import com.account.system.domain.SysBetUpdateRecord;
import com.account.system.domain.SysInputError;
import com.account.system.mapper.SysBetUpdateRecordMapper;
import com.account.system.mapper.SysInputErrorMapper;
import com.account.system.service.SysBetUpdateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hope
 * @since 2022/4/22
 */
@Service
public class SysBetUpdateRecordServiceImpl implements SysBetUpdateRecordService {

    @Autowired
    SysBetUpdateRecordMapper sysBetUpdateRecordMapper;

    @Autowired
    SysInputErrorMapper sysInputErrorMapper;

    /**
     * 修改赛果
     */
    public void saveResultRecord(SysBet sysBet, String gameResult, Long betId, List<SysBetInfo> sysBetInfos,BigDecimal win) {
        if(!sysBet.getBetId().equals(betId)){
            SysBetUpdateRecord sysBetUpdateRecord = new SysBetUpdateRecord(sysBet);
            StringBuffer option = new StringBuffer("");
            final BigDecimal[] amount = {BigDecimal.ZERO};
            final BigDecimal[] oldWin = {BigDecimal.ZERO};
            sysBetInfos.forEach(sysBetInfo -> {
                if(option.length()>0) option.append("/");
                amount[0] = amount[0].add(sysBetInfo.getBetMoney());
                option.append(changeRelust(sysBetInfo.getBetOption()))
                        .append(":").append(sysBetInfo.getBetMoney());
                oldWin[0] = oldWin[0].add(sysBetInfo.getWinLose());
            });
            sysBetUpdateRecord.setWin(oldWin[0].toString());
            if(oldWin[0].compareTo(win)!=0)sysBetUpdateRecord.setWin(sysBetUpdateRecord.getWin()+"->"+win.toString());
            sysBetUpdateRecord.setOption(option.toString());
            sysBetUpdateRecord.setAmount(amount[0].toString());
            sysBetUpdateRecord.setResult(changeRelust(gameResult)+"->"+changeRelust(sysBet.getGameResult()));
            sysBetUpdateRecordMapper.saveUpdateRecord(sysBetUpdateRecord);

            SysInputError sysInputError = new SysInputError(sysBet.getCreateBy(),0l,1l);
            if(StringUtils.isNotEmpty(sysBet.getUpdateBy()))sysInputError.setUserName(sysBet.getUpdateBy());
            sysInputErrorMapper.saveInputError(sysInputError);
        }
    }

    /**
     * 修改注单
     */
    public void saveBetRecord() {
        sysBetUpdateRecordMapper.saveUpdateRecord(new SysBetUpdateRecord());
    }

    private String changeRelust(String s){
        return s.replaceAll("1","闲")
                .replaceAll("4","庄")
                .replaceAll("7","和")
                .replaceAll("5","闲对")
                .replaceAll("8","庄对")
                .replaceAll("9","大")
                .replaceAll("6","小")
                .replaceAll("0","闲保险")
                .replaceAll("3","庄保险");
    }
}
