module com.aloha {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.aloha to javafx.fxml;
    exports com.aloha;
}
