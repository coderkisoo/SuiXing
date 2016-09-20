package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.vehicle.suixing.suixing.R;

import java.util.List;

/**
 * Created by KiSoo on 2016/7/19.
 */
public class RecentRouteAdapter extends BaseAdapter {
    private List<PoiItem> poiInfos;
    private Context context;

    public RecentRouteAdapter(Context context, List<PoiItem> pois) {
        this.poiInfos = pois;
        this.context = context;
    }

    @Override
    public int getCount() {
        return poiInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return poiInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_recent_target,null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(poiInfos.get(position).getTitle());
        holder.tv_address.setText(poiInfos.get(position).getSnippet());
        return convertView;
    }

    public void setList(List<PoiItem> list) {
        this.poiInfos = list;
        this.notifyDataSetChanged();
    }

    public void clearData() {
        this.poiInfos.clear();
        this.notifyDataSetChanged();
    }

    public class ViewHolder{
        public TextView tv_name;
        public TextView tv_address;

    }
}
