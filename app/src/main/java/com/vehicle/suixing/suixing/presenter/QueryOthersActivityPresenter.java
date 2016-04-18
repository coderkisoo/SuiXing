package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.WeiZhang1.CheGuanJu;
import com.vehicle.suixing.suixing.bean.WeiZhang1.CheGuanName;
import com.vehicle.suixing.suixing.bean.WeiZhang1.CheguanjuError;
import com.vehicle.suixing.suixing.bean.WeiZhang1.City;
import com.vehicle.suixing.suixing.bean.WeiZhang1.Province;
import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;
import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangInfo;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.QueryOthersView;
import com.vehicle.suixing.suixing.ui.activity.PeccanydActivity;
import com.vehicle.suixing.suixing.ui.adapter.CityAdapter;
import com.vehicle.suixing.suixing.ui.adapter.ProvinceAdapter;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/4/9.
 */
public class QueryOthersActivityPresenter {
    private QueryOthersView view;
    private Context context;
    private String TAG = this.getClass().getName();
    private List<Province> provinces;
    private int provincePosition = -1;
    private boolean cityChoosed = false;
    private String URL = "http://api.jisuapi.com/illegal/query?appkey=";
    private List<City> cities;
    private CheGuanJu cheGuanJu;

    public QueryOthersActivityPresenter(Context context, QueryOthersView view) {
        this.view = view;
        this.context = context;
        provinces = new ArrayList<>();
        cities = new ArrayList<>();
        cheGuanJu = new CheGuanJu();
        initList();
    }

    private void initList() {
        Type type = new TypeToken<CheGuanName>() {
        }.getType();
        Gson gson = new Gson();
        CheGuanName name = gson.fromJson(Config.json, type);
        provinces = name.getResult().getData();
    }

    public void chooseProvince() {
        final PopupWindow window = new PopupWindow();
        window.setWidth(view.getWidth());
        window.setHeight(view.getHeight() * 8);
        View popupView = View.inflate(context, R.layout.popup_list, null);
        window.setContentView(popupView);
        ListView provinceList = (ListView) popupView.findViewById(R.id.lv_my_vehicle);
        provinceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                view.showProvince(provinces.get(position).getProvince());
                view.dismissWindow(window);

                cityChoosed = false;
                view.showCity("");

                provincePosition = position;
            }
        });
        ProvinceAdapter adapter = new ProvinceAdapter(provinces, context);
        provinceList.setAdapter(adapter);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setFocusable(true);
        view.showProvinceWindow(window);
    }


    public void chooseCity() {
        if (provincePosition == -1) {
            Toast.makeText(context, "你还没有选择省份...", Toast.LENGTH_SHORT).show();
            chooseProvince();
        }else {
            cities = provinces.get(provincePosition).getList();
            if (cities == null) {
                view.showCity(provinces.get(provincePosition).getProvince());
                cityChoosed = true;
                cheGuanJu.setCarorg(provinces.get(provincePosition).getCarorg());
                cheGuanJu.setEngineno(provinces.get(provincePosition).getEngineno());
                cheGuanJu.setFrameno(provinces.get(provincePosition).getFrameno());
                cheGuanJu.setLsprefix(provinces.get(provincePosition).getLsprefix());

                Log.e(TAG, "显示城市为" + provinces.get(provincePosition).getProvince());
            } else {
                final PopupWindow window = new PopupWindow();
                window.setWidth(view.getWidth());
                window.setHeight(view.getHeight() * 8);
                View popupView = View.inflate(context, R.layout.popup_list, null);
                window.setContentView(popupView);
                ListView provinceList = (ListView) popupView.findViewById(R.id.lv_my_vehicle);
                provinceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                        view.showCity(cities.get(position).getCity());
                        view.dismissWindow(window);
                        cityChoosed = true;
                        cheGuanJu.setCarorg(cities.get(position).getCarorg());
                        cheGuanJu.setEngineno(cities.get(position).getEngineno());
                        cheGuanJu.setFrameno(cities.get(position).getFrameno());
                        cheGuanJu.setLsprefix(cities.get(position).getLsprefix());
                    }
                });
                CityAdapter adapter = new CityAdapter(cities, context);
                provinceList.setAdapter(adapter);
                window.setBackgroundDrawable(new BitmapDrawable());
                window.setFocusable(true);
                view.showCityWindow(window);
            }
        }



    }


    public void query() {
        if (!cityChoosed) {
            Toast.makeText(context, "你还没有选择城市...", Toast.LENGTH_SHORT).show();
            chooseCity();
        } else {
            view.showProgress();
            OkHttpClient client = new OkHttpClient();
            String lsprefix = "";
            try {
                lsprefix = URLEncoder.encode(cheGuanJu.getLsprefix(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String getResult =
                    URL + Config.JISU_APPKEY +
                            "&carorg=" + cheGuanJu.getCarorg() +
                            "&lsprefix=" + lsprefix +
                            "&lsnum=" + view.getVehicleNum() +
                            "&frameno=" + view.getFrameNum() +
                            "&engineno=" + view.getEngineNum();
            Request request = new Request.Builder().url(getResult).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Toast.makeText(context, "请求失败，请检测网络", Toast.LENGTH_SHORT).show();
                    view.dismissProgress();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String response2body = response.body().string();
                    view.dismissProgress();
                    Log.e(TAG, response2body);

                    if (response2body.length() > 140) {
                        Gson gson3 = new Gson();
                        Type type3 = new TypeToken<WeizhangInfo>() {
                        }.getType();
                        WeizhangInfo result3 = gson3.fromJson(response2body, type3);
                        context.startActivity(new Intent(context, PeccanydActivity.class).putExtra("isLoad", true).putExtra("list", (Serializable) result3.getResult().getList()));
// info.requestSuccess(result3.getResult().getList());
                    } else if (response2body.length() < 60) {
                        Gson gson = new Gson();
                        Type type3 = new TypeToken<CheguanjuError>() {
                        }.getType();
                        CheguanjuError result = gson.fromJson(response2body, type3);
                        view.showError(result.getMsg());

                    } else {

                        List<WeizhangDate> infos = new ArrayList<WeizhangDate>();
                        context.startActivity(new Intent(context, PeccanydActivity.class).putExtra("isLoad", true).putExtra("list", (Serializable) infos));
                    }
                }
            });
        }
    }


}
