package www.wsxingjun.com.coolweather.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import www.wsxingjun.com.coolweather.R;
import www.wsxingjun.com.coolweather.db.City;
import www.wsxingjun.com.coolweather.db.County;
import www.wsxingjun.com.coolweather.db.Province;
import www.wsxingjun.com.coolweather.utils.HttpUtil;
import www.wsxingjun.com.coolweather.utils.Instance;
import www.wsxingjun.com.coolweather.utils.JsonParseUtil;

/**
 * Created by Administrator on 2018-05-04.
 * @function:遍历全国的省市县；
 */

public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;
    public int currentLevel;

    private ProgressDialog progressDialog;

    private TextView tv_titile;
    private Button bt_back;
    private ListView lv_listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();


    //选中的省份、市、县
    private Province selectedProvince;
    private City selectedCity;


    //省份、市、县的列表
    private List<City> cityList;
    private List<County> countyList;
    private List<Province> provinceList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_choosecity, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, dataList);
        lv_listView.setAdapter(adapter);
    }

    private void initView(View view) {
        tv_titile = (TextView) view.findViewById(R.id.tv_titile);
        bt_back = (Button) view.findViewById(R.id.bt_back);
        lv_listView = (ListView) view.findViewById(R.id.lv_listView);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCountries();
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_COUNTRY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询所有的省份，如果数据库中有数据从数据库取，否则从服务器取；
     */
    private void queryProvinces() {
        tv_titile.setText("中国");
        bt_back.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        Log.d("wsxingjun",provinceList.toString());
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            Toast.makeText(getContext(), dataList.toString(),Toast.LENGTH_SHORT).show();
            //更新listView的数据的显示
            adapter.notifyDataSetChanged();
            lv_listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
//            String address = Instance.PROVINCE_ADDRESS;
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }

    }


    /**
     * 查询所有的市，如果数据库中有数据从数据库取，否则从服务器取；
     */
    private void queryCities() {
        tv_titile.setText(selectedProvince.getProvinceName());
        bt_back.setVisibility(View.VISIBLE);
        cityList = DataSupport
                .where("provinceid = ?", String.valueOf(selectedProvince.getId()))
                .find(City.class);

        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            Toast.makeText(getContext(), dataList.toString(),Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
            lv_listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
//            String address = Instance.PROVINCE_ADDRESS + provinceCode;
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }


    }

    /**
     * 查询所有的县，如果数据库中有数据从数据库取，否则从服务器取；
     */
    private void queryCountries() {
        tv_titile.setText(selectedCity.getCityName());
        bt_back.setVisibility(View.VISIBLE);
        countyList = DataSupport
                .where("cityid = ?", String.valueOf(selectedCity.getId()))
                .find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            Toast.makeText(getContext(), dataList.toString(),Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
            lv_listView.setSelection(0);
            currentLevel = LEVEL_COUNTRY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
//            String address =
//                    Instance.PROVINCE_ADDRESS + provinceCode
//                            + "/" + cityCode;
            String address =
                    "http://guolin.tech/api/china/"  + provinceCode
                            + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    /**
     * @param address
     * @param addressType
     * @function: 根据传入的地址和地方的等级向服务器查询天气的信息；
     */
    private void queryFromServer(final String address, final String addressType) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequset(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(addressType)){
                    result = JsonParseUtil.handleProvinceResponse(responseText);
                }else if("city".equals(addressType)){
                    result = JsonParseUtil.handleCityResponse(responseText,selectedProvince.getId());
                }else if("county".equals(addressType)){
                    result = JsonParseUtil.handleCountryResponse(responseText, selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(addressType)){
                                queryProvinces();
                            }else if ("city".equals(addressType)){
                                queryCities();
                            }else if ("county".equals(addressType)){
                                queryCountries();
                            }

                        }
                    });
                }

            }
        });


    }



    //显示进度对话框
    private void showProgressDialog() {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }

        progressDialog.show();
    }

    //关闭进度对话框
    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

}