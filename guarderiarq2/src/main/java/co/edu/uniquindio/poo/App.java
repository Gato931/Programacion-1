package co.edu.uniquindio.poo;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Guarderia guarderia = new Guarderia("Pequeños Gigantes", "123456789");
		Niño niñoPrueba1 = new Niño("Miguel", "5", "Hombre", "123", "Polvo", "Liliana", "333");
		Niño niñoPrueba2 = new Niño("Monica", "6", "Mujer", "321", "Leche", "Raul", "222");
		guarderia.agregarNiño(niñoPrueba1);
		guarderia.agregarNiño(niñoPrueba2);
		boolean salir = false;

		while (!salir) {
			guarderia.mostrarMensaje("\nMenu");
			guarderia.mostrarMensaje("1. Agregar niño");
			guarderia.mostrarMensaje("2. Eliminar niño");
			guarderia.mostrarMensaje("3. Actualizar niño");
			guarderia.mostrarMensaje("4. Mostrar todos los niños");
			guarderia.mostrarMensaje("5. Mostrar niños mayores a 5 años");
			guarderia.mostrarMensaje("6. Salir");
			guarderia.mostrarMensaje("Ingrese la opcion deseada: ");

			String opcion = scanner.nextLine();

			if (opcion.equals("1")) {
				guarderia.mostrarMensaje("\nIngrese los datos del niño:");

				guarderia.mostrarMensaje("Nombre: ");
				String nombre = scanner.nextLine();

				guarderia.mostrarMensaje("Edad: ");
				String edad = scanner.nextLine();

				guarderia.mostrarMensaje("Genero: ");
				String genero = scanner.nextLine();

				guarderia.mostrarMensaje("Documento: ");
				String documento = scanner.nextLine();

				guarderia.mostrarMensaje("Alergias: ");
				String alergias = scanner.nextLine();

				guarderia.mostrarMensaje("Nombre del acudiente: ");
				String acudiente = scanner.nextLine();

				guarderia.mostrarMensaje("Numero del Acudiente: ");
				String numeroAcudiente = scanner.nextLine();

				Niño niño = new Niño(nombre, edad, genero, documento, alergias, acudiente, numeroAcudiente);
				guarderia.agregarNiño(niño);
			} else if (opcion.equals("2")) {
				guarderia.mostrarMensaje("\nIngrese el documento del niño a eliminar: ");
				String docEliminar = scanner.nextLine();
				Niño niñoEliminar = null;
				for (Niño niño : guarderia.getListaNiños()) {
					if (niño.getDocumento().equals(docEliminar)) {
						niñoEliminar = niño;
						break;
					}
				}
				if (niñoEliminar != null) {
					guarderia.eliminarNiño(niñoEliminar);
				} else {
					guarderia.mostrarMensaje("Niño no encontrado");
				}
			} else if (opcion.equals("3")) {
				guarderia.mostrarMensaje("\nIngrese el documento del niño a actualizar: ");
				String documentoActualizar = scanner.nextLine();
				Niño niñoActualizar = null;
				for (Niño niño : guarderia.getListaNiños()) {
					if (niño.getDocumento().equals(documentoActualizar)) {
						niñoActualizar = niño;
						break;
					}
				}
				if (niñoActualizar != null) {
					guarderia.mostrarMensaje("Ingrese los nuevos datos:");
					guarderia.mostrarMensaje("Nuevo nombre: ");
					String nuevoNombre = scanner.nextLine();

					guarderia.mostrarMensaje("Nueva edad: ");
					String nuevaEdad = scanner.nextLine();

					guarderia.mostrarMensaje("Nuevo genero: ");
					String nuevoGenero = scanner.nextLine();

					guarderia.mostrarMensaje("Nuevas alergias: ");
					String nuevasAlergias = scanner.nextLine();

					guarderia.mostrarMensaje("Nuevo nombre del acudiente: ");
					String nuevoAcudiente = scanner.nextLine();

					guarderia.mostrarMensaje("Nuevo numero del Acudiente: ");
					String nuevoNumeroAcudiente = scanner.nextLine();

					Niño ninoModificado = new Niño(nuevoNombre, nuevaEdad, nuevoGenero, documentoActualizar, nuevasAlergias,
							nuevoAcudiente, nuevoNumeroAcudiente);
					guarderia.actualizarNiño(ninoModificado);
				} else {
					guarderia.mostrarMensaje("Niño no encontrado");
				}
			} else if (opcion.equals("4")) {
				guarderia.mostrarNiños();
			} else if (opcion.equals("5")) {
				guarderia.mostrarNiñosMayoresA5();
			} else if (opcion.equals("6")) {
				guarderia.mostrarMensaje("Saliendo");
				salir = true;
			} else {
				guarderia.mostrarMensaje("Opcion no valida");
			}
		}

		scanner.close();
	}
}
