package com.aloha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

                String ip = "";
                String msg = "";
                String type = "";
                String name = "";
                String date = "";

                try {
                    // JSON 파싱
                    JSONObject json = new JSONObject(clientMessage);
                    ip = json.getString("ip");
                    msg = json.getString("msg");
                    type = json.getString("type");
                    name = json.getString("name");
                    date = json.getString("date");
                    System.out.println("JSON 파싱");
                    System.out.println("ip: " + ip);
                    System.out.println("msg: " + msg);
                    System.out.println("type: " + type);
                    System.out.println("name: " + name);
                    System.out.println("date: " + date);
                } catch (Exception e) {
                    System.err.println("JSON 파싱 오류: " + e.getMessage());
                }

                // sound : 선생님, 질문있어요, 도와주세요
                if (type.equals("sound")) {
                    // # 제거
                    String audioName = msg;
                    System.out.println("sound: " + audioName);
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

                // action : 쉬워요, 어려워요, 놓쳤어요
                if(type.equals("action")) {
                    Main.actionMap.put(ip, msg);
                    msg = Main.actionMap.get(ip);

                    System.out.println(Main.actionMap);

                    int localEasyCount = 0;
                    int localHardCount = 0;
                    int localMissCount = 0;
                    Main.easyCount = 0;
                    Main.hardCount = 0;
                    Main.missCount = 0;
                    for (String value : Main.actionMap.values()) {
                        switch (value) {
                            case "쉬워요":
                                // localEasyCount++;
                                Main.easyCount++;
                                break;
                            case "어려워요":
                                // localHardCount++;
                                Main.hardCount++;
                                break;
                            case "놓쳤어요":
                                // localMissCount++;
                                Main.missCount++;
                                break;
                        }
                    }
                    // Main.easyCount = localEasyCount;
                    // Main.hardCount = localHardCount;
                    // Main.missCount = localMissCount;

                    System.out.println("쉬워요: " + Main.easyCount + ", 어려워요: " + Main.hardCount + ", 놓쳤어요: " + Main.missCount);

                    switch (msg) {
                        case "쉬워요":
                            Platform.runLater(() -> {
                                Main.mainController.lb_easy.setText( String.valueOf( Main.easyCount ) );
                            });
                            break;
                        case "어려워요":
                            Platform.runLater(() -> {
                                Main.mainController.lb_hard.setText( String.valueOf( Main.hardCount ) );
                            });
                            break;
                        case "놓쳤어요":
                            Platform.runLater(() -> {
                                Main.mainController.lb_miss.setText( String.valueOf( Main.missCount ) );
                            });
                            break;
                    }
                }


                // reaction : 더워요, 추워요, 환기, 소음, 좋아요, 하트
                if (type.equals("reaction")) {
                    switch (msg) {
                        case "더워요":
                            Platform.runLater(() -> {
                                Integer hot = Integer.parseInt( Main.mainController.lb_hot.getText() );
                                Main.mainController.lb_hot.setText( String.valueOf( hot + 1 ) );
                            });
                            break;
                        case "추워요":
                            Platform.runLater(() -> {
                                Integer cold = Integer.parseInt( Main.mainController.lb_cold.getText() );
                                Main.mainController.lb_cold.setText( String.valueOf( cold + 1 ) );
                            });
                            break;
                        case "환기":
                            Platform.runLater(() -> {
                                Integer air = Integer.parseInt( Main.mainController.lb_air.getText() );
                                Main.mainController.lb_air.setText( String.valueOf( air + 1 ) );
                            });
                            break;
                        case "소음":
                            Platform.runLater(() -> {
                                Integer noise = Integer.parseInt( Main.mainController.lb_noise.getText() );
                                Main.mainController.lb_noise.setText( String.valueOf( noise + 1 ) );
                            });
                            break;
                        case "좋아요":
                            Platform.runLater(() -> {
                                Integer like = Integer.parseInt( Main.mainController.lb_like.getText() );
                                Main.mainController.lb_like.setText( String.valueOf( like + 1 ) );
                            });
                            break;
                        case "하트":
                            Platform.runLater(() -> {
                                Integer heart = Integer.parseInt( Main.mainController.lb_heart.getText() );
                                Main.mainController.lb_heart.setText( String.valueOf( heart + 1 ) );
                            });
                            break;
                    }
                    
                }

                // vote : 1 2 3 4 5 6 
                if (type.equals("vote")) {
                    switch (msg) {
                        case "1":
                            Platform.runLater(() -> {
                                Integer vote1 = Integer.parseInt( Main.mainController.lb_vote1.getText() );
                                Main.mainController.lb_vote1.setText( String.valueOf( vote1 + 1 ) );
                            });
                            break;
                        case "2":
                            Platform.runLater(() -> {
                                Integer vote2 = Integer.parseInt( Main.mainController.lb_vote2.getText() );
                                Main.mainController.lb_vote2.setText( String.valueOf( vote2 + 1 ) );
                            });
                            break;
                        case "3":
                            Platform.runLater(() -> {
                                Integer vote3 = Integer.parseInt( Main.mainController.lb_vote3.getText() );
                                Main.mainController.lb_vote3.setText( String.valueOf( vote3 + 1 ) );
                            });
                            break;
                        case "4":
                            Platform.runLater(() -> {
                                Integer vote4 = Integer.parseInt( Main.mainController.lb_vote4.getText() );
                                Main.mainController.lb_vote4.setText( String.valueOf( vote4 + 1 ) );
                            });
                            break;
                        case "5":
                            Platform.runLater(() -> {
                                Integer vote5 = Integer.parseInt( Main.mainController.lb_vote5.getText() );
                                Main.mainController.lb_vote5.setText( String.valueOf( vote5 + 1 ) );
                            });
                            break;
                        case "6":
                            Platform.runLater(() -> {
                                Integer vote6 = Integer.parseInt( Main.mainController.lb_vote6.getText() );
                                Main.mainController.lb_vote6.setText( String.valueOf( vote6 + 1 ) );
                            });
                            break;
                    }
                }

                // 클라이언트에게 응답 보내기
                output.println("서버로부터의 응답: " + clientMessage);
            }

            // 클라이언트가 연결을 종료하면 연결 종료
            System.out.println("클라이언트 연결 종료.");
        } catch (IOException e) {
            System.err.println("클라이언트 : " + e.getMessage());
            // e.printStackTrace();
        } finally {
            // 클라이언트와의 연결 종료 및 자원 정리
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }

}
