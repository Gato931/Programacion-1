package co.edu.uniquindio.poo;

public class Camioneta extends Vehiculo {
  private double capacidadCarga;
  private ZonaEntrega zona;

  public Camioneta(String placa, String marca, String modelo, double capacidadCarga) {
    super(placa, marca, modelo);
    this.capacidadCarga = capacidadCarga;
  }

  @Override
  public double calcularCosto(Envio envio) {
    double peaje = envio.getRuta().getNumeroPeajes() * 12000;
    double pesoTotal = envio.calcularPesoTotal();
    return peaje + (pesoTotal * 7000);
  }

  public double getCapacidadCarga() {
    return capacidadCarga;
  }

  public void setCapacidadCarga(double capacidadCarga) {
    this.capacidadCarga = capacidadCarga;
  }

  public ZonaEntrega getZona() {
    return zona;
  }

  public void setZona(ZonaEntrega zona) {
    this.zona = zona;
  }
}
