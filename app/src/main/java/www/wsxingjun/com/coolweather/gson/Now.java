package www.wsxingjun.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @function:
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
