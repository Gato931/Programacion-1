package co.edu.uniquindio.poo;

import java.util.Scanner;

public class Moto extends Vehiculo {
	private int cilindraje;
	private int velocidadMaxima;
	private int cambios;

	public Moto(String marca, String modelo, boolean esNuevo, String combustible, String transmision, int cilindraje,
			int velocidadMaxima, int cambios) {
		super(marca, modelo, esNuevo, combustible, transmision);
		this.cilindraje = cilindraje;
		this.velocidadMaxima = velocidadMaxima;
		this.cambios = cambios;
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}

	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}

	public void setVelocidadMaxima(int velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}

	public int getCambios() {
		return cambios;
	}

	public void setCambios(int cambios) {
		this.cambios = cambios;
	}


	@Override
	public String toString() {
		return "Moto [marca=" + marca + ", modelo=" + modelo + ", cilindraje=" + cilindraje + ", esNuevo=" + esNuevo
				+ ", velocidadMaxima=" + velocidadMaxima + ", combustible=" + combustible + ", cambios=" + cambios
				+ ", transmision=" + transmision + "]";
	}

	public static Vehiculo mensajeMoto(Scanner scanner, Empleado empleado) {
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

		App.mostrarMensaje("Ingrese el cilindraje: ");
		int cilindraje = scanner.nextInt();

		App.mostrarMensaje("Ingrese la velocidad máxima: ");
		int velocidadMaxima = scanner.nextInt();

		App.mostrarMensaje("Número de cambios: ");
		int cambios = scanner.nextInt();
		scanner.nextLine();

		Vehiculo nuevaMoto = new Moto(marca, modelo, esNuevo, combustible, transmision, cilindraje, velocidadMaxima,
				cambios);
		empleado.registrarVehiculo(nuevaMoto);
		App.mostrarMensaje("Vehículo moto registrado exitosamente.");
		return nuevaMoto;
	}
}