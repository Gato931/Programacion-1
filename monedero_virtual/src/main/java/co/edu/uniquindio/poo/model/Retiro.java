package co.edu.uniquindio.poo.model;

import java.util.List;

public class Retiro extends Transaccion {

  private final Monedero monederoOrigen;

  public Retiro(double monto, String descripcion, Monedero origen) {
    super(TipoTransaccion.RETIRO, monto, descripcion);

    if (origen == null) {
      throw new IllegalArgumentException("El monedero origen es requerido");
    }

    this.monederoOrigen = origen;
  }

  /**
   * Ejecuta el retiro descontando el monto del monedero origen si hay saldo
   * suficiente,
   * acumulando puntos según el rango del cliente
   * 
   * @return true si el retiro se completó exitosamente, false si fue rechazado
   */
  @Override
  public boolean ejecutar() {
    if (!esValida()) {
      rechazar();
      return false;
    }

    if (monederoOrigen.getSaldo() >= getMonto()) {
      monederoOrigen.restarSaldo(getMonto());
      completar();

      aplicarBonusRango(monederoOrigen.getPropietario().getRangoActual());
      monederoOrigen.getPropietario().acumularPuntos(getPuntosGenerados());

      return true;
    }

    rechazar();
    return false;
  }

  /**
   * Revierte un retiro completado, devolviendo el monto al monedero
   * y descontando los puntos generados si es posible
   * 
   * @return true si la reversión fue exitosa, false en caso contrario
   */
  @Override
  public boolean revertir() {
    if (getEstado() != EstadoTransaccion.COMPLETADA) {
      return false;
    }

    monederoOrigen.agregarSaldo(getMonto());
    setEstado(EstadoTransaccion.REVERTIDA);

    int puntosActuales = monederoOrigen.getPropietario().getPuntosAcumulados();
    if (puntosActuales >= getPuntosGenerados()) {
      monederoOrigen.getPropietario().canjearPuntos(getPuntosGenerados());
    }

    return true;
  }

  /**
   * Obtiene la lista de errores de validación específicos de un retiro,
   * verificando existencia, estado activo y saldo suficiente del monedero origen
   * 
   * @return Lista de mensajes de error encontrados
   */
  @Override
  public List<String> obtenerErroresValidacion() {
    List<String> errores = super.obtenerErroresValidacion();

    if (monederoOrigen == null) {
      errores.add("El monedero origen es requerido");
    }
    if (monederoOrigen != null && !monederoOrigen.isActivo()) {
      errores.add("El monedero origen no está activo");
    }
    if (monederoOrigen != null && monederoOrigen.getSaldo() < getMonto()) {
      errores.add("Saldo insuficiente");
    }

    return errores;
  }

  public Monedero getMonederoOrigen() {
    return monederoOrigen;
  }

  /**
   * Genera un reporte detallado del retiro incluyendo
   * información del monedero origen
   * 
   * @return String formateado con los detalles del retiro
   */
  @Override
  public String generarReporteDetallado() {
    return super.generarReporteDetallado() +
        String.format("Monedero Origen: %s\n", monederoOrigen.getTipo().getNombre());
  }
}