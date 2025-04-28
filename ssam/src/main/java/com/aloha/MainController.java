package com.aloha;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainController {

    @FXML
    void initialize() {
        System.out.println("메인 화면 초기화");
        // String audio = "ssam.mp3";
        // Media media = new Media(getClass().getResource(audio).toExternalForm());
        // MediaPlayer mediaPlayer = new MediaPlayer(media);
        // mediaPlayer.play();

        // WebSocket 클라이언트 시작
        // SocketClient.startClient();
    }

}
