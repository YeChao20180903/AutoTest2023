package com.course.testng.mutiThread;

import org.testng.annotations.Test;

public class MutilThreadOnXml {
    @Test()
    public void test1(){
        System.out.printf("MutilThreadOnXml.test1   Thread Id %s%n",Thread.currentThread().getId());
    }

    @Test()
    public void test2(){
        System.out.printf("MutilThreadOnXml.test2   Thread Id %s%n",Thread.currentThread().getId());
    }

    @Test()
    public void test3(){
        System.out.printf("MutilThreadOnXml.test3   Thread Id %s%n",Thread.currentThread().getId());
    }
}
