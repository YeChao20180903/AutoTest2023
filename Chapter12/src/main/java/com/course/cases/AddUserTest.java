package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpEntity;
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

public class AddUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = sqlSession.selectOne("addUserCase", 1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发请求，获取结果
        String result = getResult(addUserCase);
        //sqlSession.clearCache(); 网上和大周老师说的清缓存的方法没用

        //NPE解决办法: 进入mysql终端，把事务的默认隔离级别设置成 "读已提交".
        //mysql> SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;

        //线程休息
        Thread.sleep(3000);
        //验证返回结果
        User user = sqlSession.selectOne("addUser", addUserCase);
        System.out.println(user.toString());
        Assert.assertEquals(addUserCase.getExpected(),result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        //创建httpPost请求对象
        HttpPost httpPost = new HttpPost(TestConfig.addUserUrl);
        //添加请求参数
        JSONObject params = new JSONObject();
        params.put("userName",addUserCase.getUserName());
        params.put("password",addUserCase.getPassword());
        params.put("sex",addUserCase.getSex());
        params.put("age",addUserCase.getAge());
        params.put("permission",addUserCase.getPermission());
        params.put("isDelete",addUserCase.getIsDelete());
        //设置请求体
        StringEntity entity = new StringEntity(params.toString(),"utf-8");
        httpPost.setEntity(entity);
        //设置请求头
        httpPost.setHeader("content-type","application/json");
        //设置cookies信息
        TestConfig.httpClient.setCookieStore(TestConfig.cookieStore);
        //执行post请求
        HttpResponse response = TestConfig.httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        return result;
    }
}
