package co.edu.uniquindio.poo;

public class ProductoElectrodomestico extends Producto {
  private int garantiaMeses;

  public ProductoElectrodomestico(String codigo, String nombre, double precioUnitario, int garantiaMeses) {
    super(codigo, nombre, precioUnitario);
    this.garantiaMeses = garantiaMeses;
  }

  public int getGarantiaMeses() {
    return garantiaMeses;
  }

  public void setGarantiaMeses(int garantiaMeses) {
    this.garantiaMeses = garantiaMeses;
  }
}
