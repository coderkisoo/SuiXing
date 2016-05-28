package com.vehicle.suixing.suixing.util.require;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vehicle.suixing.suixing.callback.GetWeizhangInfo;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.util.formatUtils.MD5Utils;

import java.io.IOException;
import java.net.URLEncoder;


/**
 * Created by KiSoo on 2016/4/5.
 */
@Deprecated
public class PeccanyQueryUtils {
    public static String URL = "http://www.cheshouye.com";
    private static GetWeizhangInfo info;
    private static String TAG = "PeccanyQueryUtils";

    /**
     * 参数设置，
     * 1.汽车车牌号码
     * 2.汽车发动机号码
     * 3.车架号（车辆识别码）
     * 请求示例：
     * http://www.cheshouye.com/api/weizhang/query_task?
     * car_info=
     * %7Bhphm%3D%E7%B2%A4B12345%26
     * classno%3D123456%26engineno%3D1234%26
     * city_id%3D152%26car_type%3D02%7D&
     * sign=32910fa63c0523d3d66c51f30790d480&
     * timestamp=1411115771125&
     * app_id=100
     */
    public static void query(final String vehiclenum, final String engineNo, String frameNum, GetWeizhangInfo call) {
        info = call;
        Log.e(TAG,"vehiclenum"+vehiclenum+"engineNo"+engineNo+"frameNum"+frameNum);
        sendQuery(vehiclenum, frameNum,engineNo);
    }

    private static void sendQuery(String vehiclenum,String frameNum,String engineNo) {
        try {
            String carInfo = "{hphm="+vehiclenum+"&classno="+frameNum+"&engineno="+engineNo+"&city_id=152&car_type=02}";
            String appId = Config.WEIZHANG_APPID;    //联系车首页获取
            String appKey = Config.WEIZHANG_KEY;  //联系车首页获取

            System.out.println("请稍后...");
            String sb = getWeizhangInfoPost(carInfo, appId, appKey);
            System.out.println("返回违章结果：" + sb);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * title:获取违章信息
     *
     * @param carInfo
     * @return
     */
    public static String getWeizhangInfoPost(String carInfo, String appId, String appKey) {
        long timestamp = System.currentTimeMillis();
        String line = null;
        String signStr = appId + carInfo + timestamp + appKey;
        String sign = MD5Utils.ecoder(signStr);
        try {
            String postUrl = URL + "/api/weizhang/query_task?";
            String content =
                    "car_info=" + URLEncoder.encode(carInfo, "utf-8") +
                            "&sign=" + sign + "&timestamp=" + timestamp +
                            "&app_id=" + appId;

            postUrl = postUrl + content;
            Log.e(TAG, "待请求的url为"+postUrl);
            post(postUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * title:获取省份城市对应ID配置
     *
     * @return
     * @throws IOException
     */
    private static void post(String url) {
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(url).build();
        Log.v(TAG, "请求的数据为" + url);
        com.squareup.okhttp.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, request.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.e(TAG, "结果：" + response.body().string());
                Log.e(TAG,"response"+response.toString());

            }
        });

    }

}
