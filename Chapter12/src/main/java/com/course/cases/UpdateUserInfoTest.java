package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class UpdateUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息接口测试")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        //发送请求，抽出成一个方法
        int result = getResult(updateUserInfoCase);


        User user = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        //验证结果
        Assert.assertNotNull(result);
        Assert.assertNotNull(user);
    }

    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息接口测试")
    public void deleteUserInfo() throws IOException, InterruptedException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase deleteUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 2);
        System.out.println(deleteUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        //发送请求，抽出成一个方法
        int result = getResult(deleteUserInfoCase);

        User user = sqlSession.selectOne(deleteUserInfoCase.getExpected(), deleteUserInfoCase);
        //验证结果
        Assert.assertNotNull(result);
        Assert.assertNotNull(user);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        //创建httpPost请求对象,并传入url
        HttpPost httpPost = new HttpPost(TestConfig.updateUserInfoUrl);
        //创建JSONObject对象，设置参数
        JSONObject param = new JSONObject();
        //课程里是updateUserInfoCase.getId()，应该是写错了，后续对比
        param.put("id",updateUserInfoCase.getUserId());
        param.put("userName",updateUserInfoCase.getUserName());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("age",updateUserInfoCase.getAge());
        param.put("permission",updateUserInfoCase.getPermission());
        param.put("isDelete",updateUserInfoCase.getIsDelete());

        //创建StringEntity对象,请求参数放入请求体
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        httpPost.setEntity(entity);

        //设置头信息
        httpPost.setHeader("content-type","application/json");
        //设置cookieStore
        TestConfig.httpClient.setCookieStore(TestConfig.cookieStore);

        //执行post请求,返回响应结果
        HttpResponse response = TestConfig.httpClient.execute(httpPost);
        //设置请求体和拿到响应体都需要设置字符集
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        //因为数据库设计更新用户后会返回一个int值，表示更新了多少条数据，所以这里需要转成int
        return Integer.parseInt(result);
    }
}
