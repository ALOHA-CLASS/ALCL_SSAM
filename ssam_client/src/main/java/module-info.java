module com.aloha {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;  // ⚡
    requires java.prefs;
    requires org.json;
    opens com.aloha to javafx.fxml;
    exports com.aloha;
}
