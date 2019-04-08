package com.cloudcreativity.intellijSchool.loginAndRegister;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseActivity;
import com.cloudcreativity.intellijSchool.databinding.ActivityFillInfoBinding;

/**
 * 注册完善资料页面
 */
public class FillInfoActivity extends BaseActivity {

    private FillInfoModel fillInfoModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFillInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_fill_info);
        binding.setInfoModel(fillInfoModel = new FillInfoModel(this,getIntent().getIntExtra("uid",0)));
        binding.tlbFill.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPhotoSuccess(String filePath) {
        fillInfoModel.onPhoto(filePath);
    }
}
