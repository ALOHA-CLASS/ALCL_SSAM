package com.aloha;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.json.JSONObject;
public class Main {
    public static void main(String[] args) {
        InetAddress localAddress;
        try {
            // 내 사설IP 주소
            localAddress = InetAddress.getLocalHost();
            String myPrivateIp = localAddress.getHostAddress();
            System.out.println("My private IP address: " + myPrivateIp);
            // JSON 객체 생성
            JSONObject json = new JSONObject();
            json.put("ip", myPrivateIp);
            json.put("msg", "!선생님");
            // JSON 객체를 문자열로 변환
            String jsonString = json.toString();
            System.out.println("JSON String: " + jsonString);
            // JSON 문자열을 다시 JSONObject로 변환
            JSONObject jsonResponse = new JSONObject(jsonString);
            String ip = jsonResponse.getString("ip");
            String message = jsonResponse.getString("msg");
            System.out.println("IP: " + ip);
            System.out.println("msg: " + message);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}