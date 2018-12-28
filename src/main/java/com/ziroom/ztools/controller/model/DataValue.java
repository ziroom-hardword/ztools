package com.ziroom.ztools.controller.model;
import java.net.DatagramSocket;

/**
 * Created by Wiggi on 2018/9/10.
 */
public class DataValue {
        private String k;
        private String v;

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }

    public DataValue(String k, String v)
        {
            this.k = k;
            this.v = v;
        }
}
