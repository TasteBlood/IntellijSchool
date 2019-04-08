package com.cloudcreativity.intellijSchool.main.fragments;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijSchool.base.BaseCommonAdapter;
import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.base.BaseGVPicAdapter;
import com.cloudcreativity.intellijSchool.databinding.LayoutFgFootBinding;
import com.cloudcreativity.intellijSchool.databinding.LayoutItemFootBinding;
import com.cloudcreativity.intellijSchool.entity.CommonEntity;
import com.cloudcreativity.intellijSchool.entity.FootEntity;
import com.cloudcreativity.intellijSchool.entity.FootEntityWrapper;
import com.cloudcreativity.intellijSchool.utils.BottomDialogUtils;
import com.cloudcreativity.intellijSchool.utils.CommonDialogUtils;
import com.cloudcreativity.intellijSchool.utils.CommonToOtherDialogUtils;
import com.cloudcreativity.intellijSchool.utils.DefaultObserver;
import com.cloudcreativity.intellijSchool.utils.HttpUtils;
import com.cloudcreativity.intellijSchool.utils.LogUtils;
import com.cloudcreativity.intellijSchool.utils.SPUtils;
import com.cloudcreativity.intellijSchool.utils.ToastUtils;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FootModal {
    private LayoutFgFootBinding binding;
    private BaseDialogImpl baseDialog;
    private Activity context;
    public BaseBindingRecyclerViewAdapter<FootEntity,LayoutItemFootBinding> adapter;
    private int pageNum=1;

    FootModal(final LayoutFgFootBinding binding, final BaseDialogImpl baseDialog, Activity context) {
        this.binding = binding;
        this.baseDialog = baseDialog;
        this.context = context;
        this.binding.refreshFoot.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                loadData(pageNum);
            }
        });

        this.binding.refreshFoot.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.refreshFoot.startRefresh();
            }
        },200);

        adapter = new BaseBindingRecyclerViewAdapter<FootEntity, LayoutItemFootBinding>(this.context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_item_foot;
            }

            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                super.onViewRecycled(holder);
            }

            @Override
            protected void onBindItem(LayoutItemFootBinding binding, final FootEntity item, final int position) {
                binding.setItem(item);
                binding.ivGender.setImageResource(item.getUserDomain().getGender().equals("男")?R.mipmap.male:R.mipmap.female);
                binding.ivLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpUtils.getInstance().proZan(item.getPid())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                                    @Override
                                    public void onSuccess(String t) {
                                        ToastUtils.showShortToast(context,t);
                                    }

                                    @Override
                                    public void onFail(ExceptionReason msg) {

                                    }
                                });
                    }
                });

                binding.ivPing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CommonDialogUtils().show(baseDialog, FootModal.this.context, item.getPid(), SPUtils.get().getUid(), 0, new CommonDialogUtils.OnSuccessListener() {
                            @Override
                            public void onSuccess(int pid, int fromId, int toId, String content) {
                                //添加成功，更新数据
                                CommonEntity entity = new CommonEntity();
                                entity.setCommonContent(content);
                                entity.setFromId(fromId);
                                entity.setFromUserName(SPUtils.get().getUser().getRealName());
                                item.getProCommentDomainList().add(entity);
                                adapter.getItems().set(position,item);
                            }

                            @Override
                            public void onFail() {

                            }
                        });
                    }
                });
                //点击更多
                binding.ibMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean canDel = false;
                        if(item.getUid()==SPUtils.get().getUid())
                            canDel = true;
                        new BottomDialogUtils().show(baseDialog, FootModal.this.context, canDel, item.getPid(), new BottomDialogUtils.OnSuccessListener() {
                            @Override
                            public void onDelete() {
                                adapter.getItems().remove(position);
                            }
                        });
                    }
                });

                //处理图片显示
                if(TextUtils.isEmpty(item.getPicture())){
                    binding.gvItem.setAdapter(null);
                    binding.gvItem.setVisibility(View.GONE);
                    binding.gvItem.setOnItemClickListener(null);
                }else{
                    String[] images = item.getPicture().split(";");
                    final List<String> list;
                    list = Arrays.asList(images);
                    BaseGVPicAdapter picAdapter = new BaseGVPicAdapter(FootModal.this.context);
                    picAdapter.update(list);
                    binding.gvItem.setAdapter(picAdapter);
                    binding.gvItem.setVisibility(View.VISIBLE);
                    binding.gvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ArrayList<Image> images = new ArrayList<>();
                            for(Object path : list){
                                if(path instanceof String)
                                    images.add(new Image(String.valueOf(path),System.currentTimeMillis(),"","image/jpeg"));
                            }
                            PreviewActivity.openActivity(FootModal.this.context, images,
                                    images, true, 9, position,false);
                        }
                    });
                }

                //处理评论列表
                if(item.getProCommentDomainList()!=null&&item.getProCommentDomainList().size()>0){
                    binding.lvItem.setVisibility(View.VISIBLE);
                    binding.lvItem.setAdapter(new BaseCommonAdapter(item.getProCommentDomainList(),context));
                    binding.lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,final int pos, long id) {
                            LogUtils.e("xuxiwu","click item once="+pos);
                            if(SPUtils.get().getUid()==item.getProCommentDomainList().get(pos).getFromId()){
                                //同一用户不可互评
                                return;
                            }
                            new CommonToOtherDialogUtils().show(baseDialog, context, item.getPid(), SPUtils.get().getUid(), item.getProCommentDomainList().get(pos).getFromId(),
                                    item.getProCommentDomainList().get(pos).getFromUserName(), new CommonToOtherDialogUtils.OnSuccessListener() {
                                        @Override
                                        public void onSuccess(int pid, int fromId, int toId, String content) {
                                            //评论成功
                                            CommonEntity commonEntity = new CommonEntity();
                                            commonEntity.setFromId(fromId);
                                            commonEntity.setFromUserName(SPUtils.get().getUser().getRealName());
                                            commonEntity.setToId(toId);
                                            commonEntity.setToUserName(item.getProCommentDomainList().get(pos).getFromUserName());
                                            commonEntity.setCommonContent(content);

                                            item.getProCommentDomainList().add(commonEntity);

                                            adapter.getItems().set(position,item);
                                        }

                                        @Override
                                        public void onFail() {

                                        }
                                    });
                        }
                    });

                }else{
                    binding.lvItem.setVisibility(View.GONE);
                    binding.lvItem.setAdapter(null);
                    binding.lvItem.setOnItemClickListener(null);
                }
            }
        };

        binding.rcvFoot.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(this.context.getResources().getDrawable(R.drawable.divider_10dp));
        binding.rcvFoot.addItemDecoration(itemDecoration);
    }

    private void loadData(final int page){
        HttpUtils.getInstance().getFeet(page,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        if(page==1){
                            binding.refreshFoot.finishRefreshing();
                        }else{
                            binding.refreshFoot.finishLoadmore();
                        }
                        if(!TextUtils.isEmpty(t)){
                            FootEntityWrapper wrapper = new Gson().fromJson(t,FootEntityWrapper.class);
                            if(wrapper.getData()!=null&&wrapper.getData().size()>0){
                                if(page==1){
                                    adapter.getItems().clear();
                                    adapter.getItems().addAll(wrapper.getData());
                                }else{
                                    adapter.getItems().addAll(wrapper.getData());
                                }
                                pageNum ++;
                            }
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if(pageNum==1){
                            binding.refreshFoot.finishRefreshing();
                        }else{
                            binding.refreshFoot.finishLoadmore();
                        }
                    }
                });
    }
}
