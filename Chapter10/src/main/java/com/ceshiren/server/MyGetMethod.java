package com.ceshiren.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/",description = "这是我全部的get方法")
public class MyGetMethod {
    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取到cookies",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        //HttpServletRequest    装请求信息的类
        //HttpServletResponse   装响应信息的类
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        return "恭喜获得cookies信息成功";
    }

    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带Cookies访问",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return "没有携带cookies，访问失败";
        }
        for (Cookie cookie:cookies){
            if("login".equals(cookie.getName()) && "true".equals(cookie.getValue())){
                return "携带cookies信息访问成功";
            }
        }
        return "没有携带cookies，访问失败";
    }

    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数才能访问的get请求方法一",httpMethod = "GET")
    public Map<String,Integer> getWithParam(@RequestParam Integer start,
                                            @RequestParam Integer end){
        Map<String,Integer> goodList = new HashMap<>();
        goodList.put("七彩虹战斧3070ti",5299);
        goodList.put("苹果14pro max",9899);
        goodList.put("macbookpro 14寸 m1 pro",14999);
        return goodList;
    }

    @RequestMapping(value = "/get/with/pathParam/{start}/{end}",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数才能访问get请求的第二种实现",httpMethod = "GET")
    public Map<String,Integer> getWithParam2(@PathVariable Integer start,
                                             @PathVariable Integer end){
        Map<String,Integer> goodList = new HashMap<>();
        goodList.put("果14",5299);
        goodList.put("零食大礼包",99);
        goodList.put("Lg 2k 165hz显示器",2799);
        return goodList;
    }

    @RequestMapping(value = "/get/with/paramAndCookies/{start}/{end}",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数和Cookies才能访问的get请求",httpMethod = "GET")
    public Map<String,Integer> getWithParamAndCookies(@PathVariable Integer start,
                                             @PathVariable Integer end,HttpServletRequest request){
        Map<String,Integer> goodList = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return goodList ;
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")){
                goodList.put("果14",5299);
                goodList.put("零食大礼包",99);
                goodList.put("Lg 2k 165hz显示器",2799);
            }
        }
        return goodList;




    }
}
