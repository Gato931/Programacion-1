package co.edu.uniquindio.poo;

public abstract class Cliente {
  private String nombre;
  private String cedula;
  private String direccion;

  public Cliente(String nombre, String cedula, String direccion) {
    this.nombre = nombre;
    this.cedula = cedula;
    this.direccion = direccion;
  }

  public abstract double calcularDescuento(double total);

  public String getNombre() {
    return nombre;
  }

  public String getCedula() {
    return cedula;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  @Override
  public String toString() {
    return nombre + " (" + cedula + ")";
  }
}
