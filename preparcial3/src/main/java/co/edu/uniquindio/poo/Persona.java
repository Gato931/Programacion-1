package co.edu.uniquindio.poo;

import java.time.LocalDate;

public abstract class Persona {
  protected String nombre;
  protected String apellidos;
  protected String documento;
  protected LocalDate fechaNacimiento;

  public Persona(String nombre, String apellidos, String documento, LocalDate fechaNacimiento) {
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.documento = documento;
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }
}
