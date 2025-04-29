package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Envio {
  private String codigo;
  private ZonaEntrega zonaEntrega;
  private Date fechaEnvio;
  private Cliente cliente;
  private Ruta ruta;
  private Vehiculo vehiculo;
  private List<Paquete> paquetes;

  public Envio(String codigo, ZonaEntrega zonaEntrega, Date fechaEnvio, Cliente cliente, Ruta ruta, Vehiculo vehiculo) {
    this.codigo = codigo;
    this.zonaEntrega = zonaEntrega;
    this.fechaEnvio = fechaEnvio;
    this.cliente = cliente;
    this.ruta = ruta;
    this.vehiculo = vehiculo;
    this.paquetes = new ArrayList<>();
    cliente.agregarEnvio(this);
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public void setZonaEntrega(ZonaEntrega zonaEntrega) {
    this.zonaEntrega = zonaEntrega;
  }

  public Date getFechaEnvio() {
    return fechaEnvio;
  }

  public void setFechaEnvio(Date fechaEnvio) {
    this.fechaEnvio = fechaEnvio;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public void setRuta(Ruta ruta) {
    this.ruta = ruta;
  }

  public Vehiculo getVehiculo() {
    return vehiculo;
  }

  public void setVehiculo(Vehiculo vehiculo) {
    this.vehiculo = vehiculo;
  }

  public void setPaquetes(List<Paquete> paquetes) {
    this.paquetes = paquetes;
  }

  public void agregarPaquete(Paquete paquete) {
    paquetes.add(paquete);
  }

  public double calcularPesoTotal() {
    double total = 0;
    for (Paquete p : paquetes)
      total += p.getPeso();
    return total;
  }

  public double calcularCostoEnvio() {
    return vehiculo.calcularCosto(this);
  }

  public List<Paquete> getPaquetes() {
    return paquetes;
  }

  public Ruta getRuta() {
    return ruta;
  }

  public ZonaEntrega getZonaEntrega() {
    return zonaEntrega;
  }
}
