package co.edu.uniquindio.poo;

import java.util.ArrayList;

public class Veterinaria {
  private String nombre;
  private String ubicacion;
  private String nit;
  private String telefono;
  private ArrayList<Mascota> listaMascotas;
  private ArrayList<Cita> listaCitas;
  private ArrayList<Propietario> listaPropietarios;
  private ArrayList<Veterinario> listaVeterinarios;

  public Veterinaria(String nombre, String ubicacion, String nit, String telefono) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
    this.nit = nit;
    this.telefono = telefono;
    this.listaMascotas = new ArrayList<>();
    this.listaCitas = new ArrayList<>();
    this.listaPropietarios = new ArrayList<>();
    this.listaVeterinarios = new ArrayList<>();
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

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public ArrayList<Mascota> getListaMascotas() {
    return listaMascotas;
  }

  public void setListaMascotas(ArrayList<Mascota> listaMascotas) {
    this.listaMascotas = listaMascotas;
  }

  public ArrayList<Cita> getListaCitas() {
    return listaCitas;
  }

  public void setListaCitas(ArrayList<Cita> listaCitas) {
    this.listaCitas = listaCitas;
  }

  public ArrayList<Propietario> getListaPropietarios() {
    return listaPropietarios;
  }

  public void setListaPropietarios(ArrayList<Propietario> listaPropietarios) {
    this.listaPropietarios = listaPropietarios;
  }

  public ArrayList<Veterinario> getListaVeterinarios() {
    return listaVeterinarios;
  }

  public void setListaVeterinarios(ArrayList<Veterinario> listaVeterinarios) {
    this.listaVeterinarios = listaVeterinarios;
  }

  /**
   * Este metodo permite buscar una mascota de una lista
   * 
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
   * 
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
   * 
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
   * Este metodo permite actualizar la informacion existente de una mascota de una
   * lista
   * 
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

  @Override
  public String toString() {
    return "Veterinaria [nombre=" + nombre + ", ubicacion=" + ubicacion + ", nit=" + nit + ", telefono=" + telefono
        + ", listaMascotas=" + listaMascotas + ", listaCitas=" + listaCitas + ", listaPropietarios=" + listaPropietarios
        + ", listaVeterinarios=" + listaVeterinarios + "]";
  }

}
