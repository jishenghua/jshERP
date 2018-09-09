package com.jsh.test;

public class MyRunnable implements Runnable {//实现Runnable接口
    public void run(){
        for(int i=0; i<30; i++){
            System.out.println(Thread.currentThread().getName()+"运行,  "+i);  //获取当前线程的名称
        }
    }
}