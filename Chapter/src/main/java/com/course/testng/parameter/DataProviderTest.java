package com.course.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "data")
    public void testDataProvider(String name, int age){
        System.out.println("\n name = " + name + ", age = " + age);
    }

    @DataProvider(name = "data")
    public Object[][] providerDta(){
        Object [][] o = new Object[][]{
                {"张三",10},
                {"李四",20},
                {"王五",30}
        };
        return o;
    }

    @Test(dataProvider = "methodData")
    public void test1(String name, int age){
        System.out.println("\n test1111方法 name = " + name + ", age = " + age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name, int age){
        System.out.println("\n test222方法 name = " + name + ", age = " + age);
    }

    @DataProvider(name = "methodData")
    public Object[][] methodDataTest(Method method){
        Object [][] o = null;
        if(method.getName().equals("test1")){
            o = new Object[][]{
                    {"小红",11},
                    {"小蓝",22},
            };
        }else if(method.getName().equals("test2")){
            o = new Object[][]{
                    {"小赵",33},
                    {"小马",44},
            };
        }
        return o;
    }

}
