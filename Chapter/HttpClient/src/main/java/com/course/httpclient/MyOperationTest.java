package com.course.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyOperationTest {
    private ResourceBundle bundle;
    private String url = "";

    @BeforeTest
    public void beforeTest(){
        //从配置文件里获取地址
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.operationTest");
    }

    @Test
    public void testPostCookie(){
        String uri = bundle.getString("test.operationTest.checkUser");
        String testUrl = this.url + uri;
        //声明一个post方法
        HttpPost post = new HttpPost(testUrl);
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //添加json参数
        JSONObject json = new JSONObject();
        json.put("account","admin");
        json.put("userPwd","123456");
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
//        CookieStore cookieStore = null;
//        client.setCookieStore(cookieStore);
        //执行post方法
        try {
            HttpResponse response = client.execute(post);

            //获取响应结果
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println("result:" + result);
            //处理结果，就是判断返回结果是否符合预期
            //将返回的响应结果字符串转化成为json对象
            JSONObject resultJson = new JSONObject(result);
            String code = resultJson.getString("code");
            String data = resultJson.getString("data");
            String message = resultJson.getString("message");
            //具体的判断返回结果的值
            Assert.assertEquals("000",code);
            Assert.assertEquals(".youximao.cn",data);
            Assert.assertEquals("登陆成功",message);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
