package com.ziroom.ztools.controller;

import com.ziroom.ztools.controller.model.NetInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:zhangbo
 * @Date:2018/6/21 10:21
 */
@Controller
public class MainController {

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
            String content = info.getContent();

            String s = str2HexStr(content);

            Socket socket = new Socket(ip, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            printWriter.write(content);
            printWriter.flush();

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            System.out.println(sb.toString());
            //4、关闭资源
            inputStream.close();
            printWriter.close();
            outputStream.close();
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
     * 字符串转换成为16进制(无需Unicode编码)
     *
     * @param str
     * @return
     */
    public static String str2HexStr(String str) throws Exception {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");

        byte[] bs = str.getBytes("utf-8");
        sb.append(String.format("%04x", (bs.length + 6)));
        sb.append("0021");

        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        sb.append("0000");
        return sb.toString().trim();
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

}
