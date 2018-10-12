package com.finger.yangwb.myfingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;


import java.lang.reflect.Method;
import java.util.List;

/**
 * @desc: 指纹登录工具类
 * @author: yangwb
 * @date: 2018/9/13 17:26
 */

public class FingerUtil {
    private Context context;
    private FingerprintManager manager;

    public FingerUtil(Context context) {
        this.context = context;
        initData();
    }
    private void initData(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            manager=(FingerprintManager)context.getSystemService(Context.FINGERPRINT_SERVICE);
            List list=getFingerInfo();
            if (list!=null && list.size()>1){//存在多个指纹
                Toast.makeText(context, "已存在多个指纹，指纹登录已取消", Toast.LENGTH_SHORT).show();
                return;
            }
            manager.authenticate(null,new CancellationSignal(),0,callback,null);
        }
    }
    private FingerprintManager.AuthenticationCallback callback=new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
//            Toast.makeText(context, errString.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Toast.makeText(context, "指纹识别成功", Toast.LENGTH_SHORT).show();
            //TODO 逻辑代码
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Toast.makeText(context, "指纹识别失败", Toast.LENGTH_SHORT).show();
        }
    };
    /**
     *  @describe: 获取指纹信息
     *  @author: yangwb
     *  @time: 2018/9/14  13:47
     *  @param:
     */
    private List getFingerInfo(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            try {
                Method method = FingerprintManager.class.getDeclaredMethod("getEnrolledFingerprints");
                Object obj=method.invoke(manager);
                if (obj !=null){
                    List list= ((List) obj);
                    return list;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }
}
