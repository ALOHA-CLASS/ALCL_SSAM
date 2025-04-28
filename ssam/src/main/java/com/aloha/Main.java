package com.aloha;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public static Scene scene;
    public static MainController mainController;
    public static Map<String, String> actionMap = new ConcurrentHashMap<>();
    public static Map<String, Integer> countMap = new ConcurrentHashMap<>() {{
        put("easy", 0);
        put("hard", 0);
        put("miss", 0);
    }};



    public static int easyCount = 0;
    public static int hardCount = 0;
    public static int missCount = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Parent root = fxmlLoader.load();
        mainController = fxmlLoader.getController();
        scene = new Scene(root);
        stage.setTitle("알클쌤 Server");
        Image icon = new Image("icon.png");
		stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        // 서버를 백그라운드에서 실행하기 위한 스레드
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            // 8080 포트에서 클라이언트 연결 대기
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("서버가 시작되었습니다. 클라이언트를 기다리는 중...");

            while (true) {  // 연결을 계속 유지하도록 무한 루프 사용
                // 클라이언트 연결 수락
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");
                // 클라이언트의 IP 주소 가져오기
                InetAddress clientAddress = clientSocket.getInetAddress();
                String clientIp = clientAddress.getHostAddress();
                // 클라이언트 IP 출력
                System.out.println("클라이언트 IP: " + clientIp);

                // 새로운 스레드로 클라이언트와의 데이터 송수신 처리
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

