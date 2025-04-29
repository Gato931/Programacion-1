package co.edu.uniquindio.poo;

public class Motocicleta extends Vehiculo {
  private int cilindrada;
  private ZonaEntrega zona;
  public Motocicleta(String placa, String marca, String modelo, int cilindrada) {
    super(placa, marca, modelo);
    this.cilindrada = cilindrada;
  }

  @Override
  public double calcularCosto(Envio envio) {
    ZonaEntrega zona = envio.getZonaEntrega();
    int factor = zona == ZonaEntrega.RURAL ? 15000 : 8000;
    return envio.getPaquetes().size() * factor;
  }

  public ZonaEntrega getZona() {
    return zona;
  }

  public void setZona(ZonaEntrega zona) {
    this.zona = zona;
  }

  public int getCilindrada() {
    return cilindrada;
  }

  public void setCilindrada(int cilindrada) {
    this.cilindrada = cilindrada;
  }
}
