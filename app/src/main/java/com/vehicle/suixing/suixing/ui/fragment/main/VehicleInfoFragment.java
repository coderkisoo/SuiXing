package com.vehicle.suixing.suixing.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KiSoo on 2016/6/1.
 */
public class VehicleInfoFragment  extends Fragment{
    private VehicleInformation information;
    @Bind(R.id.tv_vehicle_information_name)
    TextView tv_name;
    @Bind(R.id.tv_vehicle_information_frame_num)
    TextView tv_frame_num;
    @Bind(R.id.tv_vehicle_information_number)
    TextView tv_num;
    @Bind(R.id.tv_vehicle_information_gas_percent)
    TextView tv_percent;
    @Bind(R.id.tv_vehicle_information_level)
    TextView tv_size;
    @Bind(R.id.tv_vehicle_information_mileage)
    TextView tv_mileage;
    @Bind(R.id.tv_vehicle_information_engine_version)
    TextView tv_model;
    @Bind(R.id.tv_vehicle_information_engine_function)
    TextView tv_function;
    @Bind(R.id.tv_vehicle_information_light)
    TextView tv_light;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_info,null);
        ButterKnife.bind(this, view);
        tv_name.setText(information.getName());
        tv_num.setText(information.getNum());
        tv_frame_num.setText(information.getFramenum().substring(11, 17));//只显示11-17位的
        tv_percent.setText(information.getPercent());
        tv_size.setText(information.getSize());
        tv_mileage.setText(information.getMileage());
        tv_model.setText(information.getModel());
        tv_function.setText(information.getFunction());
        tv_light.setText(information.getLight());
        return view;
    }

    public VehicleInfoFragment(VehicleInformation information) {
      this.information = information;
    }

}
