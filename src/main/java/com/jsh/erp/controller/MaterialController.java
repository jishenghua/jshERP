package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialVo4Unit;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
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
 * @author ji|sheng|hua 华夏ERP
 */
@RestController
@RequestMapping(value = "/material")
public class MaterialController {
    private Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Resource
    private MaterialService materialService;

    @GetMapping(value = "/checkIsExist")
    public String checkIsExist(@RequestParam("materialId") Long id, @RequestParam("name") String name,
                               @RequestParam("model") String model, @RequestParam("color") String color,
                               @RequestParam("standard") String standard, @RequestParam("mfrs") String mfrs,
                               @RequestParam("otherField1") String otherField1, @RequestParam("otherField2") String otherField2,
                               @RequestParam("otherField3") String otherField3, @RequestParam("unit") String unit,@RequestParam("unitId") Long unitId,
                               HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int exist = materialService.checkIsExist(id, name, model, color, standard, mfrs,
                otherField1, otherField2, otherField3, unit, unitId);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 批量设置状态-启用或者禁用
     * @param enabled
     * @param materialIDs
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetEnable")
    public String batchSetEnable(@RequestParam("enabled") Boolean enabled,
                                 @RequestParam("materialIDs") String materialIDs,
                                 HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int res = materialService.batchSetEnable(enabled, materialIDs);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 根据id来查询商品名称
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/findById")
    public BaseResponseInfo findById(@RequestParam("id") Long id, HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<MaterialVo4Unit> list = materialService.findById(id);
            res.code = 200;
            res.data = list;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 查找商品信息-下拉框
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/findBySelect")
    public JSONArray findBySelect(@RequestParam("mpList") String mpList, HttpServletRequest request) {
        JSONArray dataArray = new JSONArray();
        try {
            List<MaterialVo4Unit> dataList = materialService.findBySelect();
            String[] mpArr = mpList.split(",");
            //存放数据json数组
            if (null != dataList) {
                for (MaterialVo4Unit material : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", material.getId());
                    String ratio; //比例
                    if (material.getUnitid() == null || material.getUnitid().equals("")) {
                        ratio = "";
                    } else {
                        ratio = material.getUnitName();
                        ratio = ratio.substring(ratio.indexOf("("));
                    }
                    //品名/型号/扩展信息/包装
                    String MaterialName = material.getName() + ((material.getModel() == null || material.getModel().equals("")) ? "" : "(" + material.getModel() + ")");
                    for (int i = 0; i < mpArr.length; i++) {
                        if (mpArr[i].equals("颜色")) {
                            MaterialName = MaterialName + ((material.getColor() == null || material.getColor().equals("")) ? "" : "(" + material.getColor() + ")");
                        }
                        if (mpArr[i].equals("规格")) {
                            MaterialName = MaterialName + ((material.getStandard() == null || material.getStandard().equals("")) ? "" : "(" + material.getStandard() + ")");
                        }
                        if (mpArr[i].equals("制造商")) {
                            MaterialName = MaterialName + ((material.getMfrs() == null || material.getMfrs().equals("")) ? "" : "(" + material.getMfrs() + ")");
                        }
                        if (mpArr[i].equals("自定义1")) {
                            MaterialName = MaterialName + ((material.getOtherfield1() == null || material.getOtherfield1().equals("")) ? "" : "(" + material.getOtherfield1() + ")");
                        }
                        if (mpArr[i].equals("自定义2")) {
                            MaterialName = MaterialName + ((material.getOtherfield2() == null || material.getOtherfield2().equals("")) ? "" : "(" + material.getOtherfield2() + ")");
                        }
                        if (mpArr[i].equals("自定义3")) {
                            MaterialName = MaterialName + ((material.getOtherfield3() == null || material.getOtherfield3().equals("")) ? "" : "(" + material.getOtherfield3() + ")");
                        }
                    }
                    MaterialName = MaterialName + ((material.getUnit() == null || material.getUnit().equals("")) ? "" : "(" + material.getUnit() + ")") + ratio;
                    item.put("MaterialName", MaterialName);
                    dataArray.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataArray;
    }


    /**
     * 查找商品信息-统计排序
     * @param request
     * @return
     */
    @GetMapping(value = "/findByOrder")
    public BaseResponseInfo findByOrder(HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Material> dataList = materialService.findByOrder();
            String mId = "";
            if (null != dataList) {
                for (Material material : dataList) {
                    mId = mId + material.getId() + ",";
                }
            }
            if (mId != "") {
                mId = mId.substring(0, mId.lastIndexOf(","));
            }
            map.put("mIds", mId);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
