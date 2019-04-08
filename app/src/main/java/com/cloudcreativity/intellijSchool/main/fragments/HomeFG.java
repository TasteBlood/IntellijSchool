package com.cloudcreativity.intellijSchool.main.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.LazyFragment;
import com.cloudcreativity.intellijSchool.databinding.LayoutFgHomeBinding;

public class HomeFG extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutFgHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_fg_home,container,false);
        binding.tlbHome.inflateMenu(R.menu.main_nav);
        binding.setHome(new HomeModal(binding,this,getActivity()));
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_nav,menu);
    }

    @Override
    public void initialLoadData() {

    }
}
