package com.course.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

//import org.apache.http.client.HttpClient;

public class MyCookieForPost {
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
    public void testPostMethod(){
        String uri = bundle.getString("test.cookiesPost");
        String testUrl = this.url + uri;
        //声明一个post方法
        HttpPost post = new HttpPost(testUrl);
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //添加json参数
        JSONObject json = new JSONObject();
        json.put("name","huhansan");
        json.put("age","20");
        //设置请求头信息,设置在post方法里
        post.setHeader("content-type","application/json");
        //将json参数添加到post方法中
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(entity);
        //设置cookies信息
        client.setCookieStore(this.store);
        //执行post方法
        try {
            HttpResponse response = client.execute(post);

            //获取响应结果
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println("result:" + result);
            //处理结果，就是判断返回结果是否符合预期
            //将返回的响应结果字符串转化成为json对象
            JSONObject resultJson = new JSONObject(result);
            String name = resultJson.getString("name");
            String age = resultJson.getString("age");
            //具体的判断返回结果的值
            Assert.assertEquals("i am huhansan",name);
            Assert.assertEquals("20",age);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
