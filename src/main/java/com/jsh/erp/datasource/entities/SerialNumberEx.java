package com.jsh.erp.datasource.entities;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/1/21 17:32
 */
public class SerialNumberEx extends SerialNumber{
    /**
     * 商品名称
     * */
    private String materialName;
    /**
     * 创建者名称
     * */
    private String creatorName;
    /**
     * 更新者名称
     * */
    private String updaterName;
    /**单据编号*/
    private String depotHeadNumber;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public String getDepotHeadNumber() {
        return depotHeadNumber;
    }

    public void setDepotHeadNumber(String depotHeadNumber) {
        this.depotHeadNumber = depotHeadNumber;
    }
}
