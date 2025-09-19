package co.edu.uniquindio.poo;

public class App {
	public static void main(String[] args) {
		Zoologico zoologico = new Zoologico();

		Animal perro = new Perro("Lucas", 3, "Labrador");
		Animal gato = new Gato("Kiwi", 2, "6");
		Animal leon = new Leon("Mufasa", 5, 190.5);
		Animal elefante = new Elefante("Dumbo", 7, 2.3);
		Animal loro = new Loro("Rafiqui", 1, "Quiero cacao");

		zoologico.agregarAnimal(perro);
		zoologico.agregarAnimal(gato);
		zoologico.agregarAnimal(leon);
		zoologico.agregarAnimal(elefante);
		zoologico.agregarAnimal(loro);

		zoologico.mostrarAnimales();
		zoologico.hacerSonidos();
	}
}
