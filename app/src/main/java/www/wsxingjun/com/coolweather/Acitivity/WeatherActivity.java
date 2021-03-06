package www.wsxingjun.com.coolweather.Acitivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import www.wsxingjun.com.coolweather.R;
import www.wsxingjun.com.coolweather.gson.Forecast;
import www.wsxingjun.com.coolweather.gson.Weather;
import www.wsxingjun.com.coolweather.service.AutoUpdateService;
import www.wsxingjun.com.coolweather.utils.HttpUtil;
import www.wsxingjun.com.coolweather.utils.JsonParseUtil;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView scroll_weather;
    private TextView tv_title_city;
    private TextView tv_update_time;
    private TextView tv_degreeText;
    private TextView tv_weatherInfo;
    private LinearLayout ll_forecast_layout;
    private TextView tv_aqi;
    private TextView tv_pm25;
    private TextView tv_confort;
    private TextView tv_carWash;
    private TextView tv_sport;
    private ImageView imge_everyDayImage;
    public SwipeRefreshLayout swiprefresh;

    private String mWeatherId;
    private Button nav_button;
    public DrawerLayout drawerLayout;
	
	//声明-------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置状态栏和APP融合效果
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.layout_weather);


//        initView();
        imge_everyDayImage = (ImageView) findViewById(R.id.imge_everyDayImage);

        nav_button = (Button) findViewById(R.id.nav_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        scroll_weather = (ScrollView) findViewById(R.id.scroll_weather);
        tv_title_city = (TextView) findViewById(R.id.tv_title_city);
        tv_update_time = (TextView) findViewById(R.id.tv_update_time);
        tv_degreeText = (TextView) findViewById(R.id.tv_degreeText);
        tv_weatherInfo = (TextView) findViewById(R.id.tv_weatherInfo);
        ll_forecast_layout = (LinearLayout) findViewById(R.id.ll_forecast_layout);
        tv_aqi = (TextView) findViewById(R.id.tv_aqi);
        tv_pm25 = (TextView) findViewById(R.id.tv_pm25);
        tv_confort = (TextView) findViewById(R.id.tv_confort);
        tv_carWash = (TextView) findViewById(R.id.tv_carWash);
        tv_sport = (TextView) findViewById(R.id.tv_sport);

        swiprefresh = (SwipeRefreshLayout) findViewById(R.id.swip_refresh);
        swiprefresh.setColorSchemeResources(R.color.colorPrimary);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sharedPreferences.getString("weather", null);

//        final String weatherId;
        if (weatherString != null) {
            //有缓存直接解析天气的数据；
            Weather weather = JsonParseUtil.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            //向服务器请求数据
            mWeatherId = getIntent().getStringExtra("weather_id");
            scroll_weather.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        swiprefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
                Toast.makeText(getApplicationContext(),"刷新了哈...",Toast.LENGTH_SHORT).show();
            }
        });
        String bing_pic = sharedPreferences.getString("bing_pic", null);
        if (bing_pic != null) {
            Glide.with(this)
                    .load(bing_pic)
                    .into(imge_everyDayImage);
        } else {
            loadBingPic();
        }

        nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    /**
     * 根据weatherId 请求城市天气信息
     *
     * @param weatherId
     */
    public void requestWeather(String weatherId) {

        String weatherUrl = "http://guolin.tech/api/weather?cityid=" +
                weatherId + "&key=738e39c4a2ab424bb45810777a275c17";
        HttpUtil.sendOkHttpRequset(weatherUrl, new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = JsonParseUtil.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager
                                    .getDefaultSharedPreferences(WeatherActivity.this)
                                    .edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气预报信息失败", Toast.LENGTH_SHORT).show();

                        }
                        swiprefresh.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气预报信息失败", Toast.LENGTH_SHORT).show();
                        swiprefresh.setRefreshing(false);
                    }
                });
            }

        });

        loadBingPic();
    }

    /**
     * 处理并展示Weather实体类的中的信息
     *
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degress = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;

        tv_title_city.setText(cityName);
        tv_update_time.setText(updateTime);
        tv_degreeText.setText(degress);
        tv_weatherInfo.setText(weatherInfo);
        ll_forecast_layout.removeAllViews();

        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_forecast, ll_forecast_layout, false);
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
            TextView tv_max = (TextView) view.findViewById(R.id.tv_max);
            TextView tv_min = (TextView) view.findViewById(R.id.tv_min);

            tv_date.setText(forecast.date);
            tv_info.setText(forecast.more.info);
            tv_max.setText(forecast.temperature.max);
            tv_min.setText(forecast.temperature.min);
            ll_forecast_layout.addView(view);


        }
        if (weather.aqi != null) {
            tv_aqi.setText(weather.aqi.city.aqi);
            tv_pm25.setText(weather.aqi.city.pm25);
        }

        String comfort = "舒适度：" + weather.suggestion.confort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运动建议：" + weather.suggestion.sport.info;

        tv_confort.setText(comfort);
        tv_carWash.setText(carWash);
        tv_sport.setText(sport);
        scroll_weather.setVisibility(View.VISIBLE);

        //开启服务器
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
        Log.d("AutoUpdateService","开启服务器");

    }


    private void loadBingPic() {
        final String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequset(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor
                        = PreferenceManager
                          .getDefaultSharedPreferences(WeatherActivity.this)
                          .edit();

                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this)
                                .load(bingPic)
                                .into(imge_everyDayImage);
                    }
                });
            }
        });
    }
}
