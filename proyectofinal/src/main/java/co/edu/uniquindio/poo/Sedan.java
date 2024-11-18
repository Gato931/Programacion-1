package co.edu.uniquindio.poo;

import java.util.Scanner;

public class Sedan extends Vehiculo {
	private int numeroPasajeros;
	private int numeroPuertas;
	private boolean tieneAireAcondicionado;
	private boolean tieneCamaraReversa;
	private boolean tieneVelocidadCrucero;
	private int numeroBolsasAire;
	private boolean tieneABS;
	private boolean tieneSensoresColision;
	private boolean tieneSensorTráfico;
	private boolean tieneAsistenteCarril;

	public Sedan(String marca, String modelo, boolean esNuevo, String combustible, String transmision,
			int numeroPasajeros, int numeroPuertas,
			boolean tieneAireAcondicionado, boolean tieneCamaraReversa, boolean tieneVelocidadCrucero, int numeroBolsasAire,
			boolean tieneABS,
			boolean tieneSensoresColision, boolean tieneSensorTráfico, boolean tieneAsistenteCarril) {
		super(marca, modelo, esNuevo, combustible, transmision);
		this.numeroPasajeros = numeroPasajeros;
		this.numeroPuertas = numeroPuertas;
		this.tieneAireAcondicionado = tieneAireAcondicionado;
		this.tieneCamaraReversa = tieneCamaraReversa;
		this.tieneVelocidadCrucero = tieneVelocidadCrucero;
		this.numeroBolsasAire = numeroBolsasAire;
		this.tieneABS = tieneABS;
		this.tieneSensoresColision = tieneSensoresColision;
		this.tieneSensorTráfico = tieneSensorTráfico;
		this.tieneAsistenteCarril = tieneAsistenteCarril;
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

	public boolean isTieneVelocidadCrucero() {
		return tieneVelocidadCrucero;
	}

	public void setTieneVelocidadCrucero(boolean tieneVelocidadCrucero) {
		this.tieneVelocidadCrucero = tieneVelocidadCrucero;
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

	public boolean isTieneSensoresColision() {
		return tieneSensoresColision;
	}

	public void setTieneSensoresColision(boolean tieneSensoresColision) {
		this.tieneSensoresColision = tieneSensoresColision;
	}

	public boolean isTieneSensorTráfico() {
		return tieneSensorTráfico;
	}

	public void setTieneSensorTráfico(boolean tieneSensorTráfico) {
		this.tieneSensorTráfico = tieneSensorTráfico;
	}

	public boolean isTieneAsistenteCarril() {
		return tieneAsistenteCarril;
	}

	public void setTieneAsistenteCarril(boolean tieneAsistenteCarril) {
		this.tieneAsistenteCarril = tieneAsistenteCarril;
	}

	@Override
	public String toString() {
		return "Sedan [marca=" + marca + ", modelo=" + modelo + ", numeroPasajeros=" + numeroPasajeros + ", esNuevo="
				+ esNuevo + ", numeroPuertas=" + numeroPuertas + ", combustible=" + combustible + ", transmision=" + transmision
				+ ", tieneAireAcondicionado=" + tieneAireAcondicionado + ", tieneCamaraReversa=" + tieneCamaraReversa
				+ ", tieneVelocidadCrucero=" + tieneVelocidadCrucero + ", numeroBolsasAire=" + numeroBolsasAire + ", tieneABS="
				+ tieneABS + ", tieneSensoresColision=" + tieneSensoresColision + ", tieneSensorTráfico=" + tieneSensorTráfico
				+ ", tieneAsistenteCarril=" + tieneAsistenteCarril + "]";
	}

	public static Vehiculo mensajeSedan(Scanner scanner, Empleado empleado) {
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

		App.mostrarMensaje("¿Tiene velocidad de crucero? (true/false): ");
		boolean tieneVelocidadCrucero = scanner.nextBoolean();

		App.mostrarMensaje("Número de bolsas de aire: ");
		int numeroBolsasAire = scanner.nextInt();

		App.mostrarMensaje("¿Tiene ABS? (true/false): ");
		boolean tieneABS = scanner.nextBoolean();

		App.mostrarMensaje("¿Tiene sensores de colisión? (true/false): ");
		boolean tieneSensoresColision = scanner.nextBoolean();

		App.mostrarMensaje("¿Tiene sensor de tráfico? (true/false): ");
		boolean tieneSensorTráfico = scanner.nextBoolean();

		App.mostrarMensaje("¿Tiene asistente de carril? (true/false): ");
		boolean tieneAsistenteCarril = scanner.nextBoolean();

		Vehiculo nuevoSedan = new Sedan(marca, modelo, esNuevo, combustible, transmision, numeroPasajeros,
				numeroPuertas, tieneAireAcondicionado, tieneCamaraReversa, tieneVelocidadCrucero, numeroBolsasAire,
				tieneABS, tieneSensoresColision, tieneSensorTráfico, tieneAsistenteCarril);
		empleado.registrarVehiculo(nuevoSedan);
		App.mostrarMensaje("Vehículo Sedan registrado exitosamente.");
		return nuevoSedan;
	}

}