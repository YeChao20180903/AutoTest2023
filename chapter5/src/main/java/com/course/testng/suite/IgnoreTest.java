package com.course.testng.suite;

import org.testng.annotations.Test;

public class IgnoreTest {
    @Test
    public void ignoreTest1(){
        System.out.printf("ignoreTest1 Thread Id: %s%n",Thread.currentThread().getId());
        System.out.println("ignoreTest1 执行了！！！");

    }

    @Test(enabled = false)
    public void ignoreTest2(){
        System.out.println("ignoreTest2 执行了！！！");
    }

    @Test(enabled = true)
    public void ignoreTest3(){
        System.out.printf("ignoreTest3 Thread Id: %s%n",Thread.currentThread().getId());
        System.out.println("ignoreTest3 执行了！！！");

    }
}
