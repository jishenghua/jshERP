package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.AccountHead;
import com.jsh.erp.datasource.entities.AccountHeadExample;
import com.jsh.erp.datasource.entities.AccountHeadVo4ListEx;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountHeadMapperEx {

    List<AccountHeadVo4ListEx> selectByConditionAccountHead(
            @Param("type") String type,
            @Param("creatorArray") String[] creatorArray,
            @Param("billNo") String billNo,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("organId") Long organId,
            @Param("creator") Long creator,
            @Param("handsPersonId") Long handsPersonId,
            @Param("accountId") Long accountId,
            @Param("status") String status,
            @Param("remark") String remark,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByAccountHead(
            @Param("type") String type,
            @Param("creatorArray") String[] creatorArray,
            @Param("billNo") String billNo,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("organId") Long organId,
            @Param("creator") Long creator,
            @Param("handsPersonId") Long handsPersonId,
            @Param("accountId") Long accountId,
            @Param("status") String status,
            @Param("remark") String remark);

    BigDecimal findAllMoney(
            @Param("supplierId") Integer supplierId,
            @Param("type") String type,
            @Param("modeName") String modeName,
            @Param("endTime") String endTime);

    List<AccountHeadVo4ListEx> getDetailByNumber(
            @Param("billNo") String billNo);

    int batchDeleteAccountHeadByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String[] ids);

    List<AccountHead> getAccountHeadListByAccountIds(@Param("accountIds") String[] accountIds);

    List<AccountHead> getAccountHeadListByOrganIds(@Param("organIds") String[] organIds);

    List<AccountHead> getAccountHeadListByHandsPersonIds(@Param("handsPersonIds") String[] handsPersonIds);
}