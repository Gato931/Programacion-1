package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.*;

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

  /**
   * Método abstracto que cada tipo de transacción debe implementar
   * Ejecuta la lógica específica de la transacción (depósito, retiro,
   * transferencia)
   * 
   * @return true si la ejecución fue exitosa
   */
  public abstract boolean ejecutar();

  /**
   * Método abstracto para revertir una transacción completada
   * Devuelve el dinero y resta los puntos generados
   * 
   * @return true si la reversión fue exitosa
   */
  public abstract boolean revertir();

  /**
   * Calcula los puntos base que genera esta transacción
   * Usa la fórmula: (monto / 10.000) * puntos_por_tipo
   * Depósito: 1 pto c/10K, Retiro: 2 pts c/10K, Transferencia: 3 pts c/10K
   * 
   * @return Puntos base sin aplicar bonus de rango
   */
  public int calcularPuntosBase() {
    return (int) (monto / 10_000.0) * tipo.getPuntosPor10000();
  }

  /**
   * Aplica el bonus de puntos según el rango del cliente
   * Los puntos finales = puntos_base * (1 + bonus_rango)
   * Ejemplo: Oro tiene 10% bonus, entonces 100 pts → 110 pts
   * 
   * @param rango El rango del cliente para calcular bonus
   */
  public void aplicarBonusRango(RangoCliente rango) {
    int puntosBase = calcularPuntosBase();
    double bonus = rango.getBonusPuntos();
    this.puntosGenerados = (int) (puntosBase * (1 + bonus));
  }

  /**
   * Marca la transacción como completada y registra la hora de ejecución
   */
  protected void completar() {
    this.estado = EstadoTransaccion.COMPLETADA;
    this.fechaEjecucion = LocalDateTime.now();
  }

  /**
   * Marca la transacción como rechazada
   */
  protected void rechazar() {
    this.estado = EstadoTransaccion.RECHAZADA;
  }

  /**
   * Verifica si la transacción es válida
   * 
   * @return true si no tiene errores de validación
   */
  @Override
  public boolean esValida() {
    return obtenerErroresValidacion().isEmpty();
  }

  /**
   * Obtiene la lista de errores de validación
   * Valida monto positivo y tipo no nulo
   * 
   * @return Lista de mensajes de error
   */
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

  /**
   * Genera un resumen corto de la transacción
   * 
   * @return String con ID, tipo, monto y estado
   */
  public String generarResumen() {
    return String.format("[%s] %s - $%.2f - %s %s",
        id, tipo.getDescripcion(), monto,
        estado.getIcono(), estado.getDescripcion());
  }

  /**
   * Genera un reporte detallado de la transacción
   * Incluye todos los campos: ID, tipo, monto, fechas, puntos, etc.
   * 
   * @return String con reporte completo formateado
   */
  public String generarReporteDetallado() {
    return String.format("""

        DETALLE DE TRANSACCIÓN

        ID: %s
        Tipo: %s
        Monto: $%.2f
        Estado: %s %s
        Descripción: %s
        Fecha Creación: %s
        Fecha Ejecución: %s
        Puntos Generados: %d

        """,
        id, tipo.getDescripcion(), monto,
        estado.getIcono(), estado.getDescripcion(),
        descripcion, fechaCreacion,
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

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setFechaEjecucion(LocalDateTime fechaEjecucion) {
    this.fechaEjecucion = fechaEjecucion;
  }

  @Override
  public String toString() {
    return generarResumen();
  }
}