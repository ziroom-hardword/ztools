package com.ziroom.ztools.controller.model;

import java.util.List;

/**
 * Created by Wiggi on 2018/9/10.
 */



public class JsonData {
    private  String msgType = "DEVICE_CONTROL";
    private  String devId = "123456";
    private  String prodTypeId = "LIGHT_MQTT_00302";
    private  String sno = "123456";
    private  String command = "wifi_config";
    private  String attribute = "light_wifi";
    private  String domain;
    private  String manufacturer;
    private  String model;
    private  String softversion;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSoftversion() {
        return softversion;
    }

    public void setSoftversion(String softversion) {
        this.softversion = softversion;
    }

    private List<DataValue> data;

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getProdTypeId() {
        return prodTypeId;
    }

    public void setProdTypeId(String prodTypeId) {
        this.prodTypeId = prodTypeId;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public  String getMsgType() {
        return msgType;
    }

    public  String getDevId() {
        return devId;
    }

    public  String getSno() {
        return sno;
    }

    public  String getCommand() {
        return command;
    }

    public  String getAttribute() {
        return attribute;
    }

    public List<DataValue> getData() {
        return data;
    }

    public void setData(List<DataValue> data) {
        this.data = data;
    }
}
