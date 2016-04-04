package com.vehicle.suixing.suixing.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by k1soo on 2015/10/25.
 */
public class MD5Utils {
    public static String ecoder(String password) {
        //信息摘要器
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            digest = MessageDigest.getInstance("md5");
            //变成byte数组
            byte bytes[] = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            //每一个byte和8个二进制位做'与'运算

            for (byte b : bytes) {
                int number = b & 0xff;//加盐
                //把inte类型转化成16进制
                String numberStr = Integer.toHexString(number);
                //不足8位的补全
                if (numberStr.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(numberStr);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

            return "";
        }


    }
}
