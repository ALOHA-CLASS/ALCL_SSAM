package com.aloha.service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class JsonService {

  public static JSONObject createJson(String msg, String type) {
    JSONObject json = new JSONObject();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String now = dtf.format(LocalDateTime.now());
    try {
      InetAddress ip = InetAddress.getLocalHost();
      String myIp = ip.getHostAddress();
      json.put("ip", myIp);
      json.put("type", type);
      json.put("msg", msg);
      json.put("name", "이준영");
      json.put("date", now);
      System.out.println(json);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return json;
  }
}
