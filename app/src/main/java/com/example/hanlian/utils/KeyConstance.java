package com.example.hanlian.utils;

public class KeyConstance {

    public static final int REQUEST_CODE_LOCATION = 100001;

  //  public static final String BASE_HOST = "http://120.25.238.32:998/";
    public static final String BASE_HOST = "http://www.zuichu.cc/";

    public static final String GET_TOKEN= BASE_HOST +"api/Login/GetToken";// 获取token

    public static final String AdminLoginUrl = BASE_HOST + "api/Login/AdminLogin" ; // 管理员登录

    public static final String USER_APPLICATION = BASE_HOST + "api/Sale/UserApplication" ; // 商家注册

    public static final String SEND_SALE_ACCOUNT_CODE = BASE_HOST + "api/Sale/SendBackAccountCode" ; // 发送找回用户名的验证码

    public static final String BACK_ACCOUNT = BASE_HOST + "api/Sale/BackAccount" ; // 获得用户名

    public static final String SEND_SALE_PASSWORD_CODE = BASE_HOST + "api/Sale/SendRetrieveCode" ; // 发送找回密码的验证码

    public static final String SALE_RESETTING_PASSWORD = BASE_HOST + "api/Sale/ResettingPassword" ; // 重置密码

    public static final String SELLER_APPLICATION = BASE_HOST + "api/Sale/SellerApplication" ; //商家注册

    public static final String sendApplicationCode = BASE_HOST + "api/Sale/SendApplicationCode" ; //发送申请验证码

    public static final String weburl= "http://120.25.238.32:998/web_src/notice.html" ;


}
