package com.cloudcreativity.intellijSchool.loginAndRegister;

import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.databinding.ActivityForgetPwdBinding;
import com.cloudcreativity.intellijSchool.utils.StrUtils;

public class ForgetPwdModel {
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> pwd1 = new ObservableField<>();
    public ObservableField<String> pwd2 = new ObservableField<>();
    public ObservableField<String> verifyCode = new ObservableField<>();
    public ObservableField<String> btn_verify_text = new ObservableField<>();
    public ObservableField<Boolean> is_enable = new ObservableField<>();

    private CountDownTimer timer;
    private BaseDialogImpl baseDialog;
    private ForgetPwdActivity context;
    private ActivityForgetPwdBinding binding;

    ForgetPwdModel(BaseDialogImpl baseDialog, ForgetPwdActivity context, ActivityForgetPwdBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.binding = binding;
        btn_verify_text.set("发送验证码");
        is_enable.set(true);
    }

    /**
     * 发送验证码
     */
    public void onSendVerifyClick(){
        if(!is_enable.get())
            return;
        startTimer();

    }

    /**
     * 注册点击
     */
    public void onSubmitClick(){
        if(TextUtils.isEmpty(phone.get())||!StrUtils.isPhone(phone.get())){
            binding.tilPhone.setError("手机号格式不正确");
            return;
        }
        binding.tilPhone.setError(null);
        if(TextUtils.isEmpty(pwd1.get())){
            binding.tilPwd1.setError("密码不能为空");
            return;
        }
        binding.tilPwd1.setError(null);
        if(TextUtils.isEmpty(pwd2.get())||!pwd1.get().equals(pwd2.get())){
            binding.tilPwd2.setError("密码不一致");
            return;
        }
        binding.tilPwd2.setError(null);
        if(TextUtils.isEmpty(verifyCode.get())){
            binding.tilVerifyCode.setError("验证码不能为空");
            return;
        }
        binding.tilVerifyCode.setError(null);
    }

    /**
     * 开始计时
     */
    private void startTimer(){
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
        is_enable.set(false);
        timer = new CountDownTimer(120*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_verify_text.set((millisUntilFinished/1000)+"S后");
            }

            @Override
            public void onFinish() {
                stopTimer();
            }
        };

        timer.start();

    }

    /**
     * 停止计时
     */
    void stopTimer(){
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
        is_enable.set(true);
        btn_verify_text.set("发送验证码");
    }
}
