package com.vehicle.suixing.suixing.util.MapUtil;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;

/**
 * Created by KiSoo on 2016/5/26.
 */
public class MapUtils {
    public static String getDistance(LatLng start, LatLng end){
        if (start==null||end==null)
            return "";
        return String.format("%.2f",AMapUtils.calculateLineDistance(start, end)/1000) +"km";
    }

    public static LatLng getLatLng(double latitude,double longitude){
        return new LatLng(latitude,longitude);
    }

    public static LatLng getLatLngFromPoi(PoiItem item){
        return getLatLngFromPoint(item.getLatLonPoint());
    }

    public static LatLng getLatLngFromPoint(LatLonPoint point){
        return getLatLng(point.getLatitude(),point.getLongitude());
    }

}
