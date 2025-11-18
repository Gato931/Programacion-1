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

  @Override
  public String generarReporteDetallado() {
    return super.generarReporteDetallado() +
        String.format("Monedero Destino: %s\n", monederoDestino.getTipo().getNombre());
  }
}