package www.wsxingjun.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @function：市级信息
 */

public class City extends DataSupport {

    private int mId; //共有字段；
    private String mCityName; //市的名称
    private int mCityCode;  //市的代号
    private int mProvinceId; //当前市所属省的id


    public int getProvinceId() {
        return mProvinceId;
    }

    public void setProvinceId(int mProvinceId) {
        this.mProvinceId = mProvinceId;
    }


    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public int getCityCode() {
        return mCityCode;
    }

    public void setCityCode(int mCityCode) {
        this.mCityCode = mCityCode;
    }


}
