package com.cloudcreativity.intellijSchool.loginAndRegister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.utils.AppConfig;
import com.cloudcreativity.intellijSchool.utils.DefaultObserver;
import com.cloudcreativity.intellijSchool.utils.HttpUtils;
import com.cloudcreativity.intellijSchool.utils.ToastUtils;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FillInfoModel {
    private BaseDialogImpl baseDialog;
    private FillInfoActivity context;
    private AlertDialog dialog;

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> stuNum = new ObservableField<>();
    public ObservableField<String> school = new ObservableField<>();
    public ObservableField<String> house = new ObservableField<>();
    public ObservableField<String> houseNum = new ObservableField<>();
    public ObservableField<String> sex = new ObservableField<>();
    public ObservableField<String> birth = new ObservableField<>();
    public ObservableField<String> avatar = new ObservableField<>();
    private int selectScId  = 0;
    private int selectDmId = 0;
    private String token = "";
    private int uid;
    private final Configuration config = new Configuration.Builder()
            .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
            .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
            .connectTimeout(10)           // 链接超时。默认10秒
            .useHttps(true)               // 是否使用https上传域名
            .responseTimeout(60)          // 服务器响应超时。默认60秒
            //.recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
            //.recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
            .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
            .build();

    private UploadManager uploadManager;

    FillInfoModel(final FillInfoActivity context,int uid) {
        this.context = context;
        this.baseDialog = context;
        this.uid = uid;
        //加载七牛token
        HttpUtils.getInstance().getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        token = t;
                        uploadManager = new UploadManager(config);
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        context.onBackPressed();
                    }
                });
    }

    public void onChooseSchool(){
        showSchoolDialog();
    }

    public void onChooseHouse(){
        showHouseDialog(this.selectScId);
    }

    public void onChoosePic(){
        context.openPictureDialog(true);
    }

    void onPhoto(String path){
        //上传图片
        baseDialog.showProgress("上传中");
        uploadManager.put(path,
                String.format(Locale.CHINA,
                        AppConfig.FILE_NAME,System.currentTimeMillis(),"jpg"),
                token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        avatar.set(AppConfig.QINIU_DOMAIN+key);
                        baseDialog.dismissProgress();
                    }
                },null);
    }

    public void onChooseSex(){
        showSexDialog();
    }


    public void onChooseBirth(){
        showBirthDialog();
    }

    private void showSchoolDialog(){
        HttpUtils.getInstance().getSchools()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        final CharSequence[] items;
                        try {
                            final JSONArray array = new JSONArray(t);
                            items = new CharSequence[array.length()];
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                items[i] = object.getString("schoolName");
                            }
                            dialog = new AlertDialog.Builder(context)
                                    .setTitle("选择学校")
                                    .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            school.set((String) items[which]);
                                            try {
                                                selectScId = array.getJSONObject(which).getInt("scId");
                                                selectDmId = 0;
                                                house.set("");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            dialog.dismiss();
                                        }
                                    }).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    private void showHouseDialog(int schoolId){
        HttpUtils.getInstance().getHouse(schoolId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {

                        final CharSequence[] items;
                        try {
                            JSONObject dataObj = new JSONObject(t);
                            final JSONArray array = dataObj.getJSONArray("data");
                            items = new CharSequence[array.length()];
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                items[i] = object.getString("dormitoryNum");
                            }
                            dialog = new AlertDialog.Builder(context)
                                    .setTitle(school.get())
                                    .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            house.set((String) items[which]);
                                            try {
                                                selectDmId = array.getJSONObject(which).getInt("dmId");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            dialog.dismiss();
                                        }
                                    }).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    private void showSexDialog(){
        final CharSequence[] sexItems = {"男","女"};
        dialog = new AlertDialog.Builder(context)
                .setTitle("选择性别")
                .setItems(sexItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sex.set((String) sexItems[which]);
                    }
                }).show();
    }


    private void showBirthDialog(){
        TimePickerView pickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                birth.set(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
            }
        })
                .setOutSideCancelable(true)
                .setType(new boolean[]{true,true,true,false,false,false})
                .setCancelColor(Color.parseColor("#717171"))
                .setSubmitColor(Color.parseColor("#f48c26"))
                .setTitleBgColor(Color.parseColor("#ffffff"))
                .setBgColor(Color.parseColor("#ffffff"))
                .isDialog(true)
                .build();

        pickerView.show();

    }

    public void submit(){
        if(TextUtils.isEmpty(name.get())){
            ToastUtils.showShortToast(context,"姓名不能为空");
            return;
        }

        if(TextUtils.isEmpty(stuNum.get())){
            ToastUtils.showShortToast(context,"学号不能为空");
            return;
        }

        if(TextUtils.isEmpty(school.get())){
            ToastUtils.showShortToast(context,"学校不能为空");
            return;
        }

        if(TextUtils.isEmpty(house.get())){
            ToastUtils.showShortToast(context,"宿舍不能为空");
            return;
        }

        if(TextUtils.isEmpty(houseNum.get())){
            ToastUtils.showShortToast(context,"宿舍号不能为空");
            return;
        }

        if(TextUtils.isEmpty(sex.get())){
            ToastUtils.showShortToast(context,"性别不能为空");
            return;
        }

        HttpUtils.getInstance().register2(uid,stuNum.get(),name.get(),sex.get(),birth.get(),avatar.get(),selectDmId,houseNum.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        //成功，下一步人脸采集
                        Intent intent = new Intent(context,FaceActivity.class);
                        intent.putExtra("uid",uid);
                        context.startActivity(intent);
                        context.finish();
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });




    }
}
