package com.vehicle.suixing.suixing.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.view.fragment.MeFragmentView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by KiSoo on 2016/4/11.
 */

public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_ME_INFO = 0;
    private static final int TYPE_ADD_BUTTON = 1;
    private static final int TYPE_ITEM = 2;
    private static int total = 0;
    private List<VehicleInformation> list;

    private User users;
    private MeFragmentView view;

    public InfoAdapter(List<VehicleInformation> list, User user, MeFragmentView view) {
        this.list = list;
        this.users = user;
        this.view = view;
        total = list.size() + 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case TYPE_ME_INFO:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.me_info, parent, false);
                return new InfoViewHolder(v);

            case TYPE_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item, parent, false);
                return new ListViewHolder(v);

            case TYPE_ADD_BUTTON:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_layout, parent, false);
                return new TextViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
            ImageLoader.getInstance().displayImage(users.getHead(), infoViewHolder.getCiv_head());
            infoViewHolder.getTv_name().setText(users.getName());
            infoViewHolder.getTv_motto().setText(users.getMotto());
        } else if (total - 1 == position) {
            //添加按钮
        } else {

            ListViewHolder listViewHolder = (ListViewHolder) holder;
            VehicleInformation info = list.get(position - 1);
            ImageLoader.getInstance().displayImage(info.getUrl(), listViewHolder.getIv_vehicle_img());
            listViewHolder.getTv_vehicle_name().setText(info.getName());
            listViewHolder.getTv_vehicle_model().setText(info.getModel());
            listViewHolder.getTv_vehicle_num().setText(info.getNum());
            listViewHolder.getTv_vehicle_percent().setText(info.getPercent());
            switch (position % 4) {
                case 0:
                    listViewHolder.getTv_vehicle_name().setBackgroundResource(R.mipmap.event_background);
                    break;

                case 1:
                    listViewHolder.getTv_vehicle_name().setBackgroundResource(R.mipmap.event_background1);
                    break;

                case 2:
                    listViewHolder.getTv_vehicle_name().setBackgroundResource(R.mipmap.event_background2);
                    break;
                case 3:
                    listViewHolder.getTv_vehicle_name().setBackgroundResource(R.mipmap.event_background3);
                    break;
                default:
                    listViewHolder.getTv_vehicle_name().setBackgroundResource(R.mipmap.event_background3);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return total;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (0 == position) {
            type = TYPE_ME_INFO;
        }
        //总数等于位置加1，判定为底部添加的button
        else if (total - 1 == position) {
            type = TYPE_ADD_BUTTON;
        } else {
            type = TYPE_ITEM;
        }
        return type;

    }


    /**
     * 第一个是个人信息
     */
    private class InfoViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_motto;
        private CircleImageView civ_head;

        public InfoViewHolder(View itemView) {
            super(itemView);
            civ_head = (CircleImageView) itemView.findViewById(R.id.civ_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_motto = (TextView) itemView.findViewById(R.id.tv_motto);
            civ_head.setOnClickListener(InfoAdapter.this);
            tv_name.setOnClickListener(InfoAdapter.this);
            tv_motto.setOnClickListener(InfoAdapter.this);

        }

        public TextView getTv_name() {
            return tv_name;
        }

        public TextView getTv_motto() {
            return tv_motto;
        }

        public CircleImageView getCiv_head() {
            return civ_head;
        }
    }

    /**
     * 第二个是listview列表
     */
    private class ListViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_vehicle_img;
        private TextView tv_vehicle_name;
        private TextView tv_vehicle_num;
        private TextView tv_vehicle_model;
        private TextView tv_vehicle_percent;

        public ListViewHolder(android.view.View itemView) {
            super(itemView);
            iv_vehicle_img = (ImageView) itemView.findViewById(R.id.iv_vehicle_img);
            tv_vehicle_name = (TextView) itemView.findViewById(R.id.tv_vehicle_name);
            tv_vehicle_num = (TextView) itemView.findViewById(R.id.tv_vehicle_num);
            tv_vehicle_model = (TextView) itemView.findViewById(R.id.tv_vehicle_model);
            tv_vehicle_percent = (TextView) itemView.findViewById(R.id.tv_vehicle_percent);
        }

        public ImageView getIv_vehicle_img() {
            return iv_vehicle_img;
        }

        public TextView getTv_vehicle_name() {
            return tv_vehicle_name;
        }

        public TextView getTv_vehicle_num() {
            return tv_vehicle_num;
        }

        public TextView getTv_vehicle_model() {
            return tv_vehicle_model;
        }

        public TextView getTv_vehicle_percent() {
            return tv_vehicle_percent;
        }
    }

    /**
     * 第三个是textview
     */
    private class TextViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_add_success;

        public TextViewHolder(View itemView) {
            super(itemView);
            tv_add_success = (TextView) itemView.findViewById(R.id.tv_add_success);
            tv_add_success.setOnClickListener(InfoAdapter.this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_head:
                view.editHead();
                break;
            case R.id.tv_motto:
                view.editMotto();

                break;
            case R.id.tv_name:
                view.editName();
                break;
            case R.id.tv_add_success:
                view.addVehicle();
                break;
        }
    }

}
