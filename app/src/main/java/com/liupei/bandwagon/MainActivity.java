package com.liupei.bandwagon;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liupei.bandwagon.databinding.ActivityMainBinding;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getServiceInfo();
    }

    private void getServiceInfo() {
        OkGo.get(UrlConfig.API_URL + UrlConfig.GET_INFORMATION_ABOUT_SERVER)
                .params(UrlConfig.getParams())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e(TAG, s);
                        ServiceInfo info = new Gson().fromJson(s, ServiceInfo.class);
                        if (info.getPlan_monthly_data() >> 30 < 1) {
                            info.setPlan_monthly_data(info.getPlan_monthly_data() >> 20);
                            info.setUnit("MB");
                        } else {
                            info.setPlan_monthly_data(info.getPlan_monthly_data() >> 30);
                            info.setUnit("GB");
                        }
                        if (info.getData_counter() >> 30 < 1) {
                            info.setData_counter(info.getData_counter() >> 20);
                            info.setCountUnit("MB");
                        } else {
                            info.setData_counter(info.getData_counter() >> 30);
                            info.setCountUnit("GB");
                        }
                        info.setResetDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(info.getData_next_reset() * 1000)));
                        binding.setInfo(info);
                    }
                });
    }

    private void setServiceStatus(final String status) {
        Log.e(TAG, status);
        OkGo.post(UrlConfig.API_URL + status)
                .params(UrlConfig.getParams())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e(TAG, s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.has("error")) {
                                if (jsonObject.getInt("error")==0) {
                                    String toastContent="";
                                    switch (status) {
                                        case UrlConfig.START_SERVICE:
                                            toastContent = "启动成功";
                                            break;
                                        case UrlConfig.RESTART_SERVICE:
                                            toastContent = "重启成功";
                                            break;
                                        case UrlConfig.STOP_SERVICE:
                                            toastContent = "关机成功";
                                            break;
                                    }
                                    Toast.makeText(MainActivity.this, toastContent, Toast.LENGTH_SHORT).show();
                                    getServiceInfo();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                setServiceStatus(UrlConfig.RESTART_SERVICE);
                break;
            case R.id.shutdown:
                setServiceStatus(UrlConfig.STOP_SERVICE);
                break;
            case R.id.start:
                setServiceStatus(UrlConfig.START_SERVICE);
                break;
        }
        return true;
    }
}
