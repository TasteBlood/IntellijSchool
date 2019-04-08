package com.cloudcreativity.intellijSchool.loginAndRegister;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.baiduFace.FaceDetectActivity;
import com.cloudcreativity.intellijSchool.base.BaseApp;
import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.databinding.ActivityFaceBinding;
import com.cloudcreativity.intellijSchool.utils.DefaultObserver;
import com.cloudcreativity.intellijSchool.utils.HttpUtils;
import com.cloudcreativity.intellijSchool.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FaceModel {
    private BaseDialogImpl baseDialog;
    private FaceActivity context;
    private int uid;
    private String faceData;
    private ActivityFaceBinding binding;

    FaceModel(BaseDialogImpl baseDialog, FaceActivity context, int uid, ActivityFaceBinding binding) {
        this.baseDialog = baseDialog;
        this.binding = binding;
        this.context = context;
        this.uid = uid;
    }

    void onFace(String face) {
        if (TextUtils.isEmpty(face)) {
            faceData = "";
            binding.ivFace.setImageResource(R.mipmap.face_tected);
        } else {
            //
            faceData = face;
            //加载图片
            byte data[] = Base64.decode(face, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            binding.ivFace.setImageBitmap(bitmap);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onFaceClick() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            context.startActivityForResult(new Intent(context, FaceDetectActivity.class), 10010);
        } else {
            context.requestPermissions(new String[]{Manifest.permission.CAMERA}, 10010);
        }

    }

    public void onSubmit() {
        if (TextUtils.isEmpty(faceData)) {
            ToastUtils.showShortToast(context, "人脸采集未完成");
            return;
        }

        HttpUtils.getInstance().register3(uid, faceData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog, true) {

                    @Override
                    public void onSuccess(String t) {
                        ToastUtils.showShortToast(context, "注册成功，请登录");
                        BaseApp.app.destroyActivity();
                        Intent newIntent = new Intent(context, LoginActivity.class);
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(newIntent);
                        context.finish();
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }
}
