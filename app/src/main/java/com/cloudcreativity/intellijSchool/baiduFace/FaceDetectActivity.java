package com.cloudcreativity.intellijSchool.baiduFace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.cloudcreativity.intellijSchool.base.BaseApp;

import java.util.HashMap;

public class FaceDetectActivity extends FaceLivenessActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        BaseApp.app.addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLivenessCompletion(com.baidu.idl.face.platform.FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        Intent intent = new Intent();
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            //showMessageDialog("活体检测", "检测成功");
            //获取到bestImage0 这是最佳的图片，只需要把这个图片上传到服务器上面就可以进行使用
            String base64data = base64ImageMap.get("bestImage0");
            Log.e("xuxiwu",base64data);
            intent.putExtra("image",base64data);
//            byte bytes[] = Base64.decode(base64data,Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            ImageView imageView = new ImageView(this);
//            imageView.setLayoutParams(new RadioGroup.LayoutParams(200,200));
//            imageView.setImageBitmap(bitmap);
//            new AlertDialog.Builder(this).setView(imageView).show();
            setResult(RESULT_OK,intent);
            finish();
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
//            ToastUtils.showShortToast(this,"人脸采集失败");
            intent.putExtra("image","");
            setResult(RESULT_OK,intent);
            finish();
        }

    }

    @Override
    public void finish() {
        BaseApp.app.removeActivity(this);
        super.finish();
    }
}
