package com.jsh.base;

import org.apache.log4j.Logger;

/**
 * 封装log4j日志信息，打印日志信息类
 * @author ji/sheng/hua  qq_7527.18920
 * @since 2014-01-22
 */
public class Log
{
	/**
	 * 根据异常信息获取调用类的信息
	 */
	private static final Exception ex = new Exception();
	
	/**
	 * 获取Log4j实例
	 */
	private static final  Logger log = Logger.getLogger("jsh");
	
	/**
	 * Info级别日志前缀
	 */
	public static final String LOG_INFO_PREFIX = "==========";
	
	/**
	 * error级别日志前缀
	 */
	public static final String LOG_ERROR_PREFIX = ">>>>>>>>>>";
	
	/**
	 *  debug级别日志前缀
	 */
	public static final String LOG_DEBUG_PREFIX = "-----------";
	
	/**
	 *  fatal级别日志前缀
	 */
	public static final String LOG_FATAL_PREFIX = "$$$$$$$$$$";
	
	/**
	 *  warn级别日志前缀
	 */
	public static final String LOG_WARN_PREFIX = "##########";
	
	/**
	 * 打印deug日期信息
	 * @param msg 日志信息
	 */
	public static void debugFileSync(Object msg)
	{
		log.debug(getLogDetail(msg));
	}

	/**
	 * 打印debug异常信息
	 * @param msg 日志信息
	 * @param e 异常堆栈
	 */
	public static void debugFileSync(Object msg, Throwable e)
	{
		log.debug(getLogDetail(msg), e);
	}

	/**
	 * 打印info日志信息
	 * @param msg 日志信息
	 */
	public static void infoFileSync(Object msg)
	{
		log.info(getLogDetail(msg));
	}

	/**
	 * 打印 info日志带异常信息
	 * @param msg 日志信息
	 * @param e 异常堆栈
	 */
	public static void infoFileSync(Object msg, Throwable e)
	{
		log.info(getLogDetail(msg), e);
	}

	/**
	 * 打印warn日期信息
	 * @param msg 日志信息
	 */
	public static void warnFileSync(Object msg)
	{
		log.warn(getLogDetail(msg));
	}

	/**
	 * 打印warn日志信息带异常
	 * @param msg日志信息
	 * @param e 异常堆栈
	 */
	public static void warnFileSync(Object msg, Throwable e)
	{
		log.warn(getLogDetail(msg), e);
	}

	/**
	 * 打印error日志信息
	 * @param msg 日志信息
	 */
	public static void errorFileSync(Object msg)
	{
		log.error(getLogDetail(msg));
	}

	/**
	 * 打印error日志信息带异常
	 * @param msg 日志信息
	 * @param e 异常堆栈
	 */
	public static void errorFileSync(Object msg, Throwable e)
	{
		log.error(getLogDetail(msg), e);
	}

	/**
	 * 打印fatal日志信息
	 * @param msg 日志信息
	 */
	public static void fatalFileSync(Object msg)
	{
		log.fatal(getLogDetail(msg));
	}

	/**
	 * 打印fatal日志信息带异常
	 * @param msg 日志信息
	 * @param e 异常堆栈
	 */
	public static void fatalFileSync(Object msg, Throwable e)
	{
		log.fatal(getLogDetail(msg), e);
	}
	
	/**
	 * 拼装日志详细信息
	 * @param message 要打印的日志信息
	 * @return 封装后的日志详细信息
	 */
	private static synchronized String getLogDetail(Object message)
	{
		String msg = "";
		if (null != message)
			msg = message.toString();
		StringBuffer bf = new StringBuffer();
		try
		{
			ex.fillInStackTrace();
			throw ex;
		}
		catch (Exception ex)
		{
			StackTraceElement[] trace = ex.getStackTrace();
			//获取异常堆栈中的调用类信息
			final int pos = 2;
			bf.append(msg);
			bf.append(" [class:");
			bf.append(trace[pos].getClassName());
			bf.append(" method:");
			bf.append(trace[pos].getMethodName());
			bf.append(" line:");
			bf.append(trace[pos].getLineNumber());
			bf.append("]");
		}
		return bf.toString();
	}
}
