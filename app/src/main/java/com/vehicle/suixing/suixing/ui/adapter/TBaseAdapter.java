package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Field;
import java.util.List;
@Deprecated
/**
 * Created by KiSoo on 2016/4/9.
 * adapter的基类。慎用
 */
public abstract class TBaseAdapter<T> extends BaseAdapter {

    List<T> mList;
    LayoutInflater inflater;
    Context context;
    int[] viewIds;//存放控件Id
    String[] fields;//存放viewholder类变量的名字
    int layoutId;//布局文件id
    Class<?> classOfE;//viewHolder类

    public TBaseAdapter(Context context, List<T> mList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mList = mList;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    public Object initViewHolder(View convertView) {
        try {
            if (fields.length != viewIds.length) {
                return null;
            }
            Object e = classOfE.newInstance();
            int length = fields.length;
            for (int i = 0; i < length; i++) {
                Field field = classOfE.getDeclaredField(fields[i]);//通过反射来获取变量
                field.setAccessible(true);//将变量设成可访问状态
                field.set(e, convertView.findViewById(viewIds[i]));//为变量设置值
                return e;
            }
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object e = null;
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, null);
            e = initViewHolder(convertView);
        }
        if (e != null) {
            convertView.setTag(e);
        } else {
            e = convertView.getTag();
        }
        addDataToView(mList.get(position),e);
        return convertView;
    }

    public abstract void addDataToView(T t, Object o);//抽象方法，实现某些方法用于数集的绑定


}
