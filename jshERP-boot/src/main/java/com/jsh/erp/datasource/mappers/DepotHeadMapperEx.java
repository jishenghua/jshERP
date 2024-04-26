package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/1/25 14:50
 */
public interface DepotHeadMapperEx {
    List<DepotHeadVo4List> selectByConditionDepotHead(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("creatorArray") String[] creatorArray,
            @Param("hasDebt") String hasDebt,
            @Param("statusArray") String[] statusArray,
            @Param("purchaseStatusArray") String[] purchaseStatusArray,
            @Param("number") String number,
            @Param("linkNumber") String linkNumber,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("organId") Long organId,
            @Param("organArray") String[] organArray,
            @Param("creator") Long creator,
            @Param("depotId") Long depotId,
            @Param("depotArray") String[] depotArray,
            @Param("accountId") Long accountId,
            @Param("remark") String remark,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByDepotHead(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("creatorArray") String[] creatorArray,
            @Param("hasDebt") String hasDebt,
            @Param("statusArray") String[] statusArray,
            @Param("purchaseStatusArray") String[] purchaseStatusArray,
            @Param("number") String number,
            @Param("linkNumber") String linkNumber,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("organId") Long organId,
            @Param("organArray") String[] organArray,
            @Param("creator") Long creator,
            @Param("depotId") Long depotId,
            @Param("depotArray") String[] depotArray,
            @Param("accountId") Long accountId,
            @Param("remark") String remark);

    List<MaterialsListVo> findMaterialsListMapByHeaderIdList(
            @Param("idList") List<Long> idList);

    List<MaterialCountVo> getMaterialCountListByHeaderIdList(
            @Param("idList") List<Long> idList);

    List<DepotHeadVo4InDetail> findInOutDetail(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("creatorArray") String[] creatorArray,
            @Param("organArray") String[] organArray,
            @Param("forceFlag") Boolean forceFlag,
            @Param("inOutManageFlag") Boolean inOutManageFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("oId") Integer oId,
            @Param("number") String number,
            @Param("creator") Long creator,
            @Param("remark") String remark,
            @Param("column") String column,
            @Param("order") String order,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findInOutDetailCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("creatorArray") String[] creatorArray,
            @Param("organArray") String[] organArray,
            @Param("forceFlag") Boolean forceFlag,
            @Param("inOutManageFlag") Boolean inOutManageFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("oId") Integer oId,
            @Param("number") String number,
            @Param("creator") Long creator,
            @Param("remark") String remark);

    List<DepotHeadVo4InDetail> findInOutDetailStatistic(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("creatorArray") String[] creatorArray,
            @Param("organArray") String[] organArray,
            @Param("forceFlag") Boolean forceFlag,
            @Param("inOutManageFlag") Boolean inOutManageFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("oId") Integer oId,
            @Param("number") String number,
            @Param("creator") Long creator,
            @Param("remark") String remark);

    List<DepotHeadVo4InOutMCount> findInOutMaterialCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("forceFlag") Boolean forceFlag,
            @Param("inOutManageFlag") Boolean inOutManageFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("oId") Integer oId,
            @Param("creatorArray") String[] creatorArray,
            @Param("organArray") String[] organArray,
            @Param("column") String column,
            @Param("order") String order,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findInOutMaterialCountTotal(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("forceFlag") Boolean forceFlag,
            @Param("inOutManageFlag") Boolean inOutManageFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("oId") Integer oId,
            @Param("creatorArray") String[] creatorArray,
            @Param("organArray") String[] organArray);

    List<DepotHeadVo4InOutMCount> findInOutMaterialCountStatistic(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("forceFlag") Boolean forceFlag,
            @Param("inOutManageFlag") Boolean inOutManageFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("oId") Integer oId,
            @Param("creatorArray") String[] creatorArray,
            @Param("organArray") String[] organArray);

    List<DepotHeadVo4InDetail> findAllocationDetail(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("subType") String subType,
            @Param("number") String number,
            @Param("creatorArray") String[] creatorArray,
            @Param("forceFlag") Boolean forceFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("depotFList") List<Long> depotFList,
            @Param("remark") String remark,
            @Param("column") String column,
            @Param("order") String order,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findAllocationDetailCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("subType") String subType,
            @Param("number") String number,
            @Param("creatorArray") String[] creatorArray,
            @Param("forceFlag") Boolean forceFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("depotFList") List<Long> depotFList,
            @Param("remark") String remark);

    List<DepotHeadVo4InDetail> findAllocationStatistic(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("subType") String subType,
            @Param("number") String number,
            @Param("creatorArray") String[] creatorArray,
            @Param("forceFlag") Boolean forceFlag,
            @Param("materialParam") String materialParam,
            @Param("depotList") List<Long> depotList,
            @Param("depotFList") List<Long> depotFList,
            @Param("remark") String remark);

    List<DepotHeadVo4StatementAccount> getStatementAccount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("organId") Integer organId,
            @Param("organArray") String[] organArray,
            @Param("supplierType") String supplierType,
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("typeBack") String typeBack,
            @Param("subTypeBack") String subTypeBack,
            @Param("billType") String billType,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int getStatementAccountCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("organId") Integer organId,
            @Param("organArray") String[] organArray,
            @Param("supplierType") String supplierType,
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("typeBack") String typeBack,
            @Param("subTypeBack") String subTypeBack,
            @Param("billType") String billType);

