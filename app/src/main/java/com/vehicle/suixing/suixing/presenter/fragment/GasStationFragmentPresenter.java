package com.vehicle.suixing.suixing.presenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.activity.DateActivity;
import com.vehicle.suixing.suixing.view.fragment.GasStationFragmentView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class GasStationFragmentPresenter {
    private GasStationFragmentView view;
    private Context context;
    MapStatus mapStatus;
    BaiduMap mBaiduMap;
    public PoiSearch mPoiSearch;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    //设置发起请求后服务器返回的经纬度
    public LatLng point;
    //定义一个hushmap，把检索出来的经纬度作为KEY，把检索出来的result.getAllPoi()作为vaule,在地图覆盖物里面可以通过mark的经纬度把值取出来。
    HashMap<LatLng, PoiInfo> map = new HashMap<>();

    public GasStationFragmentPresenter(GasStationFragmentView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void initLocation() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        mBaiduMap = view.getMap();
        //设置仰视角度
        mapStatus = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(-45).build();//构造MapStatus
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mapStatus);//把MapStatus转换成 MapStatusUpdate
        mBaiduMap.animateMapStatus(msu);
        //设置旋转效果
        mapStatus = new MapStatus.Builder(mBaiduMap.getMapStatus()).rotate(45).build();
        MapStatusUpdate msu1 = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.animateMapStatus(msu1);
        //设置地图缩放级别
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));
        // 定位初始化
        mLocationClient = new LocationClient(context.getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        //设置检索周边加油站信息
        //实例化一个poi对象。
        mPoiSearch = PoiSearch.newInstance();
        //注册监听器
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            public void onGetPoiResult(PoiResult result) {
                if (result == null
                        || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                    Toast.makeText(context, "附近5000米没有加油站",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                    List<PoiInfo> poiInfos = result.getAllPoi();
                    Toast.makeText(context, "搜索到附近"+poiInfos.size()+"个加油站，请点击进行预约加油",
                            Toast.LENGTH_LONG).show();
                    for (PoiInfo p : poiInfos) {
                        //把检索出来的经纬度和根据经纬度得到的所有信息放入hushmap
                        map.put(p.location, p);
                        //构建地图覆盖物图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.gasstation_mark);
                        //创建一个图层（图层里面有精确的经纬度和在经纬度上绑定一个图标）
                        OverlayOptions option = new MarkerOptions()
                                .position(p.location)//p.location是检索出来的经纬度
                                .icon(bitmap);//把图片添加到图层里面
                        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(13.0f);
                        mBaiduMap.setMapStatus(msu);
                        //  在地图上添加Marker，并显示
                        mBaiduMap.addOverlay(option);//把图层添加到地图中
                    }

                }
            }

            public void onGetPoiDetailResult(PoiDetailResult result) {
                //获取Place详情页检索结果
            }
        });
        //设置覆盖物监听事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            /**
             * 地图 Marker 覆盖物点击事件监听函数
             * @param marker 被点击的 marker
             */
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (0 == map.size())
                    return true;
                PoiInfo p = map.get(marker.getPosition());
                showPopupWindow(p.name, p.city + p.address, p.phoneNum);
                return true;
            }
        });
    }

    public void onDestroy() {
        mPoiSearch.destroy();
    }

    public void showPopupWindow(final String gasName, final String location, final String tel) {
        View infoView = View.inflate(context, R.layout.gasstation_date_popwindow, null);
        PopupWindow infoWindow = new PopupWindow(infoView, ViewGroup.LayoutParams.MATCH_PARENT, 400, true);
        TextView tv_poi_date_adress = (TextView) infoView.findViewById(R.id.tv_poi_date_adress);
        TextView tv_tel = (TextView) infoView.findViewById(R.id.tv_tel);
        TextView tv_gas_station_name = (TextView) infoView.findViewById(R.id.tv_gas_station_name);
        ImageView iv_order = (ImageView) infoView.findViewById(R.id.iv_order);
        tv_gas_station_name.setText(gasName);
        tv_poi_date_adress.setText(location);
        tv_tel.setText(tel.equals("")?"该加油站木有留下电话号码":tel);
        iv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DateActivity.class);
                //把检索出来的值通过intent传到下一个类
                intent.putExtra(Config.KEY_GAS_NAME, gasName);
                intent.putExtra(Config.KEY_GAS_LOCATION, location);
                intent.putExtra(Config.KEY_GAS_TEL, tel);
                context.startActivity(intent);
            }
        });
        infoWindow.setOutsideTouchable(true);
        infoWindow.showAtLocation(view.getParent(), Gravity.BOTTOM, 0, 0);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            //解析得到定位出来的经纬度
            point = new LatLng(location.getLatitude(), location.getLongitude());
            //设置地图中心点
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
            mBaiduMap.animateMapStatus(msu);
            //设置地图定位点图标
//            BitmapDescriptor stationIcon = BitmapDescriptorFactory
//                    .fromResource(R.mipmap.location_map);
            view.setNowLocation(location.getAddrStr());
//            mBaiduMap.addOverlay(new MarkerOptions()
//                    .position(point));
//                    .icon(stationIcon));
            //发起检索请求，必须在定位成功才能发起，否则紧缩会跪
            mPoiSearch.searchNearby(new PoiNearbySearchOption()
                    .location(point)
                    .keyword("加油")
                    .radius(100000)
                    .pageNum(10));
        }
    }
}
