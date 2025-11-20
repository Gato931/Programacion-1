package co.edu.uniquindio.poo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import co.edu.uniquindio.poo.model.*;

public class LoginController {

  private SistemaMonedero sistema;
  private Map<String, String> credenciales;

  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

  private static final Pattern PHONE_PATTERN = Pattern.compile(
      "^3[0-9]{9}$");

  public LoginController() {
    this.sistema = SistemaMonedero.getInstance();
    this.credenciales = new HashMap<>();
    inicializarDatosPrueba();
  }

  /**
   * Autentica un usuario con email y contraseña
   * Normaliza el email a minúsculas antes de buscar
   * 
   * @param email    El email del usuario
   * @param password La contraseña del usuario
   * @return Optional con el cliente si las credenciales son correctas
   */
  public Optional<Cliente> autenticar(String email, String password) {
    if (email == null || email.trim().isEmpty()) {
      return Optional.empty();
    }

    if (password == null || password.trim().isEmpty()) {
      return Optional.empty();
    }

    email = email.trim().toLowerCase();

    if (!credenciales.containsKey(email)) {
      System.out.println("Login fallido: Email no encontrado - " + email);
      return Optional.empty();
    }

    if (!credenciales.get(email).equals(password)) {
      System.out.println("Login fallido: Contraseña incorrecta para - " + email);
      return Optional.empty();
    }

    Optional<Cliente> clienteOpt = sistema.buscarClientePorEmail(email);

    if (clienteOpt.isPresent()) {
      System.out.println("Login exitoso: " + clienteOpt.get().getNombre());
    }

    return clienteOpt;
  }

  /**
   * Registra un nuevo cliente con validaciones completas
   * Valida formato de email, teléfono colombiano y longitud de contraseña
   * 
   * @param nombre   Nombre completo (mínimo 3 caracteres)
   * @param email    Email válido (único)
   * @param telefono Teléfono celular colombiano (10 dígitos, empieza con 3)
   * @param password Contraseña (entre 4 y 50 caracteres)
   * @return El cliente registrado
   * @throws IllegalArgumentException si alguna validación falla
   */
  public Cliente registrarCliente(String nombre, String email, String telefono, String password) {
    if (nombre == null || nombre.trim().isEmpty()) {
      throw new IllegalArgumentException("El nombre es requerido");
    }

    if (nombre.trim().length() < 3) {
      throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
    }

    if (email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("El email es requerido");
    }

    email = email.trim().toLowerCase();

    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new IllegalArgumentException("El formato del email no es valido");
    }

    if (telefono != null && !telefono.trim().isEmpty()) {
      String telefonoLimpio = telefono.trim().replaceAll("\\s+", "");
      if (!PHONE_PATTERN.matcher(telefonoLimpio).matches()) {
        throw new IllegalArgumentException(
            "El telefono debe ser un celular colombiano valido (10 digitos, empezando con 3)");
      }
    }

    if (password == null || password.length() < 4) {
      throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres");
    }

    if (password.length() > 50) {
      throw new IllegalArgumentException("La contraseña no puede tener mas de 50 caracteres");
    }

    if (credenciales.containsKey(email)) {
      throw new IllegalArgumentException("El email ya esta registrado");
    }

    Cliente cliente = sistema.registrarCliente(nombre.trim(), email, telefono);
    credenciales.put(email, password);

    System.out.println("Cliente registrado exitosamente: " + cliente.getNombre());
    return cliente;
  }

  /**
   * Cambia la contraseña de un usuario
   * Valida que la contraseña actual sea correcta
   * 
   * @param email          Email del usuario
   * @param passwordActual Contraseña actual
   * @param passwordNueva  Nueva contraseña (mínimo 4 caracteres)
   * @return true si el cambio fue exitoso
   */
  public boolean cambiarPassword(String email, String passwordActual, String passwordNueva) {
    email = email.trim().toLowerCase();

    if (!credenciales.containsKey(email)) {
      return false;
    }

    if (!credenciales.get(email).equals(passwordActual)) {
      return false;
    }

    if (passwordNueva == null || passwordNueva.length() < 4) {
      throw new IllegalArgumentException("La nueva contraseña debe tener al menos 4 caracteres");
    }

    credenciales.put(email, passwordNueva);
    System.out.println("Contraseña actualizada para: " + email);
    return true;
  }

  /**
   * Verifica si un email ya está registrado
   * 
   * @param email Email a verificar
   * @return true si el email existe
   */
  public boolean emailExiste(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }
    return credenciales.containsKey(email.trim().toLowerCase());
  }

  /**
   * Obtiene el número total de usuarios registrados
   * 
   * @return Cantidad de usuarios
   */
  public int contarUsuariosRegistrados() {
    return credenciales.size();
  }

  /**
   * Inicializa datos de prueba con 3 clientes y saldos iniciales
   * Usuarios: juan, maria, pedro (@uniquindio.edu.co)
   * Contraseña para todos: 1234
   */
  private void inicializarDatosPrueba() {
    try {
      Cliente cliente1 = sistema.registrarCliente(
          "Juan Perez",
          "juan@uniquindio.edu.co",
          "3001234567");
      credenciales.put("juan@uniquindio.edu.co", "1234");

      Cliente cliente2 = sistema.registrarCliente(
          "Maria Garcia",
          "maria@uniquindio.edu.co",
          "3007654321");
      credenciales.put("maria@uniquindio.edu.co", "1234");

      Cliente cliente3 = sistema.registrarCliente(
          "Pedro Lopez",
          "pedro@uniquindio.edu.co",
          "3009876543");
      credenciales.put("pedro@uniquindio.edu.co", "1234");

      sistema.realizarDeposito(cliente1,
          TipoMonedero.PRINCIPAL, 2_000_000, "Deposito inicial");

      sistema.realizarDeposito(cliente2,
          TipoMonedero.PRINCIPAL, 3_500_000, "Deposito inicial");

      sistema.realizarDeposito(cliente3,
          TipoMonedero.PRINCIPAL, 5_000_000, "Deposito inicial");

      cliente2.crearMonedero(TipoMonedero.AHORRO);
      sistema.realizarDeposito(cliente2,
          TipoMonedero.AHORRO, 1_000_000, "Ahorro inicial");

      System.out.println("Datos de prueba cargados exitosamente");
      System.out.println("Usuarios disponibles:");
      System.out.println("  - juan@uniquindio.edu.co (password: 1234)");
      System.out.println("  - maria@uniquindio.edu.co (password: 1234)");
      System.out.println("  - pedro@uniquindio.edu.co (password: 1234)");

    } catch (Exception e) {
      System.err.println("Error al cargar datos de prueba: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public SistemaMonedero getSistema() {
    return sistema;
  }
}