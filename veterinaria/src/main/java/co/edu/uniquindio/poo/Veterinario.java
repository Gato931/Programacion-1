package co.edu.uniquindio.poo;

import java.util.ArrayList;

public class Veterinario {
  private String nombre;
  private String identificacion;
  private String tarjetaProfesional;
  private int añosExperiencia;
  private String telefono;
  private String correo;
  private Especialidad especialidad;
  private ArrayList<Cita> listaCitas;

  public Veterinario(String nombre, String identificacion, String tarjetaProfesional, int añosExperiencia,
      String especialidad, String telefono, String correo) {
    this.nombre = nombre;
    this.identificacion = identificacion;
    this.tarjetaProfesional = tarjetaProfesional;
    this.añosExperiencia = añosExperiencia;
    this.telefono = telefono;
    this.correo = correo;
    this.listaCitas = new ArrayList<>();
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

  public String getTarjetaProfesional() {
    return tarjetaProfesional;
  }

  public void setTarjetaProfesional(String tarjetaProfesional) {
    this.tarjetaProfesional = tarjetaProfesional;
  }

  public int getAñosExperiencia() {
    return añosExperiencia;
  }

  public void setAñosExperiencia(int añosExperiencia) {
    this.añosExperiencia = añosExperiencia;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public Especialidad getEspecialidad() {
    return especialidad;
  }

  public void setEspecialidad(Especialidad especialidad) {
    this.especialidad = especialidad;
  }

  public ArrayList<Cita> getListaCitas() {
    return listaCitas;
  }

  public void setListaCitas(ArrayList<Cita> listaCitas) {
    this.listaCitas = listaCitas;
  }

  @Override
  public String toString() {
    return "Veterinario [nombre=" + nombre + ", identificacion=" + identificacion + ", tarjetaProfesional="
        + tarjetaProfesional + ", añosExperiencia=" + añosExperiencia + ", telefono=" + telefono + ", correo=" + correo
        + ", especialidad=" + especialidad + ", listaCitas=" + listaCitas + "]";
  }

}
