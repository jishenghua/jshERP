package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.MaterialProperty;
import com.jsh.model.vo.materials.MaterialPropertyModel;
import com.jsh.service.materials.MaterialPropertyIService;
import com.jsh.util.PageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 商品属性
 * @author ji s h e n g hua  qq:75 27 18 920
 */
@SuppressWarnings("serial")
public class MaterialPropertyAction extends BaseAction<MaterialPropertyModel> {
    private MaterialPropertyIService materialPropertyService;
    private MaterialPropertyModel model = new MaterialPropertyModel();

    /**
     * 更新商品属性
     *
     * @return
     */
    public void update() {
        Boolean flag = false;
        try {
            MaterialProperty materialProperty = materialPropertyService.get(model.getId());
            materialProperty.setNativeName(model.getNativeName());
            materialProperty.setEnabled(model.getEnabled());
            materialProperty.setSort(model.getSort());
            materialProperty.setAnotherName(model.getAnotherName());
            materialPropertyService.update(materialProperty);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改商品属性ID为 ： " + model.getId() + "失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改商品属性回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新商品属性", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新商品属性ID为  " + model.getId() + " " + tipMsg + "！", "更新商品属性" + tipMsg));
    }

    /**
     * 查找商品属性
     *
     * @return
     */
    public void findBy() {
        try {
            PageUtil<MaterialProperty> pageUtil = new PageUtil<MaterialProperty>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition());
            materialPropertyService.find(pageUtil);
            List<MaterialProperty> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (MaterialProperty materialProperty : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", materialProperty.getId());
                    item.put("nativeName", materialProperty.getNativeName());
                    item.put("enabled", materialProperty.getEnabled());
                    item.put("sort", materialProperty.getSort());
                    item.put("anotherName", materialProperty.getAnotherName());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找商品属性异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询商品属性结果异常", e);
        }
    }

    /**
     * 拼接搜索条件
     *
     * @return
     */
    private Map<String, Object> getCondition() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("nativeName_s_like", model.getNativeName());
        condition.put("sort_s_order", "asc");
        return condition;
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public MaterialPropertyModel getModel() {
        return model;
    }

    public void setMaterialPropertyService(MaterialPropertyIService materialPropertyService) {
        this.materialPropertyService = materialPropertyService;
    }
}
