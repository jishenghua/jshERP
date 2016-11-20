package com.jsh.util.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
/**
 * 分页工具类，实现分页功能
 * @author  jishenghua
 * @version  [版本号version01, 2012-1-25]
 */
@SuppressWarnings("serial")
public class PageUtil<E> implements Serializable
{
	/**
	 * 总页数，根据总数和单页显示个数进行计算
	 */
	private int totalPage = 0;
	
	/**
	 * 总个数
	 */
	private int totalCount = 0 ;
	
	/**
	 * 当前页码
	 */
	private int curPage = 1;
	
	/**
	 * 每页显示个数
	 */
	private int pageSize = 10; 
	
	/**
	 * 是否为第一页
	 */
	private boolean isFirstPage = false;
	/**
	 * 是否是最后一页
	 */
	private boolean isLastPage = false;
	
	/**
	 * 是否有上一页
	 */
	private boolean hasPrevious = false;
	
	/**
	 * 是否有下一页
	 */
	private boolean hasNext = false;
	
	/**
	 * 返回页面list数组
	 */
	private List<E> pageList = new ArrayList<E>();
	
	/**
	 * 页面搜索条件，用map来实现
	 */
	private Map<String, Object> advSearch = new Hashtable<String, Object>();
	
	public PageUtil()
	{
		
	}
	
	public PageUtil(int totalCount,int pageSize,int curPage,Map<String, Object> adv)
	{
		init(totalCount,pageSize,curPage,adv);
	}
	/**
	 * 初始化页面显示参数
	 * @param totalCount 总数
	 * @param pageSize 页面显示个数
	 * @param curPage 当前页面
	 */
	public void init(int totalCount,int pageSize,int curPage,Map<String, Object> adv)
	{
		this.totalCount = totalCount;
		this.pageSize = pageSize ;
		this.curPage = curPage;
		this.advSearch = adv;
		//计算总页数
		if(pageSize != 0)
		{			
			this.totalPage = (totalCount+pageSize-1)/pageSize;
		}
		if(curPage <1)
		{
			this.curPage = 1;
		}
		if(curPage>this.totalPage)
		{
			this.curPage = this.totalPage;
		}
		if(curPage>0&&this.totalPage!=1&&curPage<this.totalPage)
		{
			this.hasNext = true ;
		}
		if(curPage>0&&this.totalPage!=1&&curPage>1&&curPage<=this.totalPage)
		{
			this.hasPrevious = true;
		}
		if(curPage == 1)
		{
			this.isFirstPage = true ;
		}
		if(curPage == this.totalPage)
		{			
			this.isLastPage = true;
		}
	}
	public int getTotalPage()
	{
		return totalPage;
	}
	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}
	public int getTotalCount() 
	{
		return totalCount;
	}
	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}
	public int getCurPage() 
	{
		return curPage;
	}
	public void setCurPage(int curPage)
	{
		this.curPage = curPage;
	}
	public int getPageSize() 
	{
		return pageSize;
	}
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
	public boolean isFirstPage()
	{
		return isFirstPage;
	}
	public void setFirstPage(boolean isFirstPage)
	{
		this.isFirstPage = isFirstPage;
	}
	public boolean isLastPage()
	{
		return isLastPage;
	}
	public void setLastPage(boolean isLastPage)
	{
		this.isLastPage = isLastPage;
	}
	public boolean isHasPrevious()
	{
		return hasPrevious;
	}
	public void setHasPrevious(boolean hasPrevious)
	{
		this.hasPrevious = hasPrevious;
	}
	public boolean isHasNext()
	{
		return hasNext;
	}
	public void setHasNext(boolean hasNext)
	{
		this.hasNext = hasNext;
	}
	
	public List<E> getPageList()
	{
		return pageList;
	}
	public void setPageList(List<E> pageList)
	{
		this.pageList = pageList;
	}
	
	public Map<String, Object> getAdvSearch()
	{
		return advSearch;
	}
	public void setAdvSearch(Map<String, Object> advSearch)
	{
		this.advSearch = advSearch;
	}
}
