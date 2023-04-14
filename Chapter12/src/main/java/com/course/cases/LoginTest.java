package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import io.swagger.models.auth.In;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取HttpClient对象")
    public void beforeTest(){
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.httpClient = new DefaultHttpClient();
    }

    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase", 1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //第一步还是发送请求
        String result = getResult(loginCase);
        System.out.println(result);
        //第二步，验证结果
        Assert.assertEquals(loginCase.getExpected(),String.valueOf(result));
    }



    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase", 2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //第一步还是发送请求
        String result = getResult(loginCase);
        //第二步，验证结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }

    private String getResult(LoginCase loginCase) throws IOException {
        //创建httpPost请求,并传入url参数
        HttpPost httpPost = new HttpPost(TestConfig.loginUrl);
        //设置请求参数
        JSONObject params = new JSONObject();
        params.put("userName",loginCase.getUserName());
        params.put("password",loginCase.getPassword());
        //将参数放进请求体
        StringEntity entity = new StringEntity(params.toString(),"utf-8");
        httpPost.setEntity(entity);
        //设置请求头
        httpPost.setHeader("content-type","application/json");
        //设置cookieStore
        TestConfig.cookieStore = TestConfig.httpClient.getCookieStore();
        //执行请求
        HttpResponse response = TestConfig.httpClient.execute(httpPost);
        //获取响应结果
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        //输出响应结果
        return result;

    }
}
