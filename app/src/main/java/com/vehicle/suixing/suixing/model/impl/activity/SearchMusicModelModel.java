package com.vehicle.suixing.suixing.model.impl.activity;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.musicInfo.MusicData;
import com.vehicle.suixing.suixing.bean.musicInfo.MusicPath;
import com.vehicle.suixing.suixing.callback.SearchInfo;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.ISearchMusicModel;

import java.io.IOException;

/**
 * Created by KiSoo on 2016/5/13.
 */
public class SearchMusicModelModel implements ISearchMusicModel {

    private final String LIST_URL = "http://apis.baidu.com/geekery/music/query?size=10&page=1&s=";
    private final String PATH_URL = "http://apis.baidu.com/geekery/music/playinfo?hash=";
    private OkHttpClient client;

    @Override
    public void search(final Context context, String searchInfo, final SearchInfo callback) {
        if (searchInfo.length() == 0) {
            Toast.makeText(context, "请输入要搜索的信息...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (null == client) {
            client = new OkHttpClient();
        }
        client.newCall(new Request.Builder().url(LIST_URL + searchInfo).addHeader("apikey", Config.BAIDU_MUSIC_API).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Toast.makeText(context, request.toString(), Toast.LENGTH_SHORT).show();
                        callback.isSuccess(false);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        MusicData data = new Gson().fromJson(response.body().string(), new TypeToken<MusicData>() {
                        }.getType());
                        SuixingApp.onLineInfos = data.getData().getData();
                        Toast.makeText(context, "检索成功...", Toast.LENGTH_SHORT).show();
                        callback.isSuccess(true);
                    }
                });
    }

    @Override
    public void getPath(final Context context, final int position, final SearchInfo searchInfo) {
        if (null == client) {
            client = new OkHttpClient();
        }
        client.newCall(new Request.Builder().url(PATH_URL + SuixingApp.onLineInfos.get(position).getHash()).addHeader("apikey", "c18a63c08d9b4cc02a2afdf41ccbb606").build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Toast.makeText(context, request.toString(), Toast.LENGTH_SHORT).show();
                        searchInfo.isSuccess(false);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        MusicPath data = new Gson().fromJson(response.body().string(), new TypeToken<MusicPath>() {
                        }.getType());
                        SuixingApp.onLineInfos.get(position).setUrl(data.getData().getUrl());
                        searchInfo.isSuccess(true);
                    }
                });

    }
}
