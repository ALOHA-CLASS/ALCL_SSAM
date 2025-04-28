package com.aloha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Main"));
        stage.setScene(scene);
        stage.setTitle("알클쌤");
        Image icon = new Image("icon.png");
		stage.getIcons().add(icon);
        stage.show();

        // 클라이언트 소켓 쓰레드 실행
        // ClientSocketThread clientThread = new ClientSocketThread("192.168.30.11", 8080);
        // clientThread.setDaemon(true); // JavaFX 종료 시 자동 종료
        // clientThread.start();
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