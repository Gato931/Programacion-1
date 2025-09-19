package co.edu.uniquindio.poo;

public class Loro extends Animal {
	private String fraseFavorita;

	public Loro(String nombre, int edad, String fraseFavorita) {
		super(nombre, edad);
		this.fraseFavorita = fraseFavorita;
	}

	public String getFraseFavorita() {
		return fraseFavorita;
	}

	public void setFraseFavorita(String fraseFavorita) {
		this.fraseFavorita = fraseFavorita;
	}

	@Override
	public void hacerSonido() {
		System.out.println(getNombre() +     fraseFavorita);
	}

	@Override
	public void mostrarInfo() {
		super.mostrarInfo();
		System.out.println("Frase favorita: " + fraseFavorita);
	}
}
