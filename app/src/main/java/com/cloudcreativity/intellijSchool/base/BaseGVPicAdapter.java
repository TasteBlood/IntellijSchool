package com.cloudcreativity.intellijSchool.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseGVPicAdapter extends BaseAdapter {
    private List<String> images;
    private Activity context;
    private float gridWidth;

    public BaseGVPicAdapter(Activity context) {
        this.context = context;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        //30 是从布局文件中计算的
        gridWidth = (metrics.widthPixels - 30 * metrics.density) / 3;
        this.images = new ArrayList<>();
    }

    List<String> getImages(){
        return this.images;
    }

    public void update(List<String> images) {
        this.images.clear();
        this.images.addAll(images);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public Object getItem(int position) {
        return this.images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.item_layout_img_extend, null);
        ImageView iv_pic = convertView.findViewById(R.id.iv_pic);
        //ImageView iv_delete = convertView.findViewById(R.id.iv_delete);
        GlideUtils.load(context, this.images.get(position),iv_pic);
        convertView.setLayoutParams(new AbsListView.LayoutParams((int) gridWidth, (int) gridWidth));
        return convertView;
    }

}
