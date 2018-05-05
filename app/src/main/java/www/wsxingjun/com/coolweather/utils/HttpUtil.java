package www.wsxingjun.com.coolweather.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @function:传入请求的地址，并注册回调处理服务器返回数据的响应；
 */

public class HttpUtil {
    public static void sendOkHttpRequset(String address, okhttp3.Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
