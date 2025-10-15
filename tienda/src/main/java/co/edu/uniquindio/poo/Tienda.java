package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

public class Tienda {
  private String nombre;
  private List<Cliente> clientes;
  private List<Producto> productos;
  private List<Factura> facturas;

  public Tienda(String nombre) {
    this.nombre = nombre;
    clientes = new ArrayList<>();
    productos = new ArrayList<>();
    facturas = new ArrayList<>();
  }

  public String getNombre() {
    return nombre;
  }

  public List<Cliente> getClientes() {
    return clientes;
  }

  public List<Producto> getProductos() {
    return productos;
  }

  public List<Factura> getFacturas() {
    return facturas;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setClientes(List<Cliente> clientes) {
    this.clientes = clientes;
  }

  public void setProductos(List<Producto> productos) {
    this.productos = productos;
  }

  public void setFacturas(List<Factura> facturas) {
    this.facturas = facturas;
  }

  public void registrarProducto(Producto producto) {
    productos.add(producto);
  }

  public void registrarFactura(Factura factura) {
    facturas.add(factura);
  }

  public void registrarCliente(Cliente cliente) {
    clientes.add(cliente);
  }

  public Cliente buscarClientePorCedula(String cedula) {
    for (Cliente c : clientes) {
      if (c.getCedula().equalsIgnoreCase(cedula)) {
        return c;
      }
    }
    return null;
  }

  public Producto buscarProductoPorCodigo(String codigo) {
    for (Producto p : productos) {
      if (p.getCodigo().equalsIgnoreCase(codigo)) {
        return p;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "Tienda: " + nombre + " | Clientes: " + clientes.size() +
        " | Productos: " + productos.size() +
        " | Facturas: " + facturas.size();
  }
}
