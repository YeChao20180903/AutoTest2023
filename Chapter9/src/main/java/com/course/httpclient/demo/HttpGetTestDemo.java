package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class HttpGetTestDemo {
    @Test
    public void testGet() throws IOException {
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }
}
