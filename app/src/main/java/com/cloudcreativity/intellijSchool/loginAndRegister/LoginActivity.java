package com.cloudcreativity.intellijSchool.loginAndRegister;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseActivity;
import com.cloudcreativity.intellijSchool.databinding.ActivityLoginBinding;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLoginModel(new LoginModel(binding,this));
        setSupportActionBar(binding.tlbLogin);
        binding.tlbLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
