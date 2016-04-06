package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;
import com.vehicle.suixing.suixing.callback.GetWeizhangInfo;
import com.vehicle.suixing.suixing.model.PeccanydAcitivtyView;
import com.vehicle.suixing.suixing.ui.fragment.peccany.PeccanydFragment;
import com.vehicle.suixing.suixing.util.JisuApiQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by KiSoo on 2016/4/5.
 */
public class PeccanydActivityPresenter implements GetWeizhangInfo {
    private String TAG = this.getClass().getName();
    private PeccanydAcitivtyView view;
    private Context context;
    private VehicleInformation info;
    private Fragment nowFragment;
    private Fragment pastFragment;
    private List<WeizhangDate> now;
    private List<WeizhangDate> past;
    private final int BEGIN = 0;
    private final int FINISH = 1;
    private final int DEFAILT = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BEGIN:
                    view.begin();
                    break;
                case FINISH:
                    view.dismiss();
                    now();
                    break;
                case DEFAILT:
                    view.defau();
                    break;
                default:
                    break;
            }
        }
    };

    public PeccanydActivityPresenter(PeccanydAcitivtyView view, Context context, VehicleInformation info) {
        this.view = view;
        this.context = context;
        this.info = info;
        this.now = new ArrayList<>();
        this.past = new ArrayList<>();
        beginQuery();
    }

    private void beginQuery() {
        Log.e(TAG, "查询中");
        Message message = new Message();
        message.what = BEGIN;
        handler.sendMessage(message);
//        PeccanyQueryUtils.query(info.getNum(), info.getModel(), info.getFramenum(), this);
        JisuApiQuery query = new JisuApiQuery(context);
        query.query(info.getNum(), info.getFramenum(), info.getModel(), this);
    }

    private void switchDate(List<WeizhangDate> infos) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        if (null != infos)
            for (int i = 0; i < infos.size(); i++) {
                Log.e(TAG, "history不为null");
                String date = infos.get(i).getTime();
                /**
                 * 将返回值转化成年和月
                 * */
                int peccanyMonth = Integer.parseInt(date.substring(0, 4));
                int peccanyYear = Integer.parseInt(date.substring(5, 7));
                //年份小于今年，不是本月
                if (peccanyYear < year) {
                    past.add(infos.get(i));
                    continue;
                }
                //月份小于本月，不是本月
                if (peccanyMonth < month) {
                    past.add(infos.get(i));
                    continue;
                }
                //做完判断，添加到本月
                now.add(infos.get(i));
            }
        Log.e(TAG, "for循环执行完");
    }

    public void now() {
        if (nowFragment == null)
            nowFragment = new PeccanydFragment(now);
        view.now(nowFragment);
    }

    public void past() {
        if (pastFragment == null)
            pastFragment = new PeccanydFragment(past);
        view.past(pastFragment);
    }


    @Override
    public void requestSuccess(List<WeizhangDate> list) {
        switchDate(list);
        Message message = new Message();
        message.what = FINISH;
        handler.sendMessage(message);

    }

    @Override
    public void requestDefault(String error) {
        Message message = new Message();
        message.what = DEFAILT;
        handler.sendMessage(message);
    }
}
