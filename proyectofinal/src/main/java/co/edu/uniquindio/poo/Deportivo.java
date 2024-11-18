package co.edu.uniquindio.poo;

import java.util.Scanner;

public class Deportivo extends Vehiculo {
	private int numeroPasajeros;
	private int numeroPuertas;
	private int numeroBolsasAire;
	private int caballosFuerza;
	private int tiempoCienKm;

	public Deportivo(String marca, String modelo, boolean esNuevo, String combustible, String transmision,
			int numeroPasajeros, int numeroPuertas, int numeroBolsasAire, int caballosFuerza, int tiempoCienKm) {
		super(marca, modelo, esNuevo, combustible, transmision);
		this.numeroPasajeros = numeroPasajeros;
		this.numeroPuertas = numeroPuertas;
		this.numeroBolsasAire = numeroBolsasAire;
		this.caballosFuerza = caballosFuerza;
		this.tiempoCienKm = tiempoCienKm;
	}

	public int getNumeroPasajeros() {
		return numeroPasajeros;
	}

	public void setNumeroPasajeros(int numeroPasajeros) {
		this.numeroPasajeros = numeroPasajeros;
	}

	public int getNumeroPuertas() {
		return numeroPuertas;
	}

	public void setNumeroPuertas(int numeroPuertas) {
		this.numeroPuertas = numeroPuertas;
	}

	public int getNumeroBolsasAire() {
		return numeroBolsasAire;
	}

	public void setNumeroBolsasAire(int numeroBolsasAire) {
		this.numeroBolsasAire = numeroBolsasAire;
	}

	public int getCaballosFuerza() {
		return caballosFuerza;
	}

	public void setCaballosFuerza(int caballosFuerza) {
		this.caballosFuerza = caballosFuerza;
	}

	public int getTiempoCienKm() {
		return tiempoCienKm;
	}

	public void setTiempoCienKm(int tiempoCienKm) {
		this.tiempoCienKm = tiempoCienKm;
	}

	@Override
	public String toString() {
		return "Deportivo [marca=" + marca + ", modelo=" + modelo + ", numeroPasajeros=" + numeroPasajeros + ", esNuevo="
				+ esNuevo + ", combustible=" + combustible + ", numeroPuertas=" + numeroPuertas + ", transmision=" + transmision
				+ ", numeroBolsasAire=" + numeroBolsasAire + ", caballosFuerza=" + caballosFuerza + ", tiempoCienKm="
				+ tiempoCienKm + "]";
	}

	public static Vehiculo mensajeDeportivo(Scanner scanner, Empleado empleado) {
		App.mostrarMensaje("Marca del vehículo: ");
		String marca = scanner.nextLine();

		App.mostrarMensaje("Modelo del vehículo: ");
		String modelo = scanner.nextLine();

		App.mostrarMensaje("¿Es nuevo? (true/false): ");
		boolean esNuevo = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("Tipo de combustible: ");
		String combustible = scanner.nextLine();

		App.mostrarMensaje("Tipo de transmisión: ");
		String transmision = scanner.nextLine();

		App.mostrarMensaje("Ingrese el número de pasajeros: ");
		int numeroPasajeros = scanner.nextInt();

		App.mostrarMensaje("Número de puertas: ");
		int numeroPuertas = scanner.nextInt();

		App.mostrarMensaje("Bolsas de aire: ");
		int numeroBolsasAire = scanner.nextInt();

		App.mostrarMensaje("Caballos de fuerza: ");
		int caballosFuerza = scanner.nextInt();

		App.mostrarMensaje("Tiempo de 0 a 100 km/h (en segundos): ");
		int tiempoCienKm = scanner.nextInt();
		scanner.nextLine();

		Vehiculo nuevoDeportivo = new Deportivo(marca, modelo, esNuevo, combustible, transmision, numeroPasajeros,
		numeroPuertas, numeroBolsasAire, caballosFuerza, tiempoCienKm);
		empleado.registrarVehiculo(nuevoDeportivo);
		App.mostrarMensaje("Vehículo deportivo registrado exitosamente.");
		return nuevoDeportivo;
	}

}
