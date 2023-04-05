package com.ceshiren.server;

import com.ceshiren.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1")
@Api(value = "/",description = "这是我全部的post方法")
public class MyHttpPost {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "这是一个登录接口并返回cookies的post请求",httpMethod = "POST")
    public String login(HttpServletResponse response,
                                     @RequestParam(value = "username",required = true) String username,
                                     @RequestParam(value = "password") String password){
        Cookie cookie = new Cookie("uuid", "wodangranjuedezijihenshuaia");
        if (username.equals("zhangsan") && password.equals("123456")){
                response.addCookie(cookie);
                return "登录成功";
            }
            return "用户名或密码错误";
    }

    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "这是一个获取用户列表的接口",httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                              @RequestBody User user){
        //需要携带cookies信息才能访问，得先拿到cookies(HttpServletRequest)
        Cookie[] cookies = request.getCookies();
        System.out.println("Cookies: "+cookies);
        User u;
        if (Objects.isNull(cookies)){
            return "cookies信息缺失，参数不合法";
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("uuid")
                && cookie.getValue().equals("wodangranjuedezijihenshuaia")
                && user.getUsername().equals("zhangsan")
                && user.getPassword().equals("123456")){
                u = new User();
                u.setName("lisi");
                u.setAge("18");
                u.setSex("male");
                return u.toString();
            }
        }
        //验证Cookies信息
       return "参数不合法";
    }
}
