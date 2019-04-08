package com.cloudcreativity.intellijSchool.loginAndRegister;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.databinding.ActivityLoginBinding;
import com.cloudcreativity.intellijSchool.entity.UserEntity;
import com.cloudcreativity.intellijSchool.main.MainActivity;
import com.cloudcreativity.intellijSchool.utils.DefaultObserver;
import com.cloudcreativity.intellijSchool.utils.HttpUtils;
import com.cloudcreativity.intellijSchool.utils.SPUtils;
import com.cloudcreativity.intellijSchool.utils.StrUtils;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel {
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> pwd = new ObservableField<>();
    private ActivityLoginBinding binding;
    private LoginActivity context;
    private BaseDialogImpl baseDialog;

    LoginModel(ActivityLoginBinding binding, LoginActivity context) {
        this.binding = binding;
        this.context = context;
        this.baseDialog = context;
    }

    /**
     * 登录按钮点击
     */
    public void onLoginClick(){
        if(TextUtils.isEmpty(phone.get())||!StrUtils.isPhone(phone.get())){
            binding.tilPhone.setError("手机号格式不正确");
            return;
        }
        binding.tilPhone.setError(null);
        if(TextUtils.isEmpty(pwd.get())){
            binding.tilPwd.setError("密码不能为空");
            return;
        }
        binding.tilPwd.setError(null);

        HttpUtils.getInstance().login(phone.get(),StrUtils.md5(pwd.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        UserEntity entity = new Gson().fromJson(t,UserEntity.class);
                        SPUtils.get().putBoolean(SPUtils.Config.IS_LOGIN,true);
                        SPUtils.get().putInt(SPUtils.Config.UID,entity.getUid());
                        SPUtils.get().putString(SPUtils.Config.TOKEN,entity.getToken());
                        SPUtils.get().putString(SPUtils.Config.USER,t);
                        //finish skip to main
                        context.startActivity(new Intent().setClass(context,MainActivity.class));
                        context.finish();
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    /**
     * 注册按钮点击
     */
    public void onRegisterClick(){
        context.startActivity(new Intent(context,RegisterActivity.class));
    }

    /**
     * 忘记密码点击
     */
    public void onForgetClick(){
        context.startActivity(new Intent(context,ForgetPwdActivity.class));
    }
}
