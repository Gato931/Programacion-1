package co.edu.uniquindio.poo;

import java.util.Scanner;

public class Van extends Vehiculo {
	private int numeroPasajeros;
	private int numeroPuertas;
	private boolean tieneAireAcondicionado;
	private boolean tieneCamaraReversa;
	private int numeroBolsasAire;
	private boolean tieneABS;

	public Van(String marca, String modelo, boolean esNuevo, String combustible, String transmision,
			int numeroPasajeros, int numeroPuertas, boolean tieneAireAcondicionado, boolean tieneCamaraReversa,
			int numeroBolsasAire, boolean tieneABS) {
		super(marca, modelo, esNuevo, combustible, transmision);
		this.numeroPasajeros = numeroPasajeros;
		this.numeroPuertas = numeroPuertas;
		this.tieneAireAcondicionado = tieneAireAcondicionado;
		this.tieneCamaraReversa = tieneCamaraReversa;
		this.numeroBolsasAire = numeroBolsasAire;
		this.tieneABS = tieneABS;
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

	public boolean isTieneAireAcondicionado() {
		return tieneAireAcondicionado;
	}

	public void setTieneAireAcondicionado(boolean tieneAireAcondicionado) {
		this.tieneAireAcondicionado = tieneAireAcondicionado;
	}

	public boolean isTieneCamaraReversa() {
		return tieneCamaraReversa;
	}

	public void setTieneCamaraReversa(boolean tieneCamaraReversa) {
		this.tieneCamaraReversa = tieneCamaraReversa;
	}

	public int getNumeroBolsasAire() {
		return numeroBolsasAire;
	}

	public void setNumeroBolsasAire(int numeroBolsasAire) {
		this.numeroBolsasAire = numeroBolsasAire;
	}

	public boolean isTieneABS() {
		return tieneABS;
	}

	public void setTieneABS(boolean tieneABS) {
		this.tieneABS = tieneABS;
	}

	@Override
	public String toString() {
		return "Van [marca=" + marca + ", modelo=" + modelo + ", numeroPasajeros=" + numeroPasajeros + ", esNuevo="
				+ esNuevo + ", numeroPuertas=" + numeroPuertas + ", combustible=" + combustible + ", transmision=" + transmision
				+ ", tieneAireAcondicionado=" + tieneAireAcondicionado + ", tieneCamaraReversa=" + tieneCamaraReversa
				+ ", numeroBolsasAire=" + numeroBolsasAire + ", tieneABS=" + tieneABS + "]";
	}

	public static Vehiculo mensajeVan(Scanner scanner, Empleado empleado) {
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

		App.mostrarMensaje("¿Tiene aire acondicionado? (true/false): ");
		boolean tieneAireAcondicionado = scanner.nextBoolean();

		App.mostrarMensaje("¿Tiene cámara reversa? (true/false): ");
		boolean tieneCamaraReversa = scanner.nextBoolean();

		App.mostrarMensaje("Número de bolsas de aire: ");
		int numeroBolsasAire = scanner.nextInt();

		App.mostrarMensaje("¿Tiene ABS? (true/false): ");
		boolean tieneABS = scanner.nextBoolean();

		Vehiculo nuevaVan = new Van(marca, modelo, esNuevo, combustible, transmision, numeroPasajeros, numeroPuertas,
				tieneAireAcondicionado, tieneCamaraReversa, numeroBolsasAire, tieneABS);

		empleado.registrarVehiculo(nuevaVan);

		App.mostrarMensaje("Vehículo Van registrado exitosamente.");
		return nuevaVan;
	}

}
