package com.course.testng.exception;

import org.testng.annotations.Test;

public class ExceptionTest {
    //@Test(expectedExceptions = RuntimeException.class)
    public void testException(){
        System.out.println("这是一个失败的异常测试");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void runTimeExceptionSuccess(){
        System.out.println("这是一个成功的异常测试");
        throw new RuntimeException();
    }
}
