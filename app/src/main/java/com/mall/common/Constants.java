package com.mall.common;



import com.mall.app.MyApp;

import java.io.File;

public class Constants {

    public static final String Base_Url = "http://cdwan.cn/api/";

    public static final String Base_UserUrl = "http://192.168.43.220:9001/"; //用户信息相关的基础地址

    public static final String Base_UploadUrl = "http://yun918.cn/study/public/";  //资源上传的基础地址


    //网络缓存的地址
    public static final String PATH_DATA = MyApp.app.getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/mall";

}