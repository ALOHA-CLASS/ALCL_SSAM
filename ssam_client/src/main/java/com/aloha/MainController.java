package com.aloha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

    Socket socket;
    BufferedReader input;
    PrintWriter output;

    @FXML
    void initialize() {
        conenct();
    }

    void conenct() {
        try {
            // 서버에 연결 (localhost:8080)
            socket = new Socket("192.168.30.11", 8080);
            System.out.println("서버에 연결되었습니다.");

            // 서버와 데이터 송수신을 위한 스트림 설정
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);


        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    void close() {
        try {
            // 연결 종료
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @FXML
    void call(ActionEvent event) {
        System.out.println("선생님 버튼 클릭");
        // 서버에 메시지 보내기
        output.println("#선생님");
    }
        
    // 서버로부터 응답 받기
    // String serverResponse = input.readLine();
    // System.out.println("서버로부터 받은 응답: " + serverResponse);

    @FXML
    void ask(ActionEvent event) {
        System.out.println("질문있어요 버튼 클릭");
        // 서버에 메시지 보내기
        output.println("#질문있어요");
    }

    @FXML
    void easy(ActionEvent event) {
        // 쉬워요
        System.out.println("쉬워요 버튼 클릭");
        // 서버에 메시지 보내기
        output.println("#쉬워요");
    }

    @FXML
    void hard(ActionEvent event) {
        // 어려워요
        System.out.println("어려워요 버튼 클릭");
        // 서버에 메시지 보내기
        output.println("#어려워요");
    }

    @FXML
    void help(ActionEvent event) {
        // 도와주세요
        System.out.println("도와주세요 버튼 클릭");
        // 서버에 메시지 보내기
        output.println("#도와주세요");
    }

    @FXML
    void miss(ActionEvent event) {
        // 놓쳤어요
        System.out.println("놓쳤어요 버튼 클릭");
        // 서버에 메시지 보내기
        output.println("#놓쳤어요");
    }


    @FXML
    void air(ActionEvent event) {

    }

    @FXML
    void alcl(ActionEvent event) {
        String url = "https://xn--pe5b27r.com/";
        try {
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri);
            System.out.println("알클.com URL 실행 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cold(ActionEvent event) {

    }

    @FXML
    void github(ActionEvent event) {
        String url = "https://github.com/ALOHA-CLASS";
        try {
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri);
            System.out.println("GITHUB URL 실행 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gpt(ActionEvent event) {
        String url = "https://chatgpt.com/";
        try {
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri);
            System.out.println("GPT URL 실행 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void heart(ActionEvent event) {

    }


    @FXML
    void hot(ActionEvent event) {

    }

    @FXML
    void like(ActionEvent event) {

    }

    @FXML
    void noise(ActionEvent event) {

    }

    @FXML
    void notion(ActionEvent event) {
        String url = "https://www.notion.so/";
        try {
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri);
            System.out.println("notion URL 실행 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void oopy(ActionEvent event) {
        String url = "https://wwwaloha.oopy.io/";
        try {
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri);
            System.out.println("OOPY URL 실행 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void zoom(ActionEvent event) {
        String url = "https://us02web.zoom.us/j/85670468377?pwd=aNbUqoGbiDUumTlTmJyxbbRzqOxBzO.1";
        try {
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri);
            System.out.println("Zoom URL 실행 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
