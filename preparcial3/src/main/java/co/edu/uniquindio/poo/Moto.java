package co.edu.uniquindio.poo;

public class Moto extends Vehiculo {
  private int cilindraje;

  public Moto(String placa, int peajesPagados, int cilindraje) {
    super(placa, peajesPagados);
    this.cilindraje = cilindraje;
  }

  public int getCilindraje() {
    return cilindraje;
  }

  public void setCilindraje(int cilindraje) {
    this.cilindraje = cilindraje;
  }

  @Override
  public double calcularPeaje() {
    double base = 5000;
    if (cilindraje > 200)
      base += 2000;
    return base;
  }

  public String obtenerDetalleCalculoPeaje() {
    double base = 5000;
    String detalles = "Base: 5000";
    double valorPeaje = base;
    if (cilindraje > 200) {
      valorPeaje += 2000;
      detalles += ", Cilindraje > 200 (+2000)";
    }
    return detalles + " = " + valorPeaje;
  }

  @Override
  public String obtenerDescripcion() {
    return "Moto - Placa: " + placa + ", Cilindraje: " + cilindraje + "cc";
  }
}
