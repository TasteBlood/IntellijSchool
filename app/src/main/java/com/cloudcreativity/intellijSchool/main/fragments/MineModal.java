package com.cloudcreativity.intellijSchool.main.fragments;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.databinding.LayoutFgMineBinding;
import com.cloudcreativity.intellijSchool.entity.UserEntity;
import com.cloudcreativity.intellijSchool.main.MineInfoActivity;
import com.cloudcreativity.intellijSchool.main.SettingActivity;
import com.cloudcreativity.intellijSchool.utils.ToastUtils;

public class MineModal{
    private LayoutFgMineBinding binding;
    private BaseDialogImpl baseDialog;
    public UserEntity userEntity;
    private Activity context;

    MineModal(LayoutFgMineBinding binding, BaseDialogImpl baseDialog, final Activity context,UserEntity userEntity) {
        this.binding = binding;
        this.baseDialog = baseDialog;
        this.userEntity = userEntity;
        this.context = context;
        this.binding.tlbMine.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SettingActivity.class));
            }
        });
    }

    public void onMineClick(){
        context.startActivity(new Intent().setClass(context,MineInfoActivity.class));
    }

    public void onClassClick(){
        ToastUtils.showShortToast(context,"该功能正在开发中");
    }

    public void onFootClick(){
        ToastUtils.showShortToast(context,"该功能正在开发中");
    }
}
