package com.jsh.constants.asset;

/**
 * 定义资产管理常量
 * @author andy
 */
public interface AssetConstants
{
	/**
	 * 公共常量
	 * @author andy
	 */
	public class Common
	{
		
	}
	
	/**
	 * 资产常量--导入导出excel表格业务相关
	 * @author andy
	 */
	public class BusinessForExcel
	{
		/**
		 * 资产名称常量
		 */
		public static final int EXCEL_ASSETNAME = 0;
		
		/**
		 * 资产类型常量
		 */
		public static final int EXCEL_CATEGORY = 1;
		
		/**
		 * 资产单价
		 */
		public static final int EXCEL_PRICE = 2;
		
		/**
		 * 用户
		 */
		public static final int EXCEL_USER = 3;
		
		/**
		 * 购买日期
		 */
		public static final int EXCEL_PURCHASE_DATE = 4;
		
		/**
		 * 资产状态
		 */
		public static final int EXCEL_STATUS = 5;
		
		/**
		 * 位置
		 */
		public static final int EXCEL_LOCATION = 6;
		
		/**
		 * 资产编号
		 */
		public static final int EXCEL_NUM = 7;
		
		/**
		 * 序列号
		 */
		public static final int EXCEL_SERIALNO = 8; 
		
		/**
		 * 有效日期
		 */
		public static final int EXCEL_EXPIRATION_DATE = 9; 
		
		/**
		 * 保修日期
		 */
		public static final int EXCEL_WARRANTY_DATE = 10; 
		
		/**
		 * 供应商
		 */
		public static final int EXCEL_SUPPLIER = 11;
		
		/**
		 * 标签
		 */
		public static final int EXCEL_LABLE = 12;
		
		/**
		 * 描述
		 */
		public static final int EXCEL_DESC = 13;
		
		/**
		 * 表头
		 */
		public static final int EXCEL_TABLE_HEAD = 0;
		
		/**
		 * 状态 --在库
		 */
		public static final int EXCEl_STATUS_ZAIKU = 0;
		
		/**
		 * 状态 --在用
		 */
		public static final int EXCEl_STATUS_INUSE = 1;
		
		/**
		 * 状态 -- 消费
		 */
		public static final int EXCEl_STATUS_CONSUME = 2;
		
		/**
		 * action返回excel结果
		 */
		public static final String EXCEL = "excel";
	}
}
