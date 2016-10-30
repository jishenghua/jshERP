package com.jsh.junitest.common;

import net.sf.json.JSONArray;
import junit.framework.TestCase;

public class whileTest extends TestCase
{
	public void testWhile()
	{
		int i = 0;
		for(;;i ++)
		{
			while(i == 10)
				System.out.println("aa" + i);
			break;
		}
	}
}
