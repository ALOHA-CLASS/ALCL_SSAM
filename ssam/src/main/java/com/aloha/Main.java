package com.aloha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Main"));
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

// 클라이언트와의 데이터 송수신을 처리하는 클래스
class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // 클라이언트와 데이터 송수신을 위한 스트림 설정
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // 클라이언트로부터 메시지 받기
            String clientMessage;
            while ((clientMessage = input.readLine()) != null) {  // 클라이언트가 종료할 때까지 메시지 받기
                System.out.println("클라이언트로부터 받은 메시지: " + clientMessage);

                if (clientMessage.contains("#")) {
                    // # 제거
                    clientMessage = clientMessage.replace("#", "");
                    String audioName = clientMessage;
                    System.out.println("오디오 파일 재생: " + audioName);
                    Platform.runLater(() -> {
                        try {
                            String audio = audioName + ".mp3";
                            Media media = new Media(getClass().getResource(audio).toExternalForm());
                            MediaPlayer mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                // 클라이언트에게 응답 보내기
                output.println("서버로부터의 응답: " + clientMessage);
            }

            // 클라이언트가 연결을 종료하면 연결 종료
            System.out.println("클라이언트 연결 종료.");
        } catch (IOException e) {
            System.err.println("클라이언트와의 연결에서 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 클라이언트와의 연결 종료 및 자원 정리
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
