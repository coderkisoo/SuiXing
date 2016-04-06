package com.vehicle.suixing.suixing.presenter;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;
import com.vehicle.suixing.suixing.model.PeccanydFragmentView;
import com.vehicle.suixing.suixing.ui.adapter.PeccanyItemAdapter;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/5.
 */
public class PeccanydFragmentPresenter {
    private PeccanydFragmentView view;
    private Context context;
    private List<WeizhangDate> list;

    public PeccanydFragmentPresenter(PeccanydFragmentView view, Context context, List<WeizhangDate> list) {
        this.view = view;
        this.context = context;
        this.list = list;
    }

    public void showData() {
        PeccanyItemAdapter adapter = new PeccanyItemAdapter(list,context);
        view.updateList(adapter);
    }
}
