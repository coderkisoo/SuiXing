package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.WeiZhang1.Province;

import java.util.List;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class ProvinceAdapter extends BaseAdapter {

    private List<Province> provinceList;
    private LayoutInflater inflater;



    @Override
    public int getCount() {
        return provinceList.size();
    }

    @Override
    public Object getItem(int position) {
        return provinceList.get(position).getProvince();
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
        viewHolder.textView.setText(provinceList.get(position).getProvince());
        return convertView;
    }

    public ProvinceAdapter(List<Province> provinceList,Context context) {
        this.provinceList = provinceList;
        this.inflater = LayoutInflater.from(context);
    }
    private final class ViewHolder{
        TextView textView;
    }
}
