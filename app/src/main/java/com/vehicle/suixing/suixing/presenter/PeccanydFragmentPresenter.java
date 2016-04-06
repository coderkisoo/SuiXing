package com.vehicle.suixing.suixing.presenter;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.WeiZhang.WeizhangResponseHistoryJson;
import com.vehicle.suixing.suixing.model.PeccanydFragmentView;
import com.vehicle.suixing.suixing.ui.adapter.PeccanyItemAdapter;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/5.
 */
public class PeccanydFragmentPresenter {
    private PeccanydFragmentView view;
    private Context context;
    private List<WeizhangResponseHistoryJson> list;

    public PeccanydFragmentPresenter(PeccanydFragmentView view, Context context, List<WeizhangResponseHistoryJson> list) {
        this.view = view;
        this.context = context;
        this.list = list;
    }

    public void showData() {
        PeccanyItemAdapter adapter = new PeccanyItemAdapter(list,context);
        view.updateList(adapter);
    }
}
