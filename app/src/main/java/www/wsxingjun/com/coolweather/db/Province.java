package www.wsxingjun.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @function:省级信息
 */

public class Province extends DataSupport {
    private int id; //每个实体类中都有具有的子段
    private String provinceName;  //省的名字
    private int provinceCode; //省的代号


    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String mName) {
        this.provinceName = mName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int mProvinceCode) {
        this.provinceCode = mProvinceCode;
    }




}
