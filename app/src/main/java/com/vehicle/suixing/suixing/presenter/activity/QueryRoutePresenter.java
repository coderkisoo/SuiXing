package com.vehicle.suixing.suixing.presenter.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.vehicle.suixing.suixing.bean.MapBean.MapInfo;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.activity.NaviActivity;
import com.vehicle.suixing.suixing.ui.adapter.RecentRouteAdapter;
import com.vehicle.suixing.suixing.util.DialogUtils.ProgressDialogUtils;
import com.vehicle.suixing.suixing.util.MapUtil.MapUtils;
import com.vehicle.suixing.suixing.view.activity.QueryRouteActivityView;

import java.util.List;

/**
 * Created by KiSoo on 2016/7/19.
 */
public class QueryRoutePresenter {

    private Activity context;
    private QueryRouteActivityView view;
    private boolean isStart = false;
    private MapInfo startPoint, targetPoint;
    private RecentRouteAdapter adapter;

    public void exchange() {
        String start = view.getStart();
        String end = view.getEnd();
        MapInfo temp = startPoint;
        startPoint = targetPoint;
        targetPoint = temp;
        view.setText(true, end);
        view.setText(false, start);
    }

    public QueryRoutePresenter(Activity context, QueryRouteActivityView view, Intent intent) {
        this.context = context;
        this.view = view;
        startPoint = intent.getParcelableExtra(Config.KEY_START_LATING);
        targetPoint = intent.getParcelableExtra(Config.KEY_END_LATING);
        view.setText(true, startPoint == null ? "" : startPoint.getAddress());
        view.setText(false, targetPoint == null ? "" : targetPoint.getAddress());
        view.setStartWatcher(startWatcher);
        view.setEndWatcher(targetWatcher);
    }

    private TextWatcher startWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isStart = true;
            queryInfo(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private TextWatcher targetWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, final int start, int before, int count) {
            isStart = false;
            queryInfo(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void queryInfo(String info) {
        PoiSearch.Query query = new PoiSearch.Query(info, "");
        query.setPageSize(15);
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                if (i != 1000)
                    return;
                List<PoiItem> pois = poiResult.getPois();
                if (adapter == null) {
                    adapter = new RecentRouteAdapter(context, pois);
                    view.setAdapter(adapter);
                } else {
                    adapter.clearData();
                    adapter.setList(pois);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();//开始搜索
    }

    public void choose(int position) {
        if (adapter == null)
            return;
        PoiItem item = (PoiItem) adapter.getItem(position);
        LatLonPoint point = item.getLatLonPoint();
        LatLng lng = MapUtils.getLatLng(point.getLatitude(), point.getLongitude());
        if (isStart) {
            startPoint = new MapInfo(lng, item.getTitle());
        } else {
            targetPoint = new MapInfo(lng, item.getTitle());
        }
        view.setText(isStart, isStart?startPoint.getAddress():targetPoint.getAddress());
    }

    public void go() {
        ProgressDialog dialog = ProgressDialogUtils.from(context)
                .setTitle("提示：")
                .setMessage("正在确定路径，请稍后...")
                .setCanceledOnTouchOutside(false)
                .show();
        if (view.getStart() == null || view.getEnd() == null) {
            Toast.makeText(context, "请补全您的行车信息...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startPoint == null) {
            try {
                startPoint = getPoint(view.getStart());
                if (startPoint == null){
                    Toast.makeText(context, "起点确认失败，请重新输入", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
            } catch (AMapException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "起点确认失败，请重新输入", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
            }
        }
        if (targetPoint == null) {
            try {
                targetPoint = getPoint(view.getStart());
                if (targetPoint == null){
                    Toast.makeText(context, "终点确认失败，请重新输入", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
            } catch (AMapException e) {
                e.printStackTrace();
                Toast.makeText(context, "终点确认失败，请重新输入", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return;
            }
        }
        dialog.dismiss();
        context.startActivity(new Intent(context,NaviActivity.class)
                .putExtra(Config.KEY_START_LATING,startPoint)
                .putExtra(Config.KEY_END_LATING,targetPoint));
    }

    private MapInfo getPoint(String info) throws AMapException {
            List<PoiItem> list = getPoi(info);
            PoiItem item = list.get(0);
            if (item != null){
                return new MapInfo(MapUtils.getLatLngFromPoi(item),item.getTitle());
            }else {
                return null;
            }
    }

    public List<PoiItem> getPoi(String info) throws AMapException {
        PoiSearch.Query query = new PoiSearch.Query(info, "");
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        PoiSearch poiSearch = new PoiSearch(context, query);//初始化poiSearch对象
        PoiResult result = poiSearch.searchPOI();//开始搜索
        return result.getPois();
    }
}
