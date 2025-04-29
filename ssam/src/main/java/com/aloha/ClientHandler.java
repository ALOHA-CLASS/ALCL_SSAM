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
                    System.out.println(Main.actionMap);

                    String prev = Main.actionMap.put(ip, msg);
                    if( prev != null ) {
                        System.out.println("이전 값: " + prev);
                        switch (prev) {
                            case "쉬워요": Main.easyCount.decrementAndGet();    break;
                            case "어려워요": Main.hardCount.decrementAndGet();    break;
                            case "놓쳤어요": Main.missCount.decrementAndGet();    break;
                        }
                    }
                    switch (Main.actionMap.get(ip)) {
                        case "쉬워요": Main.easyCount.incrementAndGet();    break;
                        case "어려워요": Main.hardCount.incrementAndGet();    break;
                        case "놓쳤어요": Main.missCount.incrementAndGet();    break;
                    }
                    
                    System.out.println("쉬워요: " + Main.easyCount + ", 어려워요: " + Main.hardCount + ", 놓쳤어요: " + Main.missCount);

                    Platform.runLater(() -> {
                        // easy
                        Main.mainController.lb_easy.setText( String.valueOf(Main.easyCount) );
                        // hard
                        Main.mainController.lb_hard.setText( String.valueOf(Main.hardCount) );
                        // miss
                        Main.mainController.lb_miss.setText( String.valueOf(Main.missCount) );
                    });

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


                // reaction : 더워요, 추워요, 환기, 소음, 좋아요, 하트
                if (type.equals("reaction")) {

                    System.out.println(Main.reactionMap);

                    String prev = Main.reactionMap.put(ip, msg);
                    if( prev != null ) {
                        System.out.println("이전 값: " + prev);
                        switch (prev) {
                            case "더워요": Main.hotCount.decrementAndGet();    break;
                            case "추워요": Main.coldCount.decrementAndGet();    break;
                            case "환기"  : Main.airCount.decrementAndGet();    break;
                            case "소음"  : Main.noiseCount.decrementAndGet();    break;
                            case "좋아요": Main.likeCount.decrementAndGet();    break;
                            case "하트"  : Main.heartCount.decrementAndGet();    break;
                        }
                    }
                    switch (Main.reactionMap.get(ip)) {
                        case "더워요": Main.hotCount.incrementAndGet();    break;
                        case "추워요": Main.coldCount.incrementAndGet();    break;
                        case "환기"  : Main.airCount.incrementAndGet();    break;
                        case "소음"  : Main.noiseCount.incrementAndGet();    break;
                        case "좋아요": Main.likeCount.incrementAndGet();    break;
                        case "하트"  : Main.heartCount.incrementAndGet();    break;
                    }
                    
                    System.out.println("더워요: " + Main.hotCount + ", 추워요: " + Main.coldCount + ", 환기: " + Main.airCount + ", 소음: " + Main.noiseCount + ", 좋아요: " + Main.likeCount + ", 하트: " + Main.heartCount);

                    Platform.runLater(() -> {
                        // hot
                        Main.mainController.lb_hot.setText( String.valueOf(Main.hotCount) );
                        // cold
                        Main.mainController.lb_cold.setText( String.valueOf(Main.coldCount) );
                        // air
                        Main.mainController.lb_air.setText( String.valueOf(Main.airCount) );
                        // noise
                        Main.mainController.lb_noise.setText( String.valueOf(Main.noiseCount) );
                        // like
                        Main.mainController.lb_like.setText( String.valueOf(Main.likeCount) );
                        // heart
                        Main.mainController.lb_heart.setText( String.valueOf(Main.heartCount) );
                    });

                }

                // vote : 1 2 3 4 5 6 
                if (type.equals("vote")) {

                    System.out.println(Main.voteMap);

                    String prev = Main.voteMap.put(ip, msg);
                    if( prev != null ) {
                        System.out.println("이전 값: " + prev);
                        switch (prev) {
                            case "1": Main.vote1Count.decrementAndGet();    break;
                            case "2": Main.vote2Count.decrementAndGet();    break;
                            case "3": Main.vote3Count.decrementAndGet();    break;
                            case "4": Main.vote4Count.decrementAndGet();    break;
                            case "5": Main.vote5Count.decrementAndGet();    break;
                            case "6": Main.vote6Count.decrementAndGet();    break;
                        }
                    }
                    switch (Main.voteMap.get(ip)) {
                        case "1": Main.vote1Count.incrementAndGet();    break;
                        case "2": Main.vote2Count.incrementAndGet();    break;
                        case "3": Main.vote3Count.incrementAndGet();    break;
                        case "4": Main.vote4Count.incrementAndGet();    break;
                        case "5": Main.vote5Count.incrementAndGet();    break;
                        case "6": Main.vote6Count.incrementAndGet();    break;
                    }
                    
                    System.out.println("vote1: " + Main.vote1Count + ", vote2: " + Main.vote2Count + ", vote3: " + Main.vote3Count + ", vote4: " + Main.vote4Count + ", vote5: " + Main.vote5Count + ", vote6: " + Main.vote6Count);

                    Platform.runLater(() -> {
                        Main.mainController.lb_vote1.setText( String.valueOf(Main.vote1Count) );
                        Main.mainController.lb_vote2.setText( String.valueOf(Main.vote2Count) );
                        Main.mainController.lb_vote3.setText( String.valueOf(Main.vote3Count) );
                        Main.mainController.lb_vote4.setText( String.valueOf(Main.vote4Count) );
                        Main.mainController.lb_vote5.setText( String.valueOf(Main.vote5Count) );
                        Main.mainController.lb_vote6.setText( String.valueOf(Main.vote6Count) );
                    });

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
