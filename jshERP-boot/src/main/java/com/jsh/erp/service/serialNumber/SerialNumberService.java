package com.jsh.erp.service.serialNumber;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/1/21 16:33
 */
@Service
public class SerialNumberService {
    private Logger logger = LoggerFactory.getLogger(SerialNumberService.class);

    @Resource
    private SerialNumberMapper serialNumberMapper;
    @Resource
    private SerialNumberMapperEx serialNumberMapperEx;
    @Resource
    private MaterialMapperEx materialMapperEx;
    @Resource
    private MaterialService materialService;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;


    public SerialNumber getSerialNumber(long id)throws Exception {
        SerialNumber result=null;
        try{
            result=serialNumberMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<SerialNumber> getSerialNumberListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<SerialNumber> list = new ArrayList<>();
        try{
            SerialNumberExample example = new SerialNumberExample();
            example.createCriteria().andIdIn(idList);
            list = serialNumberMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<SerialNumber> getSerialNumber()throws Exception {
        SerialNumberExample example = new SerialNumberExample();
        List<SerialNumber> list=null;
        try{
            list=serialNumberMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<SerialNumberEx> select(String serialNumber, String materialName, Integer offset, Integer rows)throws Exception {
        return null;

    }

    public Long countSerialNumber(String serialNumber,String materialName)throws Exception {
        return null;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSerialNumber(JSONObject obj, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            SerialNumberEx serialNumberEx = JSONObject.parseObject(obj.toJSONString(), SerialNumberEx.class);
            /**处理商品id*/
            serialNumberEx.setMaterialId(getSerialNumberMaterialIdByBarCode(serialNumberEx.getMaterialCode()));
            //删除标记,默认未删除
            serialNumberEx.setDeleteFlag(BusinessConstants.DELETE_FLAG_EXISTS);
            //已卖出，默认未否
            serialNumberEx.setIsSell(BusinessConstants.IS_SELL_HOLD);
            Date date=new Date();
            serialNumberEx.setCreateTime(date);
            serialNumberEx.setUpdateTime(date);
            User userInfo=userService.getCurrentUser();
            serialNumberEx.setCreator(userInfo==null?null:userInfo.getId());
            serialNumberEx.setUpdater(userInfo==null?null:userInfo.getId());
            result = serialNumberMapperEx.addSerialNumber(serialNumberEx);
            logService.insertLog("序列号",BusinessConstants.LOG_OPERATION_TYPE_ADD,
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSerialNumber(JSONObject obj, HttpServletRequest request) throws Exception{
        SerialNumberEx serialNumberEx = JSONObject.parseObject(obj.toJSONString(), SerialNumberEx.class);
        int result=0;
        try{
            serialNumberEx.setMaterialId(getSerialNumberMaterialIdByBarCode(serialNumberEx.getMaterialCode()));
            Date date=new Date();
            serialNumberEx.setUpdateTime(date);
            User userInfo=userService.getCurrentUser();
            serialNumberEx.setUpdater(userInfo==null?null:userInfo.getId());
            result = serialNumberMapperEx.updateSerialNumber(serialNumberEx);
            logService.insertLog("序列号",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(serialNumberEx.getId()).toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSerialNumber(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteSerialNumberByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSerialNumber(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteSerialNumberByIds(ids);
    }

    /**
     * create by: qiankunpingtai
     *  逻辑删除序列号信息
     * create time: 2019/3/27 17:43
     * @Param: ids
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSerialNumberByIds(String ids) throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<SerialNumber> list = getSerialNumberListByIds(ids);
        for(SerialNumber serialNumber: list){
            sb.append("[").append(serialNumber.getSerialNumber()).append("]");
        }
        logService.insertLog("序列号", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        try{
            result = serialNumberMapperEx.batchDeleteSerialNumberByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String serialNumber)throws Exception {
        SerialNumberExample example = new SerialNumberExample();
        example.createCriteria().andIdNotEqualTo(id).andSerialNumberEqualTo(serialNumber).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<SerialNumber> list=null;
        try{
            list=serialNumberMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }
    /**
     * create by: cjl
     * description:
     *  根据商品名称判断商品名称是否有效
     * create time: 2019/1/23 17:04
     * @Param: materialName
     * @return Long 满足使用条件的商品的id
     */
    public Long checkMaterialName(String materialName)throws Exception{
        if(StringUtil.isNotEmpty(materialName)) {
            List<Material> mlist=null;
            try{
                mlist = materialMapperEx.findByMaterialName(materialName);
            }catch(Exception e){
                JshException.readFail(logger, e);
            }
            if (mlist == null || mlist.size() < 1) {
                //商品名称不存在
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_EXISTS_CODE,
                        ExceptionConstants.MATERIAL_NOT_EXISTS_MSG);
            }
            if (mlist.size() > 1) {
                //商品信息不唯一
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_ONLY_CODE,
                        ExceptionConstants.MATERIAL_NOT_ONLY_MSG);

            }
            //获得唯一商品
            if (BusinessConstants.ENABLE_SERIAL_NUMBER_NOT_ENABLED.equals(mlist.get(0).getEnableSerialNumber())) {
                //商品未开启序列号
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_ENABLE_SERIAL_NUMBER_CODE,
                        ExceptionConstants.MATERIAL_NOT_ENABLE_SERIAL_NUMBER_MSG);
            }
            return mlist.get(0).getId();
        }
        return null;
    }
    /**
     * create by: cjl
     * description:
     *  根据商品名称判断给商品添加序列号是否可行
     *  1、根据商品名称必须查询到唯一的商品
     *  2、该商品必须已经启用序列号
     *  3、该商品已绑定序列号数量小于商品现有库存
     *  2019-02-01
     *  用商品的库存去限制序列号的添加有点不合乎道理，去掉此限制
     * create time: 2019/1/23 17:04
     * @Param: materialName
     * @return Long 满足使用条件的商品的id
     */
    public Long getSerialNumberMaterialIdByBarCode(String materialCode)throws Exception{
        if(StringUtil.isNotEmpty(materialCode)){
            //计算商品库存和目前占用的可用序列号数量关系
            //库存=入库-出库
            //入库数量
            Long materialId = 0L;
            List<MaterialVo4Unit> list = materialService.getMaterialByBarCode(materialCode);
            if(list!=null && list.size()>0) {
                materialId = list.get(0).getId();
            }
            return materialId;
        }
        return null;
    }

    /**
     * create by: cjl
     * description:
     * 出库时判断序列号库存是否足够，
     * 同时将对应的序列号绑定单据
     * create time: 2019/1/24 16:24
     * @Param: List<DepotItem>
     * @return void
     */
    public void checkAndUpdateSerialNumber(DepotItem depotItem, String outBillNo,User userInfo, String snList) throws Exception{
        if(depotItem!=null){
            sellSerialNumber(depotItem.getMaterialId(), outBillNo, snList,userInfo);
        }
    }
    /**
     *
     *
     * */
    /**
     * create by: cjl
     * description:
     * 卖出序列号
     * create time: 2019/1/25 9:17
     * @Param: materialId
     * @Param: depotheadId
     * @Param: isSell 卖出'1'
     * @Param: Count 卖出或者赎回的数量
     * @return com.jsh.erp.datasource.entities.SerialNumberEx
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int sellSerialNumber(Long materialId, String outBillNo, String snList, User user) throws Exception{
        int result=0;
        try{
            //将中文的逗号批量替换为英文逗号
            snList = snList.replaceAll("，",",");
            String [] snArray=snList.split(",");
            result = serialNumberMapperEx.sellSerialNumber(materialId, outBillNo, snArray, new Date(),user==null?null:user.getId());
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    /**
     * create by: cjl
     * description:
     * 赎回序列号
     * create time: 2019/1/25 9:17
     * @Param: materialId
     * @Param: depotheadId
     * @Param: isSell 赎回'0'
     * @Param: Count 卖出或者赎回的数量
     * @return com.jsh.erp.datasource.entities.SerialNumberEx
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int cancelSerialNumber(Long materialId, String outBillNo,int count,User user) throws Exception{
        int result=0;
        try{
            result = serialNumberMapperEx.cancelSerialNumber(materialId,outBillNo,count,new Date(),user==null?null:user.getId());
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    /**
     * create by: cjl
     * description:
     *批量添加序列号，最多500个
     * create time: 2019/1/29 15:11
     * @Param: materialName
     * @Param: serialNumberPrefix
     * @Param: batAddTotal
     * @Param: remark
     * @return java.lang.Object
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batAddSerialNumber(String materialCode, String serialNumberPrefix, Integer batAddTotal, String remark)throws Exception {
        int result=0;
        try {
            if (StringUtil.isNotEmpty(materialCode)) {
                //查询商品id
                Long materialId = getSerialNumberMaterialIdByBarCode(materialCode);
                List<SerialNumberEx> list = null;
                //当前用户
                User userInfo = userService.getCurrentUser();
                Long userId = userInfo == null ? null : userInfo.getId();
                Date date = null;
                Long million = null;
                synchronized (this) {
                    date = new Date();
                    million = date.getTime();
                }
                int insertNum = 0;
                StringBuffer prefixBuf = new StringBuffer(serialNumberPrefix).append(million);
                list = new ArrayList<SerialNumberEx>();
                int forNum = BusinessConstants.BATCH_INSERT_MAX_NUMBER >= batAddTotal ? batAddTotal : BusinessConstants.BATCH_INSERT_MAX_NUMBER;
                for (int i = 0; i < forNum; i++) {
                    insertNum++;
                    SerialNumberEx each = new SerialNumberEx();
                    each.setMaterialId(materialId);
                    each.setCreator(userId);
                    each.setCreateTime(date);
                    each.setUpdater(userId);
                    each.setUpdateTime(date);
                    each.setRemark(remark);
                    each.setSerialNumber(new StringBuffer(prefixBuf.toString()).append(insertNum).toString());
                    list.add(each);
                }
                result = serialNumberMapperEx.batAddSerialNumber(list);
                logService.insertLog("序列号",
                        new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_BATCH_ADD).append(batAddTotal).append(BusinessConstants.LOG_DATA_UNIT).toString(),
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            }
        } catch (Exception e) {
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<SerialNumberEx> getEnableSerialNumberList(String number, String name, Long depotId, String barCode, Integer offset, Integer rows)throws Exception {
        List<SerialNumberEx> list =null;
        try{
            list = serialNumberMapperEx.getEnableSerialNumberList(StringUtil.toNull(number), StringUtil.toNull(name), depotId, barCode, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long getEnableSerialNumberCount(String number, String name, Long depotId, String barCode)throws Exception {
        Long count = 0L;
        try{
            count = serialNumberMapperEx.getEnableSerialNumberCount(StringUtil.toNull(number), StringUtil.toNull(name), depotId, barCode);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return count;
    }

    public void addSerialNumberByBill(String type, String subType, String inBillNo, Long materialId, Long depotId, String snList) throws Exception {
        //录入序列号的时候不能重复
        if ((BusinessConstants.SUB_TYPE_PURCHASE.equals(subType) ||
                BusinessConstants.SUB_TYPE_OTHER.equals(subType) ||
                BusinessConstants.SUB_TYPE_SALES_RETURN.equals(subType)||
                BusinessConstants.SUB_TYPE_RETAIL_RETURN.equals(subType)) &&
                BusinessConstants.DEPOTHEAD_TYPE_IN.equals(type)) {
            //将中文的逗号批量替换为英文逗号
            snList = snList.replaceAll("，", ",");
            List<String> snArr = StringUtil.strToStringList(snList);
            for (String sn : snArr) {
                List<SerialNumber> list = new ArrayList<>();
                SerialNumberExample example = new SerialNumberExample();
                example.createCriteria().andMaterialIdEqualTo(materialId).andSerialNumberEqualTo(sn.trim()).andIsSellEqualTo("0")
                        .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                list = serialNumberMapper.selectByExample(example);
                //判断如果不存在重复序列号就新增
                if (list == null || list.size() == 0) {
                    SerialNumber serialNumber = new SerialNumber();
                    serialNumber.setMaterialId(materialId);
                    serialNumber.setDepotId(depotId);
                    serialNumber.setSerialNumber(sn);
                    Date date = new Date();
                    serialNumber.setCreateTime(date);
                    serialNumber.setUpdateTime(date);
                    User userInfo = userService.getCurrentUser();
                    serialNumber.setCreator(userInfo == null ? null : userInfo.getId());
                    serialNumber.setUpdater(userInfo == null ? null : userInfo.getId());
                    serialNumber.setInBillNo(inBillNo);
                    serialNumberMapper.insertSelective(serialNumber);
                } else {
                    if(!inBillNo.equals(list.get(0).getInBillNo())) {
                        throw new BusinessRunTimeException(ExceptionConstants.SERIAL_NUMBERE_ALREADY_EXISTS_CODE,
                                String.format(ExceptionConstants.SERIAL_NUMBERE_ALREADY_EXISTS_MSG, sn));
                    }
                }
            }
        }
    }

    /**
     * 直接删除序列号
     * @param example
     */
    public void deleteByExample(SerialNumberExample example) {
        serialNumberMapper.deleteByExample(example);
    }
}
