package co.edu.uniquindio.poo;
import java.util.Scanner;

public class Camion extends Vehiculo{
	private double capacidadCarga;
	private boolean tieneAireAcondicionado;
	private boolean tieneFrenosAire;
	private boolean tieneABS;
	private int numeroEjes;
	private String tipoCamion;

	public Camion(String marca, String modelo, boolean esNuevo, String combustible, String transmision,
			double capacidadCarga, boolean tieneAireAcondicionado, boolean tieneFrenosAire, boolean tieneABS, int numeroEjes,
			String tipoCamion) {
		super(marca, modelo, esNuevo, combustible, transmision);
		this.capacidadCarga = capacidadCarga;
		this.tieneAireAcondicionado = tieneAireAcondicionado;
		this.tieneFrenosAire = tieneFrenosAire;
		this.tieneABS = tieneABS;
		this.numeroEjes = numeroEjes;
		this.tipoCamion = tipoCamion;
	}

	public double getCapacidadCarga() {
		return capacidadCarga;
	}

	public void setCapacidadCarga(double capacidadCarga) {
		this.capacidadCarga = capacidadCarga;
	}

	public boolean isTieneAireAcondicionado() {
		return tieneAireAcondicionado;
	}

	public void setTieneAireAcondicionado(boolean tieneAireAcondicionado) {
		this.tieneAireAcondicionado = tieneAireAcondicionado;
	}

	public boolean isTieneFrenosAire() {
		return tieneFrenosAire;
	}

	public void setTieneFrenosAire(boolean tieneFrenosAire) {
		this.tieneFrenosAire = tieneFrenosAire;
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

	public String getTipoCamion() {
		return tipoCamion;
	}

	@Override
	public String toString() {
		return "Camion [marca=" + marca + ", modelo=" + modelo + ", capacidadCarga=" + capacidadCarga + ", esNuevo="
				+ esNuevo + ", combustible=" + combustible + ", tieneAireAcondicionado=" + tieneAireAcondicionado
				+ ", transmision=" + transmision + ", tieneFrenosAire=" + tieneFrenosAire + ", tieneABS=" + tieneABS
				+ ", numeroEjes=" + numeroEjes + ", tipoCamion=" + tipoCamion + "]";
	}

	public void setTipoCamion(String tipoCamion) {
		this.tipoCamion = tipoCamion;
	}
	public static Vehiculo mensajeCamion(Scanner scanner, Empleado empleado){
		App.mostrarMensaje("Marca del vehículo: ");
		String marca = scanner.nextLine();

		App.mostrarMensaje("Modelo del vehiculo: ");
		String modelo = scanner.nextLine();

		App.mostrarMensaje("¿Es nuevo? (true/false): ");
		boolean esNuevo = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("Tipo de combustible: ");
		String combustible = scanner.nextLine();

		App.mostrarMensaje("Tipo de transmisión: ");
		String transmision = scanner.nextLine();

		App.mostrarMensaje("Capacidad de carga: ");
		Double capacidadCarga = scanner.nextDouble();

		App.mostrarMensaje("¿Tiene aire acondicionado? (true/false): ");
		boolean tieneAireAcondicionado = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("¿Tiene frenos de aire? (true/false): ");
		boolean tieneFrenosAire = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("¿Tiene ABS? (true/false):");
		boolean tieneABS = scanner.nextBoolean();
		scanner.nextLine();

		App.mostrarMensaje("Numero de ejes: ");
		int numeroEjes = scanner.nextInt();

		App.mostrarMensaje("Tipo de camion: ");
		String tipoCamion = scanner.nextLine();

		Vehiculo nuevoCamion = new Camion(marca, modelo, esNuevo, combustible, transmision, capacidadCarga, tieneAireAcondicionado, tieneFrenosAire, tieneABS, numeroEjes, tipoCamion);
		empleado.registrarVehiculo(nuevoCamion);
		App.mostrarMensaje("Vehículo registrado exitosamente.");
		return nuevoCamion;
	}
}
