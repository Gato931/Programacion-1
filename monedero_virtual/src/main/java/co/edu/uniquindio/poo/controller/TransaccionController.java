package co.edu.uniquindio.poo.controller;

import java.util.Optional;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.SistemaMonedero;
import co.edu.uniquindio.poo.model.TipoMonedero;

/**
 * Controlador de lógica para las transacciones
 * Universidad del Quindío
 */
public class TransaccionController {

  private SistemaMonedero sistema;

  // Límites de transacción
  private static final double MONTO_MINIMO = 1000.0; // 1.000 COP
  private static final double MONTO_MAXIMO = 50_000_000.0; // 50 millones COP

  public TransaccionController() {
    this.sistema = SistemaMonedero.getInstance();
  }

  /**
   * Realizar un depósito con validaciones
   */
  public boolean realizarDeposito(Cliente cliente, TipoMonedero tipoMonedero,
      double monto, String descripcion) {
    try {
      // Validaciones
      validarMonto(monto);
      validarCliente(cliente);
      validarMonederoExiste(cliente, tipoMonedero);

      // Normalizar descripción
      if (descripcion == null || descripcion.trim().isEmpty()) {
        descripcion = "Deposito en " + tipoMonedero.getNombre();
      }

      // Realizar transacción
      boolean exitoso = sistema.realizarDeposito(cliente, tipoMonedero, monto, descripcion);

      if (exitoso) {
        System.out.println(String.format("Deposito exitoso: %s deposito $%,.0f en %s",
            cliente.getNombre(), monto, tipoMonedero.getNombre()));
      }

      return exitoso;

    } catch (Exception e) {
      System.err.println("Error en deposito: " + e.getMessage());
      return false;
    }
  }

  /**
   * Realizar un retiro con validaciones
   */
  public boolean realizarRetiro(Cliente cliente, TipoMonedero tipoMonedero,
      double monto, String descripcion) {
    try {
      // Validaciones
      validarMonto(monto);
      validarCliente(cliente);

      Optional<Monedero> monederoOpt = cliente.obtenerMonedero(tipoMonedero);
      if (monederoOpt.isEmpty()) {
        throw new IllegalArgumentException("El monedero " + tipoMonedero.getNombre() + " no existe");
      }

      Monedero monedero = monederoOpt.get();

      // Verificar saldo suficiente
      if (monedero.getSaldo() < monto) {
        throw new IllegalArgumentException(String.format(
            "Saldo insuficiente. Disponible: $%,.0f COP, Solicitado: $%,.0f COP",
            monedero.getSaldo(), monto));
      }

      // Normalizar descripción
      if (descripcion == null || descripcion.trim().isEmpty()) {
        descripcion = "Retiro de " + tipoMonedero.getNombre();
      }

      // Realizar transacción
      boolean exitoso = sistema.realizarRetiro(cliente, tipoMonedero, monto, descripcion);

      if (exitoso) {
        System.out.println(String.format("Retiro exitoso: %s retiro $%,.0f de %s",
            cliente.getNombre(), monto, tipoMonedero.getNombre()));
      }

      return exitoso;

    } catch (Exception e) {
      System.err.println("Error en retiro: " + e.getMessage());
      return false;
    }
  }

  /**
   * Realizar una transferencia con validaciones completas
   */
  public boolean realizarTransferencia(Cliente clienteOrigen, TipoMonedero monederoOrigen,
      Cliente clienteDestino, TipoMonedero monederoDestino,
      double monto, String descripcion) {
    try {
      // Validaciones básicas
      validarMonto(monto);
      validarCliente(clienteOrigen);
      validarCliente(clienteDestino);

      // Validar que no sea a sí mismo
      if (clienteOrigen.getId().equals(clienteDestino.getId())) {
        throw new IllegalArgumentException("No puede transferir a su propia cuenta");
      }

      // Validar monederos
      validarMonederoExiste(clienteOrigen, monederoOrigen);
      validarMonederoExiste(clienteDestino, monederoDestino);

      // Verificar saldo suficiente (incluyendo comisión estimada)
      Optional<Monedero> monederoOrigenOpt = clienteOrigen.obtenerMonedero(monederoOrigen);
      if (monederoOrigenOpt.isPresent()) {
        Monedero monedero = monederoOrigenOpt.get();
        double comisionEstimada = monto * 0.01; // 1% de comisión
        double montoTotal = monto + comisionEstimada;

        if (monedero.getSaldo() < montoTotal) {
          throw new IllegalArgumentException(String.format(
              "Saldo insuficiente. Necesita: $%,.0f COP (incluye comision de $%,.0f), Disponible: $%,.0f COP",
              montoTotal, comisionEstimada, monedero.getSaldo()));
        }
      }

      // Normalizar descripción
      if (descripcion == null || descripcion.trim().isEmpty()) {
        descripcion = "Transferencia a " + clienteDestino.getNombre();
      }

      // Realizar transacción
      boolean exitoso = sistema.realizarTransferencia(
          clienteOrigen, monederoOrigen,
          clienteDestino, monederoDestino,
          monto, descripcion);

      if (exitoso) {
        System.out.println(String.format("Transferencia exitosa: %s -> %s: $%,.0f COP",
            clienteOrigen.getNombre(), clienteDestino.getNombre(), monto));
      }

      return exitoso;

    } catch (Exception e) {
      System.err.println("Error en transferencia: " + e.getMessage());
      return false;
    }
  }

  /**
   * Validar que el monto esté dentro de los rangos permitidos
   */
  private void validarMonto(double monto) {
    if (monto < MONTO_MINIMO) {
      throw new IllegalArgumentException(String.format(
          "El monto minimo es $%,.0f COP", MONTO_MINIMO));
    }

    if (monto > MONTO_MAXIMO) {
      throw new IllegalArgumentException(String.format(
          "El monto maximo es $%,.0f COP", MONTO_MAXIMO));
    }
  }

  /**
   * Validar que el cliente no sea nulo y esté activo
   */
  private void validarCliente(Cliente cliente) {
    if (cliente == null) {
      throw new IllegalArgumentException("El cliente no puede ser nulo");
    }

    if (!cliente.isActivo()) {
      throw new IllegalArgumentException("La cuenta del cliente esta inactiva");
    }
  }

  /**
   * Validar que el cliente tenga el monedero especificado
   */
  private void validarMonederoExiste(Cliente cliente, TipoMonedero tipo) {
    if (cliente.obtenerMonedero(tipo).isEmpty()) {
      throw new IllegalArgumentException(
          "El cliente no tiene un monedero de tipo " + tipo.getNombre());
    }
  }

  /**
   * Buscar cliente por email
   */
  public Optional<Cliente> buscarClientePorEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return Optional.empty();
    }
    return sistema.buscarClientePorEmail(email.trim().toLowerCase());
  }

  /**
   * Obtener información del saldo de un cliente
   */
  public String obtenerResumenSaldos(Cliente cliente) {
    StringBuilder resumen = new StringBuilder();
    resumen.append("Resumen de saldos para: ").append(cliente.getNombre()).append("\n");

    cliente.getMonederos().forEach(m -> {
      resumen.append(String.format("  - %s: $%,.0f COP\n",
          m.getTipo().getNombre(), m.getSaldo()));
    });

    resumen.append(String.format("Total: $%,.0f COP", cliente.calcularSaldoTotal()));

    return resumen.toString();
  }

  /**
   * Obtener el sistema
   */
  public SistemaMonedero getSistema() {
    return sistema;
  }
}