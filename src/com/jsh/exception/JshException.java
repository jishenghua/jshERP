package com.jsh.exception;

/**
 * @title: 平台异常基类 
 * @description: 用于包装一些异常信息，打印日志等服务
 * @author jishenghua
 * @since: 2014-01-24
 */
@SuppressWarnings("serial")
public class JshException extends Exception 
{
	public long errorCode = -1;
	
	public String message ;
	
	public JshException()
	{
		super();
	}

	public JshException(String message)
	{
		super(message);
		this.message = message;
	}

	public JshException(String message, Throwable cause)
	{
		super(message, cause);
		this.message = message;
	}

	public JshException(Throwable cause)
	{
		super(cause);
	}

	public JshException(long errorCode)
	{
		super();
		this.errorCode = errorCode;
	}

	public JshException(String message, long errorCode)
	{
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public JshException(String message, long errorCode, Throwable cause)
	{
		super(message, cause);
		this.errorCode = errorCode;
		this.message = message;
	}

	public JshException(long errorCode, Throwable cause)
	{
		super(cause);
		this.errorCode = errorCode;
	}

	public long getErrorCode()
	{
		return errorCode;
	}

	public String getMessage()
	{
		return message;
	}
}
