package com.jsh.erp.datasource.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典数据表 sys_dict_data
 *
 * @author jishenghua
 */
@ApiModel(value = "SysDictData", description = "字典数据表 sys_dict_data")
public class SysDictData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    @ApiModelProperty("字典编码")
    private Long dictCode;

    /** 字典排序 */
    @ApiModelProperty("字典排序")
    private Long dictSort;

    /** 字典标签 */
    @ApiModelProperty("字典标签")
    private String dictLabel;

    /** 字典键值 */
    @ApiModelProperty("字典键值")
    private String dictValue;

    /** 字典类型 */
    @ApiModelProperty("字典类型")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @ApiModelProperty("样式属性（其他样式扩展）")
    private String cssClass;

    /** 表格字典样式 */
    @ApiModelProperty("表格字典样式")
    private String listClass;

    /** 是否默认（Y是 N否） */
    @ApiModelProperty("是否默认（Y是 N否）")
    private String isDefault;

    /** 状态（0正常 1停用） */
    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    public Long getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(Long dictCode)
    {
        this.dictCode = dictCode;
    }

    public Long getDictSort()
    {
        return dictSort;
    }

    public void setDictSort(Long dictSort)
    {
        this.dictSort = dictSort;
    }

    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
    public String getDictLabel()
    {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
    public String getDictValue()
    {
        return dictValue;
    }

    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }

    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
    public String getCssClass()
    {
        return cssClass;
    }

    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    public String getListClass()
    {
        return listClass;
    }

    public void setListClass(String listClass)
    {
        this.listClass = listClass;
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
