package co.edu.uniquindio.poo;

import java.util.ArrayList;

public class Mascota {
  private String id;
  private String nombre;
  private String especie;
  private String raza;
  private int edad;
  private String color;
  private double peso;
  private ArrayList<String> listaEnfermedades;

  public Mascota(String id, String nombre, String especie, String raza, int edad, String color, double peso) {
    this.id = id;
    this.nombre = nombre;
    this.especie = especie;
    this.raza = raza;
    this.edad = edad;
    this.color = color;
    this.peso = peso;
    this.listaEnfermedades = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEspecie() {
    return especie;
  }

  public void setEspecie(String especie) {
    this.especie = especie;
  }

  public String getRaza() {
    return raza;
  }

  public void setRaza(String raza) {
    this.raza = raza;
  }

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public double getPeso() {
    return peso;
  }

  public void setPeso(double peso) {
    this.peso = peso;
  }

  public ArrayList<String> getListaEnfermedades() {
    return listaEnfermedades;
  }

  public void setListaEnfermedades(ArrayList<String> listaEnfermedades) {
    this.listaEnfermedades = listaEnfermedades;
  }

  public void agregarEnfermedad(String enfermedad) {
    if (enfermedad != null) {
      this.listaEnfermedades.add(enfermedad);
    }
  }

  @Override
  public String toString() {
    return "Mascota [id=" + id + ", nombre=" + nombre + ", especie=" + especie + ", raza=" + raza + ", edad=" + edad
        + ", color=" + color + ", peso=" + peso + ", enfermedades=" + listaEnfermedades + "]";
  }
}
