package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Transaccion implements Validable {

  private final String id;
  private final TipoTransaccion tipo;
  private final double monto;
  private final LocalDateTime fechaCreacion;
  private EstadoTransaccion estado;
  private String descripcion;
  private int puntosGenerados;
  private LocalDateTime fechaEjecucion;

  public Transaccion(TipoTransaccion tipo, double monto, String descripcion) {
    if (monto <= 0) {
      throw new IllegalArgumentException("El monto debe ser mayor a cero");
    }

    this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.tipo = tipo;
    this.monto = monto;
    this.descripcion = descripcion != null ? descripcion : "";
    this.fechaCreacion = LocalDateTime.now();
    this.estado = EstadoTransaccion.PENDIENTE;
    this.puntosGenerados = 0;
  }

  public abstract boolean ejecutar();

  public abstract boolean revertir();

  public int calcularPuntosBase() {
    return (int) (monto / 10_000.0) * tipo.getPuntosPor10000();
  }

  public void aplicarBonusRango(RangoCliente rango) {
    int puntosBase = calcularPuntosBase();
    double bonus = rango.getBonusPuntos();
    this.puntosGenerados = (int) (puntosBase * (1 + bonus));
  }

  protected void completar() {
    this.estado = EstadoTransaccion.COMPLETADA;
    this.fechaEjecucion = LocalDateTime.now();
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setFechaEjecucion(LocalDateTime fechaEjecucion) {
    this.fechaEjecucion = fechaEjecucion;
  }

  protected void rechazar() {
    this.estado = EstadoTransaccion.RECHAZADA;
  }

  @Override
  public boolean esValida() {
    return obtenerErroresValidacion().isEmpty();
  }

  @Override
  public List<String> obtenerErroresValidacion() {
    List<String> errores = new ArrayList<>();

    if (monto <= 0) {
      errores.add("El monto debe ser mayor a cero");
    }
    if (tipo == null) {
      errores.add("El tipo de transacción es requerido");
    }

    return errores;
  }

  public String generarResumen() {
    return String.format("[%s] %s - $%.2f - %s %s",
        id,
        tipo.getDescripcion(),
        monto,
        estado.getIcono(),
        estado.getDescripcion());
  }

  public String generarReporteDetallado() {
    return String.format("""
        ═══════════════════════════════════
        DETALLE DE TRANSACCIÓN
        ═══════════════════════════════════
        ID: %s
        Tipo: %s
        Monto: $%.2f
        Estado: %s %s
        Descripción: %s
        Fecha Creación: %s
        Fecha Ejecución: %s
        Puntos Generados: %d
        ═══════════════════════════════════
        """,
        id,
        tipo.getDescripcion(),
        monto,
        estado.getIcono(),
        estado.getDescripcion(),
        descripcion,
        fechaCreacion,
        fechaEjecucion != null ? fechaEjecucion : "Pendiente",
        puntosGenerados);
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

  public LocalDateTime getFechaCreacion() {
    return fechaCreacion;
  }

  public LocalDateTime getFechaEjecucion() {
    return fechaEjecucion;
  }

  public EstadoTransaccion getEstado() {
    return estado;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public int getPuntosGenerados() {
    return puntosGenerados;
  }

  protected void setEstado(EstadoTransaccion estado) {
    this.estado = estado;
  }

  protected void setPuntosGenerados(int puntos) {
    this.puntosGenerados = puntos;
  }

  @Override
  public String toString() {
    return generarResumen();
  }
}