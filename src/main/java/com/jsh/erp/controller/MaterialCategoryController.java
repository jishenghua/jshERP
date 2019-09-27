package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.entities.SerialNumberEx;
import com.jsh.erp.datasource.vo.TreeNode;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.materialCategory.MaterialCategoryService;
import com.jsh.erp.utils.BaseResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ji—sheng—hua   华夏ERP
 */
@RestController
@RequestMapping(value = "/materialCategory")
public class MaterialCategoryController {
    private Logger logger = LoggerFactory.getLogger(MaterialCategoryController.class);

    @Resource
    private MaterialCategoryService materialCategoryService;

    @GetMapping(value = "/getAllList")
    public BaseResponseInfo getAllList(@RequestParam("parentId") Long parentId, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<MaterialCategory> materialCategoryList = materialCategoryService.getAllList(parentId);
            res.code = 200;
            res.data = materialCategoryList;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据id来查询商品名称
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/findById")
    public BaseResponseInfo findById(@RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<MaterialCategory> dataList = materialCategoryService.findById(id);
            JSONObject outer = new JSONObject();
            if (null != dataList) {
                for (MaterialCategory mc : dataList) {
                    outer.put("id", mc.getId());
                    outer.put("name", mc.getName());
                    outer.put("parentId", mc.getParentid());
                    List<MaterialCategory> dataParentList = materialCategoryService.findById(mc.getParentid());
                    if(dataParentList!=null&&dataParentList.size()>0){
                        outer.put("parentName", dataParentList.get(0).getName());
                    }
                    outer.put("sort", mc.getSort());
                    outer.put("serialNo", mc.getSerialNo());
                    outer.put("remark", mc.getRemark());
                }
            }
            res.code = 200;
            res.data = outer;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
    /**
     * create by: cjl
     * description:
     * 获取商品类别树数据
     * create time: 2019/2/19 11:49
     * @Param:
     * @return com.alibaba.fastjson.JSONArray
     */
    @RequestMapping(value = "/getMaterialCategoryTree")
    public JSONArray getMaterialCategoryTree(@RequestParam("id") Long id) throws Exception{
       JSONArray arr=new JSONArray();
       List<TreeNode> materialCategoryTree = materialCategoryService.getMaterialCategoryTree(id);
       if(materialCategoryTree!=null&&materialCategoryTree.size()>0){
           for(TreeNode node:materialCategoryTree){
               String str=JSON.toJSONString(node);
               JSONObject obj=JSON.parseObject(str);
               arr.add(obj) ;
           }
       }
        return arr;
    }
    /**
     * create by: cjl
     * description:
     *  新增商品类别数据
     * create time: 2019/2/19 17:17
     * @Param: beanJson
     * @return java.lang.Object
     */
    @RequestMapping(value = "/addMaterialCategory")
    public Object addMaterialCategory(@RequestParam("info") String beanJson) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        MaterialCategory mc= JSON.parseObject(beanJson, MaterialCategory.class);
        int i= materialCategoryService.addMaterialCategory(mc);
        if(i<1){
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_CATEGORY_ADD_FAILED_CODE,
                    ExceptionConstants.MATERIAL_CATEGORY_ADD_FAILED_MSG);
        }
        return result;
    }
    /**
     * create by: cjl
     * description:
     *  修改商品类别数据
     * create time: 2019/2/20 9:30
     * @Param: beanJson
     * @return java.lang.Object
     */
    @RequestMapping(value = "/editMaterialCategory")
    public Object editMaterialCategory(@RequestParam("info") String beanJson) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        MaterialCategory mc= JSON.parseObject(beanJson, MaterialCategory.class);
        int i= materialCategoryService.editMaterialCategory(mc);
        if(i<1){
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_CATEGORY_EDIT_FAILED_CODE,
                    ExceptionConstants.MATERIAL_CATEGORY_EDIT_FAILED_MSG);
        }
        return result;
    }
    /**
     * create by: cjl
     * description:
     *  批量删除商品类别信息
     * create time: 2019/2/19 17:26
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteMaterialCategory")
    public Object batchDeleteMaterialCategory(@RequestParam("ids") String ids,@RequestParam(value="deleteType",
            required =false,defaultValue= BusinessConstants.DELETE_TYPE_NORMAL)String deleteType) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        int i=0;
        if(BusinessConstants.DELETE_TYPE_NORMAL.equals(deleteType)){
            i= materialCategoryService.batchDeleteMaterialCategoryByIdsNormal(ids);
        }else if(BusinessConstants.DELETE_TYPE_FORCE.equals(deleteType)){
            i= materialCategoryService.batchDeleteMaterialCategoryByIds(ids);
        }else{
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}],deleteType[{}]",
                    ExceptionConstants.DELETE_REFUSED_CODE,ExceptionConstants.DELETE_REFUSED_MSG,ids,deleteType);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_REFUSED_CODE,
                    ExceptionConstants.DELETE_REFUSED_MSG);
        }
        if(i<1){
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_CATEGORY_DELETE_FAILED_CODE,
                    ExceptionConstants.MATERIAL_CATEGORY_DELETE_FAILED_MSG);
        }
        return result;
    }
}
