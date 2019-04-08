package com.cloudcreativity.intellijSchool.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.databinding.LayoutDialogBottomMenuBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BottomDialogUtils {

    private Dialog dialog;

    public ObservableField<String> content = new ObservableField<>();

    public ObservableField<Boolean> canDel = new ObservableField<>();

    private BaseDialogImpl baseDialog;
    private int pid;
    private OnSuccessListener onSuccessListener;
    private Activity context;
    public void show(BaseDialogImpl baseDialog , Activity context,boolean del,int pid,OnSuccessListener successListener){
        this.canDel.set(del);
        this.baseDialog = baseDialog;
        this.pid = pid;
        this.context = context;
        this.onSuccessListener = successListener;
        dialog = new Dialog(context, R.style.myDialogStyleAnim);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        LayoutDialogBottomMenuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_dialog_bottom_menu,null,false);
        binding.setUtils(this);
        dialog.setContentView(binding.getRoot());
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        window.getAttributes().width = context.getResources().getDisplayMetrics().widthPixels;
        dialog.show();
    }

    public void onDismiss(){
        dialog.dismiss();
    }

    public void onShare(){
        dialog.dismiss();
    }

    public void onDelete(){
        HttpUtils.getInstance().deleteFoot(pid,-1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        dialog.dismiss();
                        if(onSuccessListener!=null){
                            onSuccessListener.onDelete();
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        dialog.dismiss();
                    }
                });
    }

    public void onPass(){
        dialog.dismiss();
        final CharSequence[] titles = {"诽谤、谩骂他人","色情淫秽","政治敏感话题","侵权、危害他人","其他"};
        new AlertDialog.Builder(context)
                .setTitle("举报类型")
                .setSingleChoiceItems(titles, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        content.set((String) titles[which]);
                    }
                }).setPositiveButton("确定举报", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        HttpUtils.getInstance().reportFoot(pid,content.get())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                                    @Override
                                    public void onSuccess(String t) {
                                        ToastUtils.showShortToast(context,t);
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFail(ExceptionReason msg) {

                                    }
                                });

                    }
                }).show();
    }

    public interface OnSuccessListener{
        void onDelete();
    }
}
