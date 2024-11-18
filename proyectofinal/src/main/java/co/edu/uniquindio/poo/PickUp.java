package co.edu.uniquindio.poo;

import java.util.Scanner;

public class PickUp extends Vehiculo {
	private int numeroPasajeros;
	private int numeroPuertas;
	private boolean tieneAireAcondicionado;
	private boolean tieneCamaraReversa;
	private int numeroBolsasAire;
	private boolean tieneABS;
	private boolean es4x4;
	private double capacidadMaletero;

	public PickUp(String marca, String modelo, boolean esNuevo, String combustible, String transmision,
			int numeroPasajeros, int numeroPuertas, boolean tieneAireAcondicionado, boolean tieneCamaraReversa,
			int numeroBolsasAire, boolean tieneABS, boolean es4x4, double capacidadMaletero) {
		super(marca, modelo, esNuevo, combustible, transmision);
		this.numeroPasajeros = numeroPasajeros;
		this.numeroPuertas = numeroPuertas;
		this.tieneAireAcondicionado = tieneAireAcondicionado;
		this.tieneCamaraReversa = tieneCamaraReversa;
		this.numeroBolsasAire = numeroBolsasAire;
		this.tieneABS = tieneABS;
		this.es4x4 = es4x4;
		this.capacidadMaletero = capacidadMaletero;
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

	public boolean isEs4x4() {
		return es4x4;
	}

	public void setEs4x4(boolean es4x4) {
		this.es4x4 = es4x4;
	}

	public double getCapacidadMaletero() {
		return capacidadMaletero;
	}

	public void setCapacidadMaletero(double capacidadMaletero) {
		this.capacidadMaletero = capacidadMaletero;
	}

	@Override
	public String toString() {
		return "PickUp [marca=" + marca + ", modelo=" + modelo + ", numeroPasajeros=" + numeroPasajeros + ", esNuevo="
				+ esNuevo + ", numeroPuertas=" + numeroPuertas + ", combustible=" + combustible + ", transmision=" + transmision
				+ ", tieneAireAcondicionado=" + tieneAireAcondicionado + ", tieneCamaraReversa=" + tieneCamaraReversa
				+ ", numeroBolsasAire=" + numeroBolsasAire + ", tieneABS=" + tieneABS + ", es4x4=" + es4x4
				+ ", capacidadMaletero=" + capacidadMaletero + "]";
	}

	public static Vehiculo mensajePickUp(Scanner scanner, Empleado empleado) {
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

		App.mostrarMensaje("¿Es 4x4? (true/false): ");
		boolean es4x4 = scanner.nextBoolean();

		App.mostrarMensaje("Capacidad del maletero (en litros): ");
		double capacidadMaletero = scanner.nextDouble();
		scanner.nextLine();

		Vehiculo nuevaPickUp = new PickUp(marca, modelo, esNuevo, combustible, transmision, numeroPasajeros,
				numeroPuertas, tieneAireAcondicionado, tieneCamaraReversa, numeroBolsasAire, tieneABS, es4x4,
				capacidadMaletero);
		empleado.registrarVehiculo(nuevaPickUp);
		App.mostrarMensaje("Vehículo PickUp registrado exitosamente.");
		return nuevaPickUp;
	}

}
