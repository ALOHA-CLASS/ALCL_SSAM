package com.aloha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    void call(ActionEvent event) {

        try {
            // 서버에 연결 (localhost:8080)
            Socket socket = new Socket("192.168.30.11", 8080);
            System.out.println("서버에 연결되었습니다.");

            // 서버와 데이터 송수신을 위한 스트림 설정
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // 서버에 메시지 보내기
            String message = "Hello, Server!";
            output.println("call");
            System.out.println("서버로 보낸 메시지: " + message);

            // 서버로부터 응답 받기
            String serverResponse = input.readLine();
            System.out.println("서버로부터 받은 응답: " + serverResponse);

            // 연결 종료
            input.close();
            output.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
