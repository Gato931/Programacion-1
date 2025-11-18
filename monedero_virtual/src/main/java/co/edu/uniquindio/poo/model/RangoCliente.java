package co.edu.uniquindio.poo.model;

public enum RangoCliente {
  BRONCE("Bronce", 0, 500, 1.0, 0.0),
  PLATA("Plata", 501, 1000, 0.95, 0.05),
  ORO("Oro", 1001, 5000, 0.90, 0.10),
  PLATINO("Platino", 5001, Integer.MAX_VALUE, 0.85, 0.15);

  private final String nombre;
  private final int puntosMinimos;
  private final int puntosMaximos;
  private final double descuentoComision;
  private final double bonusPuntos;

  RangoCliente(String nombre, int min, int max, double descuento, double bonus) {
    this.nombre = nombre;
    this.puntosMinimos = min;
    this.puntosMaximos = max;
    this.descuentoComision = descuento;
    this.bonusPuntos = bonus;
  }

  public static RangoCliente obtenerRangoPorPuntos(int puntos) {
    for (RangoCliente rango : values()) {
      if (puntos >= rango.puntosMinimos && puntos <= rango.puntosMaximos) {
        return rango;
      }
    }
    return BRONCE;
  }

  public String getNombre() {
    return nombre;
  }

  public int getPuntosMinimos() {
    return puntosMinimos;
  }

  public int getPuntosMaximos() {
    return puntosMaximos;
  }

  public double getDescuentoComision() {
    return descuentoComision;
  }

  public double getBonusPuntos() {
    return bonusPuntos;
  }

  public String getIcono() {
    return switch (this) {
      case BRONCE -> "[BRONCE]";
      case PLATA -> "[PLATA]";
      case ORO -> "[ORO]";
      case PLATINO -> "[PLATINO]";
    };
  }
}