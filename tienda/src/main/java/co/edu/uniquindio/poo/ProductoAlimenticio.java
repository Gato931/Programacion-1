package co.edu.uniquindio.poo;

import java.time.LocalDate;

public class ProductoAlimenticio extends Producto {
  private LocalDate fechaVencimiento;

  public ProductoAlimenticio(String codigo, String nombre, double precioUnitario, LocalDate fechaVencimiento) {
    super(codigo, nombre, precioUnitario);
    this.fechaVencimiento = fechaVencimiento;
  }

  public LocalDate getFechaVencimiento() {
    return fechaVencimiento;
  }

  public void setFechaVencimiento(LocalDate fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }
}
