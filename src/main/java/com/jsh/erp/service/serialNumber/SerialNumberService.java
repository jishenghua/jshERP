package com.jsh.erp.service.serialNumber;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.MaterialMapperEx;
import com.jsh.erp.datasource.mappers.SerialNumberMapper;
import com.jsh.erp.datasource.mappers.SerialNumberMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                           ExceptionConstants.MATERIAL_NOT_EXISTS__MSG);
               }else if(mlist.size()>1){
                   //商品信息不唯一
                   throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_ONLY_CODE,
                           ExceptionConstants.MATERIAL_NOT_ONLY__MSG);

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
            if(StringUtil.isNotEmpty(serialNumberEx.getMaterialName())){
                List<Material> mlist = materialMapperEx.findByMaterialName(serialNumberEx.getMaterialName());
                if(mlist==null||mlist.size()<1){
                    //商品名称不存在
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_EXISTS_CODE,
                            ExceptionConstants.MATERIAL_NOT_EXISTS__MSG);
                }else if(mlist.size()>1){
                    //商品信息不唯一
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_ONLY_CODE,
                            ExceptionConstants.MATERIAL_NOT_ONLY__MSG);

                }else{
                    serialNumberEx.setMaterialId(mlist.get(0).getId());
                }
            }
        }
        //删除标记,默认未删除
        serialNumberEx.setDeleteFlag(false);
        //已卖出，默认未否
        serialNumberEx.setIsSell(false);
        Date date=new Date();
        serialNumberEx.setCreateTime(date);
        serialNumberEx.setUpdateTime(date);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        User userInfo=(User)request.getSession().getAttribute("user");
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
        if(StringUtil.isNotEmpty(serialNumberEx.getMaterialName())){
            List<Material> mlist = materialMapperEx.findByMaterialName(serialNumberEx.getMaterialName());
            if(mlist==null||mlist.size()<1){
                //商品名称不存在
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_EXISTS_CODE,
                        ExceptionConstants.MATERIAL_NOT_EXISTS__MSG);
            }else if(mlist.size()>1){
                //商品信息不唯一
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NOT_ONLY_CODE,
                        ExceptionConstants.MATERIAL_NOT_ONLY__MSG);

            }else{
                serialNumberEx.setMaterialId(mlist.get(0).getId());
            }
        }
        Date date=new Date();
        serialNumberEx.setUpdateTime(date);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        User userInfo=(User)request.getSession().getAttribute("user");
        serialNumberEx.setUpdater(userInfo==null?null:userInfo.getId());
        int result = serialNumberMapperEx.updateSerialNumber(serialNumberEx);
        if(result==1){
            return serialNumberEx;
        }
        return null;
    }
}
