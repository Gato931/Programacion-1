package co.edu.uniquindio.poo;

import java.util.Scanner;

public class Camioneta extends Vehiculo {
	private int numeroPasajeros;
	private int numeroPuertas;
	private boolean tieneAireAcondicionado;
	private boolean tieneCamaraReversa;
	private boolean tieneVelocidadCrucero;
	private int numeroBolsasAire;
	private boolean tieneABS;
	private boolean es4x4;

	public Camioneta(String marca, String modelo, boolean esNuevo, String combustible, String transmision,
			int numeroPasajeros, int numeroPuertas, boolean tieneAireAcondicionado, boolean tieneCamaraReversa,
			boolean tieneVelocidadCrucero, int numeroBolsasAire, boolean tieneABS, boolean es4x4) {
		super(marca, modelo, esNuevo, combustible, transmision);
		this.numeroPasajeros = numeroPasajeros;
		this.numeroPuertas = numeroPuertas;
		this.tieneAireAcondicionado = tieneAireAcondicionado;
		this.tieneCamaraReversa = tieneCamaraReversa;
		this.tieneVelocidadCrucero = tieneVelocidadCrucero;
		this.numeroBolsasAire = numeroBolsasAire;
		this.tieneABS = tieneABS;
		this.es4x4 = es4x4;
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

	public boolean isEs4x4() {
		return es4x4;
	}

	public void setEs4x4(boolean es4x4) {
		this.es4x4 = es4x4;
	}

  @Override
	public String toString() {
		return "Camioneta [marca=" + marca + ", modelo=" + modelo + ", numeroPasajeros=" + numeroPasajeros + ", esNuevo="
				+ esNuevo + ", combustible=" + combustible + ", numeroPuertas=" + numeroPuertas + ", transmision=" + transmision
				+ ", tieneAireAcondicionado=" + tieneAireAcondicionado + ", tieneCamaraReversa=" + tieneCamaraReversa
				+ ", tieneVelocidadCrucero=" + tieneVelocidadCrucero + ", numeroBolsasAire=" + numeroBolsasAire + ", tieneABS="
				+ tieneABS + ", es4x4=" + es4x4 + "]";
	}

	public static Vehiculo mensajeCamioneta(Scanner scanner, Empleado empleado) {
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

		App.mostrarMensaje("Ingrese el numero de pasajeros: ");
		int numeroPasajeros = scanner.nextInt();

		App.mostrarMensaje("Numero de puertas");
		int numeroPuertas = scanner.nextInt();

		App.mostrarMensaje("¿Tiene aire acondicionado? (true/false): ");
		boolean tieneAireAcondicionado = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("¿Tiene camara reversa? (true/false): ");
		boolean tieneCamaraReversa = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("¿Tiene velocidad de crucero? (true/false): ");
		boolean tieneVelocidadCrucero = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("Bolsas de aire: ");
		int numeroBolsasAire = scanner.nextInt();
		scanner.nextLine();

		App.mostrarMensaje("¿Tiene ABS? (true/false):");
		boolean tieneABS = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("¿Es 4x4? (true/false):");
		boolean es4x4 = scanner.nextBoolean();
		scanner.nextLine();

		Vehiculo nuevaCamioneta = new Camioneta(marca, modelo, esNuevo, combustible, transmision, numeroPasajeros, numeroPuertas, tieneAireAcondicionado, tieneCamaraReversa, tieneVelocidadCrucero, numeroBolsasAire, tieneABS, es4x4);
		empleado.registrarVehiculo(nuevaCamioneta);
		App.mostrarMensaje("Vehículo registrado exitosamente.");
		return nuevaCamioneta;
  }

}
