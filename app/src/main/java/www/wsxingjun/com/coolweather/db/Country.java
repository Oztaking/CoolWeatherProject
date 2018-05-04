package www.wsxingjun.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @function：县级信息
 */

public class Country extends DataSupport{

    private int mId;
    private String mCountyName;
    private int mWeatherId;
    private int mCountryCode;

    public int getmId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getCountyName() {
        return mCountyName;
    }

    public void setCountyName(String mCountyName) {
        this.mCountyName = mCountyName;
    }

    public int getWeatherId() {
        return mWeatherId;
    }

    public void setWeatherId(int mWeatherId) {
        this.mWeatherId = mWeatherId;
    }

    public int getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(int mCountryCode) {
        this.mCountryCode = mCountryCode;
    }


}
