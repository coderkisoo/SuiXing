package com.vehicle.suixing.suixing.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vehicle.suixing.suixing.bean.WeiZhang1.CheGuanJu;
import com.vehicle.suixing.suixing.bean.WeiZhang1.CheGuanName;
import com.vehicle.suixing.suixing.bean.WeiZhang1.CheguanjuError;
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
import java.util.List;

/**
 * Created by KiSoo on 2016/4/6.
 */
public class JisuApiQuery {
    private String TAG = "JisuApiQuery";

    private GetWeizhangInfo info;
    /**
     * 通过传入车的前两个字母来判断地区
     */
    private String firstLetter = "";
    private String secondLetter = "";
    /*请求的URL*/
    private String URL = "http://api.jisuapi.com/illegal/query?appkey=";
    /*车管局的参数*/
    private String lsnum = "";
    private String frameNum = "";
    private String engineNo = "";

    /**
     * @param vehiclenum 车牌号
     * @param frameNum 车架号
     * @param model 引擎号
     * @param call 获得违章信息后的回调
     * @link GetWeizhangInfo
     * */
    public void query(final String vehiclenum, String frameNum, String model, GetWeizhangInfo call) {
        firstLetter = vehiclenum.substring(0, 1);
        secondLetter = vehiclenum.substring(1, 2);
        lsnum = vehiclenum.substring(1, 7);
        this.frameNum = frameNum;
        this.engineNo = model;
        info = call;
        getCheGuanJu();
    }

    /**
     * 将本地存储的json数据做解析，来获取车管局的信息
     */
    private void getCheGuanJu() {
        final OkHttpClient client = new OkHttpClient();
        Type type = new TypeToken<CheGuanName>() {
        }.getType();
        Gson gson = new Gson();
        CheGuanName name = gson.fromJson(Config.json, type);
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

            /*车架号，只需要截取后几位*/
            String m = frameNum.substring(17 - bit, 17);
            /*发出请求*/
            String GET_RESULT =
                    URL + Config.JISU_APPKEY +
                            "&carorg=" + cheGuanJu.getCarorg() +
                            "&lsprefix=" + lsprefix +
                            "&lsnum=" + lsnum +
                            "&frameno=" + m +
                            "&engineno=" + engineNo;

            final Request request2 = new Request.Builder().url(GET_RESULT).build();
            com.squareup.okhttp.Call call2 = client.newCall(request2);
            Log.v(TAG, "请求的数据为" + GET_RESULT);

            /*拿到返回的数据，做处理*/
            call2.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    /*请求失败*/
                    info.requestFail("请求失败,请检查网络");
                }

                @Override
                public void onResponse(Response response2) throws IOException {
                    String response2body = response2.body().string();
                    Log.e(TAG, response2body);
                    /*解析请求返回的数据,如果字符长度大于140，则说明本次请求得到了违章信息*/
                    if (response2body.length() > 140) {
                        Gson gson3 = new Gson();
                        Type type3 = new TypeToken<WeizhangInfo>() {
                        }.getType();
                        WeizhangInfo result3 = gson3.fromJson(response2body, type3);
                        info.requestSuccess(result3.getResult().getList());
                    } else {
                        /*如果解析小于140，则说明本次请求没有得到信息*/
                        Gson gson = new Gson();
                        Type type3 = new TypeToken<CheguanjuError>() {
                        }.getType();
                        CheguanjuError result = gson.fromJson(response2body, type3);
                       /*如果status为0，则说明没有违章*/
                        if (result.getStatus().equals("0")) {
                            List<WeizhangDate> infos = new ArrayList<WeizhangDate>();
                            info.requestSuccess(infos);
                        } else {
                            info.showText(result.getMsg());
                        }

                    }
                }
            });
        }
    }

    /**
     * 获取车管局的信息
     */

    private CheGuanJu getCheguanjuInfo(CheGuanName name) {
        /*由于给定的json字符串的问题，需要对'京'和'渝'作单独的处理*/
        if (firstLetter.equals("京")) {
            CheGuanJu cheGuanJu = new CheGuanJu();
            //给车管局设置参数
            cheGuanJu.setCarorg("beijing");
            cheGuanJu.setEngineno("0");
            cheGuanJu.setFrameno("100");
            return cheGuanJu;
        } else if (firstLetter.equals("渝")) {
            CheGuanJu cheGuanJu = new CheGuanJu();
            cheGuanJu.setCarorg("chongqing");
            cheGuanJu.setEngineno("0");
            cheGuanJu.setFrameno("4");
        } else {
            for (Province province : name.getResult().getData()) {
                if (province.getLsprefix().equals(firstLetter)) {
                    for (City city : province.getList()) {
                        if (secondLetter.equals(city.getLsnum())) {
                            CheGuanJu cheGuanJu = new CheGuanJu();
                            cheGuanJu.setCarorg(city.getCarorg());
                            cheGuanJu.setFrameno(city.getFrameno());
                            cheGuanJu.setEngineno(city.getEngineno());
                            return cheGuanJu;
                        }
                    }
                }
            }
        }
        return null;
    }

}