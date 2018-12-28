package com.ziroom.ztools.controller;

import com.alibaba.fastjson.JSON;
import com.ziroom.ztools.controller.model.DataValue;
import com.ziroom.ztools.controller.model.JsonData;
import com.ziroom.ztools.controller.model.NetInfo;
import com.ziroom.ztools.controller.mqtt.MQTTClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:zhangbo
 * @Date:2018/6/21 10:21
 */
@Controller
public class MainController {


    //@Autowired
   // private MQTTClient mqttClient;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "commit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> commit(@RequestBody NetInfo info) {
        Map<String, String> map = new HashMap<>();
        try {
            String ip = info.getIp();
            Integer port = info.getPort();
            String ssid = info.getSsid();
            String password = info.getPassword();
            JsonData jsonData = new JsonData();
            List<DataValue> list = new ArrayList<DataValue>();
            DataValue dv_ssid  = new DataValue("ssid",ssid);
            list.add(dv_ssid);
            DataValue dv_password  = new DataValue("password",password);
            list.add(dv_password);

            jsonData.setData(list);

            System.out.println(JSON.toJSONString(jsonData));
            byte[] s = str2Str(JSON.toJSONString(jsonData));
            System.out.println(ip+"  " +port);
            Socket socket = new Socket(ip, port);
            OutputStream os = socket.getOutputStream();
            //os.write(content.getBytes("utf-8"));
            os.write(s);
            os.flush();

            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }

            System.out.println(sb.toString());
            //4、关闭资源
            is.close();
            os.close();
            socket.close();


            map.put("result", sb.toString());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "异常：" + e.getMessage());
            return map;
        }

    }

    /**
     *
     * @param str
     * @return
     */
    public static byte[] str2Str(String str) throws Exception {
        byte[] bs = str.getBytes("utf-8");

        int length = bs.length + 6;
        byte[] resp= new byte[length];
        resp[0] = (byte)((length & 0xFF00)>>8);
        resp[1] =  (byte)(length & 0xFF);
        resp[2] =  0x00;
        resp[3] =  0x21;

        for(int i=0;i<bs.length;i++){
            resp[4+i] = bs[i];
        }
        resp[length -2] = 0x00;
        resp[length -1] = 0x00;

        return resp;
    }

}
