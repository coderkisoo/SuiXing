package com.vehicle.suixing.suixing.ui.fragment.peccany;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;
import com.vehicle.suixing.suixing.view.fragment.PeccanydFragmentView;
import com.vehicle.suixing.suixing.presenter.fragment.PeccanydFragmentPresenter;
import com.vehicle.suixing.suixing.ui.adapter.PeccanyItemAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KiSoo on 2016/4/5.
 */
public class PeccanydFragment extends Fragment implements PeccanydFragmentView {
    /**
     * 查询完毕的fragment
     */
    private View view;
    @Bind(R.id.lv_now_peccany)
    ListView lv_now_peccany;
    private List<WeizhangDate> list;
    private PeccanydFragmentPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.now_peccany, null);
        ButterKnife.bind(this, view);
        presenter = new PeccanydFragmentPresenter(this,getActivity(),list);
        presenter.showData();
        return view;
    }

    public PeccanydFragment(List<WeizhangDate> list) {
        this.list = list;

    }

    @Override
    public void updateList(PeccanyItemAdapter adapter) {
        lv_now_peccany.setAdapter(adapter);
        View emptyView = View.inflate(getActivity(), R.layout.un_pessany, null);
        ((ViewGroup)lv_now_peccany.getParent()).addView(emptyView);
        lv_now_peccany.setEmptyView(emptyView);
    }
}
