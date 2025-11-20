package co.edu.uniquindio.poo.controller;

import java.util.Optional;
import co.edu.uniquindio.poo.model.*;

public class TransaccionController {

  private SistemaMonedero sistema;
  private static final double MONTO_MINIMO = 1000.0;
  private static final double MONTO_MAXIMO = 50_000_000.0;

  public TransaccionController() {
    this.sistema = SistemaMonedero.getInstance();
  }

  /**
   * Realiza un depósito validando monto, cliente y monedero
   * Normaliza la descripción si está vacía
   * 
   * @param cliente      Cliente que deposita
   * @param tipoMonedero Monedero donde depositar
   * @param monto        Cantidad a depositar
   * @param descripcion  Descripción del depósito
   * @return true si fue exitoso
   */
  public boolean realizarDeposito(Cliente cliente, TipoMonedero tipoMonedero,
      double monto, String descripcion) {
    try {
      validarMonto(monto);
      validarCliente(cliente);
      validarMonederoExiste(cliente, tipoMonedero);

      if (descripcion == null || descripcion.trim().isEmpty()) {
        descripcion = "Deposito en " + tipoMonedero.getNombre();
      }

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
   * Realiza un retiro con validaciones de saldo y límites
   * 
   * @param cliente      Cliente que retira
   * @param tipoMonedero Monedero desde donde retirar
   * @param monto        Cantidad a retirar
   * @param descripcion  Descripción del retiro
   * @return true si fue exitoso
   */
  public boolean realizarRetiro(Cliente cliente, TipoMonedero tipoMonedero,
      double monto, String descripcion) {
    try {
      validarMonto(monto);
      validarCliente(cliente);

      Optional<Monedero> monederoOpt = cliente.obtenerMonedero(tipoMonedero);
      if (monederoOpt.isEmpty()) {
        throw new IllegalArgumentException("El monedero " + tipoMonedero.getNombre() + " no existe");
      }

      Monedero monedero = monederoOpt.get();

      if (monedero.getSaldo() < monto) {
        throw new IllegalArgumentException(String.format(
            "Saldo insuficiente. Disponible: $%,.0f COP, Solicitado: $%,.0f COP",
            monedero.getSaldo(), monto));
      }

      if (descripcion == null || descripcion.trim().isEmpty()) {
        descripcion = "Retiro de " + tipoMonedero.getNombre();
      }

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
   * Realiza una transferencia entre dos clientes
   * Valida que no sea a sí mismo y que tenga saldo suficiente incluyendo comisión
   * 
   * @param clienteOrigen   Cliente que envía
   * @param monederoOrigen  Monedero origen
   * @param clienteDestino  Cliente que recibe
   * @param monederoDestino Monedero destino
   * @param monto           Monto a transferir (sin comisión)
   * @param descripcion     Descripción de la transferencia
   * @return true si fue exitosa
   */
  public boolean realizarTransferencia(Cliente clienteOrigen, TipoMonedero monederoOrigen,
      Cliente clienteDestino, TipoMonedero monederoDestino,
      double monto, String descripcion) {
    try {
      validarMonto(monto);
      validarCliente(clienteOrigen);
      validarCliente(clienteDestino);

      if (clienteOrigen.getId().equals(clienteDestino.getId())) {
        throw new IllegalArgumentException("No puede transferir a su propia cuenta");
      }

      validarMonederoExiste(clienteOrigen, monederoOrigen);
      validarMonederoExiste(clienteDestino, monederoDestino);

      Optional<Monedero> monederoOrigenOpt = clienteOrigen.obtenerMonedero(monederoOrigen);
      if (monederoOrigenOpt.isPresent()) {
        Monedero monedero = monederoOrigenOpt.get();
        double comisionEstimada = monto * 0.01;
        double montoTotal = monto + comisionEstimada;

        if (monedero.getSaldo() < montoTotal) {
          throw new IllegalArgumentException(String.format(
              "Saldo insuficiente. Necesita: $%,.0f COP (incluye comision de $%,.0f), Disponible: $%,.0f COP",
              montoTotal, comisionEstimada, monedero.getSaldo()));
        }
      }

      if (descripcion == null || descripcion.trim().isEmpty()) {
        descripcion = "Transferencia a " + clienteDestino.getNombre();
      }

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
   * Valida que el monto esté dentro de los límites permitidos
   * 
   * @param monto El monto a validar
   * @throws IllegalArgumentException si está fuera de los límites
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
   * Valida que el cliente no sea nulo y esté activo
   * 
   * @param cliente El cliente a validar
   * @throws IllegalArgumentException si es nulo o inactivo
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
   * Valida que el cliente tenga el monedero especificado
   * 
   * @param cliente El cliente a verificar
   * @param tipo    El tipo de monedero requerido
   * @throws IllegalArgumentException si no tiene ese monedero
   */
  private void validarMonederoExiste(Cliente cliente, TipoMonedero tipo) {
    if (cliente.obtenerMonedero(tipo).isEmpty()) {
      throw new IllegalArgumentException(
          "El cliente no tiene un monedero de tipo " + tipo.getNombre());
    }
  }

  /**
   * Busca un cliente por su email
   * 
   * @param email Email a buscar (normaliza a minúsculas)
   * @return Optional con el cliente si existe
   */
  public Optional<Cliente> buscarClientePorEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return Optional.empty();
    }
    return sistema.buscarClientePorEmail(email.trim().toLowerCase());
  }

  /**
   * Genera un resumen de los saldos de todos los monederos del cliente
   * 
   * @param cliente El cliente del resumen
   * @return String formateado con los saldos
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

  public SistemaMonedero getSistema() {
    return sistema;
  }
}