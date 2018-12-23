package com.jsh.erp.service.supplier;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.entities.SupplierExample;
import com.jsh.erp.datasource.mappers.SupplierMapper;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    public int insertSupplier(String beanJson, HttpServletRequest request) {
        Supplier supplier = JSONObject.parseObject(beanJson, Supplier.class);
        return supplierMapper.insertSelective(supplier);
    }

    public int updateSupplier(String beanJson, Long id) {
        Supplier supplier = JSONObject.parseObject(beanJson, Supplier.class);
        supplier.setId(id);
        return supplierMapper.updateByPrimaryKeySelective(supplier);
    }

    public int deleteSupplier(Long id) {
        return supplierMapper.deleteByPrimaryKey(id);
    }

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
}
