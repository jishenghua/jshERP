package com.jsh.service.basic;

import com.jsh.base.BaseIService;
import com.jsh.util.JshException;
import com.jsh.model.po.Logdetails;

public interface LogIService extends BaseIService<Logdetails>
{
	/**
	 * 增加
	 * @param t 对象
	 * @throws JshException
	 */
	@Override
	void save(Logdetails t);
}