    List<DepotHeadVo4StatementAccount> getStatementAccountTotalPay(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("organId") Integer organId,
            @Param("organArray") String[] organArray,
            @Param("supplierType") String supplierType,
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("typeBack") String typeBack,
            @Param("subTypeBack") String subTypeBack,
            @Param("billType") String billType);

    List<DepotHeadVo4List> getDetailByNumber(
            @Param("number") String number);

    int batchDeleteDepotHeadByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    List<DepotHead> getDepotHeadListByAccountIds(@Param("accountIds") String[] accountIds);

    List<DepotHead> getDepotHeadListByOrganIds(@Param("organIds") String[] organIds);

    List<DepotHead> getDepotHeadListByCreator(@Param("creatorArray") String[] creatorArray);

    List<InOutPriceVo> getBuyAndSaleStatisticsList(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("creatorArray") String[] creatorArray,
            @Param("forceFlag") Boolean forceFlag);

    List<DepotHeadVo4List> debtList(
            @Param("organId") Long organId,
            @Param("creatorArray") String[] creatorArray,
            @Param("status") String status,
            @Param("number") String number,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("depotArray") String[] depotArray,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int debtListCount(
            @Param("organId") Long organId,
            @Param("creatorArray") String[] creatorArray,
            @Param("status") String status,
            @Param("number") String number,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("depotArray") String[] depotArray);

    List<FinishDepositVo> getFinishDepositByNumberList(
            @Param("numberList") List<String> numberList);

    BigDecimal getFinishDepositByNumberExceptCurrent(
            @Param("linkNumber") String linkNumber,
            @Param("number") String number);

    void setAccountIdToNull(
            @Param("id") Long id);

    int getSerialNumberBySell(
            @Param("number") String number);

    List<DepotHeadVo4List> waitBillList(
            @Param("type") String type,
            @Param("subTypeArray") String[] subTypeArray,
            @Param("creatorArray") String[] creatorArray,
            @Param("statusArray") String[] statusArray,
            @Param("number") String number,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("depotArray") String[] depotArray,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long waitBillCount(
            @Param("type") String type,
            @Param("subTypeArray") String[] subTypeArray,
            @Param("creatorArray") String[] creatorArray,
            @Param("statusArray") String[] statusArray,
            @Param("number") String number,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("depotArray") String[] depotArray);

    BigDecimal getBillAllPriceByOrganId(
            @Param("organId") Long organId);
}
