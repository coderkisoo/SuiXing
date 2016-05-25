package com.vehicle.suixing.suixing.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.musicInfo.Mp3Info;
import com.vehicle.suixing.suixing.util.musicUtil.MediaUtil;

/**
 * Created by KiSoo on 2016/4/11.
 */

public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_SEARCH = 3001;
    public final static int TYPE_MUSIC = 3002;

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View view,int type,int postion);
    }

    public MusicAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case TYPE_SEARCH:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_search,parent,false);
                return new SearchHolder(v);
            case TYPE_MUSIC:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item, parent, false);
                return new MusicListHolder(v);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position!=0){
            MusicListHolder musicListHolder = (MusicListHolder) holder;
            Mp3Info info = SuixingApp.mp3Infos.get(position - 1);
            musicListHolder.getTv_album().setText(info.getAlbum_name());
            musicListHolder.getTv_artist().setText(info.getSingername());
            musicListHolder.getTv_duration().setText(MediaUtil.formatTime(info.getDuration()));
            musicListHolder.getTv_title().setText(info.getFilename());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, TYPE_MUSIC, position);
                }
            });
        }else {
            SearchHolder searchHolder = (SearchHolder) holder;
            searchHolder.getRl_search().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,TYPE_SEARCH,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return SuixingApp.mp3Infos.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (0 == position) {
            type = TYPE_SEARCH;
        } else {
            type = TYPE_MUSIC;
        }
        return type;
    }

    /**
     * 第一个是搜索框
     */
    private class SearchHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_search;

        public SearchHolder(View itemView) {
            super(itemView);
            rl_search = (RelativeLayout) itemView.findViewById(R.id.rl_search);
        }

        public RelativeLayout getRl_search() {
            return rl_search;
        }
    }

    /**
     * 第二个是音乐信息列表
     */
    private class MusicListHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;        //音乐标题
        private TextView tv_duration;    //音乐时长
        private TextView tv_artist;    //音乐艺术家
        private TextView tv_album;      //专辑名


        public MusicListHolder(View itemView) {
            super(itemView);
            tv_duration = (TextView) itemView.findViewById(R.id.iv_vehicle_img);
            tv_album = (TextView) itemView.findViewById(R.id.tv_album);
            tv_artist = (TextView) itemView.findViewById(R.id.tv_artist);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
        public TextView getTv_title() {
            return tv_title;
        }

        public TextView getTv_duration() {
            return tv_duration;
        }

        public TextView getTv_artist() {
            return tv_artist;
        }

        public TextView getTv_album() {
            return tv_album;
        }
    }

}
