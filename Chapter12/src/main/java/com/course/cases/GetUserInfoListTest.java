package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;

import org.apache.http.HttpEntity;
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
import java.util.List;

public class GetUserInfoListTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户信息")
    public void getUserInfoList() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase", 1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        //发送请求，获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);
        //验证结果
        List<User> userList = sqlSession.selectList(getUserListCase.getExpected(), getUserListCase);
        for (User user:userList){
            System.out.println("获取到的user:"+user.toString());
        }

        JSONArray userListJson = new JSONArray(userList);

        Assert.assertEquals(userListJson.length(),resultJson.length());
        for (int i = 0; i < resultJson.length(); i++) {
            JSONObject expected = (JSONObject) userListJson.get(i);
            JSONObject actual = (JSONObject) resultJson.get(i);
            Assert.assertEquals(expected.toString(),actual.toString());
        }


    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        //创建post请求
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("sex",getUserListCase.getSex());
        param.put("age",getUserListCase.getAge());

        //创建请求体,设置字符集
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        //post请求设置带上entity请求体
        post.setEntity(entity);
        //设置带上cookies信息
        TestConfig.httpClient.setCookieStore(TestConfig.cookieStore);
        //设置请求头
        post.setHeader("content-type","application/json");
        HttpResponse response = TestConfig.httpClient.execute(post);
        //获取响应体,设置字符集
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        //返回对象为JSONArray, 故创建JSONArray对象，传入String形参进行转换
        JSONArray jsonArray = new JSONArray(result);
        //返回JSONArray对象
        return jsonArray;

    }
}
