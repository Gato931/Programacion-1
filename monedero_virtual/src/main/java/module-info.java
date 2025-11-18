module co.edu.uniquindio.poo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    // Abrir paquetes para JavaFX FXML
    opens co.edu.uniquindio.poo.app to javafx.fxml, javafx.graphics;
    opens co.edu.uniquindio.poo.controller to javafx.fxml;
    opens co.edu.uniquindio.poo.model to javafx.fxml, javafx.base;
    opens co.edu.uniquindio.poo.viewController to javafx.fxml;

    // Exportar paquetes
    exports co.edu.uniquindio.poo.app;
    exports co.edu.uniquindio.poo.controller;
    exports co.edu.uniquindio.poo.model;
    exports co.edu.uniquindio.poo.viewController;
}