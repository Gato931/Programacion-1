package co.edu.uniquindio.poo.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aplicación principal del Monedero Virtual
 * Universidad del Quindío
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("/co/edu/uniquindio/poo/login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        primaryStage.setTitle("Monedero Virtual - Universidad del Quindio");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void cambiarEscena(String fxmlFile, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("/co/edu/uniquindio/poo/" + fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setTitle(titulo);
        primaryStage.setScene(scene);
    }

    public static Object cambiarEscenaConControlador(String fxmlFile, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("/co/edu/uniquindio/poo/" + fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setTitle(titulo);
        primaryStage.setScene(scene);

        return fxmlLoader.getController();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}