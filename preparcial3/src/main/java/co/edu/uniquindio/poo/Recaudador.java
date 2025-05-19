package co.edu.uniquindio.poo;

import java.time.LocalDate;

public class Recaudador extends Persona {
  private double sueldoMensual;

  public Recaudador(String nombre, String apellidos, String documento, LocalDate fechaNacimiento, double sueldo) {
    super(nombre, apellidos, documento, fechaNacimiento);
    this.sueldoMensual = sueldo;
  }

  public double getSueldoMensual() {
    return sueldoMensual;
  }

  public void setSueldoMensual(double sueldoMensual) {
    this.sueldoMensual = sueldoMensual;
  }

}
