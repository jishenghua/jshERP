package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.entities.SerialNumberEx;
import com.jsh.erp.datasource.vo.TreeNode;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.materialCategory.MaterialCategoryService;
import com.jsh.erp.utils.BaseResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ji—sheng—hua   jshERP
 */
@RestController
@RequestMapping(value = "/materialCategory")
@Api(tags = {"商品类别"})
public class MaterialCategoryController {
    private Logger logger = LoggerFactory.getLogger(MaterialCategoryController.class);

    @Resource
    private MaterialCategoryService materialCategoryService;

    /**
     * 获取全部商品类别
     * @param parentId
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getAllList")
    @ApiOperation(value = "获取全部商品类别")
    public BaseResponseInfo getAllList(@RequestParam("parentId") Long parentId, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<MaterialCategory> materialCategoryList = materialCategoryService.getAllList(parentId);
            res.code = 200;
            res.data = materialCategoryList;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
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
    @GetMapping(value = "/findById")
    @ApiOperation(value = "根据id来查询商品名称")
    public BaseResponseInfo findById(@RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<MaterialCategory> dataList = materialCategoryService.findById(id);
            JSONObject outer = new JSONObject();
            if (null != dataList) {
                for (MaterialCategory mc : dataList) {
                    outer.put("id", mc.getId());
                    outer.put("name", mc.getName());
                    outer.put("parentId", mc.getParentId());
                    List<MaterialCategory> dataParentList = materialCategoryService.findById(mc.getParentId());
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
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
    /**
     * 获取商品类别树数据
     * @Param:
     * @return com.alibaba.fastjson.JSONArray
     */
    @RequestMapping(value = "/getMaterialCategoryTree")
    @ApiOperation(value = "获取商品类别树数据")
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
}
