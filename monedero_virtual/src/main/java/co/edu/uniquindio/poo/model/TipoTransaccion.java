package co.edu.uniquindio.poo.model;

public enum TipoTransaccion {
  DEPOSITO("Deposito", 1),
  RETIRO("Retiro", 2),
  TRANSFERENCIA("Transferencia", 3),
  CANJE_PUNTOS("Canje de Puntos", 0);

  private final String descripcion;
  private final int puntosPor10000;

  TipoTransaccion(String descripcion, int puntosPor10000) {
    this.descripcion = descripcion;
    this.puntosPor10000 = puntosPor10000;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public int getPuntosPor10000() {
    return puntosPor10000;
  }
}