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

  public void mostrarMensaje(String mensaje){
    System.out.println(mensaje);
  }

  public void agregarNiño(Niño niño) {
    boolean existe = false;
    for (Niño niñoExistente : listaNiños) {
      if (niñoExistente.getDocumento().equals(niño.getDocumento())) {
        existe = true;
        break;
      }
    }
    if (!existe) {
      listaNiños.add(niño);
      mostrarMensaje("Niño agregado correctamente");
    } else {
      mostrarMensaje("Ya existe un niño con el mismo documento");
    }
  }

  public void eliminarNiño(Niño niño) {
    listaNiños.remove(niño);
    mostrarMensaje("Niño eliminado correctamente");
  }

  public void actualizarNiño(Niño niñoActualizar) {

    for (Niño niño : listaNiños) {

      if (niño.getDocumento().equals(niñoActualizar.getDocumento())) {

        niño.setNombre(niñoActualizar.getNombre());
        niño.setEdad(niñoActualizar.getEdad());
        niño.setGenero(niñoActualizar.getGenero());
        niño.setAlergias(niñoActualizar.getAlergias());
        niño.setAcudiente(niñoActualizar.getAcudiente());
        niño.setNumeroAcudiente(niñoActualizar.getNumeroAcudiente());
        break;
      }
    }
  }

  public void mostrarNiños() {
    if (listaNiños.isEmpty()) {
      mostrarMensaje("No hay niños matriculados");
    } else {
      mostrarMensaje("\nLista de niños matriculados:");
      for (Niño niño : listaNiños) {
        mostrarMensaje(niño.toString());
      }
    }
  }
  public ArrayList<Niño> obtenerNiñosMayoresA5() {
        ArrayList<Niño> mayores = new ArrayList<>();
        for (Niño niño : listaNiños) {
          int edadInt = Integer.parseInt(niño.getEdad());
          if (edadInt > 5) {
            mayores.add(niño);
          }
        }
        return mayores;
    }
    public void mostrarNiñosMayoresA5() {
        ArrayList<Niño> mayores = obtenerNiñosMayoresA5();
        if (mayores.isEmpty()) {
            mostrarMensaje("No hay niños mayores a 5 años");
        } else {
            mostrarMensaje("\nNiños mayores a 5 años: ");
            for (Niño niño : mayores) {
                mostrarMensaje(niño.toString());
            }
        }
    }
}
