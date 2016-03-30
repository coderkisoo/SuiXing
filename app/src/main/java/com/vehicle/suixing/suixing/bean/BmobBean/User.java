package com.vehicle.suixing.suixing.bean.BmobBean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class User extends BmobUser {
    private String name;
    private BmobFile head;
    private String motto;

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
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
