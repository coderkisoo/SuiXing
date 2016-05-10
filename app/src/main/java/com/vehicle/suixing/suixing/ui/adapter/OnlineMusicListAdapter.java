package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.model.info.OnLineMusicInfo;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by lenovo on 2016/4/25.
 */
public class OnlineMusicListAdapter extends BaseAdapter {
    private Context context;
    List<OnLineMusicInfo> onLineMusicInfos;
    OnLineMusicInfo onLineMusicInfo;

    /**
     * 构造函数
     * @param context 上下文
     * @param onLineMusicInfos 获取到的在线音乐信息的list
     */
    public OnlineMusicListAdapter(Context context, List<OnLineMusicInfo> onLineMusicInfos) {
        this.context = context;
        this.onLineMusicInfos = onLineMusicInfos;
    }

    /**
     * 拿到获得的音乐list的长度
     * @return
     */
    @Override
    public int getCount() {
        return onLineMusicInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NViewHolder viewHolder = new NViewHolder();
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.online_music_list_item,null);
            viewHolder.music_item_song = (TextView) convertView.findViewById(R.id.music_item_song);
            viewHolder.music_item_singer = (TextView) convertView.findViewById(R.id.music_item_singer);
            viewHolder.music_item_album = (TextView) convertView.findViewById(R.id.music_item_album);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (NViewHolder) convertView.getTag();
        }
        onLineMusicInfo = onLineMusicInfos.get(position);
        viewHolder.music_item_song.setText(onLineMusicInfo.getMusic_name());
        viewHolder.music_item_singer.setText(onLineMusicInfo.getSinger_name());
        viewHolder.music_item_album.setText(onLineMusicInfo.getAlbum_name());
        return convertView;
    }

    /**
     * 定义内部类
     * 实现视图的缓存，优化listView
     */
    public class NViewHolder{
        public TextView music_item_song;
        public TextView music_item_singer;
        public TextView music_item_album;
    }
}
