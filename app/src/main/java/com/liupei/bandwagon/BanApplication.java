package com.liupei.bandwagon;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by Administrator on 2017/6/11.
 */

public class BanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.init(this);
        OkGo.getInstance().setCertificates();
    }
}
