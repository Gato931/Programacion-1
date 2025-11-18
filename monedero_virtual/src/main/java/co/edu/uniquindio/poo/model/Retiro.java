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

  @Override
  public String generarReporteDetallado() {
    return super.generarReporteDetallado() +
        String.format("Monedero Origen: %s\n", monederoOrigen.getTipo().getNombre());
  }
}