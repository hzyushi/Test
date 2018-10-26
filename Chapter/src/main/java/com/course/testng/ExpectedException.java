package com.course.testng;

import org.testng.annotations.Test;

public class ExpectedException {

    /**
     * 期望结果为某一个异常的时候需要用到异常测试
     * 例如传入了某些不合法的参数，程序抛出了异常
     */

    //下面是测试结果会失败的异常测试
    @Test(expectedExceptions = RuntimeException.class)
    public void runTimeExceptionFail(){
        System.out.println("这是一个失败的异常测试");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void runTimeExceptionSuccess(){
        System.out.println("这是一个成功的异常测试");
        throw new RuntimeException();
    }
}
