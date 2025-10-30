module co.edu.uniquindio.poo {
    requires javafx.controls;
    requires javafx.fxml;

    exports co.edu.uniquindio.poo.app to javafx.graphics;
    exports co.edu.uniquindio.poo.viewController to javafx.fxml;
    exports co.edu.uniquindio.poo.controller;
    exports co.edu.uniquindio.poo.model;

    opens co.edu.uniquindio.poo.app to javafx.fxml;
    opens co.edu.uniquindio.poo.viewController to javafx.fxml;
}
