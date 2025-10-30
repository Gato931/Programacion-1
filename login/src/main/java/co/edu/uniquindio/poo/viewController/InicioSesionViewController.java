package co.edu.uniquindio.poo.viewController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InicioSesionViewController implements Initializable {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoContraseña;

    @FXML
    private Button botonIniciarSesion;

    @FXML
    private TextField textoDepuracion;

    private ArrayList<Usuario> listaUsuarios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaUsuarios = new ArrayList<>();
        listaUsuarios.add(new Usuario("admin", "1234"));
        listaUsuarios.add(new Usuario("empleado", "abcd"));

        botonIniciarSesion.setOnAction(event -> validarInicioSesion());
    }

    private void validarInicioSesion() {
        String usuario = campoUsuario.getText().trim();
        String contraseña = campoContraseña.getText().trim();

        boolean credencialValida = listaUsuarios.stream()
                .anyMatch(u -> u.getUsuario().equals(usuario) && u.getContraseña().equals(contraseña));

        if (credencialValida) {
            textoDepuracion.setText("Inicio de sesión exitoso");
        } else {
            textoDepuracion.setText("Usuario o contraseña incorrectos");
        }
    }
}
