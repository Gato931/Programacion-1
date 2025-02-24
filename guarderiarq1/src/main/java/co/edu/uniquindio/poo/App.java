package co.edu.uniquindio.poo;

import java.util.Scanner;
import java.util.ArrayList;

public class App {
	public static void main(String[] args) {
		ArrayList<Niño> listaNiños = new ArrayList<>();

		Niño niñoPrueba1 = new Niño("Jose", "6", "Hombre", "123", "Ninguna", "Angela", "333");
		Niño niñoPrueba2 = new Niño("Maria", "5", "Mujer", "321", "Leche", "Daniel", "222");
		Niño niñoPrueba3 = new Niño("Julian", "6", "Hombre", "213", "Polvo", "Liliana", "111");

		Scanner scanner = new Scanner(System.in);

		mostrarMensaje("Ingrese los datos del niño: ");

		mostrarMensaje("Nombre: ");
		String nombre = scanner.nextLine();

		mostrarMensaje("Edad: ");
		String edad = scanner.nextLine();

		mostrarMensaje("Genero: ");
		String genero = scanner.nextLine();

		mostrarMensaje("Documento: ");
		String documento = scanner.nextLine();

		mostrarMensaje("Alergias: ");
		String alergias = scanner.nextLine();

		mostrarMensaje("Nombre del acudiente: ");
		String acudiente = scanner.nextLine();

		mostrarMensaje("Numero del Acudiente: ");
		String numeroAcudiente = scanner.nextLine();

		Niño niño = new Niño(nombre, edad, genero, documento, alergias, acudiente, numeroAcudiente);

		listaNiños.add(niñoPrueba1);
		listaNiños.add(niñoPrueba2);
		listaNiños.add(niñoPrueba3);
		listaNiños.add(niño);

		mostrarMensaje("\nDatos registrados del niño:");
		mostrarMensaje(niño.toString());
		mostrarMensaje("\nLa lista completa de los niños actuales es: ");
		mostrarMensaje(listaNiños.toString());
		scanner.close();
	}

	public static void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}
}
