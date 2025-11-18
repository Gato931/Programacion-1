package co.edu.uniquindio.poo.model;

public interface Puntuable {
  void acumularPuntos(int puntos);

  int getPuntosAcumulados();

  boolean canjearPuntos(int puntos);

  RangoCliente calcularRango();
}