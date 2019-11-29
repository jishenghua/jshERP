package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DepotEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji sheng hua 752*718*920
 */
@RestController
@RequestMapping(value = "/depot")
public class DepotController {
    private Logger logger = LoggerFactory.getLogger(DepotController.class);

    @Resource
    private DepotService depotService;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private SystemConfigService systemConfigService;

    @Resource
    private MaterialService materialService;

    @GetMapping(value = "/getAllList")
    public BaseResponseInfo getAllList(HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<Depot> depotList = depotService.getAllList();
            res.code = 200;
            res.data = depotList;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 用户对应仓库显示
     * @param type
     * @param keyId
     * @param request
     * @return
     */
    @PostMapping(value = "/findUserDepot")
    public JSONArray findUserDepot(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                 HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            List<Depot> dataList = depotService.findUserDepot();
            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 1);
            outer.put("text", "仓库列表");
            outer.put("state", "open");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Depot depot : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", depot.getId());
                    item.put("text", depot.getName());
                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + depot.getId().toString() + "]");
                    } catch (Exception e) {
                        logger.error(">>>>>>>>>>>>>>>>>设置用户对应的仓库：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                    }
                    if (flag == true) {
                        item.put("checked", true);
                    }
                    //结束
                    dataArray.add(item);
                }
            }
            outer.put("children", dataArray);
            arr.add(outer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 获取用户拥有权限的仓库列表
     * @param type
     * @param keyId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findDepotByUserId")
    public JSONArray findDepotByUserId(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                       HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            List<Depot> dataList = depotService.findUserDepot();
            //开始拼接json数据
            if (null != dataList) {
                boolean depotFlag = systemConfigService.getDepotFlag();
                for (Depot depot : dataList) {
                    JSONObject item = new JSONObject();
                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + depot.getId().toString() + "]");
                    } catch (DataAccessException e) {
                        logger.error(">>>>>>>>>>>>>>>>>查询用户对应的仓库：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                    }
                    if (!depotFlag || flag) {
                        item.put("id", depot.getId());
                        item.put("depotName", depot.getName());
                        arr.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
    /**
     * create by: cjl
     * description:
     * 查询仓库列表信息
     * create time: 2019/2/25 14:32
     * @Param: pageSize
     * @Param: currentPage
     * @Param: search
     * @return java.lang.String
     */
    @RequestMapping(value = "/getDepotList")
    public String getDepotList(
            @RequestParam(value = Constants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = Constants.CURRENT_PAGE, required = false) Integer currentPage,
            @RequestParam(value = Constants.SEARCH, required = false) String search) throws Exception{
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        //查询参数
        JSONObject obj=JSON.parseObject(search);
        Set<String> key= obj.keySet();
        for(String keyEach: key){
            parameterMap.put(keyEach,obj.getString(keyEach));
        }
        PageQueryInfo queryInfo = new PageQueryInfo();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (pageSize == null || pageSize <= 0) {
            pageSize = BusinessConstants.DEFAULT_PAGINATION_PAGE_SIZE;
        }
        if (currentPage == null || currentPage <= 0) {
            currentPage = BusinessConstants.DEFAULT_PAGINATION_PAGE_NUMBER;
        }
        PageHelper.startPage(currentPage,pageSize,true);
        List<DepotEx> list = depotService.getDepotList(parameterMap);
        //获取分页查询后的数据
       PageInfo<DepotEx> pageInfo = new PageInfo<>(list);
        objectMap.put("page", queryInfo);
        if (list == null) {
            queryInfo.setRows(new ArrayList<Object>());
            queryInfo.setTotal(BusinessConstants.DEFAULT_LIST_NULL_NUMBER);
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
        queryInfo.setRows(list);
        queryInfo.setTotal(pageInfo.getTotal());
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  批量删除仓库信息
     * create time: 2019/3/29 11:15
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteDepotByIds")
    public Object batchDeleteDepotByIds(@RequestParam("ids") String ids,@RequestParam(value="deleteType",
            required =false,defaultValue=BusinessConstants.DELETE_TYPE_NORMAL)String deleteType) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        int i=0;
        if(BusinessConstants.DELETE_TYPE_NORMAL.equals(deleteType)){
            i= depotService.batchDeleteDepotByIdsNormal(ids);
        }else if(BusinessConstants.DELETE_TYPE_FORCE.equals(deleteType)){
            i= depotService.batchDeleteDepotByIds(ids);
        }else{
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}],deleteType[{}]",
                    ExceptionConstants.DELETE_REFUSED_CODE,ExceptionConstants.DELETE_REFUSED_MSG,ids,deleteType);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_REFUSED_CODE,
                    ExceptionConstants.DELETE_REFUSED_MSG);
        }
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.DEPOT_DELETE_FAILED_CODE,ExceptionConstants.DEPOT_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DEPOT_DELETE_FAILED_CODE,
                    ExceptionConstants.DEPOT_DELETE_FAILED_MSG);
        }
        return result;
    }
    @PostMapping(value = "/updateDepotIsDefault")
    public String updateDepotIsDefault(@RequestParam("isDefault") Boolean isDefault,
                                        @RequestParam("depotID") Long depotID,
                                        HttpServletRequest request) throws Exception{
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int res = depotService.updateDepotIsDefault(isDefault, depotID);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/getAllListWithStock")
    public BaseResponseInfo getAllList(@RequestParam("mId") Long mId,
                                       HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<Depot> list = depotService.getAllList();
            List<DepotEx> depotList = new ArrayList<DepotEx>();
            for(Depot depot: list) {
                DepotEx de = new DepotEx();
                if(mId!=0) {
                    BigDecimal stock = materialService.getInitStock(mId, depot.getId());
                    de.setStock(stock);
                } else {
                    de.setStock(BigDecimal.ZERO);
                }
                de.setId(depot.getId());
                de.setName(depot.getName());
                depotList.add(de);
            }
            res.code = 200;
            res.data = depotList;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
