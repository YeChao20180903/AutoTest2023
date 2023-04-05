package com.course.httpclient.demo;

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

public class MyCookiesForGet {
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
    public void getWithCookies() throws IOException {
        String uri = bundle.getString("get.With.Cookies");
        String testUrl = this.url + uri;
        HttpGet httpGet = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        //设置需要携带的Cookies信息
        client.setCookieStore(cookieStore);
        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200){
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }
    }

}
