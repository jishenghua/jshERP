package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Account;
import com.jsh.erp.datasource.entities.AccountVo4Sum;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.vo.AccountVo4InOutList;
import com.jsh.erp.datasource.vo.AccountVo4List;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountMapperEx {

    List<Account> getAccountByParam(
            @Param("name") String name,
            @Param("serialNo") String serialNo);

    List<AccountVo4List> selectByConditionAccount(
            @Param("name") String name,
            @Param("serialNo") String serialNo,
            @Param("remark") String remark);

    BigDecimal getAccountSum(
            @Param("accountId") Long accountId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("forceFlag") Boolean forceFlag);

    BigDecimal getAccountSumByHead(
            @Param("accountId") Long accountId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("forceFlag") Boolean forceFlag);

    BigDecimal getAccountSumByDetail(
            @Param("accountId") Long accountId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("forceFlag") Boolean forceFlag);

    List<DepotHead> getManyAccountSum(
            @Param("accountId") Long accountId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("forceFlag") Boolean forceFlag);

    List<AccountVo4Sum> getAccountSumByParam(
            @Param("name") String name,
            @Param("serialNo") String serialNo,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("forceFlag") Boolean forceFlag,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    List<DepotHead> getManyAccountSumByParam(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("forceFlag") Boolean forceFlag);

    List<AccountVo4InOutList> findAccountInOutList(
            @Param("accountId") Long accountId,
            @Param("number") String number,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("forceFlag") Boolean forceFlag,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findAccountInOutListCount(
            @Param("accountId") Long accountId,
            @Param("number") String number,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("forceFlag") Boolean forceFlag);

    int batchDeleteAccountByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

}