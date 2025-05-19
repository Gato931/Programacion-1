package co.edu.uniquindio.poo;

public class Camion extends Vehiculo {
  private int ejes;
  private double capacidadCarga;

  public Camion(String placa, int peajesPagados, int ejes, double capacidadCarga) {
    super(placa, peajesPagados);
    this.ejes = ejes;
    this.capacidadCarga = capacidadCarga;
  }

  public int getEjes() {
    return ejes;
  }

  public void setEjes(int ejes) {
    this.ejes = ejes;
  }

  public void setCapacidadCarga(double capacidadCarga) {
    this.capacidadCarga = capacidadCarga;
  }

  public double getCapacidadCarga() {
    return capacidadCarga;
  }

  @Override
  public double calcularPeaje() {
    double base = 7000 * ejes;
    if (capacidadCarga > 10)
      base *= 1.10;
    return base;
  }

  public String obtenerDetalleCalculoPeaje() {
    double base = 7000 * ejes;
    String detalles = "Base: 7000 x Ejes (" + ejes + ") = " + (7000 * ejes);
    double valorPeaje = base;
    if (capacidadCarga > 10) {
      valorPeaje *= 1.10;
      detalles += ", Carga > 10t (x1.10)";
    }
    return detalles + " = " + valorPeaje;
  }

  @Override
  public String obtenerDescripcion() {
    return "Camion - Placa: " + placa + ", Ejes: " + ejes + ", Carga: " + capacidadCarga + "t";
  }
}
