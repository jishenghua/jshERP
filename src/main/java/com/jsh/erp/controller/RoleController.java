package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Role;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.role.RoleService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ji sheng hua 华夏ERP
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private RoleService roleService;

    @Resource
    private UserBusinessService userBusinessService;

    /**
     * 角色对应应用显示
     * @param request
     * @return
     */
    @PostMapping(value = "/findUserRole")
    public JSONArray findUserRole(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                  HttpServletRequest request)throws Exception {
        JSONArray arr = new JSONArray();
        try {
            List<Role> dataList = roleService.findUserRole();
            if (null != dataList) {
                for (Role role : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", role.getId());
                    item.put("text", role.getName());
                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + role.getId().toString() + "]");
                    } catch (Exception e) {
                        logger.error(">>>>>>>>>>>>>>>>>设置用户对应的角色：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                    }
                    if (flag == true) {
                        item.put("checked", true);
                    }
                    arr.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    @PostMapping(value = "/list")
    public List<Role> list(HttpServletRequest request)throws Exception {
        return roleService.getRole();
    }

    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  逻辑删除角色信息
     * create time: 2019/3/28 15:39
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteRoleByIds")
    public Object batchDeleteRoleByIds(@RequestParam("ids") String ids) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        int i= roleService.batchDeleteRoleByIds(ids);
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.ROLE_DELETE_FAILED_CODE,ExceptionConstants.ROLE_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.ROLE_DELETE_FAILED_CODE,
                    ExceptionConstants.ROLE_DELETE_FAILED_MSG);
        }
        return result;
    }

}
