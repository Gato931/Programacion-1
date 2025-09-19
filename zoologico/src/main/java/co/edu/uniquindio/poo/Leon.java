package co.edu.uniquindio.poo;

public class Leon extends Animal {
	private double peso;

	public Leon(String nombre, int edad, double peso) {
		super(nombre, edad);
		this.peso = peso;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public void hacerSonido() {
		System.out.println(getNombre() + " Rooooar");
	}

	@Override
	public void mostrarInfo() {
		super.mostrarInfo();
		System.out.println("Peso: " + peso);
	}
}
