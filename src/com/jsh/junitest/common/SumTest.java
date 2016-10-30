package com.jsh.junitest.common;

import junit.framework.TestCase;

public class SumTest extends TestCase
{
    public void testSum()
    {
        //================宋芳====================
        int[] songfang = {6,6,6,-4,-4,-6,3,12,6,-6,6,-12,3,-6,12};
        int songfangSum = 0;
        int songying = 0;
        int songshu = 0;
        for(int i = 0 ;i < songfang.length;i ++)
        {
            songfangSum += songfang[i];
            if(songfang[i] > 0)
                songying ++;
            else
                songshu ++;
        }

        System.out.println("宋芳斗地主局数：" + songfang.length + ",赢" + songying + "局，输" + songshu +"局,统计玩牌的计分是：" + songfangSum);
        //================宋芳====================

        //===============王维========================
        int[] wangwei = {-3,-3,-3,8,2,12,-6,-6,6,3,-3,6,-6,3,-6};
        int wangweiSum = 0;
        int wangweiying = 0;
        int wangweishu = 0;
        for(int i = 0 ;i < wangwei.length;i ++)
        {
            wangweiSum += wangwei[i];
            if(wangwei[i] > 0)
                wangweiying ++;
            else
                wangweishu ++;
        }
        System.out.println("王维斗地主局数：" + wangwei.length + ",赢" + wangweiying + "局，输" + wangweishu +"局,统计玩牌的计分是：" + wangweiSum);
        //===============王维========================

        //===============王鹏========================
        int[] wangpeng = {-3,-3,-3,-4,2,-6,3,-6,-12,3,-3,6,3,3,-6};
        int wangpengSum = 0;
        int wangpengying = 0;
        int wangpengshu = 0;
        for(int i = 0 ;i < wangpeng.length;i ++)
        {
            wangpengSum += wangpeng[i];
            if(wangpeng[i] > 0)
                wangpengying ++;
            else
                wangpengshu ++;
        }
        System.out.println("王鹏斗地主局数：" + wangwei.length + ",赢" + wangpengying + "局，输" + wangpengshu +"局,统计玩牌的计分是：" + wangpengSum);
        //===============王鹏========================
        
        int[] jishenghua={3,-6,2,5,-9,4,7,-8,-6,-1,4};
        int jishenghuaSum=0;
        int jishenghuaYing=0;
        int jishenghuaShu=0;
        for(int i=0;i<jishenghua.length;i++)
        {
        	jishenghuaSum+=jishenghua[i];
        	if(jishenghua[i]>0)
        	   jishenghuaYing++;
        	else
        	   jishenghuaShu++;
        }
        System.out.println("季圣华斗地主局数："+jishenghua.length+",赢"+jishenghuaYing+"局，输"+jishenghuaShu+"局,统计玩牌的计分是："+jishenghuaSum);
    }
}
