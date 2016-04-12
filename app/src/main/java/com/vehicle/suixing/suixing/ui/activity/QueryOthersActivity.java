package com.vehicle.suixing.suixing.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.model.QueryOthersView;
import com.vehicle.suixing.suixing.presenter.QueryOthersActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/4/9.
 */
public class QueryOthersActivity extends BaseSlidingActivity implements QueryOthersView {

    private QueryOthersActivityPresenter presenter;
    private ProgressDialog dialog;
    @Bind(R.id.et_frame_num)
    MaterialEditText et_frame_num;
    @Bind(R.id.et_vehicle_num)
    MaterialEditText et_vehicle_num;
    @Bind(R.id.et_engine_num)
    EditText et_engine_num;
    @Bind(R.id.tv_province)
    TextView tv_province;
    @Bind(R.id.tv_city)
    TextView tv_city;
    @Bind(R.id.ll_province_show)
    LinearLayout ll_province_show;
    @Bind(R.id.ll_city_show)
    LinearLayout ll_city_show;


    @OnClick(R.id.tv_province)
    void tv_province(){
        /**
         * 选择省
         * */
        presenter.chooseProvince();
    }
    @OnClick(R.id.tv_city)
    void tv_city(){
        /**
         * 选择市
         * */
        presenter.chooseCity();
    }
    @OnClick(R.id.tv_query)
    void tv_query(){
        /**
         * 查询
         * */
        presenter.query();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryothers);
        ButterKnife.bind(this);
        presenter = new QueryOthersActivityPresenter(this,this);
    }

    @Override
    public void showProvinceWindow(PopupWindow window) {
        window.showAsDropDown(ll_province_show);
    }

    @Override
    public void showCity(String city) {
        tv_city.setText(city);
    }

    @Override
    public void showCityWindow(PopupWindow window) {
        window.showAsDropDown(ll_city_show);
    }

    @Override
    public void showProvince(String province) {
        tv_province.setText(province);
    }

    @Override
    public int getWidth() {
        return tv_city.getWidth();
    }

    @Override
    public int getHeight() {
        return tv_city.getHeight();
    }

    @Override
    public void dismissWindow(PopupWindow window) {
        window.dismiss();
    }

    @Override
    public String getFrameNum() {
        return et_frame_num.getText().toString();
    }

    @Override
    public String getVehicleNum() {
        return et_vehicle_num.getText().toString();
    }

    @Override
    public String getEngineNum() {
        return et_engine_num.getText().toString();
    }

    @Override
    public void showProgress() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在查询中");
        dialog.setTitle("提示：");
        dialog.show();
    }

    @Override
    public void dismissProgress() {
        dialog.dismiss();
    }

    @Override
    public void showError(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(msg);
            }
        });
    }
}
