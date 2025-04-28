module com.aloha {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;  
    requires javafx.media;
    requires java.desktop;
    requires org.json; // JSON 처리 라이브러리
    
    // 네트워크 관련 모듈 (예: HTTP 요청)
    requires java.net.http;

    // XML 처리 필요시
    requires java.xml;

    
    opens com.aloha to javafx.fxml;
    exports com.aloha;
}
