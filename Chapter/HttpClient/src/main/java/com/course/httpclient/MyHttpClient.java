package com.course.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyHttpClient {

    @Test
    public void test1(){
        String result;
        HttpGet get = new HttpGet("http://www.baidu.com");
        //执行get方法
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(get);
            //整个响应的全体的内容
            result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
