package com.cloudcreativity.intellijSchool.utils;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 整个程序的网络接口配置
 */
public interface APIServiceVideo {
    /**
     * 网络请求的配置
     */
    long timeOut =10;//网络超时
    /**
     * 整体的接口配置
     */
    String HOST_APP = "http://platform.sina.com.cn/";
//    String HOST_APP = "http://192.168.31.158:8081/";


    /**
     *  首页分类
     */
   @GET("opencourse/get_disciplines?app_key=1919446470")
    //@GET("admin/activity/getList")
    Observable<String> getType();
    /**
     *  视频列表
     */
    @GET("opencourse/get_courses?app_key=1919446470")
    Observable<String> getTypeList(@Query("discipline_id") String id, @Query("page") int page,
                                   @Query("count_per_page") int num, @Query("order_by") String order);
}
