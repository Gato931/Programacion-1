package co.edu.uniquindio.poo.model;

public enum TipoMonedero {
  PRINCIPAL("Principal", "[P]"),
  AHORRO("Ahorro", "[A]"),
  GASTOS_DIARIOS("Gastos Diarios", "[GD]"),
  EMERGENCIA("Emergencia", "[E]"),
  INVERSION("Inversion", "[I]");

  private final String nombre;
  private final String icono;

  TipoMonedero(String nombre, String icono) {
    this.nombre = nombre;
    this.icono = icono;
  }

  public String getNombre() {
    return nombre;
  }

  public String getIcono() {
    return icono;
  }
}