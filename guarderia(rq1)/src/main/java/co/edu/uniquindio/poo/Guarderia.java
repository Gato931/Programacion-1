package co.edu.uniquindio.poo;

import java.util.ArrayList;

public class Guarderia {
  private String nombre;
  private String nit;
  private ArrayList<Niño>listaNiños;

  public Guarderia(String nombre, String nit) {
    listaNiños = new ArrayList<>();
  }

  public String getNit() {
    return nit;
  }

  public void setNit(String nit) {
    this.nit = nit;
  }

  public ArrayList<Niño> getListaNiños() {
    return listaNiños;
  }

  public void setListaNiños(ArrayList<Niño> listaNiños) {
    this.listaNiños = listaNiños;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void agregarNiño(Niño niño) {
    listaNiños.add(niño);
    Main.mostrarMensaje("Niño agregado correctamente");
  }

  public void eliminiarNiño(Niño niño) {
    listaNiños.remove(niño);
    Main.mostrarMensaje("Niño eliminado correctamente");
  }

  public void mostrarNiños() {
    if (listaNiños.isEmpty()) {
      Main.mostrarMensaje("No hay niños matriculados");
    } else {
      Main.mostrarMensaje("Lista de niños matriculados:");
      for (Niño niños : listaNiños) {
        Main.mostrarMensaje("Nombre: " + niños.getNombre());
        Main.mostrarMensaje("Edad: " + niños.getEdad());
        Main.mostrarMensaje("Género: " + niños.getGenero());
        Main.mostrarMensaje("Documento: " + niños.getDocumento());
        Main.mostrarMensaje("Alergias: " + niños.getAlergias());
        Main.mostrarMensaje("Acudiente: " + niños.getAcudiente());
        Main.mostrarMensaje("Número de Acudiente: " + niños.getNumeroAcudiente());
      }
    }
  }
}