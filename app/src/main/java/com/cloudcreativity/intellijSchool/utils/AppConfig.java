package com.cloudcreativity.intellijSchool.utils;

/**
 * 这个app的属性配置
 */
public class AppConfig {
    /**
     * 是否是开发调试阶段
     */
    static boolean DEBUG = true;
    /**
     * 网络数据缓存的文件夹名称
     */
    static final String CACHE_FILE_NAME = "app_cache";
    /**
     * 网络图片缓存的文件夹名称
     */
    static final String CACHE_IMAGE_NAME = "app_image_cache";
    /**
     * 这是SharePreference的名称
     */
    static final String SP_NAME = "people_pass_app_config";

    /**
     * 这是统一的文件名
     */
    public static String FILE_NAME = "people_pass_image_%d.%s";

    /**
     * 这是APP热更新的下载缓存目录
     */
    static String APP_HOT_UPDATE_FILE = "pass_app_hot_update.apk";

    /**
     * 这是七牛域名
     */
    public static String QINIU_DOMAIN = "http://qiniu.dnwapp.com/";

}
