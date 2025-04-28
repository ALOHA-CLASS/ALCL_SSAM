package com.aloha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;

import com.aloha.service.JsonService;

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
            System.err.println("서버와의 연결 실패");
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
            System.err.println("연결 종료 시 에러");
        }
    }

    
    // 사운드
    @FXML
    void call(ActionEvent event) {
        output.println(JsonService.createJson("선생님", "sound"));
    }

    @FXML
    void ask(ActionEvent event) {
        output.println(JsonService.createJson("질문있어요", "sound"));
    }

    @FXML
    void easy(ActionEvent event) {
        output.println(JsonService.createJson("쉬워요", "action"));
    }

    @FXML
    void hard(ActionEvent event) {
        output.println(JsonService.createJson("어려워요", "action"));
    }

    @FXML
    void help(ActionEvent event) {
        output.println(JsonService.createJson("도와주세요", "sound"));
    }

    @FXML
    void miss(ActionEvent event) {
        output.println(JsonService.createJson("놓쳤어요", "action"));
    }

    // url
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

    // 투표 메서드
    @FXML
    void vote1() {
        output.println(JsonService.createJson("1", "vote"));
    }
    @FXML
    void vote2() {
        output.println(JsonService.createJson("2", "vote"));
    }
    @FXML
    void vote3() {
        output.println(JsonService.createJson("3", "vote"));
    }
    @FXML
    void vote4() {
        output.println(JsonService.createJson("4", "vote"));
    }
    @FXML
    void vote5() {
        output.println(JsonService.createJson("5", "vote"));
    }
    @FXML
    void vote6() {
        output.println(JsonService.createJson("6", "vote"));
    }

    // 리액션
    @FXML
    void heart(ActionEvent event) {
        output.println(JsonService.createJson("하트", "reaction"));
    }

    @FXML
    void hot(ActionEvent event) {
        output.println(JsonService.createJson("더워요", "reaction"));
    }

    @FXML
    void cold(ActionEvent event) {
        output.println(JsonService.createJson("추워요", "reaction"));
    }

    @FXML
    void like(ActionEvent event) {
        output.println(JsonService.createJson("좋아요", "reaction"));
    }

    @FXML
    void noise(ActionEvent event) {
        output.println(JsonService.createJson("소음", "reaction"));
    }

    @FXML
    void air(ActionEvent event) {
        output.println(JsonService.createJson("환기", "reaction"));
    }
}
