package com.jsh.erp.service.orgaUserRel;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.service.organization.OrganizationResource;
import com.jsh.erp.service.organization.OrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/3/11 18:10
 */
@Service(value = "orgaUserRel_component")
@OrgaUserRelResource
public class OrgaUserRelComponent implements ICommonQuery {
    @Resource
    private OrgaUserRelService orgaUserRelService;
    @Override
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> parameterMap) {
        return getOrgaUserRelList(parameterMap);
    }
    private List<?> getOrgaUserRelList(Map<String, String> map) {
        return null;
    }
    @Override
    public Long counts(Map<String, String> parameterMap) {
        return null;
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return orgaUserRelService.insertOrgaUserRel(beanJson,request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return orgaUserRelService.updateOrgaUserRel(beanJson,id);
    }

    @Override
    public int delete(Long id) {
        return orgaUserRelService.deleteOrgaUserRel(id);
    }

    @Override
    public int batchDelete(String ids) {
        return orgaUserRelService.batchDeleteOrgaUserRel(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return 0;
    }
}
