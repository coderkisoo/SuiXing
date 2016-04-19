package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.view.fragment.PeccanyFragmentView;
import com.vehicle.suixing.suixing.ui.activity.PeccanydActivity;
import com.vehicle.suixing.suixing.ui.activity.QueryOthersActivityActivity;
import com.vehicle.suixing.suixing.ui.adapter.PopupListAdapter;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class PeccanyFragmentPresenter {
    private boolean isSelected = false;

    private String TAG = this.getClass().getName();
    private PeccanyFragmentView view;
    private Context context;
    private VehicleInformation info;

    public PeccanyFragmentPresenter(PeccanyFragmentView view, Context context) {
        this.view = view;
        this.context = context;
        view.setVehicle("");
    }

    /**
     * 弹出选择车辆的框
     */
    public void showWindow() {
        if (SuixingApp.infos.size() != 0) {
            final PopupWindow window = new PopupWindow();
            window.setWidth(view.getWidth());
            window.setHeight(view.getHeight()*SuixingApp.infos.size());
            View popupView = View.inflate(context, R.layout.popup_list, null);
            window.setContentView(popupView);
            ListView lv_my_vehicle = (ListView) popupView.findViewById(R.id.lv_my_vehicle);
            lv_my_vehicle.setAdapter(new PopupListAdapter(context, SuixingApp.infos));
            lv_my_vehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
                    view.setVehicle(SuixingApp.infos.get(position).getNum());
                    isSelected = true;
                    info = SuixingApp.infos.get(position);
                    window.dismiss();
                }
            });
            window.setBackgroundDrawable(new BitmapDrawable());
            window.setFocusable(true);
            view.showWindow(window);
        } else {
            Toast.makeText(context, "您还没有添加车辆...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查询其他车辆
     */

    public void queryOthers() {
        context.startActivity(new Intent(context, QueryOthersActivityActivity.class));
    }

    /**
     * 查询
     */
    public void query() {
        if (isSelected){
            Log.d(TAG, "正在查询中");
//            context.startActivity(new Intent(context,));
            context.startActivity(new Intent(context, PeccanydActivity.class).putExtra("carInfo",info));
        }else{
            Toast.makeText(context, "您还未选择车辆...", Toast.LENGTH_SHORT).show();
        }
    }
}

