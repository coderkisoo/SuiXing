package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;
import com.vehicle.suixing.suixing.callback.GetWeizhangInfo;
import com.vehicle.suixing.suixing.callback.SwitchDate;
import com.vehicle.suixing.suixing.model.activity.IPeccanyActivityModel;
import com.vehicle.suixing.suixing.model.impl.activity.PeccanyActivityModel;
import com.vehicle.suixing.suixing.view.activity.PeccanydAcitivtyView;
import com.vehicle.suixing.suixing.ui.fragment.peccany.PeccanydFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/4/5.
 */
public class PeccanydActivityPresenter implements
        GetWeizhangInfo ,
        SwitchDate{
    private String TAG = this.getClass().getName();
    private PeccanydAcitivtyView view;
    private IPeccanyActivityModel model;
    private Context context;
    private Fragment nowFragment;
    private Fragment pastFragment;
    private List<WeizhangDate> now;
    private List<WeizhangDate> past;
    public final int BEGIN = 1000;
    public final int FINISH = 1001;
    public final int DEFAILT = 1002;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                /*开始查询*/
                case BEGIN:
                    view.begin();
                    break;
                /*结束查询*/
                case FINISH:
                    view.dismiss();
                    now();
                    break;
                /*查询失败*/
                case DEFAILT:
                    view.fail();
                    break;
                default:
                    break;
            }
        }
    };

    public PeccanydActivityPresenter(PeccanydAcitivtyView view, Context context, VehicleInformation info) {
        this.view = view;
        this.context = context;
        this.now = new ArrayList<>();
        this.past = new ArrayList<>();
        this.model = new PeccanyActivityModel();
        model.beginQuery(info,this);
        beginQuery();
    }

    public PeccanydActivityPresenter(List<WeizhangDate> infos, Context context, PeccanydAcitivtyView view) {
        this.now = new ArrayList<>();
        this.past = new ArrayList<>();
        this.view = view;
        this.context = context;
        this.model = new PeccanyActivityModel();
        switchDate(infos);
    }
    /*此处为手动输入各项参数的查询没有查询过*/
    private void beginQuery() {
        Message message = new Message();
        message.what = BEGIN;
        handler.sendMessage(message);
    }

    private void switchDate(List<WeizhangDate> infos) {
        model.switchDate(infos,this);
        Message message = new Message();
        message.what = FINISH;
        handler.sendMessage(message);
    }
    /**
     * 监听点击事件，now
     * */
    public void now() {
        if (nowFragment == null)
            nowFragment = new PeccanydFragment(now);
        view.now(nowFragment);
    }
    /**
     * 监听点击事件，past
     * */
    public void past() {
        if (pastFragment == null)
            pastFragment = new PeccanydFragment(past);
        view.past(pastFragment);
    }

    /**
     * 查询成功
     * */
    @Override
    public void requestSuccess(List<WeizhangDate> list) {
        switchDate(list);
    }

    /**
     * 查询失败
     * */
    @Override
    public void requestFail(String error) {
        Message message = new Message();
        message.what = DEFAILT;
        handler.sendMessage(message);
    }

    @Override
    public void showText(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPast(List<WeizhangDate> past) {
        this.past = past;
    }

    @Override
    public void getNow(List<WeizhangDate> now) {
        this.now = now;
    }
}
