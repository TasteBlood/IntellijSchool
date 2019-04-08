package com.cloudcreativity.intellijSchool.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.base.BaseDialogImpl;
import com.cloudcreativity.intellijSchool.databinding.LayoutCommonToOtherDialogBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommonToOtherDialogUtils {
    private Dialog dialog;

    public ObservableField<String> content = new ObservableField<>();

    private int pid;
    private int fromId;
    private int toId;
    private BaseDialogImpl baseDialog;
    private OnSuccessListener onSuccessListener;

    public void show(BaseDialogImpl baseDialog,Context context, int pid, int fromId, int toId, String who,OnSuccessListener successListener){
        this.baseDialog = baseDialog;
        this.pid = pid;
        this.fromId = fromId;
        this.toId = toId;
        this.onSuccessListener = successListener;
        dialog = new Dialog(context, R.style.myDialogStyleAnim);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        LayoutCommonToOtherDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_common_to_other_dialog,null,false);
        binding.setUtils(this);
        binding.tvReplyWho.setText("回复".concat(who));
        dialog.setContentView(binding.getRoot());
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        window.getAttributes().width = context.getResources().getDisplayMetrics().widthPixels;
        dialog.show();
    }

    public void onSend(){
        if(TextUtils.isEmpty(content.get())){
            return;
        }
        HttpUtils.getInstance().proComment(pid,fromId,toId,content.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        if(onSuccessListener!=null){
                            onSuccessListener.onSuccess(pid,fromId,toId,content.get());
                        }

                        dialog.dismiss();
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if(onSuccessListener!=null){
                            onSuccessListener.onFail();
                        }

                        dialog.dismiss();
                    }
                });

    }

    public interface OnSuccessListener{
        void onSuccess(int pid,int fromId,int toId,String content);
        void onFail();
    }
}
