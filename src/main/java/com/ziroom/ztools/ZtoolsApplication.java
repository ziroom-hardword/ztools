package com.ziroom.ztools;

import com.ziroom.ztools.controller.mqtt.MQTTClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZtoolsApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZtoolsApplication.class, args);
    }
}
