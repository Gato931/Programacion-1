package co.edu.uniquindio.poo;

public class ClienteFrecuente extends Cliente {
  private int puntosFidelidad;
  private static final double DESCUENTO = 0.05;

  public ClienteFrecuente(String nombre, String cedula, String direccion, int puntosFidelidad) {
    super(nombre, cedula, direccion);
    this.puntosFidelidad = puntosFidelidad;
  }

  @Override
  public double calcularDescuento(double total) {
    return total * DESCUENTO;
  }

  public void setPuntosFidelidad(int puntosFidelidad) {
    this.puntosFidelidad = puntosFidelidad;
  }

  public static double getDescuento() {
    return DESCUENTO;
  }

  public int getPuntosFidelidad() {
    return puntosFidelidad;
  }
}
