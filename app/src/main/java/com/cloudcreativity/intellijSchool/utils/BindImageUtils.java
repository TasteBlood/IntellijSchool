package com.cloudcreativity.intellijSchool.utils;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.entity.UserEntity;

import java.io.File;

public class BindImageUtils {

    @BindingAdapter("avatar")
    public static void displayAvatar(ImageView imageView,String url){
        if(TextUtils.isEmpty(url))
            return;
        GlideUtils.loadCircle(imageView.getContext(),url,imageView);
    }

    @BindingAdapter("avatarWithSex")
    public static void displayAvatar(ImageView imageView, UserEntity userEntity){
        if(TextUtils.isEmpty(userEntity.getHeadPic())) {
            if("男".equals(userEntity.getGender())){
                GlideUtils.load(imageView.getContext(), R.mipmap.avatar_male,imageView);
            }else{
                GlideUtils.load(imageView.getContext(), R.mipmap.avatar_female,imageView);
            }
            return;
        }
        GlideUtils.loadCircle(imageView.getContext(),userEntity.getHeadPic(),imageView);
    }

    @BindingAdapter("path")
    public static void displayFile(ImageView imageView,String url){
        GlideUtils.load(imageView.getContext(),new File(url),imageView);
    }

    @BindingAdapter("url")
    public static void displayNet(ImageView imageView,String url){
        GlideUtils.load(imageView.getContext(),url,imageView);
    }

}
