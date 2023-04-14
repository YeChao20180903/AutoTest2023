package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException, InterruptedException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase", 1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        //发送请求，获取结果
        JSONArray resultJson = getJsonResult(getUserInfoCase);
//        Thread.sleep(3000);
        //验证结果
        User user = sqlSession.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);
        //这里需要创建一个ArrayList,并把user加进这个ArrayList中
        //目的：是为了转换成JSONArray
        List userList = new ArrayList();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        JSONArray jsonArray1 = new JSONArray(resultJson.getString(0));
        //断言结果
        Assert.assertEquals(jsonArray.toString(),jsonArray1.toString());
    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        //创建httpPost请求
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        //设置请求参数，请求参数是json对象，故new JSONObject。
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoCase.getUserId());
        //设置请求体,并设置字符集
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        //post请求带上请求体
        post.setEntity(entity);
        //设置请求头
        post.setHeader("content-type","application/json");
        //设置cookieStore
        TestConfig.httpClient.setCookieStore(TestConfig.cookieStore);
        //执行post请求，获取响应结果
        HttpResponse response = TestConfig.httpClient.execute(post);
        //拿到响应体,设置字符集
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        List resultList = Arrays.asList(result);
        JSONArray jsonArray = new JSONArray(resultList);
        return jsonArray;
    }
}
