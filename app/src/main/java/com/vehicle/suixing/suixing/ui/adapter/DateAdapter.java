package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.Bmob_date_gasstation;

import java.util.List;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class DateAdapter extends BaseAdapter {
    private List<Bmob_date_gasstation> dateList;
    private Context context;

    @Override
    public int getCount() {
        return dateList.size();
    }

    public void setDateList(List<Bmob_date_gasstation> dateList) {
        this.dateList = dateList;
    }

    @Override
    public Object getItem(int position) {
        return dateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gas_station_info, null);
            holder = new ViewHolder();
            holder.tv_station_name = (TextView) convertView.findViewById(R.id.tv_station_name);
            holder.tv_gas_location = (TextView) convertView.findViewById(R.id.tv_gas_location);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.tv_gas_type = (TextView) convertView.findViewById(R.id.tv_gas_type);
            holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_station_name.setText(dateList.get(position).getGasstation_name());
        holder.tv_gas_location.setText(dateList.get(position).getGasstation_drass());
        holder.tv_amount.setText((dateList.get(position).getDate_money().equals("") ? "" : Integer.valueOf(dateList.get(position).getDate_money()) / 5 + "")+"L");
        holder.tv_gas_type.setText(dateList.get(position).getDate_oilStyle());
        holder.tv_order_time.setText(dateList.get(position).getDate_time());
        holder.tv_money.setText("总价 ：" + dateList.get(position).getDate_money() + "元");
        return convertView;
    }

    public DateAdapter(List<Bmob_date_gasstation> dateList, Context context) {
        this.dateList = dateList;
        this.context = context;
    }

    private class ViewHolder {
        private TextView tv_station_name;//名字
        private TextView tv_gas_location;//位置
        private TextView tv_amount;//油量
        private TextView tv_gas_type;//油类型
        private TextView tv_order_time;//订单时间
        private TextView tv_money;//总价
    }
}
