package co.edu.uniquindio.poo.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("/co/edu/uniquindio/poo/inicioSesion.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio de Sesión");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
