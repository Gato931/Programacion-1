package co.edu.uniquindio.poo;

import java.util.ArrayList;

public class Veterinaria {
  private String nombre;
  private String ubicacion;
  private String nit;
  private ArrayList<Mascota> listaMascotas;

  public Veterinaria(String nombre, String ubicacion, String nit) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
    this.nit = nit;
    this.listaMascotas = new ArrayList<>();
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public String getNit() {
    return nit;
  }

  public void setNit(String nit) {
    this.nit = nit;
  }

  public ArrayList<Mascota> getListaMascotas() {
    return listaMascotas;
  }

  public void setListaMascotas(ArrayList<Mascota> listaMascotas) {
    this.listaMascotas = listaMascotas;
  }

  /**
   * Este metodo permite buscar una mascota de una lista
   * @param id
   * @return
   */
  public Mascota buscarMascota(String id) {
    Mascota mascotaEncontrada = null;
    for (Mascota mascota : listaMascotas) {
      if (mascota.getId().equals(id)) {
        return mascota;
      }
    }
    return mascotaEncontrada;
  }

  /**
   * Este metodo permite agregar una mascota de una lista
   * @param nuevaMascota
   * @return
   */
  public String agregarMascota(Mascota nuevaMascota) {
    String mensaje = "";
    Mascota mascotaEncontrada = null;
    mascotaEncontrada = buscarMascota(nuevaMascota.getId());
    if (mascotaEncontrada == null) {
      listaMascotas.add(nuevaMascota);
      mensaje = "Mascota añadida correctamente a la base de datos";
    }
    return mensaje;
  }

  /**
   * Este metodo permite eliminar una mascota de una lista
   * @param id
   * @return
   */
  public String eliminarMascota(String id) {
    String mensaje = "";
    Mascota mascotaEncontrada = null;
    mascotaEncontrada = buscarMascota(id);
    if (mascotaEncontrada != null) {
      listaMascotas.remove(mascotaEncontrada);
      mensaje = "Mascota eliminada correctamente de la base de datos";
    }
    return mensaje;
  }

  /**
   * Este metodo permite actualizar la informacion existente de una mascota de una lista
   * @param numeroIdentificacion
   * @param nuevaInformacion
   * @return
   */
  public String actualizarMascota(String id, Mascota nuevaInformacion, String enfermedad) {
    String mensaje = "";
    Mascota mascotaEncontrada = buscarMascota(id);
    if (mascotaEncontrada != null) {
      mascotaEncontrada.setNombre(nuevaInformacion.getNombre());
      mascotaEncontrada.setEdad(nuevaInformacion.getEdad());
      mascotaEncontrada.setPeso(nuevaInformacion.getPeso());
      mascotaEncontrada.agregarEnfermedad(enfermedad);
      mensaje = "La informacion de la mascota fue actualizada correctamente en la base de datos";
    }
      return mensaje;
  }

  /**
   * Este metodo permite mostrar una lista de mascotas
   */
  public String listarMascotas() {
    String mensaje = "";
    if (listaMascotas.isEmpty()) {
      mensaje = "No hay mascotas registradas en la base de datos";
      return mensaje;
    }
    mensaje = " Lista de mascotas registradas:\n\n";
    for (Mascota mascota : listaMascotas) {
      mensaje += mascota.toString() + "\n";
    }
    return mensaje;
  }

}
