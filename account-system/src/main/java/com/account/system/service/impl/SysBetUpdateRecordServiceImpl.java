package com.account.system.service.impl;

import com.account.common.utils.StringUtils;
import com.account.system.domain.*;
import com.account.system.domain.search.BetSearch;
import com.account.system.mapper.BetMapper;
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

    @Autowired
    BetMapper betMapper;

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
    public void saveBetRecord(BetUpdate betUpdate, List<SysBetInfo> oldBetInfos, List<SysBetInfo> newBetInfos,String gameResult) {
        SysBet sysBet = betMapper.getBet(betUpdate.getBetId());
        SysBetUpdateRecord sysBetUpdateRecord = new SysBetUpdateRecord(sysBet);
        //卡号
        sysBetUpdateRecord.setCard(compare(sysBet.getCard(),betUpdate.getCard()));
        //下注类型
        sysBetUpdateRecord.setType(compare(sysBet.getType(),betUpdate.getType()));
        //玩法
        StringBuffer oldOption = new StringBuffer("");
        final BigDecimal[] oldAmount = {BigDecimal.ZERO};
        final BigDecimal[] oldWin = {BigDecimal.ZERO};
        oldBetInfos.forEach(sysBetInfo -> {
            if(oldOption.length()>0) oldOption.append("/");
            oldAmount[0] = oldAmount[0].add(sysBetInfo.getBetMoney());
            oldOption.append(changeRelust(sysBetInfo.getBetOption()))
                    .append(":").append(sysBetInfo.getBetMoney());
            oldWin[0] = oldWin[0].add(sysBetInfo.getWinLose());
        });
        StringBuffer newOption = new StringBuffer("");
        final BigDecimal[] newAmount = {BigDecimal.ZERO};
        final BigDecimal[] newWin = {BigDecimal.ZERO};
        newBetInfos.forEach(sysBetInfo -> {
            if(newOption.length()>0) newOption.append("/");
            newAmount[0] = newAmount[0].add(sysBetInfo.getBetMoney().setScale(4));
            newOption.append(changeRelust(sysBetInfo.getBetOption()))
                    .append(":").append(sysBetInfo.getBetMoney().setScale(4));
            newWin[0] = newWin[0].add(sysBetInfo.getWinLose().setScale(4));
        });

        sysBetUpdateRecord.setOption(compare(oldOption.toString(),newOption.toString()));
        //金额
        sysBetUpdateRecord.setAmount(compare(oldAmount[0],newAmount[0]));
        //输赢
        sysBetUpdateRecord.setWin(compare(oldWin[0],newWin[0]));
        //操作员
        String oldCreateBy = sysBet.getUpdateBy()==null?sysBet.getCreateBy():sysBet.getUpdateBy();
        sysBetUpdateRecord.setCreateBy(compare(oldCreateBy,betUpdate.getUpdateBy()));
        //结果
        sysBetUpdateRecord.setResult(compare(changeRelust(gameResult),changeRelust(betUpdate.getGameResult())));
        sysBetUpdateRecordMapper.saveUpdateRecord(sysBetUpdateRecord);

        SysInputError sysInputError = new SysInputError(oldCreateBy,0l,1l);
        sysInputErrorMapper.saveInputError(sysInputError);
    }

    @Override
    public List selectBetUpdateList(BetSearch betSearch) {
        return sysBetUpdateRecordMapper.selectBetUpdateList(betSearch);
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

    private String compare(Object a,Object b){
        if(a.equals(b)){
            return a.toString();
        }else {
            return  a+"->"+b;
        }
    }
}