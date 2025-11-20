package co.edu.uniquindio.poo.model;

import java.util.UUID;

public class Beneficio implements Reporteable {

  private final String id;
  private final String nombre;
  private final String descripcion;
  private final int puntosRequeridos;
  private final TipoBeneficio tipo;
  private final double valorDescuento;
  private boolean activo;

  public enum TipoBeneficio {
    DESCUENTO_COMISION("Descuento en Comisión"),
    BONO_SALDO("Bono de Saldo"),
    SIN_CARGOS("Mes Sin Cargos"),
    PUNTOS_EXTRA("Puntos Extra");

    private final String descripcion;

    TipoBeneficio(String desc) {
      this.descripcion = desc;
    }

    public String getDescripcion() {
      return descripcion;
    }
  }

  public Beneficio(String nombre, String descripcion, int puntosRequeridos,
      TipoBeneficio tipo, double valorDescuento) {
    this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.puntosRequeridos = puntosRequeridos;
    this.tipo = tipo;
    this.valorDescuento = valorDescuento;
    this.activo = true;
  }

  /**
   * Verifica si el cliente puede canjear este beneficio
   * según sus puntos acumulados y el estado del beneficio
   * 
   * @param cliente El cliente a evaluar
   * @return true si el cliente puede canjear el beneficio
   */
  public boolean puedeSerCanjeado(Cliente cliente) {
    return activo && cliente.getPuntosAcumulados() >= puntosRequeridos;
  }

  /**
   * Genera un reporte resumido del beneficio con nombre, puntos y estado
   * 
   * @return String formateado con información básica
   */
  @Override
  public String generarReporte() {
    return String.format("%s - %d puntos - %s",
        nombre,
        puntosRequeridos,
        activo ? "Activo" : "Inactivo");
  }

  /**
   * Genera un reporte detallado del beneficio con toda la información disponible
   * 
   * @return String formateado con información completa del beneficio
   */
  @Override
  public String generarReporteDetallado() {
    return String.format("""

        BENEFICIO

        ID: %s
        Nombre: %s
        Descripción: %s
        Tipo: %s
        Puntos Requeridos: %d
        Valor: %.2f
        Estado: %s

        """,
        id, nombre, descripcion,
        tipo.getDescripcion(),
        puntosRequeridos,
        valorDescuento,
        activo ? "Activo " : "Inactivo ");
  }

  public String getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public int getPuntosRequeridos() {
    return puntosRequeridos;
  }

  public TipoBeneficio getTipo() {
    return tipo;
  }

  public double getValorDescuento() {
    return valorDescuento;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}