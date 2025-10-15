package co.edu.uniquindio.poo;

public abstract class Producto {
  private String codigo;
  private String nombre;
  private double precioUnitario;

  public Producto(String codigo, String nombre, double precioUnitario) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.precioUnitario = precioUnitario;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setPrecioUnitario(double precioUnitario) {
    this.precioUnitario = precioUnitario;
  }

  public double getPrecioUnitario() {
    return precioUnitario;
  }

  public String getNombre() {
    return nombre;
  }

  public String getCodigo() {
    return codigo;
  }

  @Override
  public String toString() {
    return nombre + " - $" + precioUnitario;
  }
}
