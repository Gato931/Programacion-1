package co.edu.uniquindio.poo;

import java.util.ArrayList;

public class Zoologico {
  private ArrayList<Animal> animales;

  public Zoologico() {
    animales = new ArrayList<>();
  }

  public void agregarAnimal(Animal animal) {
    animales.add(animal);
    System.out.println("Animal registrado: " + animal.getNombre());
  }

  public void mostrarAnimales() {
    System.out.println("Lista de animales: ");
    for (Animal animal : animales) {
      animal.mostrarInfo();
    }
  }

  public Animal buscarAnimalPorNombre(String nombre) {
    for (Animal animal : animales) {
      if (animal.getNombre().equalsIgnoreCase(nombre)) {
        return animal;
      }
    }
    return null;
  }

  public void actualizarNombreAnimal(String nombreActual, String nuevoNombre) {
    for (Animal animal : animales) {
      if (animal.getNombre().equalsIgnoreCase(nombreActual)) {
        animal.setNombre(nuevoNombre);
        System.out.println("Nombre actualizado: " + nuevoNombre);
        return;
      }
    }
    System.out.println("Animal no encontrado: " + nombreActual);
  }

  public void eliminarAnimal(String nombre) {
    for (int i = 0; i < animales.size(); i++) {
      Animal animal = animales.get(i);
      if (animal.getNombre().equalsIgnoreCase(nombre)) {
        animales.remove(i);
        System.out.println("Animal eliminado: " + nombre);
        return;
      }
    }
    System.out.println("Animal no encontrado: " + nombre);
  }

  public void hacerSonidos() {
    System.out.println("Sonidos de los animales: ");
    for (Animal animal : animales) {
      animal.hacerSonido();
    }
  }
}
