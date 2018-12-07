package com.strongit.android.phone.oa.login.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.strongit.android.base.util.SystemPropertiesUtil;
import com.strongit.phone.oa.R;

/**
 * @desc:
 * @author: yangwb
 * @date: 2018/11/20 16:19
 */

public class CheckFingerDialog extends Dialog {


    private ImageView img_close;
    private ImageView img_finger;
    private Context context;
    public CheckFingerDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public CheckFingerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_finger_layout);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initUI();
    }
    private void initUI() {
        img_close = ((ImageView) findViewById(R.id.img_close));
        img_finger = ((ImageView) findViewById(R.id.img_finger));
        //获取当前设备的指纹是否是屏下指纹
        if (SystemPropertiesUtil.getBooleanPropert(context,"ro.hardware.fp.fod",false)){
            img_finger.setVisibility(View.INVISIBLE);
        }
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    //设置弹窗的大小和位置
    public void setAttr(){
        Window window=getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.verticalMargin=0.17f;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }
}
