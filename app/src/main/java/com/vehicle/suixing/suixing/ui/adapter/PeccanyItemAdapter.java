package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.WeiZhang.WeizhangResponseHistoryJson;

import java.util.List;


/**
 * Created by KiSoo on 2016/4/5.
 */
public class PeccanyItemAdapter extends BaseAdapter {
    private List<WeizhangResponseHistoryJson> list;
    private Context context;

    public PeccanyItemAdapter(List<WeizhangResponseHistoryJson> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_peccany_card, null);
            holder.tv_peccany_time = (TextView) convertView.findViewById(R.id.tv_peccany_time);
            holder.tv_peccany_location = (TextView) convertView.findViewById(R.id.tv_peccany_location);
            holder.tv_peccany_reason = (TextView) convertView.findViewById(R.id.tv_peccany_reason);
            holder.tv_peccany_remarks = (TextView) convertView.findViewById(R.id.tv_peccany_remarks);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
//        holder.tv_peccany_time.setText(list.get(position).getOccur_date());
//        holder.tv_peccany_location.setText(list.get(position).getOccur_area());
//        holder.tv_peccany_reason.setText(list.get(position).getInfo());
//        holder.tv_peccany_remarks.setText("扣" + list.get(position).getFen() + "分，罚金" + list.get(position).getMoney() + "元");
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_peccany_time;
        private TextView tv_peccany_location;
        private TextView tv_peccany_reason;
        private TextView tv_peccany_remarks;
    }
}
