package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;

import java.util.List;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class PopupListAdapter extends BaseAdapter {
    private List<VehicleInformation> list;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position).getNum();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.popup_list_item,null);
        }
        TextView itemName = (TextView) convertView.findViewById(R.id.tv_vehicle_item_name);
        itemName.setText(list.get(position).getNum());
        return convertView;
    }

    public PopupListAdapter(Context context, List<VehicleInformation> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }
}
