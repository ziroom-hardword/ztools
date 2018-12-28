package com.ziroom.ztools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.ztools.controller.model.JsonData;

/**
 * Created by Wiggi on 2018/10/31.
 */
public class Test {
    @org.junit.Test
    public void jsonConvert()
    {
        String str1 = "{\"msgType\":\"DEVICE_INFO_REPORT\",\"devId\":\"002629007EB7\",\"domain\":\"ZIROOM\",\"manufacturer\":\"JX1\",\"prodTypeId\":\"LIGHT_MQTT_00302\",\"model\":\"00001\",\"softversion\":\"1.0.2\"}";

        JSONObject beeAuthObject  =JSON.parseObject(str1);
        //JsonData jsonData = (JsonData) JSON.parse(str1);
    }
}
