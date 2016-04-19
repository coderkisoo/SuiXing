package com.vehicle.suixing.suixing.ui.fragment.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.UpdateList;
import com.vehicle.suixing.suixing.view.activity.MainActivityView;
import com.vehicle.suixing.suixing.view.fragment.MeFragmentView;
import com.vehicle.suixing.suixing.presenter.MeFragmentPresenter;
import com.vehicle.suixing.suixing.ui.activity.AddVehicleActivity;
import com.vehicle.suixing.suixing.ui.activity.EditMottoActivity;
import com.vehicle.suixing.suixing.ui.activity.EditNameActivity;
import com.vehicle.suixing.suixing.ui.adapter.InfoAdapter;
import com.vehicle.suixing.suixing.util.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class MeFragment extends Fragment implements
        MeFragmentView,
        View.OnClickListener ,
        UpdateList{
    public static final int TAKE_PHOTO = 2001;
    public static final int CROP_PHOTO = 2002;
    public static final int GALLERY = 2003;
    private final String TAG = this.getClass().getName();
    private MainActivityView mainActivityView;

    private View view;
    private MeFragmentPresenter presenter;
    private AlertDialog dialog;

    private String path;

    @Bind(R.id.rv_vehicle)
    RecyclerView rv_vehicle;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;

    public MeFragment(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        presenter = new MeFragmentPresenter(getActivity(), this, mainActivityView);
        dialog = new AlertDialog.Builder(getActivity()).create();
        initEvents();
        presenter.getInfo();
        return view;
    }


    private void initEvents() {
        srl_refresh.setColorSchemeColors(Color.parseColor("#4FA3F9"),
                Color.parseColor("#50A2F8"), Color.parseColor("#3F51B5"),
                Color.parseColor("#303F9F"));
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }


    @Override
    public void setAdapter(InfoAdapter infoAdapter) {
        rv_vehicle.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_vehicle.setAdapter(infoAdapter);
    }

    @Override
    public void editHead() {
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.edit_head_dialog);
        TextView tv_camera = (TextView) window.findViewById(R.id.tv_camera);
        TextView tv_gallery = (TextView) window.findViewById(R.id.tv_gallery);
        tv_camera.setOnClickListener(MeFragment.this);
        tv_gallery.setOnClickListener(MeFragment.this);
    }

    @Override
    public void editMotto() {
        startActivity(new Intent(getActivity(), EditMottoActivity.class));
    }

    @Override
    public void editName() {
        startActivity(new Intent(getActivity(), EditNameActivity.class));
    }

    @Override
    public void setUpdate(boolean b) {
        srl_refresh.setRefreshing(b);
    }

    @Override
    public void addVehicle() {
        startActivity(new Intent(getActivity(), AddVehicleActivity.class));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_camera:
                dialog.dismiss();
                if (hasSDCard()) {
                    getNewPath();
                    Log.e(TAG, "拍摄的照片路径:" + path);
                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    cameraIntent.putExtra("scale", true);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), path)));
                    startActivityForResult(cameraIntent, TAKE_PHOTO);
                }

                break;
            case R.id.tv_gallery:
                dialog.dismiss();
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentFromGallery, GALLERY);
                break;
            default:
                break;
        }
    }

    private void getNewPath() {
        path = "camera" + System.currentTimeMillis() + ".jpg";
    }

    private boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (hasSDCard())
                    presenter.setHead(FileUtils.getImageAbsolutePath(getActivity(), Uri.fromFile(new File(Environment.getExternalStorageDirectory(), path))));
                break;
            case GALLERY:
                if (null != data) {
                    String img_url = FileUtils.getImageAbsolutePath(getActivity(), data.getData());
                    Log.e(TAG, "上传路径为:" + img_url);
                    presenter.setHead(img_url);
                }
                break;
            case CROP_PHOTO:
                if (null != data) {
                    Bundle extras = data.getExtras();
                    Bitmap img = extras.getParcelable("data");
                    if (null != img) {
                        FileUtils.savePicToSdcard(img, path);
                    }
                }
                getNewPath();

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void updateList(List<VehicleInformation> infos) {
        SuixingApp.infos = infos;
        presenter.onResume();
    }
}
