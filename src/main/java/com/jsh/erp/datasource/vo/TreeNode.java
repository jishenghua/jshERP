package com.jsh.erp.datasource.vo;

import java.util.List;

/**
 * Description
 *  树形结构基本元素
 * @Author: cjl
 * @Date: 2019/2/19 11:27
 */
public class TreeNode {
    /**
     * id主键
     * */
     private Long id;
     /**
      * text显示的文本
      * */
    private String text;
    /**
     *state节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
     * */
    private String state="open";
    /**
     *iconCls 节点图标id
     * */
    private String iconCls;
    /**
     * checked 是否被选中
     * */
    private boolean checked;
    /**
     *attributes 自定义属性
     * */
    private String attributes;
    /**
     * children 子节点
     * */
    private List<TreeNode> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
