package com.cloudcreativity.intellijSchool.foot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.databinding.ActivityAddFootBinding;
import com.cloudcreativity.intellijSchool.utils.DefaultObserver;
import com.cloudcreativity.intellijSchool.utils.GlideUtils;
import com.cloudcreativity.intellijSchool.utils.HttpUtils;
import com.cloudcreativity.intellijSchool.utils.ToastUtils;
import com.cloudcreativity.intellijSchool.utils.UploadService;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.donkingliang.imageselector.utils.ImageSelector;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddFootModel {

    public MyPicAdapter adapter;
    private BaseDialogImpl baseDialog;
    private AddFootActivity context;
    private ActivityAddFootBinding binding;
    public ObservableField<String> content = new ObservableField<>();

    private String qiNiuToken;

    AddFootModel(BaseDialogImpl baseDialog, AddFootActivity context, ActivityAddFootBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.binding = binding;
        adapter = new MyPicAdapter(context);

        binding.gvAddFoot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==adapter.getCount()-1&&position<=8&&!(adapter.getImages().get(position) instanceof String)){
                    //这就说明这是最后一个
                    ArrayList<String> list = new ArrayList<>();
                    for(Object o : adapter.getImages()){
                        if(o instanceof String)
                            list.add(String.valueOf(o));
                    }
                    ImageSelector.builder()
                            .useCamera(true)
                            .setSingle(false)
                            .setMaxSelectCount(9)
                            .setSelected(list)
                            .start(AddFootModel.this.context,100);
                }else{
                    //这不是最后一个就执行啥
                    ArrayList<Image> images = new ArrayList<>();
                    for(Object path : adapter.getImages()){
                        if(path instanceof String)
                            images.add(new Image(String.valueOf(path),System.currentTimeMillis(),"","image/jpeg"));
                    }
                    PreviewActivity.openActivity(AddFootModel.this.context, images,
                            images, true, 9, position,false);
                }
            }
        });

        getQiNiuToken();
    }

    private void getQiNiuToken() {
        HttpUtils.getInstance().getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        qiNiuToken = t;
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        context.finish();
                    }
                });
    }


    public class MyPicAdapter extends BaseAdapter {
        private List<Object> images;
        private Activity context;
        private float gridWidth;

        MyPicAdapter(Activity context) {
            this.context = context;
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            //30 是从布局文件中计算的
            gridWidth = (metrics.widthPixels - 50 * metrics.density) / 4;
            this.images = new ArrayList<>();
            this.images.add(R.drawable.ic_add_img);
        }

        List<Object> getImages(){
            return this.images;
        }

        void update(ArrayList<String> images) {
            this.images.clear();
            this.images.addAll(images);
            if(this.images.size()<9){
                this.images.add(R.drawable.ic_add_img);
            }
            this.notifyDataSetChanged();
        }

        private void remove(int position){
            this.images.remove(position);
            List<Object> newImages = new ArrayList<>();
            for(Object o : this.images){
                if(o instanceof String)
                    newImages.add(o);
            }
            this.images.clear();
            this.images.addAll(newImages);
            this.images.add(R.drawable.ic_add_img);

            notifyDataSetChanged();
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
            convertView = mInflater.inflate(R.layout.item_layout_img_extend_delete, null);
            ImageView iv_pic = convertView.findViewById(R.id.iv_pic);
            ImageView iv_delete = convertView.findViewById(R.id.iv_delete);
            if (this.images.get(position) instanceof Integer) {
                iv_pic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_img));
            } else {
                GlideUtils.load(context, new File(String.valueOf(this.images.get(position))),iv_pic);
            }
            convertView.setLayoutParams(new AbsListView.LayoutParams((int) gridWidth, (int) gridWidth));
            if(position==images.size()-1&&images.size()<9){
                iv_delete.setVisibility(View.GONE);
            }else{
                //这是预览的接口
                if(images.get(position) instanceof Integer){
                    iv_delete.setVisibility(View.GONE);
                }else{
                    iv_delete.setVisibility(View.VISIBLE);
                }
            }
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShortToast(context,"删除了");
                    remove(position);
                }
            });
            return convertView;
        }

    }


    public void onSubmitClick(){

        //进行全部资料的提交
        if(TextUtils.isEmpty(content.get())){
            ToastUtils.showShortToast(context,"内容不能为空");
            return;
        }
        List<String> images = new ArrayList<>();
        for(Object o  : adapter.getImages()){
            if(o instanceof String)
                images.add(String.valueOf(o));
        }
        new UploadService(qiNiuToken,images,null,ProgressDialog.show(context,"处理文件中","请稍后",true)).execute();
    }


    //上传成功，进行其他资料的提交
    void uploadSuccess(Map<String,Object> data) {
        StringBuilder images = new StringBuilder();
        List<String> temp = (List<String>) data.get("images");
        for(String s : temp){
            images.append(s).append(";");
        }
        HttpUtils.getInstance().addFoot(content.get(),images.toString(),null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        ToastUtils.showShortToast(context,"发布成功");
                        EventBus.getDefault().post(AddFootActivity.MSG_ADD_SUCCESS);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                             context.finish();
                            }
                        },200);
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });


    }
}
