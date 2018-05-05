package www.wsxingjun.com.coolweather.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import www.wsxingjun.com.coolweather.db.City;
import www.wsxingjun.com.coolweather.db.County;
import www.wsxingjun.com.coolweather.db.Province;

/**
 * Created by Administrator on 2018-05-04.
 */

public class JsonParseUtil {
    /**
     * Parse Province Json Data
     */

    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                int length = allProvinces.length();
                for (int i=0; i<length; i++){
                    JSONObject provincesJSONObject = allProvinces.getJSONObject(i);
                    //将数据取出存储到数据库中；
                    Province province = new Province();
                    province.setProvinceName(provincesJSONObject.getString("name"));
                    province.setProvinceCode(provincesJSONObject.getInt("id"));
                    province.save();// save province DataBase's data;
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * Parse City Json Data
     */

    public static boolean handleCityResponse(String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities = new JSONArray(response);
                int length = allCities.length();
                for (int i=0; i<length; i++){
                    JSONObject cityJsonObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(cityJsonObject.getInt("id"));
                    city.setCityName(cityJsonObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * Parse County Json Data
     */

    public static boolean handleCountryResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties = new JSONArray(response);
                int length = allCounties.length();
                for (int i=0; i<length; i++){
                    JSONObject countryJSONObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countryJSONObject.getString("name"));
                    county.setWeatherId(countryJSONObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();// save province DataBase's data;
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
