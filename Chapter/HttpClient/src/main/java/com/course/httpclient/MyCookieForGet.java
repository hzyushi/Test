package com.course.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

//import org.apache.http.client.HttpClient;

public class MyCookieForGet {
    /**
     * 重点：获取cookie，以及携带获取的cookie进行访问
     */
    private ResourceBundle bundle;
    private String url = "";
    private CookieStore store = null;

    @BeforeTest
    public void beforeTest(){
        //从配置文件里获取地址
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookie(){
        String result = "";
        String uri = bundle.getString("getcookie.uri");
        String testUrl = url + uri;
        System.out.println("testUrl = " + testUrl);
        HttpGet get = new HttpGet(testUrl);
        //HttpClient无法获取cookie信息，必须是DefaultHttpClient才可以获取cookie信息
//        HttpClient client = new DefaultHttpClient();
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = client.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //获取cookie信息
        store = client.getCookieStore();
        List<Cookie> cookList = store.getCookies();
        for (Cookie cookie:cookList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name = " + name + "; value = " + value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookie"})
    public void testWithCookies(){
        String url = this.url + bundle.getString("test.cookieGet");
        System.out.println("url = " + url);
        HttpGet get = new HttpGet(url);
        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookies信息
        client.setCookieStore(store);
        try {
            HttpResponse response = client.execute(get);
            //获取响应状态码
            int status = response.getStatusLine().getStatusCode();
            System.out.println("status = " + status);
            if(status == 200){
                String result = EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
