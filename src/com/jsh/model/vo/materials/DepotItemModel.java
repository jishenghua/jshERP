package com.jsh.model.vo.materials;

import java.io.InputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class DepotItemModel implements Serializable
{
    private DepotItemShowModel showModel = new DepotItemShowModel();

    /**======开始接受页面参数=================**/
	private Long HeaderId;
	private Long MaterialId;
	private Double OperNumber;
	private Double UnitPrice;
	private Double AllPrice;
	private String Remark = "";
	private String Img = "";
    
	private String Inserted = "";	//json插入记录
	private String Deleted = "";	//json删除记录
	private String Updated = "";	//json修改记录
	
	private String HeadIds = "";	//表头集合列表
	private String MaterialIds = "";	//材料列表
	private String MonthTime = "";  //月份
  	private String browserType = "";
  	/**
  	 * 文件名称
  	 */
  	private String fileName = "";
    /**
     * 分类ID
     */
    private Long depotItemID = 0l;
    
    /**
     * 分类IDs 批量操作使用
     */
    private String depotItemIDs = "";
    
    /**
     * 每页显示的个数
     */
    private int pageSize = 800;
    
    /**
     * 当前页码
     */
    private int pageNo = 1;
    
    /**
     * 用户IP，用户记录操作日志
     */
    private String clientIp = "";
    
    
    /**
     * 输入流，导出excel文件
     */
  	private InputStream excelStream;

	public DepotItemShowModel getShowModel() {
		return showModel;
	}

	public void setShowModel(DepotItemShowModel showModel) {
		this.showModel = showModel;
	}

	public Long getHeaderId() {
		return HeaderId;
	}

	public void setHeaderId(Long headerId) {
		HeaderId = headerId;
	}

	public Long getMaterialId() {
		return MaterialId;
	}

	public void setMaterialId(Long materialId) {
		MaterialId = materialId;
	}

	public Double getOperNumber() {
		return OperNumber;
	}

	public void setOperNumber(Double operNumber) {
		OperNumber = operNumber;
	}

	public Double getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		UnitPrice = unitPrice;
	}

	public Double getAllPrice() {
		return AllPrice;
	}

	public void setAllPrice(Double allPrice) {
		AllPrice = allPrice;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getImg() {
		return Img;
	}

	public void setImg(String img) {
		Img = img;
	}

	public Long getDepotItemID() {
		return depotItemID;
	}

	public void setDepotItemID(Long depotItemID) {
		this.depotItemID = depotItemID;
	}

	public String getDepotItemIDs() {
		return depotItemIDs;
	}

	public void setDepotItemIDs(String depotItemIDs) {
		this.depotItemIDs = depotItemIDs;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getInserted() {
		return Inserted;
	}

	public void setInserted(String inserted) {
		Inserted = inserted;
	}

	public String getDeleted() {
		return Deleted;
	}

	public void setDeleted(String deleted) {
		Deleted = deleted;
	}

	public String getUpdated() {
		return Updated;
	}

	public void setUpdated(String updated) {
		Updated = updated;
	}

	public String getHeadIds() {
		return HeadIds;
	}

	public void setHeadIds(String headIds) {
		HeadIds = headIds;
	}

	public String getMonthTime() {
		return MonthTime;
	}

	public void setMonthTime(String monthTime) {
		MonthTime = monthTime;
	}

	public String getMaterialIds() {
		return MaterialIds;
	}

	public void setMaterialIds(String materialIds) {
		MaterialIds = materialIds;
	}

	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
}
