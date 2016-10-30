package com.jsh.junitest.common;

import junit.framework.TestCase;

public class ForTest extends TestCase
{
	public void testrrr()
	{
		String[] aa= {"111","222","333"};
		for(String str:aa)
			System.out.println(str);
		System.out.println("bbb");
	}
}
