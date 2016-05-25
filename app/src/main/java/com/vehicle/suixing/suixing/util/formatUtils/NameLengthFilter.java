package com.vehicle.suixing.suixing.util.formatUtils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

/**
 * Created by KiSoo on 2016/2/13.
 */
public class NameLengthFilter implements InputFilter {
    private Context context;
    int MAX_EN;// 最大英文/数字长度 一个汉字算两个字母
    int destCount,sourceCount = 0;

    public NameLengthFilter(int mAX_EN, Context context) {
        super();
        this.context = context;
        MAX_EN = mAX_EN;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        destCount = dest.toString().length()
                + DensityUtil.getChineseCount(dest.toString());
        sourceCount = source.toString().length()
                + DensityUtil.getChineseCount(source.toString());
        if (source.equals(" ") || source.toString().contentEquals("\n")) return "";
        else if (destCount + sourceCount > MAX_EN) {
            Toast.makeText(context, "超过最大字数限制啦",
                    Toast.LENGTH_SHORT).show();
            return "";

        } else {
            return source;
        }
    }


    public int getLength(){
        return destCount+sourceCount;
    }
}