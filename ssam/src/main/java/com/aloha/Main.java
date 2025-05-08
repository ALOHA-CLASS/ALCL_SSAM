package com.aloha;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static Scene scene;
    public static MainController mainController;
    // Action
    // { ip : msg }
    // { "192.168.30.10" : "쉬워요" }
    public static Map<String, String> actionMap = new ConcurrentHashMap<>();
    // 쉬워요 여려워요 놓쳤어요
    public static AtomicInteger easyCount = new AtomicInteger(0);
    public static AtomicInteger hardCount = new AtomicInteger(0);
    public static AtomicInteger missCount = new AtomicInteger(0);
    // Reaction
    public static Map<String, String> reactionMap = new ConcurrentHashMap<>();
    public static AtomicInteger hotCount = new AtomicInteger(0);
    public static AtomicInteger coldCount = new AtomicInteger(0);
    public static AtomicInteger airCount = new AtomicInteger(0);
    public static AtomicInteger noiseCount = new AtomicInteger(0);
    public static AtomicInteger likeCount = new AtomicInteger(0);
    public static AtomicInteger heartCount = new AtomicInteger(0);
    // Vote
    public static Map<String, String> voteMap = new ConcurrentHashMap<>();
    public static AtomicInteger vote1Count = new AtomicInteger(0);
    public static AtomicInteger vote2Count = new AtomicInteger(0);
    public static AtomicInteger vote3Count = new AtomicInteger(0);
    public static AtomicInteger vote4Count = new AtomicInteger(0);
    public static AtomicInteger vote5Count = new AtomicInteger(0);
    public static AtomicInteger vote6Count = new AtomicInteger(0);


    // 창 위치
    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Parent root = fxmlLoader.load();
        mainController = fxmlLoader.getController();
        scene = new Scene(root);
        stage.setTitle("알클쌤 Server");
        Image icon = new Image("icon.png");
		stage.getIcons().add(icon);
        stage.setScene(scene);

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

        // 서버를 백그라운드에서 실행하기 위한 스레드
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            // 8888 포트에서 클라이언트 연결 대기
            ServerSocket serverSocket = new ServerSocket(8888);
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

