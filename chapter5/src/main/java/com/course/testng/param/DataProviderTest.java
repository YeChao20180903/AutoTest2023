package com.course.testng.param;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {
    @Test(dataProvider = "data")
    public void testDataProvider(String name,Integer age){
        System.out.println("name = "+name+"; age = "+age);
    }

    @DataProvider(name = "data")
    public Object[][] dataProvider(){
        Object[][] o = new Object[][]{
                {"zhangsan",25},
                {"yechao",30},
                {"wangwu",18}
        };
        return o;
    }
    @Test(dataProvider = "dataProvider")
    public void test1(String name,int age){
        System.out.println("test1方法 name="+name+"; age="+age);
    }

    @Test(dataProvider = "dataProvider")
    public void test2(String name,int age){
        System.out.println("test2方法 name="+name+"; age="+age);
    }

    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider(Method method){
        Object[][] result = null;
        if (method.getName().equals("test1")){
            result = new Object[][]{
                    {"jingtian",30},
                    {"zhangjike",28}
            };
        }else if (method.getName().equals("test2")){
            result = new Object[][]{
                    {"jinchen",32},
                    {"chenyao",25}
            };
        }

        return result;
    }
}
