package com.course.testng.parameter;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParameterTest {

    @Test
    @Parameters({"name","age"})
    public void test(String name, int age){
        System.out.println("\n name = " + name + ", age = " + age);
    }
}
