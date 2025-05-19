package co.edu.uniquindio.poo;

public class Carro extends Vehiculo {
  private boolean electrico;
  private boolean servicioPublico;

  public Carro(String placa, int peajesPagados, boolean electrico, boolean servicioPublico) {
    super(placa, peajesPagados);
    this.electrico = electrico;
    this.servicioPublico = servicioPublico;
  }

  public boolean isElectrico() {
    return electrico;
  }

  public void setElectrico(boolean electrico) {
    this.electrico = electrico;
  }

  public boolean isServicioPublico() {
    return servicioPublico;
  }

  public void setServicioPublico(boolean servicioPublico) {
    this.servicioPublico = servicioPublico;
  }

  @Override
  public double calcularPeaje() {
    double base = 10000;
    if (electrico)
      base *= 0.8;
    if (servicioPublico)
      base *= 1.15;
    return base;
  }

  public String obtenerDetalleCalculoPeaje() {
    double base = 10000;
    String detalles = "Base: 10000";
    double valorPeaje = base;
    if (electrico) {
      valorPeaje *= 0.8;
      detalles += ", Eléctrico (x0.8)";
    }
    if (servicioPublico) {
      valorPeaje *= 1.15;
      detalles += ", Servicio Público (x1.15)";
    }
    return detalles + " = " + valorPeaje;
  }

  @Override
  public String obtenerDescripcion() {
    return "Carro - Placa: " + placa + ", Eléctrico: " + electrico + ", Servicio Público: " + servicioPublico;
  }
}
