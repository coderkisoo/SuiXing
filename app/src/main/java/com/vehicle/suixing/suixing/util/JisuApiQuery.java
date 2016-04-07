package com.vehicle.suixing.suixing.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vehicle.suixing.suixing.bean.WeiZhang1.CheGuanJu;
import com.vehicle.suixing.suixing.bean.WeiZhang1.CheGuanName;
import com.vehicle.suixing.suixing.bean.WeiZhang1.City;
import com.vehicle.suixing.suixing.bean.WeiZhang1.Province;
import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;
import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangInfo;
import com.vehicle.suixing.suixing.callback.GetWeizhangInfo;
import com.vehicle.suixing.suixing.common.Config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by KiSoo on 2016/4/6.
 */
public class JisuApiQuery {
    private GetWeizhangInfo info;
    private String firstLetter = "";
    private String secondLetter = "";
    private String TAG = "JisuApiQuery";
    private String cheguanUrl = "http://api.jisuapi.com/illegal/carorg?appkey=";
    private String URL = "http://api.jisuapi.com/illegal/query?appkey=";
    private Context context;
    private String lsnum = "";
    private String frameNum = "";
    private String engineNo = "";

    private void getCheguanju() {
        getCheGuanJu(cheguanUrl + Config.JISU_APPKEY);
    }

    public JisuApiQuery(Context context) {
        this.context = context;
    }

    public void query(final String vehiclenum, String frameNum, String model, GetWeizhangInfo call) {
        firstLetter = vehiclenum.substring(0, 1);
        secondLetter = vehiclenum.substring(1, 2);
        lsnum = vehiclenum.substring(1, 7);

        this.frameNum = frameNum;
        this.engineNo = model;
        info = call;
        Log.e(TAG, "vehiclenum" + vehiclenum + "engineNo" + model + "frameNum" + frameNum);
        getCheguanju();
    }

    private void getCheGuanJu(final String url) {
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(url).build();
        Log.v(TAG, "请求的数据为" + url);
        com.squareup.okhttp.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, request.toString());
                info.requestDefault("请求失败,请检查网络");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String response1body = response.body().string();
                Type type = new TypeToken<CheGuanName>() {
                }.getType();
                Gson gson = new Gson();
                CheGuanName name = gson.fromJson(response1body, type);
                Log.e(TAG, "json1为" + response1body);
                CheGuanJu cheGuanJu = getCheguanjuInfo(name);
                if (null == cheGuanJu) {
                    Log.e(TAG, "chegaunju为null");
                } else {
                    String lsprefix = "";
                    try {
                        lsprefix = URLEncoder.encode(firstLetter, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    int bit = 0;
                    if (cheGuanJu.getFrameno().length() > 0) {
                        bit = Integer.valueOf(cheGuanJu.getFrameno());
                        if (bit == 100)
                            bit = 17;
                    }
//                    int bit2 = 7;
//                    if (cheGuanJu.getEngineno().length() > 0) {
//                        bit2 = Integer.valueOf(cheGuanJu.getEngineno());
//                        if (bit2 == 100)
//                            bit2 = 7;
//                    }
                    String m = frameNum.substring(17 - bit, 17);
//                    String n = engineNo.substring(7 - bit2, 7);
                    String getResult =
                            URL + Config.JISU_APPKEY +
                                    "&carorg=" + cheGuanJu.getCarorg() +
                                    "&lsprefix=" + lsprefix +
                                    "&lsnum=" + lsnum +
                                    "&frameno=" + m +
                                    "&engineno=" + engineNo;
                    final Request request2 = new Request.Builder().url(getResult).build();
                    Log.v(TAG, "请求的数据为" + getResult);
                    com.squareup.okhttp.Call call2 = client.newCall(request2);
                    call2.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Log.e(TAG, "第二次请求失败");
                            info.requestDefault("请求失败,请检查网络");
                        }

                        @Override
                        public void onResponse(Response response2) throws IOException {
                            String response2body = response2.body().string();
                            if (response2body.length() > 140) {
                                Gson gson3 = new Gson();
                                Type type3 = new TypeToken<WeizhangInfo>() {
                                }.getType();
                                WeizhangInfo result3 = gson3.fromJson(response2body, type3);
                                info.requestSuccess(result3.getResult().getList());
                            } else {
                                List<WeizhangDate> infos = new ArrayList<WeizhangDate>();
                                info.requestSuccess(infos);
                            }
                        }
                    });
                }
            }
        });

    }

    /**
     * 获取车管局的信息
     */

    private CheGuanJu getCheguanjuInfo(CheGuanName name) {
        if (firstLetter.equals("京")) {
            CheGuanJu cheGuanJu = new CheGuanJu();
            //给车管局设置参数
            cheGuanJu.setCarorg("beijing");
            cheGuanJu.setEngineno("0");
            cheGuanJu.setFrameno("100");
            return cheGuanJu;
        }
        Iterator<Province> provinceIterator = name.getResult().getData().iterator();
        while (provinceIterator.hasNext()) {
            Province province = provinceIterator.next();
            if (province.getLsprefix().equals(firstLetter)){
                Iterator<City> cityIterator = province.getList().iterator();
                while (cityIterator.hasNext()){
                    City city = cityIterator.next();
                    if (secondLetter.equals(city.getLsnum())){
                        CheGuanJu cheGuanJu = new CheGuanJu();
                        cheGuanJu.setCarorg(city.getCarorg());
                        cheGuanJu.setFrameno(city.getFrameno());
                        cheGuanJu.setEngineno(city.getEngineno());
                        return cheGuanJu;
                    }
                }
            }
        }

//        int cursorSize = 0;
//        while (cursorSize < name.getResult().getData().size()) {
//            int citySize = 0;
//            if (name.getResult().getData().get(cursorSize).getLsprefix().equals(firstLetter))
//                while (citySize < name.getResult().getData().get(cursorSize).getList().size()) {
//                    if (name.getResult().getData().get(cursorSize).getList().get(citySize).getLsnum().equals(secondLetter)) {
//                        Log.e(TAG, "设置成功" + name.getResult().getData().get(cursorSize).getLsprefix());
//                        Log.e(TAG, "设置成功" + name.getResult().getData().get(cursorSize).getList().get(citySize).getLsnum());
//                        CheGuanJu cheGuanJu = new CheGuanJu();
//                        //给车管局设置参数
//                        cheGuanJu.setCarorg(name.getResult().getData().get(cursorSize).getList().get(citySize).getCarorg());
//                        cheGuanJu.setEngineno(name.getResult().getData().get(cursorSize).getList().get(citySize).getEngineno());
//                        cheGuanJu.setFrameno(name.getResult().getData().get(cursorSize).getList().get(citySize).getFrameno());
//                        return cheGuanJu;
//                    }
//                    citySize++;
//                    Log.e(TAG, "内层" + citySize);
//                }
//            cursorSize++;
//            Log.e(TAG, cursorSize + "外层");
//        }
        return null;
    }

}
