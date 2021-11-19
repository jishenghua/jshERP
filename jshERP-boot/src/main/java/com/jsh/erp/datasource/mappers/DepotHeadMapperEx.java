package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.vo.DepotHeadVo4InDetail;
import com.jsh.erp.datasource.vo.DepotHeadVo4InOutMCount;
import com.jsh.erp.datasource.vo.DepotHeadVo4List;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
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
            @Param("statusArray") String[] statusArray,
            @Param("number") String number,
            @Param("linkNumber") String linkNumber,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("organId") Long organId,
            @Param("creator") Long creator,
            @Param("depotId") Long depotId,
            @Param("depotArray") String[] depotArray,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByDepotHead(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("creatorArray") String[] creatorArray,
            @Param("statusArray") String[] statusArray,
            @Param("number") String number,
            @Param("linkNumber") String linkNumber,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("organId") Long organId,
            @Param("creator") Long creator,
            @Param("depotId") Long depotId,
            @Param("depotArray") String[] depotArray);

    String findMaterialsListByHeaderId(
            @Param("id") Long id);

    List<DepotHeadVo4InDetail> findByAll(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("materialParam") String materialParam,
            @Param("depotId") Integer depotId,
            @Param("oId") Integer oId,
            @Param("number") String number,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findByAllCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("materialParam") String materialParam,
            @Param("depotId") Integer depotId,
            @Param("oId") Integer oId,
            @Param("number") String number);

    List<DepotHeadVo4InOutMCount> findInOutMaterialCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("materialParam") String materialParam,
            @Param("depotId") Integer depotId,
            @Param("oId") Integer oId,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findInOutMaterialCountTotal(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("materialParam") String materialParam,
            @Param("depotId") Integer depotId,
            @Param("oId") Integer oId);

    List<DepotHeadVo4InDetail> findAllocationDetail(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("subType") String subType,
            @Param("number") String number,
            @Param("materialParam") String materialParam,
            @Param("depotId") Integer depotId,
            @Param("depotIdF") Integer depotIdF,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findAllocationDetailCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("subType") String subType,
            @Param("number") String number,
            @Param("materialParam") String materialParam,
            @Param("depotId") Integer depotId,
            @Param("depotIdF") Integer depotIdF);

    List<DepotHeadVo4StatementAccount> findStatementAccount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("organId") Integer organId,
            @Param("supType") String supType,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findStatementAccountCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("organId") Integer organId,
            @Param("supType") String supType);

    BigDecimal findAllMoney(
            @Param("supplierId") Integer supplierId,
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("modeName") String modeName,
            @Param("endTime") String endTime);

    BigDecimal findAllOtherMoney(
            @Param("supplierId") Integer supplierId,
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("endTime") String endTime);

    List<DepotHeadVo4List> getDetailByNumber(
            @Param("number") String number);

    int batchDeleteDepotHeadByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    List<DepotHead> getDepotHeadListByAccountIds(@Param("accountIds") String[] accountIds);

    List<DepotHead> getDepotHeadListByOrganIds(@Param("organIds") String[] organIds);

    List<DepotHead> getDepotHeadListByCreator(@Param("creatorArray") String[] creatorArray);

    BigDecimal getBuyAndSaleStatistics(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("hasSupplier") Integer hasSupplier,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime);

    BigDecimal getBuyAndSaleRetailStatistics(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime);

    List<DepotHeadVo4List> debtList(
            @Param("organId") Long organId,
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("creatorArray") String[] creatorArray,
            @Param("status") String status,
            @Param("number") String number,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("materialParam") String materialParam,
            @Param("depotArray") String[] depotArray);
}
