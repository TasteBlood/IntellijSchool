package com.cloudcreativity.intellijSchool.main;

import android.app.Activity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.databinding.ActivityMainBinding;

public class MainModel {
    private ActivityMainBinding binding;
    private Activity context;

    MainModel(ActivityMainBinding binding, Activity context) {
        this.binding = binding;
        this.context = context;


        binding.navMain.setMode(BottomNavigationBar.MODE_FIXED);
        binding.navMain.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //binding.navMain.setBarBackgroundColor(R.color.gray_f1f1f1);

        binding.navMain.addItem(new BottomNavigationItem(R.mipmap.home,"首页")).setActiveColor(R.color.colorPrimary).setInActiveColor(R.color.gray_717171);
        binding.navMain.addItem(new BottomNavigationItem(R.mipmap.foot,"足迹")).setActiveColor(R.color.colorPrimary).setInActiveColor(R.color.gray_717171);
        binding.navMain.addItem(new BottomNavigationItem(R.mipmap.mine,"我的")).setActiveColor(R.color.colorPrimary).setInActiveColor(R.color.gray_717171);

        binding.navMain.initialise();

    }

}
