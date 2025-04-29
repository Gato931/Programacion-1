package co.edu.uniquindio.poo;

public class Furgoneta extends Vehiculo {
  private String transmision; // "manual" o "automatica"

  public Furgoneta(String placa, String marca, String modelo, String transmision) {
    super(placa, marca, modelo);
    this.transmision = transmision;
  }

  @Override
  public double calcularCosto(Envio envio) {
    return envio.getRuta().getDistanciaKm() * 3000 + 10000;
  }

  public String getTransmision() {
    return transmision;
  }

  public void setTransmision(String transmision) {
    this.transmision = transmision;
  }
}
