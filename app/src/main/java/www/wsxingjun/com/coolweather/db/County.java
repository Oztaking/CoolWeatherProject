package www.wsxingjun.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @function：县级信息
 */

public class County extends DataSupport{

    private int id;
    private String countyName;
    private String weatherId;
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String mCountyName) {
        this.countyName = mCountyName;
    }

    public String  getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String mWeatherId) {
        this.weatherId = mWeatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int mCityId) {
        this.cityId = mCityId;
    }


}
