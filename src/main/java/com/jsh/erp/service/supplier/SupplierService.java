package com.jsh.erp.service.supplier;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.entities.SupplierExample;
import com.jsh.erp.datasource.mappers.SupplierMapper;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierService {
    private Logger logger = LoggerFactory.getLogger(SupplierService.class);

    @Resource
    private SupplierMapper supplierMapper;

    public Supplier getSupplier(long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    public List<Supplier> getSupplier() {
        SupplierExample example = new SupplierExample();
        return supplierMapper.selectByExample(example);
    }

    public List<Supplier> select(String supplier, String type, String phonenum, String telephone, String description, int offset, int rows) {
        return supplierMapper.selectByConditionSupplier(supplier, type, phonenum, telephone, description, offset, rows);
    }

    public int countSupplier(String supplier, String type, String phonenum, String telephone, String description) {
        return supplierMapper.countsBySupplier(supplier, type, phonenum, telephone, description);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSupplier(String beanJson, HttpServletRequest request) {
        Supplier supplier = JSONObject.parseObject(beanJson, Supplier.class);
        return supplierMapper.insertSelective(supplier);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSupplier(String beanJson, Long id) {
        Supplier supplier = JSONObject.parseObject(beanJson, Supplier.class);
        supplier.setId(id);
        return supplierMapper.updateByPrimaryKeySelective(supplier);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSupplier(Long id) {
        return supplierMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSupplier(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        SupplierExample example = new SupplierExample();
        example.createCriteria().andIdIn(idList);
        return supplierMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andIdNotEqualTo(id).andSupplierEqualTo(name);
        List<Supplier> list = supplierMapper.selectByExample(example);
        return list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAdvanceIn(Long supplierId, Double advanceIn){
        Supplier supplier = supplierMapper.selectByPrimaryKey(supplierId);
        supplier.setAdvancein(supplier.getAdvancein() + advanceIn);  //增加预收款的金额，可能增加的是负值
        return supplierMapper.updateByPrimaryKeySelective(supplier);
    }

    public List<Supplier> findBySelectCus() {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andTypeLike("客户").andEnabledEqualTo(true);
        example.setOrderByClause("id desc");
        return supplierMapper.selectByExample(example);
    }

    public List<Supplier> findBySelectSup() {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andTypeLike("供应商").andEnabledEqualTo(true);
        example.setOrderByClause("id desc");
        return supplierMapper.selectByExample(example);
    }

    public List<Supplier> findBySelectRetail() {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andTypeLike("会员").andEnabledEqualTo(true);
        example.setOrderByClause("id desc");
        return supplierMapper.selectByExample(example);
    }

    public List<Supplier> findById(Long supplierId) {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andIdEqualTo(supplierId);
        example.setOrderByClause("id desc");
        return supplierMapper.selectByExample(example);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetEnable(Boolean enabled, String supplierIDs) {
        List<Long> ids = StringUtil.strToLongList(supplierIDs);
        Supplier supplier = new Supplier();
        supplier.setEnabled(enabled);
        SupplierExample example = new SupplierExample();
        example.createCriteria().andIdIn(ids);
        return supplierMapper.updateByExampleSelective(supplier, example);
    }

    public List<Supplier> findUserCustomer(){
        SupplierExample example = new SupplierExample();
        example.createCriteria().andTypeEqualTo("客户");
        example.setOrderByClause("id desc");
        List<Supplier> list = supplierMapper.selectByExample(example);
        return list;
    }

    public List<Supplier> findByAll(String supplier, String type, String phonenum, String telephone, String description) {
        return supplierMapper.findByAll(supplier, type, phonenum, telephone, description);
    }

    public BaseResponseInfo importExcel(List<Supplier> mList) throws Exception {
        BaseResponseInfo info = new BaseResponseInfo();
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            for(Supplier s: mList) {
                supplierMapper.insertSelective(s);
            }
            info.code = 200;
            data.put("message", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            info.code = 500;
            data.put("message", e.getMessage());
        }
        info.data = data;
        return info;
    }
}
