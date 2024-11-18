package co.edu.uniquindio.poo;

import java.util.Scanner;

public class Bus extends Vehiculo {
	private int numeroPasajeros;
	private int numeroPuertas;
	private boolean tieneAireAcondicionado;
	private boolean tieneCamaraReversa;
	private int numeroBolsasAire;
	private boolean tieneABS;
	private int numeroEjes;
	private int numeorSalidasEmergencia;

	public Bus(String marca, String modelo, boolean esNuevo, String combustible, String transmision,
			int numeroPasajeros, int numeroPuertas, boolean tieneAireAcondicionado, boolean tieneCamaraReversa,
			int numeroBolsasAire, boolean tieneABS, int numeroEjes, int numeorSalidasEmergencia) {
		super(marca, modelo, esNuevo, combustible, transmision);
		this.numeroPasajeros = numeroPasajeros;
		this.numeroPuertas = numeroPuertas;
		this.tieneAireAcondicionado = tieneAireAcondicionado;
		this.tieneCamaraReversa = tieneCamaraReversa;
		this.numeroBolsasAire = numeroBolsasAire;
		this.tieneABS = tieneABS;
		this.numeroEjes = numeroEjes;
		this.numeorSalidasEmergencia = numeorSalidasEmergencia;
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

	public int getNumeroEjes() {
		return numeroEjes;
	}

	public void setNumeroEjes(int numeroEjes) {
		this.numeroEjes = numeroEjes;
	}

	public int getNumeorSalidasEmergencia() {
		return numeorSalidasEmergencia;
	}

	public void setNumeorSalidasEmergencia(int numeorSalidasEmergencia) {
		this.numeorSalidasEmergencia = numeorSalidasEmergencia;
	}

	@Override
	public String toString() {
		return "Bus [marca=" + marca + ", modelo=" + modelo + ", numeroPasajeros=" + numeroPasajeros + ", esNuevo="
				+ esNuevo + ", numeroPuertas=" + numeroPuertas + ", combustible=" + combustible + ", transmision=" + transmision
				+ ", tieneAireAcondicionado=" + tieneAireAcondicionado + ", tieneCamaraReversa=" + tieneCamaraReversa
				+ ", numeroBolsasAire=" + numeroBolsasAire + ", tieneABS=" + tieneABS + ", numeroEjes=" + numeroEjes
				+ ", numeorSalidasEmergencia=" + numeorSalidasEmergencia + "]";
	}

	public static Vehiculo mensajeBus(Scanner scanner, Empleado empleado) {
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

		App.mostrarMensaje("Número de ejes: ");
		int numeroEjes = scanner.nextInt();

		App.mostrarMensaje("Número de salidas de emergencia: ");
		int numeorSalidasEmergencia = scanner.nextInt();

		Vehiculo nuevoBus = new Bus(marca, modelo, esNuevo, combustible, transmision, numeroPasajeros, numeroPuertas,
				tieneAireAcondicionado, tieneCamaraReversa, numeroBolsasAire, tieneABS, numeroEjes, numeorSalidasEmergencia);

		empleado.registrarVehiculo(nuevoBus);

		App.mostrarMensaje("Vehículo Bus registrado exitosamente.");
		return nuevoBus;
	}

}
