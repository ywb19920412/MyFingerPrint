package com.finger.yangwb.myfingerprint;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button finger_switch;
    private FingerprintManager fingerprintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finger_switch = ((Button) findViewById(R.id.finger_switch));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        }
        finger_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!fingerprintManager.isHardwareDetected()){//设备是否支持指纹功能
                        Toast.makeText(MainActivity.this, "设备不支持指纹功能", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!fingerprintManager.hasEnrolledFingerprints()) {//设备是否已经录入指纹
                        Toast.makeText(MainActivity.this, "您还未录入指纹，前往系统设置页面录入", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                        return;
                    }
                    List list=getFingerInfo();
                    if (list!=null && list.size()>1){//存在多个指纹
                        Toast.makeText(MainActivity.this, "已存在多个指纹，开启指纹功能失败！", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "开启指纹功能成功！", Toast.LENGTH_SHORT).show();
                        new FingerUtil(MainActivity.this);
                    }
                }
            }
        });
    }
    /**
     *  @describe: 获取指纹信息
     *  @author: yangwb
     *  @time: 2018/9/14  13:46
     *  @param:
     */
    private List getFingerInfo(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            try {
                Method method = FingerprintManager.class.getDeclaredMethod("getEnrolledFingerprints");
                Object obj=method.invoke(fingerprintManager);
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
