package com.cloudcreativity.intellijSchool.main.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.LazyFragment;
import com.cloudcreativity.intellijSchool.databinding.LayoutFgMineBinding;
import com.cloudcreativity.intellijSchool.entity.UserEntity;
import com.cloudcreativity.intellijSchool.utils.SPUtils;

public class MineFG extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutFgMineBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_fg_mine,container,false);
        UserEntity userEntity = SPUtils.get().getUser();
        binding.setMine(new MineModal(binding,this,context,userEntity));
        binding.ivGender.setImageResource(userEntity.getGender().equals("男")?R.mipmap.male:R.mipmap.female);
        return binding.getRoot();
    }

    @Override
    public void initialLoadData() {

    }
}
