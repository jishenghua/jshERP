package com.jsh.erp.service.supplier;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.service.depot.DepotResource;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "supplier_component")
@SupplierResource
public class SupplierComponent implements ICommonQuery {

    @Resource
    private SupplierService supplierService;

    @Override
    public Object selectOne(String condition)throws Exception {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getSupplierList(map);
    }

    private List<?> getSupplierList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String supplier = StringUtil.getInfo(search, "supplier");
        String type = StringUtil.getInfo(search, "type");
        String phonenum = StringUtil.getInfo(search, "phonenum");
        String telephone = StringUtil.getInfo(search, "telephone");
        String description = StringUtil.getInfo(search, "description");
        String order = QueryUtils.order(map);
        return supplierService.select(supplier, type, phonenum, telephone, description, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String supplier = StringUtil.getInfo(search, "supplier");
        String type = StringUtil.getInfo(search, "type");
        String phonenum = StringUtil.getInfo(search, "phonenum");
        String telephone = StringUtil.getInfo(search, "telephone");
        String description = StringUtil.getInfo(search, "description");
        return supplierService.countSupplier(supplier, type, phonenum, telephone, description);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request)throws Exception {
        return supplierService.insertSupplier(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id)throws Exception {
        return supplierService.updateSupplier(beanJson, id);
    }

    @Override
    public int delete(Long id)throws Exception {
        return supplierService.deleteSupplier(id);
    }

    @Override
    public int batchDelete(String ids)throws Exception {
        return supplierService.batchDeleteSupplier(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return supplierService.checkIsNameExist(id, name);
    }

}
