package com.jsh.service.asset;

import java.io.File;
import java.io.InputStream;

import com.jsh.base.BaseIService;
import com.jsh.exception.AmsException;
import com.jsh.model.po.Asset;
import com.jsh.util.common.PageUtil;

public interface AssetIService extends BaseIService<Asset>
{
	/**
	 * 导出信息
	 * @return
	 */
	InputStream exmportExcel(String isAllPage,PageUtil<Asset> pageUtil)throws AmsException;
	
	/**
	 * 导入资产excel文件--表格格式 同 媒资列表 || 资产名称-资产类型-单价-用户-购买时间-状态-位置-资产编号-序列号-有效日期-保修日期-供应商-标签-描述
	 * 业务规则：导入时，检查资产名称是否存在，如存在就不考虑表格中资产类型。如资产名不存在，就新建资产名，类型用表格中的，但类型必须是系统中存在的，不存在的不能导入。
	 * 资产名称，用户可以添加，其他的应该不能填
	 * 
	 * @param assetFile excel表格文件
	 * @param isCheck 是否检查 0--手工确定 1--直接导入数据库中
	 * @return 错误的表格数据
	 * @throws AmsException
	 */
	InputStream importExcel(File assetFile,int isCheck)throws AmsException;
}
