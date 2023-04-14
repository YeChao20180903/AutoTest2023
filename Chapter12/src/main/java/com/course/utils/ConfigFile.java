package com.course.utils;

import com.course.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    public static ResourceBundle bundle
            = ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaceName interfaceName){
        String address = bundle.getString("test.url");
        String uri = "";
        //最终的测试地址
        String testUrl;

        if (interfaceName == InterfaceName.LOGIN){
            uri = bundle.getString("login.uri");
        }else if (interfaceName == InterfaceName.UPDATEUSERINFO){
            uri = bundle.getString("updateUserInfo.uri");
        }else if (interfaceName == InterfaceName.GETUSERLIST){
            uri = bundle.getString("getUserList.uri");
        } else if (interfaceName == InterfaceName.GETUSERINFO) {
            uri = bundle.getString("getUserInfo.uri");
        } else if (interfaceName == InterfaceName.ADDUSER) {
            uri = bundle.getString("addUser.uri");
        }
        testUrl = address + uri;
        return testUrl;
    }

}
