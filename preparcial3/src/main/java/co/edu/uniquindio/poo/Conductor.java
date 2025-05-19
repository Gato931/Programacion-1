package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Conductor extends Persona {
  private List<Vehiculo> vehiculos;

  public Conductor(String nombre, String apellidos, String documento, LocalDate fechaNacimiento) {
    super(nombre, apellidos, documento, fechaNacimiento);
    this.vehiculos = new ArrayList<>();
  }

  public List<Vehiculo> getVehiculos() {
    return vehiculos;
  }

  public void setVehiculos(List<Vehiculo> vehiculos) {
    this.vehiculos = vehiculos;
  }

  public void asignarVehiculo(Vehiculo vehiculo) {
    vehiculos.add(vehiculo);
  }

  public List<Vehiculo> obtenerVehiculosPorTipo(String tipo) {
    List<Vehiculo> resultado = new ArrayList<>();
    for (Vehiculo vehiculo : vehiculos) {
      if (vehiculo.getClass().getSimpleName().equalsIgnoreCase(tipo)) {
        resultado.add(vehiculo);
      }
    }
    return resultado;
  }

  public void imprimirTotalPeajesPorVehiculo() {
    System.out.println("Reporte de peajes pagados por " + nombre + " " + apellidos + ":");
    for (Vehiculo vehiculo : vehiculos) {
      double peaje = vehiculo.calcularPeaje();
      String detallePeaje = "";
      if (vehiculo instanceof Carro carro) {
        detallePeaje = carro.obtenerDetalleCalculoPeaje();
      } else if (vehiculo instanceof Moto moto) {
        detallePeaje = moto.obtenerDetalleCalculoPeaje();
      } else if (vehiculo instanceof Camion camion) {
        detallePeaje = camion.obtenerDetalleCalculoPeaje();
      }
      System.out.println("- " + vehiculo.obtenerDescripcion() + ": " + peaje + " (Cálculo: " + detallePeaje + ")");
    }
  }
}
