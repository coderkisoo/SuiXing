package com.vehicle.suixing.suixing.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by k1soo on 2015/10/25.
 */
public class MD5Utils {
    public static String ecoder(String password) {
//        //信息摘要器
//        try {
//            MessageDigest digest = MessageDigest.getInstance("MD5");
//
//            digest = MessageDigest.getInstance("md5");
//            //变成byte数组
//            byte bytes[] = digest.digest(password.getBytes());
//            StringBuffer buffer = new StringBuffer();
//            //每一个byte和8个二进制位做'与'运算
//
//            for (byte b : bytes) {
//                int number = b & 0xff;//加盐
//                //把inte类型转化成16进制
//                String numberStr = Integer.toHexString(number);
//                //不足8位的补全
//                if (numberStr.length() == 1) {
//                    buffer.append("0");
//                }
//                buffer.append(numberStr);
//            }
//            return buffer.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//
//            return "";
//        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = new byte[0];
        try {
            byteArray = password.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }
}
