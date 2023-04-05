package com.course.httpclient.demo;

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

public class MyCookiesForPost {
    String url;
    private ResourceBundle bundle;
    private CookieStore cookieStore;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }
    @Test
    public void getCookies() throws IOException {
//        System.out.println(url);
        String testUrl = url + bundle.getString("getCookies.uri");
        HttpGet httpGet = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);

        cookieStore = client.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie:cookies){
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            System.out.println("cookieName = "+cookieName+";cookieValue = "+cookieValue);
        }
    }

    @Test(dependsOnMethods = {"getCookies"})
    public void postWithParamAndCookies() throws IOException {
        //在配置文件application.properties中定义postWithCookies的uri,并获取uri
        String testUrl = this.url + bundle.getString("post.with.cookies");
        //声明一个HttpPost对象，并传入testUrl
        HttpPost httpPost = new HttpPost(testUrl);
        //设置httpPost请求的请求头信息
        httpPost.setHeader("content-type","application/json");

        //设置httpPost请求的参数，参数类型为json
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","huhansan");
        jsonObject.put("age","18");

        StringEntity entity = new StringEntity(jsonObject.toString());
        httpPost.setEntity(entity);
        //创建一个DefaultHttpClient对象，设置请求的cookies信息
        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(cookieStore);
        //client执行post请求
        HttpResponse response = client.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        //获取响应的结果，转换成jsonObject对象
        JSONObject responseJson = new JSONObject(result);
        //获取响应结果中的值
        String res = (String) responseJson.get("result");
        String status = (String) responseJson.get("status");
        //做断言，看结果是否符合预期
        Assert.assertEquals(res,"success");
        Assert.assertEquals(status,"1");
    }
}
