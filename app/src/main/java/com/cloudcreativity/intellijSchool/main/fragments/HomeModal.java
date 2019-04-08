package com.cloudcreativity.intellijSchool.main.fragments;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.databinding.LayoutFgHomeBinding;
import com.cloudcreativity.intellijSchool.databinding.LayoutHomeItemBinding;
import com.cloudcreativity.intellijSchool.entity.HomeEntity;
import com.cloudcreativity.intellijSchool.entity.HomeTypeEntity;
import com.cloudcreativity.intellijSchool.utils.BannerImageLoader;
import com.cloudcreativity.intellijSchool.utils.DefaultObserverVideo;
import com.cloudcreativity.intellijSchool.utils.GsonUtils;
import com.cloudcreativity.intellijSchool.utils.HttpUtilsVideo;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeModal {
    private LayoutFgHomeBinding binding;
    private BaseDialogImpl baseDialog;
    public BaseBindingRecyclerViewAdapter<HomeEntity.CoursesBean,LayoutHomeItemBinding> adapter;
    private Activity context;
    private int page = 1;

    HomeModal(final LayoutFgHomeBinding binding, BaseDialogImpl baseDialog,Activity context) {
        this.binding = binding;
        this.baseDialog = baseDialog;
        this.context = context;

       /* binding.refreshHome.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.refreshHome.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
            }
        });*/

        initBanner();
        initTab();
    }

    private void initBanner(){
        List<String> bannerImages = new ArrayList<>();
        bannerImages.add("http://pic.rmb.bdstatic.com/ad5144d464ddb0ad54af350d9977e049.jpeg");
        bannerImages.add("http://5b0988e595225.cdn.sohucs.com/images/20171010/97c4aba7f7794650a32ea608ec234fe8.jpeg");
        bannerImages.add("http://pic.baike.soso.com/p/20131107/20131107111414-64857025.jpg");
        bannerImages.add("http://pic.baike.soso.com/p/20131108/20131108093451-81591137.jpg");
        List<String> bannerTitles = new ArrayList<>();
        bannerTitles.add("I am a title");
        bannerTitles.add("this is a good story");
        bannerTitles.add("I am family");
        bannerTitles.add("This is a chance");
        binding.bannerHome.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setBannerAnimation(Transformer.FlipHorizontal)
                .setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
    }
    private List<HomeTypeEntity.Disciplines> disciplines = new ArrayList<>();
    private List<HomeEntity.CoursesBean> coursesBeans = new ArrayList<>();
    /**
     * 课程分类
     * */
    private void initTab(){
        HttpUtilsVideo.getInstance().getType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserverVideo<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        HomeTypeEntity homeTypeEntity = GsonUtils.jsonToBean(t,HomeTypeEntity.class);
                        disciplines.addAll(homeTypeEntity.getResult().getData().getDisciplines());
                        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        for(int i=0;i<disciplines.size();i++){
                            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(disciplines.get(i).getName()));
                        }
                        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                typeList(disciplines.get(tab.getPosition()).getId(),page,10);
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        Log.e("err----",msg.toString());
                    }
                });

        adapter = new BaseBindingRecyclerViewAdapter<HomeEntity.CoursesBean, LayoutHomeItemBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_home_item;
            }

            @Override
            protected void onBindItem(LayoutHomeItemBinding binding, HomeEntity.CoursesBean item, int position) {
                binding.setItem(item);

            }
        };

        binding.recyclerView.setLayoutManager(new GridLayoutManager(context,2));

    }

    /**
     * 课程分类列表
     * */
    private void typeList(String id,int pages,int num) {
        HttpUtilsVideo.getInstance().getTypeList(id,pages,num,"total_click%"+num+"DESC")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserverVideo<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        Log.e("list---->",t);
                       /* if(page==1){
                            binding.refreshFoot.finishRefreshing();
                        }else{
                            binding.refreshFoot.finishLoadmore();
                        }*/
                        if(!TextUtils.isEmpty(t)){
                            HomeEntity wrapper = GsonUtils.jsonToBean(t,HomeEntity.class);
                            coursesBeans.addAll(wrapper.getResult().getData().getCourses());
                            if(wrapper.getResult().getData().getCourses()!=null&&wrapper.getResult().getData().getCourses().size()>0){

                                if(page==1){
                                    adapter.getItems().clear();
                                    adapter.getItems().addAll(coursesBeans);
                                }else{
                                    adapter.getItems().addAll(coursesBeans);
                                }
                                page ++;
                            }
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        Log.e("listError---->",msg.toString());
                        /*if(page==1){
                            binding.refreshFoot.finishRefreshing();
                        }else{
                            binding.refreshFoot.finishLoadmore();
                        }*/
                    }
                });
    }
}
