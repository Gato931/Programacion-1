package co.edu.uniquindio.poo.model;

import java.util.List;

public class Deposito extends Transaccion {

  private final Monedero monederoDestino;

  public Deposito(double monto, String descripcion, Monedero destino) {
    super(TipoTransaccion.DEPOSITO, monto, descripcion);

    if (destino == null) {
      throw new IllegalArgumentException("El monedero destino es requerido");
    }

    this.monederoDestino = destino;
  }

  /**
   * Ejecuta el depósito agregando el monto al monedero destino,
   * acumulando puntos según el rango del cliente
   * 
   * @return true si el depósito se completó exitosamente, false si fue rechazado
   */
  @Override
  public boolean ejecutar() {
    if (!esValida()) {
      rechazar();
      return false;
    }

    monederoDestino.agregarSaldo(getMonto());
    completar();

    aplicarBonusRango(monederoDestino.getPropietario().getRangoActual());
    monederoDestino.getPropietario().acumularPuntos(getPuntosGenerados());

    return true;
  }

  /**
   * Revierte un depósito completado, retirando el monto del monedero
   * y descontando los puntos generados si es posible
   * 
   * @return true si la reversión fue exitosa, false si no hay saldo suficiente
   */
  @Override
  public boolean revertir() {
    if (getEstado() != EstadoTransaccion.COMPLETADA) {
      return false;
    }

    if (monederoDestino.getSaldo() >= getMonto()) {
      monederoDestino.restarSaldo(getMonto());
      setEstado(EstadoTransaccion.REVERTIDA);

      int puntosActuales = monederoDestino.getPropietario().getPuntosAcumulados();
      if (puntosActuales >= getPuntosGenerados()) {
        monederoDestino.getPropietario().canjearPuntos(getPuntosGenerados());
      }

      return true;
    }

    return false;
  }

  /**
   * Obtiene la lista de errores de validación específicos de un depósito,
   * verificando la existencia y estado activo del monedero destino
   * 
   * @return Lista de mensajes de error encontrados
   */
  @Override
  public List<String> obtenerErroresValidacion() {
    List<String> errores = super.obtenerErroresValidacion();

    if (monederoDestino == null) {
      errores.add("El monedero destino es requerido");
    }
    if (monederoDestino != null && !monederoDestino.isActivo()) {
      errores.add("El monedero destino no está activo");
    }

    return errores;
  }

  public Monedero getMonederoDestino() {
    return monederoDestino;
  }

  /**
   * Genera un reporte detallado del depósito incluyendo
   * información del monedero destino
   * 
   * @return String formateado con los detalles del depósito
   */
  @Override
  public String generarReporteDetallado() {
    return super.generarReporteDetallado() +
        String.format("Monedero Destino: %s\n", monederoDestino.getTipo().getNombre());
  }
}