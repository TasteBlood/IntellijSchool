package com.cloudcreativity.intellijSchool.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseActivity;
import com.cloudcreativity.intellijSchool.databinding.ActivitySettingBinding;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.setSetModel(new SettingModel(binding,this));
        binding.tlbSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
