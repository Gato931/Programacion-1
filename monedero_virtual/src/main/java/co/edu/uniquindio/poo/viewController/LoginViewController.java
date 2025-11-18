package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.app.App;
import co.edu.uniquindio.poo.controller.LoginController;
import co.edu.uniquindio.poo.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.util.Optional;

public class LoginViewController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registrarButton;
    @FXML
    private Label mensajeLabel;

    private LoginController loginController;

    @FXML
    public void initialize() {
        loginController = new LoginController();

        emailField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarError(" Por favor complete todos los campos");
            return;
        }

        Optional<Cliente> clienteOpt = loginController.autenticar(email, password);

        if (clienteOpt.isPresent()) {
            try {

                DashboardViewController dashboard = (DashboardViewController) App.cambiarEscenaConControlador(
                        "dashboard.fxml",
                        "Monedero Virtual - Dashboard");
                dashboard.setCliente(clienteOpt.get());
            } catch (Exception e) {
                mostrarError("Error al cargar el dashboard");
                e.printStackTrace();
            }
        } else {
            mostrarError(" Email o contraseña incorrectos");
            passwordField.clear();
        }
    }

    @FXML
    private void handleRegistrar() {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Registro de Cliente");
        dialog.setHeaderText("Complete los datos para registrarse");

        ButtonType registrarButtonType = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registrarButtonType, ButtonType.CANCEL);

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre completo");

        TextField emailNewField = new TextField();
        emailNewField.setPromptText("Email");

        TextField telefonoField = new TextField();
        telefonoField.setPromptText("Teléfono");

        PasswordField passwordNewField = new PasswordField();
        passwordNewField.setPromptText("Contraseña");

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailNewField, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(telefonoField, 1, 2);
        grid.add(new Label("Contraseña:"), 0, 3);
        grid.add(passwordNewField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registrarButtonType) {
                try {
                    return loginController.registrarCliente(
                            nombreField.getText(),
                            emailNewField.getText(),
                            telefonoField.getText(),
                            passwordNewField.getText());
                } catch (Exception e) {
                    mostrarError("Error al registrar: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<Cliente> result = dialog.showAndWait();
        if (result.isPresent()) {
            mostrarExito(" Cliente registrado exitosamente");
            emailField.setText(result.get().getEmail());
        }
    }

    private void mostrarError(String mensaje) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle("-fx-text-fill: #f44336;");
    }

    private void mostrarExito(String mensaje) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle("-fx-text-fill: #4CAF50;");
    }
}