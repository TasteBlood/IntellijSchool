package com.cloudcreativity.intellijSchool.foot;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseActivity;
import com.cloudcreativity.intellijSchool.databinding.ActivityAddFootBinding;
import com.donkingliang.imageselector.utils.ImageSelector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Map;

public class AddFootActivity extends BaseActivity {

    private AddFootModel addFootModel;

    public static final String MSG_ADD_SUCCESS = "msg_add_foot_success";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ActivityAddFootBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_foot);
        binding.setAddFoot(addFootModel = new AddFootModel(this,this,binding));
        binding.tlbAddFoot.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK)
            return;
        if(requestCode==100&&data!=null){
            ArrayList<String> listExtra = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            addFootModel.adapter.update(listExtra);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void onEvent(Map<String,Object> data){
        if(data!=null){
            addFootModel.uploadSuccess(data);
        }
    }
}
