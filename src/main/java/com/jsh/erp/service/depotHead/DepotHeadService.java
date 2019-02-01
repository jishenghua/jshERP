package com.jsh.erp.service.depotHead;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.entities.DepotHeadExample;
import com.jsh.erp.datasource.entities.DepotItem;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.DepotHeadMapper;
import com.jsh.erp.datasource.mappers.DepotHeadMapperEx;
import com.jsh.erp.datasource.mappers.DepotItemMapperEx;
import com.jsh.erp.datasource.vo.DepotHeadVo4InDetail;
import com.jsh.erp.datasource.vo.DepotHeadVo4InOutMCount;
import com.jsh.erp.datasource.vo.DepotHeadVo4List;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.serialNumber.SerialNumberService;
import com.jsh.erp.service.supplier.SupplierService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jsh.erp.utils.Tools.getCenternTime;

@Service
public class DepotHeadService {
    private Logger logger = LoggerFactory.getLogger(DepotHeadService.class);

    @Resource
    private DepotHeadMapper depotHeadMapper;
    @Resource
    private DepotHeadMapperEx depotHeadMapperEx;
    @Resource
    private UserService userService;
    @Resource
    DepotItemService depotItemService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private SerialNumberService serialNumberService;
    @Resource
    DepotItemMapperEx depotItemMapperEx;


    public DepotHead getDepotHead(long id) {
        return depotHeadMapper.selectByPrimaryKey(id);
    }

