module it1901 {
    requires javafx.controls;
    requires javafx.fxml;

    opens it1901 to javafx.fxml;
    exports it1901;
}
