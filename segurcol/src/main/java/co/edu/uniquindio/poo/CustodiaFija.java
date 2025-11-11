package co.edu.uniquindio.poo;

public class CustodiaFija extends Servicio {
  private int numeroVigilantes;
  private String ubicacion;

  public CustodiaFija(String codigoContrato, String cliente, double tarifaMensual,
      int numeroVigilantes, String ubicacion) {
    super(codigoContrato, cliente, tarifaMensual);
    this.numeroVigilantes = numeroVigilantes;
    this.ubicacion = ubicacion;
  }

  @Override
  public double calcularCostoMensual() {
    double costo = getTarifaMensual();

    // Costo adicional por vigilante
    costo += numeroVigilantes * 2500000; // $2,500,000 por vigilante

    // Descuento por múltiples vigilantes
    if (numeroVigilantes > 5) {
      costo *= 0.90; // 10% descuento
    }

    return costo;
  }

  public int getNumeroVigilantes() {
    return numeroVigilantes;
  }

  public void setNumeroVigilantes(int numeroVigilantes) {
    this.numeroVigilantes = numeroVigilantes;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }
}