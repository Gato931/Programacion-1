package co.edu.uniquindio.poo;

import java.util.ArrayList;

public class Propietario {
  private String nombre;
  private String identificacion;
  private String telefono;
  private String direccion;
  private String apellido;
  private ArrayList<Mascota> listaMascotas;

  public Propietario(String nombre, String identificacion, String telefono, String direccion, String apellido) {
    this.nombre = nombre;
    this.identificacion = identificacion;
    this.telefono = telefono;
    this.direccion = direccion;
    this.apellido = apellido;
    this.listaMascotas = new ArrayList<>();
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public ArrayList<Mascota> getListaMascotas() {
    return listaMascotas;
  }

  public void setListaMascotas(ArrayList<Mascota> listaMascotas) {
    this.listaMascotas = listaMascotas;
  }

  @Override
  public String toString() {
    return "Propietario [nombre=" + nombre + ", identificacion=" + identificacion + ", telefono=" + telefono
        + ", direccion=" + direccion + ", apellido=" + apellido + ", listaMascotas=" + listaMascotas + "]";
  }

}
