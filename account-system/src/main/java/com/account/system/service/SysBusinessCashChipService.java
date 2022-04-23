package com.account.system.service;


import com.account.system.domain.search.SysBusinessCashChipAddSearch;

/**
 * 买码换现 服务层
 */
public interface SysBusinessCashChipService
{

   int addBuyCode(SysBusinessCashChipAddSearch businessCashChipAddSearch);
}
