package co.edu.uniquindio.poo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.SistemaMonedero;

/**
 * Controlador de lógica para el Login
 * Universidad del Quindío
 */
public class LoginController {

  private SistemaMonedero sistema;
  private Map<String, String> credenciales;

  // Patrón para validar emails
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

  // Patrón para validar teléfonos colombianos
  private static final Pattern PHONE_PATTERN = Pattern.compile(
      "^3[0-9]{9}$");

  public LoginController() {
    this.sistema = SistemaMonedero.getInstance();
    this.credenciales = new HashMap<>();
    inicializarDatosPrueba();
  }

  /**
   * Autenticar usuario con email y contraseña
   */
  public Optional<Cliente> autenticar(String email, String password) {
    // Validación de entrada
    if (email == null || email.trim().isEmpty()) {
      return Optional.empty();
    }

    if (password == null || password.trim().isEmpty()) {
      return Optional.empty();
    }

    // Normalizar email (convertir a minúsculas)
    email = email.trim().toLowerCase();

    // Verificar credenciales
    if (!credenciales.containsKey(email)) {
      System.out.println("Login fallido: Email no encontrado - " + email);
      return Optional.empty();
    }

    if (!credenciales.get(email).equals(password)) {
      System.out.println("Login fallido: Contraseña incorrecta para - " + email);
      return Optional.empty();
    }

    // Buscar cliente en el sistema
    Optional<Cliente> clienteOpt = sistema.buscarClientePorEmail(email);

    if (clienteOpt.isPresent()) {
      System.out.println("Login exitoso: " + clienteOpt.get().getNombre());
    }

    return clienteOpt;
  }

  /**
   * Registrar nuevo cliente con validaciones completas
   */
  public Cliente registrarCliente(String nombre, String email, String telefono, String password) {
    // Validar nombre
    if (nombre == null || nombre.trim().isEmpty()) {
      throw new IllegalArgumentException("El nombre es requerido");
    }

    if (nombre.trim().length() < 3) {
      throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
    }

    // Validar y normalizar email
    if (email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("El email es requerido");
    }

    email = email.trim().toLowerCase();

    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new IllegalArgumentException("El formato del email no es valido");
    }

    // Validar teléfono
    if (telefono != null && !telefono.trim().isEmpty()) {
      String telefonoLimpio = telefono.trim().replaceAll("\\s+", "");
      if (!PHONE_PATTERN.matcher(telefonoLimpio).matches()) {
        throw new IllegalArgumentException(
            "El telefono debe ser un celular colombiano valido (10 digitos, empezando con 3)");
      }
    }

    // Validar contraseña
    if (password == null || password.length() < 4) {
      throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres");
    }

    if (password.length() > 50) {
      throw new IllegalArgumentException("La contraseña no puede tener mas de 50 caracteres");
    }

    // Verificar si el email ya existe
    if (credenciales.containsKey(email)) {
      throw new IllegalArgumentException("El email ya esta registrado");
    }

    // Registrar cliente
    Cliente cliente = sistema.registrarCliente(nombre.trim(), email, telefono);
    credenciales.put(email, password);

    System.out.println("Cliente registrado exitosamente: " + cliente.getNombre());

    return cliente;
  }

  /**
   * Cambiar contraseña de un usuario
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
   * Verificar si un email ya está registrado
   */
  public boolean emailExiste(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }
    return credenciales.containsKey(email.trim().toLowerCase());
  }

  /**
   * Obtener el número total de usuarios registrados
   */
  public int contarUsuariosRegistrados() {
    return credenciales.size();
  }

  /**
   * Inicializar datos de prueba con montos realistas en COP
   */
  private void inicializarDatosPrueba() {
    try {
      // Cliente 1 - Juan
      Cliente cliente1 = sistema.registrarCliente(
          "Juan Perez",
          "juan@uniquindio.edu.co",
          "3001234567");
      credenciales.put("juan@uniquindio.edu.co", "1234");

      // Cliente 2 - María
      Cliente cliente2 = sistema.registrarCliente(
          "Maria Garcia",
          "maria@uniquindio.edu.co",
          "3007654321");
      credenciales.put("maria@uniquindio.edu.co", "1234");

      // Cliente 3 - Pedro
      Cliente cliente3 = sistema.registrarCliente(
          "Pedro Lopez",
          "pedro@uniquindio.edu.co",
          "3009876543");
      credenciales.put("pedro@uniquindio.edu.co", "1234");

      // Depósitos iniciales con montos realistas en COP
      sistema.realizarDeposito(cliente1,
          co.edu.uniquindio.poo.model.TipoMonedero.PRINCIPAL,
          2_000_000, "Deposito inicial"); // 2 millones

      sistema.realizarDeposito(cliente2,
          co.edu.uniquindio.poo.model.TipoMonedero.PRINCIPAL,
          3_500_000, "Deposito inicial"); // 3.5 millones

      sistema.realizarDeposito(cliente3,
          co.edu.uniquindio.poo.model.TipoMonedero.PRINCIPAL,
          5_000_000, "Deposito inicial"); // 5 millones

      // Crear monederos adicionales para demostración
      cliente2.crearMonedero(co.edu.uniquindio.poo.model.TipoMonedero.AHORRO);
      sistema.realizarDeposito(cliente2,
          co.edu.uniquindio.poo.model.TipoMonedero.AHORRO,
          1_000_000, "Ahorro inicial");

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

  /**
   * Obtener el sistema (útil para pruebas)
   */
  public SistemaMonedero getSistema() {
    return sistema;
  }
}