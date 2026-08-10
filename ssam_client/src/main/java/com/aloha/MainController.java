package com.aloha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.prefs.Preferences;

import com.aloha.service.JsonService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {

    private static final String PREF_KEY_IP = "server_ip";
    private static final String DEFAULT_IP = "192.168.0.0";
    private static final int PORT = 8888;

    private final Preferences prefs = Preferences.userNodeForPackage(MainController.class);

    Socket socket;
    BufferedReader input;
    PrintWriter output;

    @FXML
    void initialize() {
        connect();
    }

    void connect() {
        String ip = prefs.get(PREF_KEY_IP, DEFAULT_IP);
        try {
            socket = new Socket(ip, PORT);
            System.out.println("서버에 연결되었습니다. (" + ip + ")");
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            System.err.println("서버와의 연결 실패: " + ip);
        }
    }

    void close() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("연결 종료 시 에러");
        }
    }

    @FXML
    void openSettings(ActionEvent event) {
        String currentIp = prefs.get(PREF_KEY_IP, DEFAULT_IP);

        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.initStyle(StageStyle.TRANSPARENT);
        settingsStage.setTitle("서버 IP 설정");

        Label titleLabel = new Label("서버 IP 주소 설정");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label ipLabel = new Label("IP:");
        ipLabel.setStyle("-fx-text-fill: white;");

        TextField ipField = new TextField(currentIp);
        ipField.setPrefWidth(180);
        ipField.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-prompt-text-fill: rgba(255,255,255,0.5); -fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        ipField.setPromptText("예: 192.168.0.1");

        HBox ipRow = new HBox(8, ipLabel, ipField);
        ipRow.setAlignment(Pos.CENTER_LEFT);

        Button saveBtn = new Button("저장 후 재연결");
        saveBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 5px;");
        saveBtn.setDefaultButton(true);
        saveBtn.setOnAction(e -> {
            String newIp = ipField.getText().trim();
            if (!newIp.isEmpty()) {
                prefs.put(PREF_KEY_IP, newIp);
                close();
                connect();
            }
            settingsStage.close();
        });

        Button cancelBtn = new Button("취소");
        cancelBtn.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 5px;");
        cancelBtn.setCancelButton(true);
        cancelBtn.setOnAction(e -> settingsStage.close());

        HBox btnRow = new HBox(8, saveBtn, cancelBtn);
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(12, titleLabel, ipRow, btnRow);
        root.setPadding(new Insets(16));
        root.setAlignment(Pos.CENTER_LEFT);
        root.setStyle("-fx-background-color: rgba(20,20,20,0.92); -fx-border-color: rgba(255,255,255,0.6); -fx-border-width: 1px; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        settingsStage.setScene(scene);
        settingsStage.showAndWait();
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
        String url = "https://us02web.zoom.us/j/82055043371?pwd=SPvxcKITteQrtwtelZab3ZIDMUF0bZ.1";
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