    public List<DepotHead> getDepotHead() {
        DepotHeadExample example = new DepotHeadExample();
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHeadVo4List> select(String type, String subType, String number, String beginTime, String endTime, String dhIds, int offset, int rows) {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list = depotHeadMapperEx.selectByConditionDepotHead(type, subType, number, beginTime, endTime, dhIds, offset, rows);
        if (null != list) {
            for (DepotHeadVo4List dh : list) {
                if(dh.getOthermoneylist() != null) {
                    String otherMoneyListStr = dh.getOthermoneylist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneylist(otherMoneyListStr);
                }
                if(dh.getOthermoneyitem() != null) {
                    String otherMoneyItemStr = dh.getOthermoneyitem().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneyitem(otherMoneyItemStr);
                }
                if(dh.getChangeamount() != null) {
                    dh.setChangeamount(dh.getChangeamount().abs());
                }
                if(dh.getTotalprice() != null) {
                    dh.setTotalprice(dh.getTotalprice().abs());
                }
                dh.setOpertimeStr(getCenternTime(dh.getOpertime()));
                dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                resList.add(dh);
            }
        }
        return resList;
    }



    public int countDepotHead(String type, String subType, String number, String beginTime, String endTime, String dhIds) {
        return depotHeadMapperEx.countsByDepotHead(type, subType, number, beginTime, endTime, dhIds);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotHead(String beanJson, HttpServletRequest request) {
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        Object userInfo = request.getSession().getAttribute("user");
        if (userInfo != null) {
            User sessionUser = (User) userInfo;
            String uName = sessionUser.getUsername();
            depotHead.setOperpersonname(uName);
        }
        depotHead.setCreatetime(new Timestamp(System.currentTimeMillis()));
        depotHead.setStatus(false);
        return depotHeadMapper.insert(depotHead);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotHead(String beanJson, Long id) {
        DepotHead dh = depotHeadMapper.selectByPrimaryKey(id);
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        depotHead.setId(id);
        depotHead.setStatus(dh.getStatus());
        depotHead.setCreatetime(dh.getCreatetime());
        depotHead.setOperpersonname(dh.getOperpersonname());
        return depotHeadMapper.updateByPrimaryKey(depotHead);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepotHead(Long id) {
        return depotHeadMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotHead(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdIn(idList);
        return depotHeadMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdNotEqualTo(id);
        List<DepotHead> list = depotHeadMapper.selectByExample(example);
        return list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String depotHeadIDs) {
        List<Long> ids = StringUtil.strToLongList(depotHeadIDs);
        DepotHead depotHead = new DepotHead();
        depotHead.setStatus(status);
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdIn(ids);
        return depotHeadMapper.updateByExampleSelective(depotHead, example);
    }

    public String buildNumber(String type, String subType, String beginTime, String endTime) {
        String newNumber = "0001"; //新编号
        try {
            DepotHeadExample example = new DepotHeadExample();
            example.createCriteria().andTypeEqualTo(type).andSubtypeEqualTo(subType)
                    .andOpertimeGreaterThanOrEqualTo(StringUtil.getDateByString(beginTime,null))
                    .andOpertimeLessThanOrEqualTo(StringUtil.getDateByString(endTime,null));
            example.setOrderByClause("Id desc");
            List<DepotHead> dataList = depotHeadMapper.selectByExample(example);
            //存放数据json数组
            if (null != dataList && dataList.size() > 0) {
                DepotHead depotHead = dataList.get(0);
                if (depotHead != null) {
                    String number = depotHead.getDefaultnumber(); //最大的单据编号
                    if (number != null) {
                        Integer lastNumber = Integer.parseInt(number.substring(12, 16)); //末四尾
                        lastNumber = lastNumber + 1;
                        Integer nLen = lastNumber.toString().length();
                        if (nLen == 1) {
                            newNumber = "000" + lastNumber.toString();
                        } else if (nLen == 2) {
                            newNumber = "00" + lastNumber.toString();
                        } else if (nLen == 3) {
                            newNumber = "0" + lastNumber.toString();
                        } else if (nLen == 4) {
                            newNumber = lastNumber.toString();
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>>>>>>>>>>>单据编号生成异常", e);
        }
        return newNumber;
    }

    public Long getMaxId() {
        return depotHeadMapperEx.getMaxId();
    }

    public String findMaterialsListByHeaderId(Long id) {
        String allReturn = depotHeadMapperEx.findMaterialsListByHeaderId(id);
        return allReturn;
    }

    public List<DepotHead> findByMonth(String monthTime) {
        DepotHeadExample example = new DepotHeadExample();
        monthTime = monthTime + "-31 00:00:00";
        Date month = StringUtil.getDateByString(monthTime, null);
        example.createCriteria().andOpertimeLessThanOrEqualTo(month);
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHead> getDepotHeadGiftOut(String projectId) {
        DepotHeadExample example = new DepotHeadExample();
        if (projectId != null) {
            example.createCriteria().andProjectidEqualTo(Long.parseLong(projectId));
        }
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHeadVo4InDetail> findByAll(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId, Integer offset, Integer rows) {
        return depotHeadMapperEx.findByAll(beginTime, endTime, type, pid, dids, oId, offset, rows);
    }

    public int findByAllCount(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId) {
        return depotHeadMapperEx.findByAllCount(beginTime, endTime, type, pid, dids, oId);
    }

    public List<DepotHeadVo4InOutMCount> findInOutMaterialCount(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId, Integer offset, Integer rows) {
        return depotHeadMapperEx.findInOutMaterialCount(beginTime, endTime, type, pid, dids, oId, offset, rows);
    }

    public int findInOutMaterialCountTotal(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId) {
        return depotHeadMapperEx.findInOutMaterialCountTotal(beginTime, endTime, type, pid, dids, oId);
    }

    public List<DepotHeadVo4StatementAccount> findStatementAccount(String beginTime, String endTime, Integer organId, String supType, Integer offset, Integer rows) {
        return depotHeadMapperEx.findStatementAccount(beginTime, endTime, organId, supType, offset, rows);
    }

    public int findStatementAccountCount(String beginTime, String endTime, Integer organId, String supType) {
        return depotHeadMapperEx.findStatementAccountCount(beginTime, endTime, organId, supType);
    }

    public BigDecimal findAllMoney(Integer supplierId, String type, String subType, String mode, String endTime) {
        String modeName = "";
        if (mode.equals("实际")) {
            modeName = "ChangeAmount";
        } else if (mode.equals("合计")) {
            modeName = "DiscountLastMoney";
        }
        return depotHeadMapperEx.findAllMoney(supplierId, type, subType, modeName, endTime);
    }

    public List<DepotHeadVo4List> getDetailByNumber(String number) {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list = depotHeadMapperEx.getDetailByNumber(number);
        if (null != list) {
            for (DepotHeadVo4List dh : list) {
                if(dh.getOthermoneylist() != null) {
                    String otherMoneyListStr = dh.getOthermoneylist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneylist(otherMoneyListStr);
                }
                if(dh.getOthermoneyitem() != null) {
                    String otherMoneyItemStr = dh.getOthermoneyitem().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneyitem(otherMoneyItemStr);
                }
                if(dh.getChangeamount() != null) {
                    dh.setChangeamount(dh.getChangeamount().abs());
                }
                if(dh.getTotalprice() != null) {
                    dh.setTotalprice(dh.getTotalprice().abs());
                }
                dh.setOpertimeStr(getCenternTime(dh.getOpertime()));
                dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                resList.add(dh);
            }
        }
        return resList;
    }

    /**
     * create by: cjl
     * description:
     *  新增单据主表及单据子表信息
     * create time: 2019/1/25 14:36
     * @Param: beanJson
     * @Param: inserted
     * @Param: deleted
     * @Param: updated
     * @return java.lang.String
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addDepotHeadAndDetail(String beanJson, String inserted, String deleted, String updated) throws Exception {
        /**处理单据主表数据*/
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        User userInfo=userService.getCurrentUser();
        depotHead.setOperpersonname(userInfo==null?null:userInfo.getUsername());
        //构造新的编号
        String dNumber = depotHead.getDefaultnumber();
        String number = dNumber.substring(0, 12); //截取前缀
        String beginTime = Tools.getNow() + " 00:00:00";
        String endTime = Tools.getNow() + " 23:59:59";
        String newNumber = buildNumber(depotHead.getType(), depotHead.getSubtype(), beginTime, endTime);  //从数据库查询最新的编号+1,这样能防止重复
        String allNewNumber = number + newNumber;
        String frontNumber = depotHead.getNumber();
        if(frontNumber.indexOf(number) > -1) {
            depotHead.setNumber(allNewNumber); //从后台取值
        } else {
            depotHead.setNumber(frontNumber); //从前端文本框里面获取
        }
        depotHead.setDefaultnumber(allNewNumber); //初始编号，一直都从后台取值
        depotHead.setCreatetime(new Timestamp(System.currentTimeMillis()));
        depotHead.setStatus(false);
        depotHeadMapperEx.adddepotHead(depotHead);

        /**入库和出库处理预付款信息*/
        if(BusinessConstants.PAY_TYPE_PREPAID.equals(depotHead.getPaytype())){
            if(depotHead.getOrganid()!=null) {
                supplierService.updateAdvanceIn(depotHead.getOrganid(), BigDecimal.ZERO.subtract(depotHead.getTotalprice()));
            }
        }
        /**入库和出库处理单据子表信息*/
        depotItemService.saveDetials(inserted,deleted,updated,depotHead.getId());
    }
    /**
     * create by: cjl
     * description:
     * 更新单据主表及单据子表信息
     * create time: 2019/1/28 14:47
     * @Param: id
     * @Param: beanJson
     * @Param: inserted
     * @Param: deleted
     * @Param: updated
     * @Param: preTotalPrice
     * @return java.lang.Object
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateDepotHeadAndDetail(Long id, String beanJson, String inserted, String deleted, String updated, BigDecimal preTotalPrice)throws Exception {
        /**更新单据主表信息*/
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        depotHead.setId(id);
        User userInfo=userService.getCurrentUser();
        depotHead.setOperpersonname(userInfo==null?null:userInfo.getUsername());
        depotHead.setOpertime(new Timestamp(System.currentTimeMillis()));
        depotHeadMapperEx.updatedepotHead(depotHead);
        /**入库和出库处理预付款信息*/
        if(BusinessConstants.PAY_TYPE_PREPAID.equals(depotHead.getPaytype())){
            if(depotHead.getOrganid()!=null){
                supplierService.updateAdvanceIn(depotHead.getOrganid(), BigDecimal.ZERO.subtract(depotHead.getTotalprice().subtract(preTotalPrice)));
            }
        }
        /**入库和出库处理单据子表信息*/
        depotItemService.saveDetials(inserted,deleted,updated,depotHead.getId());
    }

    /**
     * create by: cjl
     * description:
     *  删除单据主表及子表信息
     * create time: 2019/1/28 17:29
     * @Param: id
     * @return java.lang.Object
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void deleteDepotHeadAndDetail(Long id) throws Exception {
        //查询单据主表信息
        DepotHead depotHead =getDepotHead(id);
        User userInfo=userService.getCurrentUser();
        //删除出库数据回收序列号
        if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())
                &&!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubtype())){
            //查询单据子表列表
            List<DepotItem> depotItemList = depotItemMapperEx.findDepotItemListBydepotheadId(id,BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED);
            /**回收序列号*/
            if(depotItemList!=null&&depotItemList.size()>0){
                for(DepotItem depotItem:depotItemList){
                    serialNumberService.cancelSerialNumber(depotItem.getMaterialid(), depotItem.getHeaderid(),depotItem.getOpernumber().intValue(),userInfo);
                }
            }
        }
        /**删除单据子表数据*/
        depotItemMapperEx.deleteDepotItemByDepotHeadIds(new Long []{id});
        /**删除单据主表信息*/
        deleteDepotHead(id);
    }
    /**
     * create by: cjl
     * description:
     *  批量删除单据主表及子表信息
     * create time: 2019/1/28 17:29
     * @Param: id
     * @return java.lang.Object
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void batchDeleteDepotHeadAndDetail(String ids) throws Exception{
        if(StringUtil.isNotEmpty(ids)){
            String [] headIds=ids.split(",");
            for(int i=0;i<headIds.length;i++){
                deleteDepotHeadAndDetail(new Long(headIds[i]));
            }
        }
    }
}
