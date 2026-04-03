package com.jsh.erp.controller;

import com.jsh.erp.base.AjaxResult;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.SysDictData;
import com.jsh.erp.service.SysDictDataService;
import com.jsh.erp.service.SysDictTypeService;
import com.jsh.erp.service.UserService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnStr;

/**
 * 数据字典信息
 *
 * @author jishenghua
 */
@RestController
@RequestMapping("/dict/data")
public class SysDictDataController extends BaseController {

    @Resource
    private SysDictDataService dictDataService;

    @Resource
    private SysDictTypeService dictTypeService;

    @Resource
    private UserService userService;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    public TableDataInfo list(@RequestParam(value = Constants.SEARCH, required = false) String search) {
        SysDictData dictData = new SysDictData();
        dictData.setDictType(StringUtil.getInfo(search, "dictType"));
        dictData.setDictLabel(StringUtil.getInfo(search, "dictLabel"));
        dictData.setStatus(StringUtil.getInfo(search, "status"));
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    /**
     * 查询字典数据详细
     */
    @GetMapping(value = "/{dictCode}")
    @ApiOperation(value = "查询字典数据详细")
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    @ApiOperation(value = "根据字典类型查询字典数据信息")
    public AjaxResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtil.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        return success(data);
    }

    /**
     * 新增字典数据
     */
    @ApiOperation(value = "新增字典数据")
    @PostMapping(value = "/add")
    public String add(@Validated @RequestBody SysDictData dict) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        dict.setCreateBy(userService.getCurrentUser().getLoginName());
        return returnStr(objectMap, dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典数据
     */
    @ApiOperation(value = "修改保存字典数据")
    @PutMapping(value = "/update")
    public String edit(@Validated @RequestBody SysDictData dict) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        dict.setUpdateBy(userService.getCurrentUser().getLoginName());
        return returnStr(objectMap, dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典数据
     */
    @DeleteMapping("/{dictCodes}")
    @ApiOperation(value = "删除字典数据")
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除")
    public String deleteResource(@RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = dictDataService.deleteDictData(id, request);
        return returnStr(objectMap, delete);
    }

    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    public String batchDeleteResource(@RequestParam("ids") String ids, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = dictDataService.batchDeleteDictData(ids, request);
        return returnStr(objectMap, delete);
    }
}
