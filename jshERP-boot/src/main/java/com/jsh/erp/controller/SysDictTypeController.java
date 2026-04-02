package com.jsh.erp.controller;

import com.jsh.erp.base.AjaxResult;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.SysDictType;
import com.jsh.erp.service.SysDictTypeService;
import com.jsh.erp.service.UserService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;
import static com.jsh.erp.utils.ResponseJsonUtil.returnStr;

/**
 * 数据字典信息
 *
 * @author jishenghua
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/dict/type")
public class SysDictTypeController extends BaseController {

    @Resource
    private SysDictTypeService dictTypeService;

    @Resource
    private UserService userService;

    @ApiOperation("获取字典分页列表")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam(value = Constants.SEARCH, required = false) String search) {
        SysDictType dictType = new SysDictType();
        dictType.setDictName(StringUtil.getInfo(search, "dictName"));
        dictType.setDictType(StringUtil.getInfo(search, "dictType"));
        dictType.setStatus(StringUtil.getInfo(search, "status"));
        Map<String, Object> params = new HashMap<>();
        params.put("beginTime", StringUtil.getInfo(search, "beginTime"));
        params.put("endTime", StringUtil.getInfo(search, "endTime"));
        dictType.setParams(params);
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    /**
     * 查询字典类型详细
     */
    @ApiOperation("查询字典类型详细")
    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable Long dictId) {
        return success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @ApiOperation("新增字典类型")
    @PostMapping(value = "/add")
    public String add(@Validated @RequestBody SysDictType dict) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return returnJson(objectMap, "新增字典'" + dict.getDictName() + "'失败，字典类型已存在", ErpInfo.ERROR.code);
        }
        dict.setCreateBy(userService.getCurrentUser().getLoginName());
        return returnStr(objectMap, dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @ApiOperation("修改字典类型")
    @PutMapping(value = "/update")
    public String edit(@Validated @RequestBody SysDictType dict) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return returnJson(objectMap, "修改字典'" + dict.getDictName() + "'失败，字典类型已存在", ErpInfo.ERROR.code);
        }
        dict.setUpdateBy(userService.getCurrentUser().getLoginName());
        return returnStr(objectMap, dictTypeService.updateDictType(dict));
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除")
    public String deleteResource(@RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = dictTypeService.deleteDictType(id, request);
        return returnStr(objectMap, delete);
    }

    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    public String batchDeleteResource(@RequestParam("ids") String ids, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = dictTypeService.batchDeleteDictType(ids, request);
        return returnStr(objectMap, delete);
    }

    /**
     * 刷新字典缓存
     */
    @ApiOperation("刷新字典缓存")
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache() {
        dictTypeService.resetDictCache();
        return success();
    }

    /**
     * 获取字典选择框列表
     */
    @ApiOperation("获取字典选择框列表")
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return success(dictTypes);
    }
}
