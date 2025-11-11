package co.edu.uniquindio.poo;

public class Equipo implements Reporteable, Costeable {
  private String codigo;
  private TipoEquipo tipo;
  private EstadoEquipo estado;
  private double valorReposicion;
  private double costoMantenimientoMensual;

  public Equipo(String codigo, TipoEquipo tipo, double valorReposicion) {
    this.codigo = codigo;
    this.tipo = tipo;
    this.estado = EstadoEquipo.OPERATIVO;
    this.valorReposicion = valorReposicion;
    this.costoMantenimientoMensual = valorReposicion * 0.02; // 2% del valor
  }

  // Ejercicio 16: Generar equipo de repuesto
  public Equipo generarRepuesto(String nuevoCodigo) {
    if (nuevoCodigo == null || nuevoCodigo.trim().isEmpty()) {
      throw new IllegalArgumentException("El código del repuesto no puede estar vacío");
    }

    Equipo repuesto = new Equipo(nuevoCodigo, this.tipo, this.valorReposicion);
    repuesto.setEstado(EstadoEquipo.ALMACEN);
    return repuesto;
  }

  // Implementación de Costeable
  @Override
  public double obtenerCostoMensual() {
    return costoMantenimientoMensual;
  }

  // Implementación de Reporteable
  @Override
  public String generarReporte() {
    return String.format("""
        ══════════════════════════════════
        REPORTE DE EQUIPO
        ══════════════════════════════════
        Código: %s
        Tipo: %s
        Estado: %s
        Valor Reposición: $%,.2f
        Costo Mantenimiento: $%,.2f
        ══════════════════════════════════
        """,
        codigo, tipo, estado, valorReposicion, costoMantenimientoMensual);
  }

  // Getters y Setters
  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public TipoEquipo getTipo() {
    return tipo;
  }

  public void setTipo(TipoEquipo tipo) {
    this.tipo = tipo;
  }

  public EstadoEquipo getEstado() {
    return estado;
  }

  public void setEstado(EstadoEquipo estado) {
    this.estado = estado;
  }

  public double getValorReposicion() {
    return valorReposicion;
  }

  public void setValorReposicion(double valorReposicion) {
    this.valorReposicion = valorReposicion;
  }

  public double getCostoMantenimientoMensual() {
    return costoMantenimientoMensual;
  }

  @Override
  public String toString() {
    return String.format("%s [%s] - Estado: %s - Valor: $%.2f",
        tipo, codigo, estado, valorReposicion);
  }
}