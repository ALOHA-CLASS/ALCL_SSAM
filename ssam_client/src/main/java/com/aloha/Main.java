package com.aloha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;

    // 창 위치
    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("알클쌤");
        Image icon = new Image("icon.png");
		stage.getIcons().add(icon);
        // 화면 배경 투명
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        // 클릭 이벤트 : 클릭 위치 동기화
        root.setOnMousePressed( event -> {
            x = event.getSceneX();
            y = event.getSceneY();
            root.setCursor(Cursor.OPEN_HAND);
        });
        // 투명창 드래그
        root.setOnMouseDragged( event -> {
            stage.setX( event.getScreenX() - x );
            stage.setY( event.getScreenY() - y );
            root.setCursor(Cursor.CLOSED_HAND);
        });
        // 커서 초기화
        root.setOnMouseReleased(event -> {
            root.setCursor(Cursor.DEFAULT);
        });

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}


class ClientSocketThread extends Thread {

    private final String serverIp;
    private final int serverPort;

    public ClientSocketThread(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(serverIp, serverPort);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)) {

            System.out.println("서버에 연결되었습니다.");

            // 메시지 전송 예시
            output.println("Hello, Server!");

            String serverResponse = input.readLine();
            System.out.println("서버로부터 응답: " + serverResponse);

            while (true) {
                System.out.print("서버에 보낼 메시지를 입력하세요: ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) break;

                output.println(message);
                String response = input.readLine();
                System.out.println("서버 응답: " + response);
            }

            System.out.println("클라이언트 연결 종료.");

        } catch (IOException e) {
            System.err.println("클라이언트 오류: " + e.getMessage());
        }
    }
}