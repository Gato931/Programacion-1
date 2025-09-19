package co.edu.uniquindio.poo;

public class Perro extends Animal {
  private String raza;

  public Perro(String nombre, int edad, String raza) {
    super(nombre, edad);
    this.raza = raza;
  }

  public String getRaza() {
    return raza;
  }

  public void setRaza(String raza) {
    this.raza = raza;
  }

  @Override
  public void hacerSonido() {
    System.out.println(getNombre() + " Guau");
  }

  @Override
  public void mostrarInfo() {
    super.mostrarInfo();
    System.out.println("Raza: " + raza);
  }
}
