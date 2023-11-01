package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Person;
import com.jsh.erp.service.person.PersonService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji|sheng|hua 华夏erp
 */
@RestController
@RequestMapping(value = "/person")
@Api(tags = {"经手人管理"})
public class PersonController {
    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Resource
    private PersonService personService;

    /**
     * 全部数据列表
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getAllList")
    @ApiOperation(value = "全部数据列表")
    public BaseResponseInfo getAllList(HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Person> personList = personService.getPerson();
            map.put("personList", personList);
            res.code = 200;
            res.data = personList;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据Id获取经手人信息
     * @param personIds
     * @param request
     * @return
     */
    @GetMapping(value = "/getPersonByIds")
    @ApiOperation(value = "根据Id获取经手人信息")
    public BaseResponseInfo getPersonByIds(@RequestParam("personIds") String personIds,
                                           HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<Long,String> personMap = personService.getPersonMap();
            String names = personService.getPersonByMapAndIds(personMap, personIds);
            map.put("names", names);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据类型获取经手人信息
     * @param type
     * @param request
     * @return
     */
    @GetMapping(value = "/getPersonByType")
    @ApiOperation(value = "根据类型获取经手人信息")
    public BaseResponseInfo getPersonByType(@RequestParam("type") String type,
                                            HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Person> personList = personService.getPersonByType(type);
            map.put("personList", personList);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据类型获取经手人信息 1-销售员，2-仓管员，3-财务员
     * @param typeNum
     * @param request
     * @return
     */
    @GetMapping(value = "/getPersonByNumType")
    @ApiOperation(value = "根据类型获取经手人信息1-销售员，2-仓管员，3-财务员")
    public JSONArray getPersonByNumType(@RequestParam("type") String typeNum,
                                        HttpServletRequest request)throws Exception {
        JSONArray dataArray = new JSONArray();
        try {
            String type = "";
            if (typeNum.equals("1")) {
                type = "销售员";
            } else if (typeNum.equals("2")) {
                type = "仓管员";
            } else if (typeNum.equals("3")) {
                type = "财务员";
            }
            List<Person> personList = personService.getPersonByType(type);
            if (null != personList) {
                for (Person person : personList) {
                    JSONObject item = new JSONObject();
                    item.put("value", person.getId().toString());
                    item.put("text", person.getName());
                    dataArray.add(item);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return dataArray;
    }

    /**
     * 批量设置状态-启用或者禁用
     * @param jsonObject
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    @ApiOperation(value = "批量设置状态")
    public String batchSetStatus(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request)throws Exception {
        Boolean status = jsonObject.getBoolean("status");
        String ids = jsonObject.getString("ids");
        Map<String, Object> objectMap = new HashMap<>();
        int res = personService.batchSetStatus(status, ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }
}
