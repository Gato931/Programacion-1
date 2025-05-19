package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

public class Peaje {
  private String nombre;
  private String departamento;
  private double valorTotalRecaudado;
  private List<IPeajeCalculable> vehiculosProcesados;
  private List<Double> valoresPeajeProcesados;
  private List<Conductor> conductoresVehiculosProcesados;
  private List<Recaudador> recaudadores;
  private List<Conductor> conductoresRegistrados;

  public Peaje(String nombre, String departamento) {
    this.nombre = nombre;
    this.departamento = departamento;
    this.valorTotalRecaudado = 0;
    this.vehiculosProcesados = new ArrayList<>();
    this.valoresPeajeProcesados = new ArrayList<>();
    this.conductoresVehiculosProcesados = new ArrayList<>();
    this.recaudadores = new ArrayList<>();
    this.conductoresRegistrados = new ArrayList<>();
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDepartamento() {
    return departamento;
  }

  public void setDepartamento(String departamento) {
    this.departamento = departamento;
  }

  public double getValorTotalRecaudado() {
    return valorTotalRecaudado;
  }

  public void setValorTotalRecaudado(double valorTotalRecaudado) {
    this.valorTotalRecaudado = valorTotalRecaudado;
  }

  public List<IPeajeCalculable> getVehiculosProcesados() {
    return vehiculosProcesados;
  }

  public void setVehiculosProcesados(List<IPeajeCalculable> vehiculosProcesados) {
    this.vehiculosProcesados = vehiculosProcesados;
  }

  public List<Double> getValoresPeajeProcesados() {
    return valoresPeajeProcesados;
  }

  public void setValoresPeajeProcesados(List<Double> valoresPeajeProcesados) {
    this.valoresPeajeProcesados = valoresPeajeProcesados;
  }

  public List<Conductor> getConductoresVehiculosProcesados() {
    return conductoresVehiculosProcesados;
  }

  public void setConductoresVehiculosProcesados(List<Conductor> conductoresVehiculosProcesados) {
    this.conductoresVehiculosProcesados = conductoresVehiculosProcesados;
  }

  public List<Recaudador> getRecaudadores() {
    return recaudadores;
  }

  public void setRecaudadores(List<Recaudador> recaudadores) {
    this.recaudadores = recaudadores;
  }

  public List<Conductor> getConductoresRegistrados() {
    return conductoresRegistrados;
  }

  public void setConductoresRegistrados(List<Conductor> conductoresRegistrados) {
    this.conductoresRegistrados = conductoresRegistrados;
  }

  public void registrarConductor(Conductor conductor) {
    if (!this.conductoresRegistrados.contains(conductor)) {
      this.conductoresRegistrados.add(conductor);
    }
  }

  public void agregarRecaudador(Recaudador recaudador) {
    this.recaudadores.add(recaudador);
  }

  public void procesarVehiculo(IPeajeCalculable vehiculo, Conductor conductor) {
    double valor = vehiculo.calcularPeaje();
    valorTotalRecaudado += valor;
    this.vehiculosProcesados.add(vehiculo);
    this.valoresPeajeProcesados.add(valor);
    this.conductoresVehiculosProcesados.add(conductor);
    System.out.println(vehiculo.obtenerDescripcion() + " - Valor peaje: " + valor);
  }

  public void imprimirResumen() {
    System.out.println("Resumen Peaje - " + nombre);
    for (int i = 0; i < this.vehiculosProcesados.size(); i++) {
      IPeajeCalculable vehiculo = this.vehiculosProcesados.get(i);
      double valorPeaje = this.valoresPeajeProcesados.get(i);
      String detallePeaje = "";
      if (vehiculo instanceof Carro carro) {
        detallePeaje = carro.obtenerDetalleCalculoPeaje();
      } else if (vehiculo instanceof Moto moto) {
        detallePeaje = moto.obtenerDetalleCalculoPeaje();
      } else if (vehiculo instanceof Camion camion) {
        detallePeaje = camion.obtenerDetalleCalculoPeaje();
      }
      System.out
          .println(vehiculo.obtenerDescripcion() + " - Peaje pagado: " + valorPeaje + " (Cálculo: " + detallePeaje + ")");
    }
    System.out.println("Total recaudado: " + valorTotalRecaudado);
  }

  public Recaudador buscarRecaudadorPorNombreCompleto(String nombreCompleto) {
    if (nombreCompleto == null) {
      return null;
    }
    String nombreCompletoNormalizado = nombreCompleto.trim().toLowerCase().replaceAll("\\s+", " ");
    for (Recaudador recaudador : this.recaudadores) {
      String nombreCompletoRecaudador = (recaudador.getNombre() + " " + recaudador.getApellidos()).trim().toLowerCase()
          .replaceAll("\\s+", " ");
      if (nombreCompletoNormalizado.equals(nombreCompletoRecaudador)) {
        return recaudador;
      }
    }
    return null;
  }

  public List<Conductor> conductoresConCamionAltoTonelaje() {
    List<Conductor> conductoresUnicos = new ArrayList<>();
    for (int i = 0; i < this.vehiculosProcesados.size(); i++) {
      IPeajeCalculable vehiculo = this.vehiculosProcesados.get(i);
      Conductor conductor = this.conductoresVehiculosProcesados.get(i);
      if (conductor != null && vehiculo instanceof Camion camion && camion.getCapacidadCarga() > 10) {
        if (!conductoresUnicos.contains(conductor)) {
          conductoresUnicos.add(conductor);
        }
      }
    }
    return conductoresUnicos;
  }
}