package co.edu.uniquindio.poo;

public class Elefante extends Animal {
	private double tamanoColmillos;

	public Elefante(String nombre, int edad, double tamanoColmillos) {
		super(nombre, edad);
		this.tamanoColmillos = tamanoColmillos;
	}

	public double getTamanoColmillos() {
		return tamanoColmillos;
	}

	public void setTamanoColmillos(double tamanoColmillos) {
		this.tamanoColmillos = tamanoColmillos;
	}

	@Override
	public void hacerSonido() {
		System.out.println(getNombre() + " Pawooooo");
	}

	@Override
	public void mostrarInfo() {
		super.mostrarInfo();
		System.out.println("Tamaño de colmillos: " + tamanoColmillos);
	}
}
