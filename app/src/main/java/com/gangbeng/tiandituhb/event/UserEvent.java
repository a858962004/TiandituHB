package com.gangbeng.tiandituhb.event;

import java.io.Serializable;

/**
 * @author zhanghao
 * @date 2018-10-17
 */

public class UserEvent implements Serializable {
    private String loginname;
    private String username;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
