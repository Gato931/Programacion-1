package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransaccionProgramada {

  private final String id;
  private final TipoTransaccion tipo;
  private final double monto;
  private final String descripcion;
  private final Monedero origen;
  private final Monedero destino;
  private final LocalDateTime proximaEjecucion;
  private final Periodicidad periodicidad;
  private boolean activa;
  private final List<Transaccion> historialEjecuciones;

  public TransaccionProgramada(TipoTransaccion tipo, double monto,
      String descripcion, Monedero origen,
      Monedero destino, LocalDateTime proximaEjecucion,
      Periodicidad periodicidad) {
    this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.tipo = tipo;
    this.monto = monto;
    this.descripcion = descripcion;
    this.origen = origen;
    this.destino = destino;
    this.proximaEjecucion = proximaEjecucion;
    this.periodicidad = periodicidad;
    this.activa = true;
    this.historialEjecuciones = new ArrayList<>();
  }

  public boolean debeEjecutarse() {
    return activa && LocalDateTime.now().isAfter(proximaEjecucion);
  }

  public Optional<Transaccion> ejecutar() {
    if (!debeEjecutarse()) {
      return Optional.empty();
    }

    Transaccion transaccion = null;

    switch (tipo) {
      case DEPOSITO -> {
        transaccion = new Deposito(monto, descripcion, origen);
      }
      case RETIRO -> {
        transaccion = new Retiro(monto, descripcion, origen);
      }
      case TRANSFERENCIA -> {
        if (destino != null) {
          transaccion = new Transferencia(monto, descripcion, origen, destino);
        }
      }
      case CANJE_PUNTOS -> {
        throw new UnsupportedOperationException("El canje de puntos no puede programarse");
      }
    }

    if (transaccion != null && transaccion.ejecutar()) {
      historialEjecuciones.add(transaccion);

      origen.getPropietario().enviarNotificacion(
          String.format("Transacción programada ejecutada: %s",
              transaccion.generarResumen()),
          TipoNotificacion.TRANSACCION_PROGRAMADA);

      return Optional.of(transaccion);
    }

    return Optional.empty();
  }

  public LocalDateTime calcularProximaEjecucion() {
    return switch (periodicidad) {
      case DIARIA -> proximaEjecucion.plusDays(1);
      case SEMANAL -> proximaEjecucion.plusWeeks(1);
      case QUINCENAL -> proximaEjecucion.plusWeeks(2);
      case MENSUAL -> proximaEjecucion.plusMonths(1);
      case ANUAL -> proximaEjecucion.plusYears(1);
      case UNICA -> proximaEjecucion;
    };
  }

  public String getId() {
    return id;
  }

  public TipoTransaccion getTipo() {
    return tipo;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDateTime getProximaEjecucion() {
    return proximaEjecucion;
  }

  public Periodicidad getPeriodicidad() {
    return periodicidad;
  }

  public boolean isActiva() {
    return activa;
  }

  public void setActiva(boolean activa) {
    this.activa = activa;
  }

  public List<Transaccion> getHistorialEjecuciones() {
    return new ArrayList<>(historialEjecuciones);
  }

  @Override
  public String toString() {
    return String.format("[%s] %s: $%.2f - Próxima: %s (%s)",
        id,
        tipo.getDescripcion(),
        monto,
        proximaEjecucion.toLocalDate(),
        periodicidad.getDescripcion());
  }

  public String getDescripcion() {
    return descripcion;
  }

  public Monedero getOrigen() {
    return origen;
  }

  public Monedero getDestino() {
    return destino;
  }
}