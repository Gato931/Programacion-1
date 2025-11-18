package co.edu.uniquindio.poo.model;

public enum TipoNotificacion {
  SALDO_BAJO("Saldo Bajo", "[!]", true),
  TRANSACCION_COMPLETADA("Transaccion Completada", "[OK]", false),
  TRANSACCION_PROGRAMADA("Recordatorio Transaccion", "[PROG]", true),
  NUEVO_RANGO("Nuevo Rango Alcanzado", "[RANGO]", false),
  PUNTOS_ACUMULADOS("Puntos Acumulados", "[PTS]", false),
  BENEFICIO_CANJEADO("Beneficio Canjeado", "[GIFT]", false);

  private final String descripcion;
  private final String icono;
  private final boolean esAlerta;

  TipoNotificacion(String descripcion, String icono, boolean esAlerta) {
    this.descripcion = descripcion;
    this.icono = icono;
    this.esAlerta = esAlerta;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getIcono() {
    return icono;
  }

  public boolean esAlerta() {
    return esAlerta;
  }
}