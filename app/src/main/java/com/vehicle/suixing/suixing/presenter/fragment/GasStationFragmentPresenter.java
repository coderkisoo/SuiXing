package com.vehicle.suixing.suixing.presenter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.MapBean.MapInfo;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.activity.DateActivity;
import com.vehicle.suixing.suixing.ui.activity.QueryRouteActivity;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.util.MapUtil.MapUtils;
import com.vehicle.suixing.suixing.util.formatUtils.DensityUtil;
import com.vehicle.suixing.suixing.view.fragment.GasStationFragmentView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class GasStationFragmentPresenter implements LocationSource,
        AMapLocationListener,
        AMap.OnMarkerClickListener,
        AMap.OnPOIClickListener,
        AMap.OnMapLongClickListener,
        LocationSource.OnLocationChangedListener {
    private GasStationFragmentView view;
    public static final String TAG = "GasStationFragmentPresenter";
    private Activity context;
    public AMapLocationClient client = null;
    private AMap aMap;
    private HashMap<LatLng, PoiItem> gasMap = new HashMap<>();

    public GasStationFragmentPresenter(GasStationFragmentView view, Activity context) {
        this.view = view;
        this.context = context;
    }

    //在此处初始化地图控件
    public void initMapView() {
        //配置
        aMap = view.getMap();
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);//定位模式
        aMap.setOnMarkerClickListener(this);
        aMap.setMyLocationEnabled(true);
        aMap.setOnPOIClickListener(this);
        aMap.setOnMapLongClickListener(this);
        UiSettings settings = aMap.getUiSettings();
        settings.setZoomControlsEnabled(true);//缩放按钮
        settings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        settings.setMyLocationButtonEnabled(true);//定位按钮
        settings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);//右下角logo
        settings.setCompassEnabled(true);//指南针
        settings.setScaleControlsEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.current_location));// 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.GRAY);//自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);// 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);// 构造 LocationManagerProxy 对象
        client = new AMapLocationClient(context.getApplicationContext());
        client.setLocationListener(this);
        client.startLocation();
    }


    private void showPoiInfo(final Poi poi) {
        if (myLocation==null)
            return;
        View locationView = View.inflate(context, R.layout.location_info, null);
        PopupWindow locationWindow = new PopupWindow(locationView,DensityUtil.getDisplayWidth(context) - 80,DensityUtil.dip2px(context,88),true);
        TextView tv_location_name = (TextView) locationView.findViewById(R.id.tv_location_name);
        TextView tv_distance = (TextView) locationView.findViewById(R.id.tv_distance);
        ImageView iv_go = (ImageView) locationView.findViewById(R.id.iv_go);
        tv_location_name.setText(poi.getName());
        tv_distance.setText(MapUtils.getDistance(MapUtils.getLatLng(myLocation.getLatitude(),myLocation.getLongitude()),poi.getCoordinate()));
        iv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeTo(poi.getCoordinate(),poi.getName());
            }
        });
        locationWindow.setOutsideTouchable(true);
        locationWindow.showAtLocation(view.getParent(), Gravity.BOTTOM, 0, 40);
        Log.e(TAG,"宽度为"+locationWindow.getWidth());
    }

    public void onDestroy() {
        client.onDestroy();
    }

    public void showGasWindow(final PoiItem poiItem) {
        if (myLocation==null)
            return;
        View infoView = View.inflate(context, R.layout.gasstation_date_popwindow, null);
        PopupWindow locationWindow = new PopupWindow(infoView,DensityUtil.getDisplayWidth(context) - 80,DensityUtil.dip2px(context,160),true);
        TextView tv_poi_date_adress = (TextView) infoView.findViewById(R.id.tv_poi_date_adress);
        TextView tv_tel = (TextView) infoView.findViewById(R.id.tv_tel);
        TextView tv_gas_station_name = (TextView) infoView.findViewById(R.id.tv_gas_station_name);
        TextView tv_distance = (TextView) infoView.findViewById(R.id.tv_distance);
        ImageView iv_order = (ImageView) infoView.findViewById(R.id.iv_order);
        ImageView iv_go = (ImageView) infoView.findViewById(R.id.iv_go);
        tv_gas_station_name.setText(poiItem.getTitle());
        LatLonPoint point = poiItem.getLatLonPoint();
        final LatLng target = MapUtils.getLatLng(point.getLatitude(),point.getLongitude());
        tv_distance.setText(MapUtils.getDistance(MapUtils.getLatLng(myLocation.getLatitude(),myLocation.getLongitude()),target));
        tv_poi_date_adress.setText(poiItem.getSnippet());
        tv_tel.setText(poiItem.getTel());
        iv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeTo(target,poiItem.getTitle());
            }
        });
        iv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DateActivity.class);
                intent.putExtra(Config.KEY_GAS_NAME, poiItem.getTitle());
                intent.putExtra(Config.KEY_GAS_LOCATION, poiItem.getSnippet());
                intent.putExtra(Config.KEY_GAS_TEL, poiItem.getTel());
                intent.putExtra(Config.KEY_FROM,myLocation.getLatitude());
                intent.putExtra(Config.KEY_START_LATING,new MapInfo(MapUtils.getLatLng(myLocation.getLatitude(),myLocation.getLongitude()),myLocation.getAddress()));
                LatLonPoint point = poiItem.getLatLonPoint();
                intent.putExtra(Config.KEY_END_LATING,new MapInfo(target,poiItem.getSnippet()));
                context.startActivity(intent);
            }
        });
        locationWindow.setOutsideTouchable(true);
        locationWindow.showAtLocation(view.getParent(), Gravity.BOTTOM, 0, 40);
    }

    private void routeTo(LatLng target,String name) {
        Intent intent = new Intent(context,QueryRouteActivity.class);
        intent.putExtra(Config.KEY_START_LATING,new MapInfo(MapUtils.getLatLng(myLocation.getLatitude(),myLocation.getLongitude()),myLocation.getAddress()));
        intent.putExtra(Config.KEY_END_LATING,new MapInfo(target,name));
        context.startActivity(intent);
    }

    //自定义路径
    public void searchTarget() {
        if (myLocation == null){
            Toast.makeText(context,"地图尚未初始化完全，请稍后...",Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(new Intent(context, QueryRouteActivity.class)
                .putExtra(Config.KEY_START_LATING, new MapInfo(MapUtils.getLatLng(myLocation.getLatitude(),myLocation.getLongitude()),myLocation.getAddress())));
    }

    /***
     * LocationSource的方法
     */

    private OnLocationChangedListener listener;

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        listener  = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        listener = null;
    }

    private AMapLocation myLocation;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (listener!=null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                view.setNowLocation(aMapLocation.getAddress());
                myLocation = aMapLocation;
                client.stopLocation();
                queryNearGas();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                Toast.makeText(context,errText,Toast.LENGTH_SHORT).show();
            }
            listener.onLocationChanged(aMapLocation);// 显示系统小蓝点
        }
    }

    private void queryNearGas() {
        PoiSearch.Query query = new PoiSearch.Query("加油站", "汽车维修|餐饮服务", myLocation.getCityCode());
        query.setPageSize(10);
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                gasMap.clear();
                aMap.clear();
                List<PoiItem> gasList = poiResult.getPois();
                for (PoiItem poiItem : gasList) {
                    LatLonPoint point = poiItem.getLatLonPoint();
                    LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                    gasMap.put(latLng, poiItem);
                    addMarkerOnMap(latLng, poiItem.getTitle());
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    private void addMarkerOnMap(LatLng lng, String title) {
        aMap.addMarker(new MarkerOptions()
                .position(lng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.gasstation_mark))
                .title(title));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (gasMap.containsKey(marker.getPosition())) {
            showGasWindow(gasMap.get(marker.getPosition()));
       }
        return true;
    }


    @Override
    public void onPOIClick(Poi poi) {
        showPoiInfo(poi);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        view.showInfo();
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
