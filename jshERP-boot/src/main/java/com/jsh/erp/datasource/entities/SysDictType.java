package com.jsh.erp.datasource.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 字典类型表 sys_dict_type
 *
 * @author jishenghua
 */
@ApiModel(value = "SysDictType", description = "字典类型表 sys_dict_type")
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @ApiModelProperty("字典主键")
    private Long dictId;

    /** 字典名称 */
    @ApiModelProperty("字典名称")
    private String dictName;

    /** 字典类型 */
    @ApiModelProperty("字典类型")
    private String dictType;

    /** 状态（0正常 1停用） */
    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    private String deleteFlag;

    public Long getDictId()
    {
        return dictId;
    }

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    @NotBlank(message = "字典名称不能为空")
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
