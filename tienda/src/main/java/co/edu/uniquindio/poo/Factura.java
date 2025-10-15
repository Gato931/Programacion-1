package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {
  private int numero;
  private LocalDate fecha;
  private Cliente cliente;
  private List<DetalleFactura> detalles;

  public Factura(int numero, Cliente cliente) {
    this.numero = numero;
    this.fecha = LocalDate.now();
    this.cliente = cliente;
    this.detalles = new ArrayList<>();
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<DetalleFactura> getDetalles() {
    return detalles;
  }

  public void setDetalles(List<DetalleFactura> detalles) {
    this.detalles = detalles;
  }

  public void agregarDetalle(Producto producto, int cantidad) {
    detalles.add(new DetalleFactura(producto, cantidad));
  }

  public double calcularTotal() {
    double total = 0;
    for (DetalleFactura d : detalles) {
      total += d.calcularSubtotal();
    }
    double descuento = cliente.calcularDescuento(total);
    return total - descuento;
  }

  public void mostrarFactura() {
    System.out.println("Factura No. " + numero);
    System.out.println("Fecha: " + fecha);
    System.out.println("Cliente: " + cliente);
    System.out.println("Detalles:");
    for (DetalleFactura d : detalles) {
      System.out.println("- " + d);
    }
    System.out.println("Total a pagar: $" + calcularTotal());
  }
}
