package com.course.testng.test;

import org.testng.annotations.Test;

public class TestPrintf {
    @Test
    public void testPrintf(){
        int i = 5;
        double j = 5;
        System.out.printf("i的值为%s,j的值为%n",i,j);
    }
}
