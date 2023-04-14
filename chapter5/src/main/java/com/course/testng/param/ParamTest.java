package com.course.testng.param;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParamTest {
    @Test
    @Parameters({"name","age"})
    public void paramTest(String name,Integer age){
        System.out.println("name = "+name+"; age = "+age);
        System.out.printf("Thread Id: %s%n",Thread.currentThread().getId());
    }
}
