package www.wsxingjun.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @function:
 */

public class Weather {
    public String status; //成功返回：ok；失败返回原因；
    public Basic basic;
    public AQI aqi;
    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
