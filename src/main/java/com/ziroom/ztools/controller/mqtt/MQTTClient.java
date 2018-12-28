package com.ziroom.ztools.controller.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.ztools.controller.model.DataValue;
import com.ziroom.ztools.controller.model.JsonData;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Wiggi on 2018/10/31.
 */
//@Component
public class MQTTClient {
    public static String str="";
    public static final String HOST = "tcp://localhost:1883";
    public static final String TOPIC1 = "local/iot/ziroom/+/+/notify";
    //private static final String broadcastTopic = "local/iot/ziroom/broadcast";
    private static final String clientid = "ziroom";
    private static final String otaAddr = "http://link.zihome.com/ota.bin";
   // private static final String otaAddr = "http://file.ziroom.com/g4m1/M00/05/38/ChAFB1u9cneAROugAAq_-ObKwkc002.bin";
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "admin";
    private String passWord = "password";
    private String oldDevId;
    private int count = 0;
    @SuppressWarnings("unused")
    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void start() throws MqttException {

        System.out.println("connect mqtt server");

        // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        // MQTT的连接设置
        options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置连接的用户名
        options.setUserName(userName);
        // 设置连接的密码
        options.setPassword(passWord.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        options.setAutomaticReconnect(true);
        // 设置回调
        client.setCallback(new MqttCallback(){
            public void connectionLost(Throwable cause) {
                // 连接丢失后，一般在这里面进行重连
//                System.out.println("连接断开，可以做重连");
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete---------" + token.isComplete());
            }
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                try {
                    System.out.println(message.toString());

                  //  JsonData jsonData = (JsonData) JSON.parse(JSON.toJSONString(message.toString()));
                    JSONObject jsonObject  =JSON.parseObject(message.toString());
                    if(jsonObject.getString("msgType").equals("DEVICE_INFO_REPORT"))
                    {

                        String devId = jsonObject.getString("devId");
                        System.out.println(devId + "  " +oldDevId);
                        if(!devId.equals(oldDevId) && count == 1)
                        {
                            oldDevId = devId;
                            count = 0;
                            String controlTopic = "local/iot/ziroom/LIGHT_MQTT_00302/" + devId + "/control";
                            JsonData otaJsonData = new JsonData();
                            otaJsonData.setMsgType("DEVICE_CONTROL");
                            otaJsonData.setDevId(devId);
                            otaJsonData.setAttribute("light_ota");
                            otaJsonData.setCommand("ota");
                            List<DataValue> list = new ArrayList<DataValue>();
                            DataValue addr = new DataValue("addr", otaAddr);
                            list.add(addr);
                            otaJsonData.setData(list);
                            System.out.println(JSON.toJSONString(otaJsonData).toString());
                            client.publish(controlTopic, JSON.toJSONString(otaJsonData).getBytes(), 1, false);
                        }
                        else
                            count++;

                    }
//                  System.out.println(" 从服务器收到的消息为:"+message.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        client.connect(options);
        //订阅消息
        int[] Qos  = {1};
        String[] topic1 = {TOPIC1};
        client.subscribe(topic1, Qos);
    }
    @SuppressWarnings("static-access")
    //用来给调用处返回消息内容
    public String resc() {
        return this.str;
    }

}
