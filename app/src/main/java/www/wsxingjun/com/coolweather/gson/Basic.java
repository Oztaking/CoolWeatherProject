package www.wsxingjun.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-05-05.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;  //城市的名称

    @SerializedName("id")
    public String weatherId; //城市对应的 天气id；

    public Update update;

    public class Update{
        @SerializedName("loc") //天气的更新时间
        public String updateTime;
    }
}
