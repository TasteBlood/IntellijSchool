package com.cloudcreativity.intellijSchool.loginAndRegister;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseActivity;
import com.cloudcreativity.intellijSchool.databinding.ActivityForgetPwdBinding;

/**
 * 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity {
    private ForgetPwdModel model;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityForgetPwdBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_pwd);
        binding.tlbForget.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.setForgetModel(model = new ForgetPwdModel(this,this,binding));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.stopTimer();
    }
}
