package www.wsxingjun.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @function:
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort confort;

    @SerializedName("cw")
    public CarWash carWash;


    public Sport sport;


    public class Comfort {
        @SerializedName("txt")
        public String info;
    }

    public class CarWash {
        @SerializedName("txt")
        public String info;
    }

    public class Sport {
        @SerializedName("txt")
        public String info;
    }
}
