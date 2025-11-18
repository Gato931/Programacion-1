package co.edu.uniquindio.poo.model;

public enum EstadoTransaccion {
  PENDIENTE("Pendiente", "[...]"),
  COMPLETADA("Completada", "[OK]"),
  RECHAZADA("Rechazada", "[X]"),
  PROGRAMADA("Programada", "[PROG]"),
  REVERTIDA("Revertida", "[REV]");

  private final String descripcion;
  private final String icono;

  EstadoTransaccion(String descripcion, String icono) {
    this.descripcion = descripcion;
    this.icono = icono;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getIcono() {
    return icono;
  }
}