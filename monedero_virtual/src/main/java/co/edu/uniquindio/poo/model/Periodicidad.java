package co.edu.uniquindio.poo.model;

public enum Periodicidad {
  UNICA("Única"),
  DIARIA("Diaria"),
  SEMANAL("Semanal"),
  QUINCENAL("Quincenal"),
  MENSUAL("Mensual"),
  ANUAL("Anual");

  private final String descripcion;

  Periodicidad(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return descripcion;
  }
}