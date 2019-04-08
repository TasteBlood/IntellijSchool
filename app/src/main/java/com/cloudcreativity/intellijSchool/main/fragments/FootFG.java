package com.cloudcreativity.intellijSchool.main.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.LazyFragment;
import com.cloudcreativity.intellijSchool.databinding.LayoutFgFootBinding;
import com.cloudcreativity.intellijSchool.foot.AddFootActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class FootFG extends LazyFragment {

    private LayoutFgFootBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String msg){
        if(AddFootActivity.MSG_ADD_SUCCESS.equals(msg)){
            //刷新数据
            binding.refreshFoot.startRefresh();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fg_foot,container,false);
        binding.tlbFoot.inflateMenu(R.menu.foot_nav);
        binding.setFoot(new FootModal(binding,this,context));
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.tlbFoot);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.foot_nav,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                context.startActivity(new Intent(context,AddFootActivity.class));
                return true;
        }
        return true;
    }

    @Override
    public void initialLoadData() {

    }
}
