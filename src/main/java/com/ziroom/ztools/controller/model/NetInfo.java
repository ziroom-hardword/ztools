package com.ziroom.ztools.controller.model;

/**
 * @Author:zhangbo
 * @Date:2018/6/21 12:30
 */
public class NetInfo {
    public String ip;
    public Integer port;
    public String ssid;
    public String password;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
