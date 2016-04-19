package com.vehicle.suixing.suixing.view.fragment;

import com.vehicle.suixing.suixing.ui.adapter.InfoAdapter;

/**
 * Created by KiSoo on 2016/4/11.
 */
public interface MeFragmentView {
    void setAdapter(InfoAdapter infoAdapter);

    void editHead();

    void editMotto();

    void editName();

    void setUpdate(boolean b);

    void addVehicle();

}
