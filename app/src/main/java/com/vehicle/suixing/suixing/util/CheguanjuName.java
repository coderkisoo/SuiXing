package com.vehicle.suixing.suixing.util;

import android.util.Log;

import com.vehicle.suixing.suixing.bean.WeiZhang.CheGuanJu;

/**
 * Created by KiSoo on 2016/4/6.
 */
@Deprecated
public class CheguanjuName {
    private static CheGuanJu cheguanju;
    private static String TAG = "CheguanjuName";

    public static CheGuanJu getGuanjuName(String name) {
        String firstLetter = name.substring(0, 1);
        String secondLetter = name.substring(1, 2);
        cheguanju = new CheGuanJu();
        switch (firstLetter) {
            case "京":
                setParams("beijing", 0, 100);
                return cheguanju;
            case "皖":
                setParams("anhui", 0, 6);
                return cheguanju;
            case "闽":
                setParams("fujian", 4, 0);
                return cheguanju;
            case "甘":

                setParams("", 0, 0);
                return cheguanju;
            case "粤":
                setParams("guangdong", 6, 4);
                return cheguanju;
            case "桂":
                setParams("", 6, 4);
                return cheguanju;
            case "贵":
                setParams("guizhou", 0, 6);
                return cheguanju;
            case "琼":
                setParams("hainan", 4, 0);
                return cheguanju;
            case "冀":
                setParams("hebei", 4, 0);
                return cheguanju;
            case "豫":
                setParams("henan", 4, 0);
                return cheguanju;
            case "黑":
                setParams("heilongjiang", 4, 0);
                return cheguanju;
            case "鄂":
                setParams("hubei", 4, 0);
                return cheguanju;
            case "湘":
                setParams("changsha", 0, 5);
                return cheguanju;

            case "吉":
                setParams("jilin", 4, 0);
                return cheguanju;
            case "苏":
                setParams("jiangsu", 0, 6);
                return cheguanju;
            case "赣":
                setParams("jiangxi", 0, 6);
                return cheguanju;
            case "辽":
                //特殊处理
                switch (secondLetter) {
                    case "A":
                        setParams("shenyang", 4, 0);
                        return cheguanju;
                    case "B":
                        setParams("dalian", 4, 0);
                        return cheguanju;
                    case "C":
                        setParams("anshan", 4, 0);
                        return cheguanju;
                    case "E":
                        setParams("benxi", 4, 0);
                        return cheguanju;
                    case "N":
                        setParams("chaoyang", 4, 0);
                        return cheguanju;
                    case "F":
                        setParams("dandong", 4, 0);
                        return cheguanju;
                    case "D":
                        setParams("", 4, 0);
                        return cheguanju;
                    case "J":
                        setParams("fuxin", 0, 100);
                        return cheguanju;
                    case "P":
                        setParams("huludao", 100, 0);
                        return cheguanju;
                    case "G":
                        setParams("jinzhou", 6, 0);
                        return cheguanju;
                    case "K":
                        setParams("liaoyang", 4, 0);
                        return cheguanju;
                    case "H":
                        setParams("yingkou", 6, 0);
                        return cheguanju;
                    default:
                        setParams("shenyang", 4, 0);
                        return cheguanju;
                }
            case "宁":
                setParams("ningxia",0,4);
                return cheguanju;
            case "蒙":
                switch (secondLetter){
                    case "M":
                        setParams("alashan",0,100);
                        return cheguanju;
                    case "L":

                }

        }
        return cheguanju;
    }

    /**
     * 车架号码位数，
     * 发动机号码位数
     */
    private static void setParams(String carorg, int framNum, int engineno) {
        cheguanju.setCarorg(carorg);
        cheguanju.setEngineno(engineno);
        cheguanju.setFrameno(framNum);
        Log.d(TAG, "carorg" + carorg + "frameNo" + framNum + "engineno" + engineno);
    }

}
