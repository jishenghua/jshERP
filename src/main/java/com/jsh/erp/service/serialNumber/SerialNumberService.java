package com.jsh.erp.service.serialNumber;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.MaterialMapperEx;
import com.jsh.erp.datasource.mappers.SerialNumberMapper;
import com.jsh.erp.datasource.mappers.SerialNumberMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private Logger logger = LoggerFactory.getLogger(MaterialService.class);

    @Resource
    private SerialNumberMapper serialNumberMapper;
    @Resource
    private SerialNumberMapperEx serialNumberMapperEx;
    @Resource
    private MaterialMapperEx materialMapperEx;
    @Resource
    private DepotItemService depotItemService;
    @Resource
    private UserService userService;


    public SerialNumber getSerialNumber(long id) {
        return serialNumberMapper.selectByPrimaryKey(id);
    }

    public List<SerialNumber> getSerialNumber() {
        SerialNumberExample example = new SerialNumberExample();
        return serialNumberMapper.selectByExample(example);
    }

    public List<SerialNumberEx> select(String serialNumber, String materialName, Integer offset, Integer rows) {
        return serialNumberMapperEx.selectByConditionSerialNumber(serialNumber, materialName,offset, rows);

    }

    public int countSerialNumber(String serialNumber,String materialName) {
        return serialNumberMapperEx.countSerialNumber(serialNumber, materialName);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSerialNumber(String beanJson, HttpServletRequest request) {
        SerialNumber serialNumber = JSONObject.parseObject(beanJson, SerialNumber.class);
        return serialNumberMapper.insertSelective(serialNumber);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSerialNumber(String beanJson, Long id) {
        SerialNumber serialNumber = JSONObject.parseObject(beanJson, SerialNumber.class);
        serialNumber.setId(id);
        int res = serialNumberMapper.updateByPrimaryKeySelective(serialNumber);
        return res;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSerialNumber(Long id) {
        return serialNumberMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSerialNumber(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        SerialNumberExample example = new SerialNumberExample();
        example.createCriteria().andIdIn(idList);
        return serialNumberMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String serialNumber) {
        SerialNumberExample example = new SerialNumberExample();
        example.createCriteria().andIdNotEqualTo(id).andSerialNumberEqualTo(serialNumber);
        List<SerialNumber> list = serialNumberMapper.selectByExample(example);
        return list.size();
    }



    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetEnable(Boolean enabled, String materialIDs) {
        List<Long> ids = StringUtil.strToLongList(materialIDs);
        SerialNumber serialNumber = new SerialNumber();
        SerialNumberExample example = new SerialNumberExample();
        example.createCriteria().andIdIn(ids);
        return serialNumberMapper.updateByExampleSelective(serialNumber, example);
    }


    public List<SerialNumberEx> findById(Long id){
        return serialNumberMapperEx.findById(id);
    }




    public void checkIsExist(Long id, String materialName, String serialNumber) {
        /**
         * 商品名称不为空时，检查商品名称是否存在
         * */
            if(StringUtil.isNotEmpty(materialName)){
                List<Material> mlist = materialMapperEx.findByMaterialName(materialName);
               if(mlist==null||mlist.size()<1){
                   //商品名称不存在
                   throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_EXISTS_CODE,
                           ExceptionConstants.MATERIAL_NOT_EXISTS_MSG);
               }else if(mlist.size()>1){
                   //商品信息不唯一
                   throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_ONLY_CODE,
                           ExceptionConstants.MATERIAL_NOT_ONLY_MSG);

               }
            }
            /***
             * 判断序列号是否已存在
             * */
            List <SerialNumberEx> list = serialNumberMapperEx.findBySerialNumber(serialNumber);
            if(list!=null&&list.size()>0){
                if(list.size()>1){
                    //存在多个同名序列号
                    throw new BusinessRunTimeException(ExceptionConstants.SERIAL_NUMBERE_ALREADY_EXISTS_CODE,
                            ExceptionConstants.SERIAL_NUMBERE_ALREADY_EXISTS_MSG);
                }else{
                    //存在一个序列号
                    if(id==null){
                        //新增，存在要添加的序列号
                        throw new BusinessRunTimeException(ExceptionConstants.SERIAL_NUMBERE_ALREADY_EXISTS_CODE,
                                ExceptionConstants.SERIAL_NUMBERE_ALREADY_EXISTS_MSG);
                    }
                        if(id.equals(list.get(0).getId())){
                            //修改的是同一条数据
                        }else{
                            //存在一条不同的序列号信息
                            throw new BusinessRunTimeException(ExceptionConstants.SERIAL_NUMBERE_ALREADY_EXISTS_CODE,
                                    ExceptionConstants.SERIAL_NUMBERE_ALREADY_EXISTS_MSG);
                        }
                }

            }
    }

    /**
     * 新增序列号信息
     * */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public SerialNumberEx addSerialNumber(SerialNumberEx serialNumberEx) {
        if(serialNumberEx==null){
            return null;
        }
        /**处理商品id*/
        if(serialNumberEx.getMaterialId()==null){
            serialNumberEx.setMaterialId(getSerialNumberMaterialIdByMaterialName(serialNumberEx.getMaterialName()));
        }
        //删除标记,默认未删除
        serialNumberEx.setDeleteFlag(false);
        //已卖出，默认未否
        serialNumberEx.setIsSell(false);
        Date date=new Date();
        serialNumberEx.setCreateTime(date);
        serialNumberEx.setUpdateTime(date);
        User userInfo=userService.getCurrentUser();
        serialNumberEx.setCreator(userInfo==null?null:userInfo.getId());
        serialNumberEx.setUpdater(userInfo==null?null:userInfo.getId());
        int result=serialNumberMapperEx.addSerialNumber(serialNumberEx);
        if(result==1){
            return serialNumberEx;
        }
        return null;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public SerialNumberEx updateSerialNumber(SerialNumberEx serialNumberEx) {
        if(serialNumberEx==null){
            return null;
        }
        /**处理商品id*/
        serialNumberEx.setMaterialId(getSerialNumberMaterialIdByMaterialName(serialNumberEx.getMaterialName()));
        Date date=new Date();
        serialNumberEx.setUpdateTime(date);
        User userInfo=userService.getCurrentUser();
        serialNumberEx.setUpdater(userInfo==null?null:userInfo.getId());
        int result = serialNumberMapperEx.updateSerialNumber(serialNumberEx);
        if(result==1){
            return serialNumberEx;
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
     * create time: 2019/1/23 17:04
     * @Param: materialName
     * @return Long 满足使用条件的商品的id
     */
    public Long getSerialNumberMaterialIdByMaterialName(String materialName){
        if(StringUtil.isNotEmpty(materialName)){
            List<Material> mlist = materialMapperEx.findByMaterialName(materialName);
            if(mlist==null||mlist.size()<1){
                //商品名称不存在
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_EXISTS_CODE,
                        ExceptionConstants.MATERIAL_NOT_EXISTS_MSG);
            }
            if(mlist.size()>1){
                //商品信息不唯一
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_ONLY_CODE,
                        ExceptionConstants.MATERIAL_NOT_ONLY_MSG);

            }
            //获得唯一商品
            if(BusinessConstants.MATERIAL_NOT_ENABLE_SERIAL_NUMBER==mlist.get(0).getEnableSerialNumber()){
                //商品未开启序列号
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_ENABLE_SERIAL_NUMBER_CODE,
                        ExceptionConstants.MATERIAL_NOT_ENABLE_SERIAL_NUMBER_MSG);
            }
            //计算商品库存和目前占用的可用序列号数量关系
            //库存=入库-出库
            //入库数量
            Long materialId=mlist.get(0).getId();
            int inSum = depotItemService.findByTypeAndMaterialId(BusinessConstants.DEPOTHEAD_TYPE_STORAGE, materialId);
            //出库数量
            int outSum = depotItemService.findByTypeAndMaterialId(BusinessConstants.DEPOTHEAD_TYPE_OUT, materialId);
            //查询当前商品下有效的序列号
            int serialNumberSum = serialNumberMapperEx.findSerialNumberByMaterialId(materialId);
            if((inSum-outSum)<=serialNumberSum){
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_SERIAL_NUMBERE_NOT_MORE_THAN_STORAGE_CODE,
                        ExceptionConstants.MATERIAL_SERIAL_NUMBERE_NOT_MORE_THAN_STORAGE_MSG);
            }
            return materialId;
        }
        return null;
    }

}
