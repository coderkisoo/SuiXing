package com.vehicle.suixing.suixing.presenter.fragment;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.musicInfo.Mp3Info;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.service.MusicPlayService;
import com.vehicle.suixing.suixing.ui.adapter.MusicAdapter;
import com.vehicle.suixing.suixing.util.DialogUtils.ProgressDialogUtils;
import com.vehicle.suixing.suixing.util.musicUtil.MediaUtil;
import com.vehicle.suixing.suixing.util.RegisterUtils.SpUtils;
import com.vehicle.suixing.suixing.util.dataBase.DbDao;
import com.vehicle.suixing.suixing.view.fragment.MusicFragmentView;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by KiSoo on 2016/5/11.
 */
public class MusicFragmentPresenter {
    private Context context;
    private MusicFragmentView view;
    private MusicAdapter adapter;
    private int listPosition = 0; // 标识列表位置
    private boolean isPlaying = false; // 正在播放
    private Messenger messenger = null;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
        }
    };

    public MusicFragmentPresenter(final Context context, MusicFragmentView view) {
        this.context = context;
        this.view = view;
        adapter = new MusicAdapter(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int type, int postion) {
                switch (type) {
                    case MusicAdapter.TYPE_SEARCH:
                        //搜索
//                        context.startActivity(new Intent(context,));
                        break;
                    case MusicAdapter.TYPE_MUSIC:
                        //音乐列表
                        Config.isLocal = true;
                        listPosition = postion - 1;
                        start();
                        break;
                }
            }
        });
        view.setAdapter(adapter);
        /**
         *  更新音乐数据
         */
        if (SpUtils.isFirstLoad(context)) {
            final MaterialDialog remindDialog = new MaterialDialog(context);
            remindDialog
                    .setTitle("提示:")
                    .setMessage("是否加载本地音乐?")
                    .setCanceledOnTouchOutside(false)
                    .setNegativeButton("否", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remindDialog.dismiss();
                        }
                    })
                    .setPositiveButton("是", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //清除提示的Dialog，开始加载数据
                            remindDialog.dismiss();
                            updateList();
                        }
                    })
                    .show();
        }
    }

    /***
     * 刷新音乐信息
     */
    public void updateList() {
        view.setLoad(true);
        final ProgressDialog progressDialog =
                ProgressDialogUtils.from(context)
                        .setCanceledOnTouchOutside(false)
                        .setTitle("提示：")
                        .setMessage("正在加载中，请稍后...")
                        .show();
        Observable.create(new Observable.OnSubscribe<List<Mp3Info>>() {
            @Override
            public void call(Subscriber<? super List<Mp3Info>> subscriber) {
                subscriber.onNext(SuixingApp.mp3Infos);
                progressDialog.dismiss();
                adapter.notifyDataSetChanged();
                view.setLoad(false);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<Mp3Info>>() {
                    @Override
                    public void call(List<Mp3Info> mp3Infos) {
                        DbDao.clearData(context);
                        SuixingApp.mp3Infos = MediaUtil.getMp3Infos(context);
                        DbDao.addPartMusic(SuixingApp.mp3Infos, context);
                    }
                });
    }



    /***
     * 播放/暂停
     */
    public void start() {
        if (SpUtils.isFirstLoad(context)) {
            Toast.makeText(context, "请先导入歌曲", Toast.LENGTH_SHORT).show();
            return;
        }
        Message message = new Message();
        if (isPlaying) {
            isPlaying = false;
            message.obj = false;
        } else {
            isPlaying = true;
            message.obj = true;
            message.arg1 = listPosition;
        }
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /***
     * 下一曲
     */
    public void next() {
        Message message = new Message();
        message.obj = true;
        message.arg1 = ++listPosition;
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void bindService() {
        context.bindService(new Intent(context, MusicPlayService.class), connection, context.BIND_AUTO_CREATE);
    }

    public void unBindService() {
        context.unbindService(connection);
    }




}
