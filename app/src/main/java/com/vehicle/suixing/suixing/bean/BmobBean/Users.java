package com.vehicle.suixing.suixing.bean.BmobBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class Users extends BmobObject{
    private String username;
    private String password;
    private String tel;
    private String name;
    private BmobFile head;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getHead() {
        return head;
    }

    public void setHead(BmobFile head) {
        this.head = head;
    }
}
