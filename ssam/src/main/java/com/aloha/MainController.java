package com.aloha;

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

        Main.easyCount = 0;
        Main.hardCount = 0;
        Main.missCount = 0;
    }

    // 
    void reActionReset() {
        lb_hot.setText("0");
        lb_cold.setText("0");
        lb_air.setText("0");
        lb_noise.setText("0");
        lb_like.setText("0");
        lb_heart.setText("0");
    }

    // 
    void voteReset() {
        lb_vote1.setText("0");
        lb_vote2.setText("0");
        lb_vote3.setText("0");
        lb_vote4.setText("0");
        lb_vote5.setText("0");
        lb_vote6.setText("0");
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
