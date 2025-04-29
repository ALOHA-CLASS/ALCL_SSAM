package com.aloha;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    public Label lb_air;

    @FXML
    public Label lb_cold;

    @FXML
    public Label lb_easy;

    @FXML
    public Label lb_hard;

    @FXML
    public Label lb_heart;

    @FXML
    public Label lb_hot;

    @FXML
    public Label lb_like;

    @FXML
    public Label lb_miss;

    @FXML
    public Label lb_noise;

    @FXML
    public Label lb_vote1;

    @FXML
    public Label lb_vote2;

    @FXML
    public Label lb_vote3;

    @FXML
    public Label lb_vote4;

    @FXML
    public Label lb_vote5;

    @FXML
    public Label lb_vote6;

    

    @FXML
    void initialize() {
        System.out.println("메인 화면 초기화");
    }

    // 
    void actionReset() {
        lb_easy.setText("0");
        lb_hard.setText("0");
        lb_miss.setText("0");

        Main.easyCount = new AtomicInteger(0);
        Main.hardCount = new AtomicInteger(0);
        Main.missCount = new AtomicInteger(0);

        Main.actionMap = new ConcurrentHashMap<>();
    }

    // 
    void reActionReset() {
        lb_hot.setText("0");
        lb_cold.setText("0");
        lb_air.setText("0");
        lb_noise.setText("0");
        lb_like.setText("0");
        lb_heart.setText("0");

        Main.hotCount = new AtomicInteger(0);
        Main.coldCount = new AtomicInteger(0);
        Main.airCount = new AtomicInteger(0);
        Main.noiseCount = new AtomicInteger(0);
        Main.likeCount = new AtomicInteger(0);
        Main.heartCount = new AtomicInteger(0);

        Main.reactionMap = new ConcurrentHashMap<>();
    }

    // 
    void voteReset() {
        lb_vote1.setText("0");
        lb_vote2.setText("0");
        lb_vote3.setText("0");
        lb_vote4.setText("0");
        lb_vote5.setText("0");
        lb_vote6.setText("0");

        Main.vote1Count = new AtomicInteger(0);
        Main.vote2Count = new AtomicInteger(0);
        Main.vote3Count = new AtomicInteger(0);
        Main.vote4Count = new AtomicInteger(0);
        Main.vote5Count = new AtomicInteger(0);
        Main.vote6Count = new AtomicInteger(0);

        Main.voteMap = new ConcurrentHashMap<>();
    }


    @FXML
    void initAction(ActionEvent event) {
        actionReset();
    }

    
    @FXML
    void initReAction(ActionEvent event) {
        reActionReset();
    }
    
    @FXML
    void initVote(ActionEvent event) {
        voteReset();
    }
    
    @FXML
    void initAll(ActionEvent event) {
        actionReset();
        reActionReset();
        voteReset();
    }


}
