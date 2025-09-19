package co.edu.uniquindio.poo;

public class Gato extends Animal {
	private String numeroBigotes;

	public Gato(String nombre, int edad, String numeroBigotes) {
		super(nombre, edad);
		this.numeroBigotes = numeroBigotes;
	}

	public String getNumeroBigotes() {
		return numeroBigotes;
	}

	public void setNumeroBigotes(String numeroBigotes) {
		this.numeroBigotes = numeroBigotes;
	}

	@Override
	public void hacerSonido() {
		System.out.println(getNombre() + " Miau");
	}

	@Override
	public void mostrarInfo() {
		super.mostrarInfo();
		System.out.println("Numero de Bigotes: " + numeroBigotes);
	}
}
