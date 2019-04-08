package com.cloudcreativity.intellijSchool.utils;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 整个程序的网络接口配置
 */
public interface APIService {
    /**
     * 网络请求的配置
     */
    long timeOut =10;//网络超时
    /**
     * 整体的接口配置
     */
//    String TEST_HOST = "http://192.168.31.229/";
    String TEST_HOST = "http://192.168.31.229:8081/";
    String ONLINE_HOST = "http://192.168.31.229:8081/";
    String HOST = AppConfig.DEBUG ? TEST_HOST : ONLINE_HOST;
    String HOST_APP = AppConfig.DEBUG ? TEST_HOST+"admin/" : ONLINE_HOST+"admin/";

    @GET("getQiNiuToken")
    Observable<String> getQiNiuToken();

    @GET("school/getPageList")
    Observable<String> getSchools();

    @GET("dormitory/getPageList")
    Observable<String> getHouse(@Query("scId") int scId);

    @POST("user/register1")
    @FormUrlEncoded
    Observable<String> register1(@Field("userName") String phone,
                                 @Field("userPass") String userPass,
                                 @Field("sms") String sms);

    @POST("user/register2")
    @FormUrlEncoded
    Observable<String> register2(@Field("uId") int uid,
                                 @Field("studentId") String stuId,
                                 @Field("realName") String realName,
                                 @Field("gender") String gender,
                                 @Field("birth") String birth,
                                 @Field("headPic") String headPic,
                                 @Field("dmId") int dmId,
                                 @Field("dormNum") String dormNum);

    @POST("user/register3")
    @FormUrlEncoded
    Observable<String> register3(@Field("uId") int uId,
                                 @Field("image") String base64);

    @POST("user/login")
    @FormUrlEncoded
    Observable<String> login(@Field("userName") String phone,
                             @Field("userPass") String userPass);

    /**
     * 版本热更新
     */
    @GET("getLastVersion")
    Observable<String> getLastVersion();

    /**
     *
     * @param content 文本内容
     * @param pictures 图片地址s
     * @param video 视频地址
     *  添加足迹
     */
    @POST("produce/add")
    @FormUrlEncoded
    Observable<String> addFoot(@Field("content") String content,
                               @Field("picture") String pictures,
                               @Field("video") String video);

    /**
     *
     * @param pageNum 页码
     * @param pageSize 页大小
     * 分页查询页大小
     */
    @POST("produce/getPageList")
    @FormUrlEncoded
    Observable<String> getFeet(@Field("pageNum") int pageNum,
                               @Field("pageSize") int pageSize);

    /**
     *
     * @param id 作品id
     * @param state 状态 1正常  2举报 3删除
     */
    @POST("produce/edit")
    @FormUrlEncoded
    Observable<String> deleteFoot(@Field("pId") int id,
                                  @Field("pState") int state);

    /**
     *
     * @param pId 作品id
     * @param reason 举报内容
     */
    @POST("report/add")
    @FormUrlEncoded
    Observable<String> reportFoot(@Field("pId") int pId,
                                  @Field("reason") String reason);

    /**
     *
     * @param id 作品id
     *  点赞
     */
    @POST("proZan/add")
    @FormUrlEncoded
    Observable<String> proZan(@Field("targetId") int id);


    @POST("proComment/add")
    @FormUrlEncoded
    Observable<String> proComment(@Field("pId") int pId,
                                  @Field("fromId") int fromId,
                                  @Field("toId") int toId,
                                  @Field("commentContent") String content);
}
