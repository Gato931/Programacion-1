package co.edu.uniquindio.poo;

public class Ruta {
  private int numeroPeajes;
  private double distanciaKm;

  public Ruta(int numeroPeajes, double distanciaKm) {
    this.numeroPeajes = numeroPeajes;
    this.distanciaKm = distanciaKm;
  }

  public int getNumeroPeajes() {
    return numeroPeajes;
  }

  public double getDistanciaKm() {
    return distanciaKm;
  }
}
