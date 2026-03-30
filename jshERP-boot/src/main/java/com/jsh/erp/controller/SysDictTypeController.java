package com.jsh.erp.controller;

import com.jsh.erp.base.AjaxResult;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.SysDictType;
import com.jsh.erp.service.SysDictTypeService;
import com.jsh.erp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author jishenghua
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

    @Resource
    private SysDictTypeService dictTypeService;

    @Resource
    private UserService userService;

    @ApiOperation("获取字典分页列表")
    @GetMapping("/list")
    public TableDataInfo list(SysDictType dictType) {
        startPage();
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
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictType dict) throws Exception {
        if (!dictTypeService.checkDictTypeUnique(dict))
        {
            return error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(userService.getCurrentUser().getLoginName());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @ApiOperation("新增字典类型")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictType dict) throws Exception {
        if (!dictTypeService.checkDictTypeUnique(dict))
        {
            return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(userService.getCurrentUser().getLoginName());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @ApiOperation("删除字典类型")
    @DeleteMapping("/{dictIds}")
    public AjaxResult remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return success();
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
