package co.edu.uniquindio.poo;

public class ClienteCorporativo extends Cliente {
  private String nit;
  private double descuentoEspecial;

  public ClienteCorporativo(String nombre, String cedula, String direccion, String nit, double descuentoEspecial) {
    super(nombre, cedula, direccion);
    this.nit = nit;
    this.descuentoEspecial = descuentoEspecial;
  }

  @Override
  public double calcularDescuento(double total) {
    return total * descuentoEspecial;
  }

  public void setNit(String nit) {
    this.nit = nit;
  }

  public double getDescuentoEspecial() {
    return descuentoEspecial;
  }

  public void setDescuentoEspecial(double descuentoEspecial) {
    this.descuentoEspecial = descuentoEspecial;
  }

  public String getNit() {
    return nit;
  }
}
