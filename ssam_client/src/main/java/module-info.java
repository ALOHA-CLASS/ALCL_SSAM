module com.aloha {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;  // ⚡
    requires org.json;
    opens com.aloha to javafx.fxml;
    exports com.aloha;
}
