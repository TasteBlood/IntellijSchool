package com.cloudcreativity.intellijSchool.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.loginAndRegister.LoginActivity;
import com.cloudcreativity.intellijSchool.utils.SPUtils;
import com.cloudcreativity.intellijSchool.utils.ToastUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class IndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //检查权限
        String permissions[] = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            ActivityCompat.requestPermissions(this,permissions,10010);
        }

    }

    private void init() {
        View index = findViewById(R.id.iv_index);
        Animation animation = new ScaleAnimation(1.0f,1.1f,1.0f,1.1f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(2500);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(SPUtils.get().isLogin()){
                    //跳转到主页
                    startActivity(new Intent().setClass(IndexActivity.this,MainActivity.class));
                }else{
                    //跳转到登录
                    startActivity(new Intent().setClass(IndexActivity.this,LoginActivity.class));
                }
                onBackPressed();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        index.startAnimation(animation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==10010){
            if(!(Manifest.permission.CAMERA.equals(permissions[0])&&PackageManager.PERMISSION_GRANTED==grantResults[0])){
                ToastUtils.showLongToast(this,"权限获取失败，请在设置中开启");
                return;
            }
            if(!(Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[1])&&PackageManager.PERMISSION_GRANTED==grantResults[1])){
                ToastUtils.showLongToast(this,"权限获取失败，请在设置中开启");
                return;
            }
            if(!(Manifest.permission.READ_EXTERNAL_STORAGE.equals(permissions[2])&&PackageManager.PERMISSION_GRANTED==grantResults[2])){
                ToastUtils.showLongToast(this,"权限获取失败，请在设置中开启");
                return;
            }
            init();
        }
    }
}
