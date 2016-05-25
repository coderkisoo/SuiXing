package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.musicInfo.Mp3Info;
import com.vehicle.suixing.suixing.util.musicUtil.MediaUtil;

import java.util.List;

/**
 * Created by lenovo on 2016/4/20.
 */
public class MusicListAdapter extends BaseAdapter {
    private Context context;        //上下文对象引用
    private List<Mp3Info> mp3Infos;    //存放Mp3Info引用的集合
    private Mp3Info mp3Info;        //Mp3Info对象引用
    private int pos = -1;            //列表位置

    /**
     * 构造函数
     *
     * @param context  上下文
     * @param mp3Infos 集合对象
     */
    public MusicListAdapter(Context context, List<Mp3Info> mp3Infos) {
        this.context = context;
        this.mp3Infos = mp3Infos;
    }

    @Override
    public int getCount() {
        return mp3Infos.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.music_list_item, null);
            //viewHolder.albumImage = (ImageView) convertView.findViewById(R.id.albumImage);
            viewHolder.musicTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.musicArtist = (TextView) convertView.findViewById(R.id.tv_artist);
            viewHolder.musicAlbum = (TextView) convertView.findViewById(R.id.tv_album);
            viewHolder.musicDuration = (TextView) convertView.findViewById(R.id.tv_duration);
            convertView.setTag(viewHolder);            //表示给View添加一个格外的数据，
        } else {
            viewHolder = (ViewHolder) convertView.getTag();//通过getTag的方法将数据取出来
        }
        mp3Info = mp3Infos.get(position);
        viewHolder.musicTitle.setText(mp3Info.getFilename());            //显示标题
        viewHolder.musicArtist.setText(mp3Info.getSingername());        //显示艺术家
        viewHolder.musicDuration.setText(MediaUtil.formatTime(mp3Info.getDuration()));//显示时长
        viewHolder.musicAlbum.setText(mp3Info.getAlbum_name());

        return convertView;
    }

    /**
     * 定义一个内部类
     * 声明相应的控件引用
     *
     * @author wwj
     */
    public class ViewHolder {
        //所有控件对象引用
        public TextView musicTitle;        //音乐标题
        public TextView musicDuration;    //音乐时长
        public TextView musicArtist;    //音乐艺术家
        public TextView musicAlbum;
    }
}
