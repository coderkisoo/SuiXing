package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.WeiZhang1.City;

import java.util.List;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class CityAdapter extends BaseAdapter {

    private List<City> cityList;
    private LayoutInflater inflater;



    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position).getCity();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.province_item,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_province_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(cityList.get(position).getCity());
        return convertView;
    }

    public CityAdapter(List<City> cityList, Context context) {
        this.cityList = cityList;
        this.inflater = LayoutInflater.from(context);
    }
    private final class ViewHolder{
        TextView textView;
    }
}
