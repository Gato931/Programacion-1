package co.edu.uniquindio.poo;
import java.util.Scanner;

public class Administrador extends Empleado {
	private String preguntaSeguridad;
	private String respuestaSeguridad;

	public Administrador(String nombre, String apellido, String dni, String email, String telefono, String usuario, String contraseña, String preguntaSeguridad, String respuestaSeguridad) {
		super(nombre, apellido, dni, email, telefono, usuario, contraseña);
		this.preguntaSeguridad = preguntaSeguridad;
		this.respuestaSeguridad = respuestaSeguridad;
	}

	public String getPreguntaSeguridad() {
		return preguntaSeguridad;
	}

	public void setPreguntaSeguridad(String preguntaSeguridad) {
		this.preguntaSeguridad = preguntaSeguridad;
	}

	public String getRespuestaSeguridad() {
		return respuestaSeguridad;
	}

	public void setRespuestaSeguridad(String respuestaSeguridad) {
		this.respuestaSeguridad = respuestaSeguridad;
	}

	// Metodo para recuperar o restablecer la contraseña
	public boolean recuperarContraseña(Scanner scanner) {
		App.mostrarMensaje("Para recuperar tu contraseña, responde a la pregunta de seguridad:");
		App.mostrarMensaje(preguntaSeguridad);
		App.mostrarMensaje("Tu respuesta: ");
		String respuestaUsuario = scanner.nextLine();

		if (respuestaUsuario.equalsIgnoreCase(respuestaSeguridad)) {
			App.mostrarMensaje("Respuesta correcta, tu contraseña actual es: " + getContraseña());
			App.mostrarMensaje("¿Quieres establecer una nueva contraseña? (si/no): ");
			String opcion = scanner.nextLine();

			if (opcion.equalsIgnoreCase("si")) {
				App.mostrarMensaje("Ingresa tu nueva contraseña: ");
				String nuevaContraseña = scanner.nextLine();
				setContraseña(nuevaContraseña);
				App.mostrarMensaje("Contraseña actualizada exitosamente");
			}
			return true;
		} else {
			App.mostrarMensaje("Respuesta incorrecta, no se pudo recuperar la contraseña");
			return false;
		}
	}

	public void generarReporte() {
		App.mostrarMensaje("Generando reporte de negocios...");
	}
}
