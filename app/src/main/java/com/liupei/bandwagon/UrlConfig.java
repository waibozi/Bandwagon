package com.liupei.bandwagon;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/11.
 */

public class UrlConfig {

    public static final String API_URL = "https://api.64clouds.com/v1/";

    /**
     * 获取服务器基本信息
     */
    public static final String GET_INFORMATION_ABOUT_SERVER = "getServiceInfo";

    /**
     * 重启
     */
    public static final String RESTART_SERVICE = "restart";

    /**
     * 启动
     */
    public static final String START_SERVICE = "start";

    /**
     * 停止
     */
    public static final String STOP_SERVICE = "stop";

    /**
     * 停止 会丢失数据，谨慎使用
     */
    public static final String KILL_SERVICE = "kill";

    /**
     * 获取可安装的操作系统
     */
    public static final String GET_AVAILABLE_OS = "getAvailableOS";

    /**
     * 重装系统 之前需要调用 GET_AVAILABLE_OS 获取系统列表
     */
    public static final String REINSTALL_OS = "reinstallOS";

    /**
     * 重置root账户密码
     */
    public static final String RESET_ROOT_PASSWORD = "resetRootPassword";

    /**
     * 获取限速状态
     */
    public static final String GET_RATE_LIMIT_STATUS = "getRateLimitStatus";

    public static HashMap<String, String> getParams() {
        HashMap<String, String> param = new HashMap<>();
        param.put("veid", getVeid());
        param.put("api_key", getApiKey());
        return param;
    }

    public static native String getVeid();

    public static native String getApiKey();

    static {
        System.loadLibrary("native-lib");
    }
}
