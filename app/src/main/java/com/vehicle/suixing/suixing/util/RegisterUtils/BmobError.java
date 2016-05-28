package com.vehicle.suixing.suixing.util.RegisterUtils;


import com.vehicle.suixing.suixing.util.Log;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class BmobError {
    /**
     * 这个类用来解析返回的异常
     * */
    private static String TAG = BmobError.class.getName();

    public static String error(int code) {
        Log.e(TAG, "本次解析的异常号是:" + code);
        switch (code) {
            case 9001:
                Log.e(TAG, "AppKey is Null, Please initialize BmobSDK.Application Id为空，请初始化.");
                return "Application Id为空，请初始化.";

            case 9002:
                Log.e(TAG, "Parse data error.解析返回数据出错.");
                return "解析返回数据出错.";

            case 9003:
                Log.e(TAG, "upload file error.上传文件出错.");
                return "上传文件出错";

            case 9004:
                Log.e(TAG, "upload file failure 文件上传失败");
                return "文件上传失败.";

            case 9005:
                Log.e(TAG, "A batch operation can not be more than 50 批量操作只支持最多50条");
                return "批量操作只支持最多50条";

            case 9006:
                Log.e(TAG, "objectId is null objectId为空.");
                return "objectId为空.";

            case 9007:
                Log.e(TAG, "BmobFile File size must be less than 10M. 文件大小超过10M.");
                return "文件大小超过10M.";

            case 9008:
                Log.e(TAG, "\tBmobFile File does not exist.\t上传文件不存在");
                return "上传文件不存在.";

            case 9009:
                Log.e(TAG, "No cache data.没有缓存数据");
                return "没有缓存数据.";

            case 9010:
                Log.e(TAG, "The network is not normal.(Time out) 网络超时.");
                return "网络超时...";

            case 9011:
                Log.e(TAG, "BmobUser does not support batch operations.BmobUser类不支持批量操作.");
                return "BmobUser类不支持批量操作.";

            case 9012:
                Log.e(TAG, "context is null.上下文为空.");
                return "上下文为空.";

            case 9013:
                Log.e(TAG, "BmobObject Object names(database table name) format is not correct.BmobObject（数据表名称）格式不正确.");
                return "BmobObject（数据表名称）格式不正确.";

            case 9014:
                Log.e(TAG, "第三方账号授权失败 第三方账号授权失败.");
                return "第三方账号授权失败.";

            case 9015:
                Log.e(TAG, "其他错误均返回此code 其他错误均返回此code.");
                return "其他错误均返回此code.";

            case 9016:
                Log.e(TAG, "The network is not available,please check your network!无网络连接，请检查您的手机网络..");
                return "无网络连接，请检查您的手机网络...";

            case 9017:
                Log.e(TAG, "与第三方登录有关的错误，具体请看对应的错误描述\t与第三方登录有关的错误，具体请看对应的错误描述");
                return "与第三方登录有关的错误，具体请看对应的错误描述";

            case 9018:
                Log.e(TAG, "参数不能为空 参数不能为空..");
                return "参数不能为空...";

            case 9019:
                Log.e(TAG, "格式不正确：手机号码、邮箱地址、验证码 格式不正确：手机号码、邮箱地址、验证码");
                return "手机号码格式不正确...";

            case -1:
                Log.e(TAG, "微信返回的错误码，可能是未安装微信，也可能是微信没获得网络权限等");
                return "微信返回的错误码，可能是未安装微信，也可能是微信没获得网络权限等";

            case -2:
                Log.e(TAG, "微信支付用户中断操作");
                return "微信支付用户中断操作";
            case -3:
                Log.e(TAG, "未安装微信支付插件");
                return "未安装微信支付插件";
            case 102:
                Log.e(TAG, "设置了安全验证，但是签名或IP不对");
                return "设置了安全验证，但是签名或IP不对";
            case 6001:
                Log.e(TAG, "支付宝支付用户中断操作");
                return "支付宝支付用户中断操作";
            case 4000:
                Log.e(TAG, "支付宝支付出错，可能是参数有问题");
                return "支付宝支付出错，可能是参数有问题";
            case 10010:
                Log.e(TAG, "mobile '%s' send message limited.\t该手机号发送短信达到限制(对于一个应用来说，一天给同一手机号发送短信不能超过10条，一小时给同一手机号发送短信不能超过5条，一分钟给同一手机号发送短信不能超过1条)");
                return "该手机号发送短信数量达到限制";
            case 10011:
                Log.e(TAG,"no remaining number for send messages.\t该账户无可用的发送短信条数");
                return "该账户无可用的发送短信条数";
            case 10012:
                Log.e(TAG,"your credit info must verify ok.\t身份信息必须审核通过才能使用该功能");
                return "身份信息必须审核通过才能使用该功能";
            case 10013:
                Log.e(TAG,"sms content illegal.\t非法短信内容");
                return "非法短信内容";
            case 108:
                Log.e(TAG,"username and password required.\t用户名和密码是必需的");
                return "用户名和密码是必需的";
            case 101:
                Log.e(TAG,"username or password incorrect");
                return "用户名或密码错误,请重新登录";
            default:
                Log.e(TAG,"错误码为:"+code);
                return "错误码"+code+"";
        }
    }
}
