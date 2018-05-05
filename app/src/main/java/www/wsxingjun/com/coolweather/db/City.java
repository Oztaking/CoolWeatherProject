package www.wsxingjun.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @function：市级信息
 */

public class City extends DataSupport {

    private int id; //共有字段；
    private String cityName; //市的名称
    private int cityCode;  //市的代号
    private int provinceId; //当前市所属省的id


    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int mProvinceId) {
        this.provinceId = mProvinceId;
    }


    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String mCityName) {
        this.cityName = mCityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int mCityCode) {
        this.cityCode = mCityCode;
    }


}
