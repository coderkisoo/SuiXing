package com.vehicle.suixing.suixing.model.info;

/**
 * Created by lenovo on 2016/4/20.
 */
public class MemberInfo {
    public int    _id;
    public String name;
    public int age;
    public String website;
    public String weibo;
    public MemberInfo(){}
    public MemberInfo(int _id,String name,int age,String website,String weibo){
        this._id = _id;
        this.name = name;
        this.age = age;
        this.website = website;
        this.weibo = weibo;
    }
}
