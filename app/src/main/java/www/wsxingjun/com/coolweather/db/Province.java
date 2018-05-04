package www.wsxingjun.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @function:省级信息
 */

public class Province extends DataSupport {
    private int mId; //每个实体类中都有具有的子段
    private String mName;  //省的名字
    private int mProvinceCode; //省的代号


    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getProvinceCode() {
        return mProvinceCode;
    }

    public void setProvinceCode(int mProvinceCode) {
        this.mProvinceCode = mProvinceCode;
    }




}
