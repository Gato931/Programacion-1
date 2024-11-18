package co.edu.uniquindio.poo;

import java.util.LinkedList;

import java.util.Scanner;

//import co.edu.uniquindio.poo.exceptions.datosIncorrectosExeption;

public class App {

	// Listas para almacenar empleados, clientes y vehículos
	private static LinkedList<Empleado> empleados = new LinkedList<>();
	private static LinkedList<Cliente> clientes = new LinkedList<>();
	private static LinkedList<Vehiculo> vehiculosDisponibles = new LinkedList<>();

	public static void main(String[] args) {
		// Crear un administrador inicial
		Administrador admin = new Administrador("Joseph", "Root", "123456789", "joseph@ucarrouq.com", "admin123", "joseph",
				"admin123", "Cuantos hijos tengo", "3");
		empleados.add(admin);

		// Crear empleados de ejemplo
		Empleado empleado1 = new Empleado("Juan", "Perez", "12345678", "juan.perez@ucarrouq.com", "123456", "juan123",
				"123");
		empleados.add(empleado1);
		// Crear clientes de ejemplo
		Cliente cliente1 = new Cliente("Nikol", "Morales", "101010", "nm@gmail.com", "123456", "torresdeorense");
		clientes.add(cliente1);

		// Crear vehiculos de ejemplo
		Vehiculo moto = new Moto("Yamaha", "YZF-R3", true, "Gasolina", "Manual", 321, 180, 6);
		Vehiculo sedan = new Sedan("Toyota", "Corolla", true, "Gasolina", "Automático", 5, 4, true, true, false, 6, true,
				true, true, true);
		vehiculosDisponibles.add(moto);
		vehiculosDisponibles.add(sedan);

		Scanner scanner = new Scanner(System.in);
		boolean salir = false;

		for (;;) {
			if (salir) {
				break;
			}

			mostrarMensaje("\nBienvenido al sistema 'Tu Carro UQ'!");
			mostrarMensaje("1. Iniciar sesión como Administrador");
			mostrarMensaje("2. Iniciar sesión como Empleado");
			mostrarMensaje("3. Iniciar sesión como Cliente");
			mostrarMensaje("4. Salir");
			mostrarMensaje("Seleccione una opción: ");
			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) {
				iniciarSesionAdministrador(scanner);
			} else if (opcion == 2) {
				iniciarSesionEmpleado(scanner);
			} else if (opcion == 3) {
				iniciarSesionCliente(scanner);
			} else if (opcion == 4) {
				mostrarMensaje("Gracias por usar el sistema, hasta luego");
				salir = true;
			} else {
				mostrarMensaje("Opcion invalida, intentelo nuevamente.");
			}
		}
		scanner.close();
	}

	// Metodo para iniciar sesion como administrador
	private static void iniciarSesionAdministrador(Scanner scanner) {
		mostrarMensaje("Ingrese el nombre de usuario del administrador: ");
		String usuario = scanner.nextLine();

		mostrarMensaje("Ingrese la contraseña: ");
		String contraseña = scanner.nextLine();

		boolean usuarioExistente = false;

		for (Empleado empleado : empleados) {
			if (empleado instanceof Administrador && empleado.getUsuario().equals(usuario)) {
				usuarioExistente = true;
				if (empleado.getContraseña().equals(contraseña)) {
					mostrarMenuAdministrador(scanner, (Administrador) empleado);
					return;
				} else {
					datosIncorrectos(scanner, usuario, true, contraseña, (Administrador) empleado);
				}
			}
		}

		if (!usuarioExistente) {
			datosIncorrectos(scanner, usuario, false, contraseña, null);
		}
	}

	private static void datosIncorrectos(Scanner scanner, String usuario, boolean usuarioExistente, String contraseña,
			Administrador administrador) {
		if (!usuarioExistente) {
			mostrarMensaje("El usuario no existe, intentalo denuevo");
		} else {
			mostrarMensaje("Parece que tu contraseña es incorrecta: " + contraseña + "\n¿Qué deseas hacer?");
			mostrarMensaje("1. Realizar la pregunta de seguridad para recuperar la contraseña");
			mostrarMensaje("2. Volver al menú principal");
			mostrarMensaje("Seleccione una opcion (1 o 2):");

			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) {
				administrador.recuperarContraseña(scanner);
				return;

			} else if (opcion == 2) {
				mostrarMensaje("Regresando al menú principal...");
			} else {
				mostrarMensaje("Opcion invalida. Regresando al menú principal...");
			}
		}
	}

	// Metodo para iniciar sesion como empleado
	private static void iniciarSesionEmpleado(Scanner scanner) {
		mostrarMensaje("Ingrese el nombre de usuario del empleado: ");
		String usuario = scanner.nextLine();
		mostrarMensaje("Ingrese la contraseña: ");
		String contraseña = scanner.nextLine();

		for (Empleado empleado : empleados) {
			if (empleado instanceof Empleado && empleado.getUsuario().equals(usuario)
					&& empleado.getContraseña().equals(contraseña)) {
				mostrarMensaje("Sesion iniciada correctamente, bienvenido " + empleado.nombre);
				mostrarMenuEmpleado(scanner, (Empleado) empleado);
				return;
			}
		}
		mostrarMensaje("Credenciales incorrectas, no se pudo iniciar sesión");
	}

	// Metodo para iniciar sesion como cliente
	private static void iniciarSesionCliente(Scanner scanner) {
		mostrarMensaje("Ingrese su nombre: ");
		String nombre = scanner.nextLine();

		for (Cliente cliente : clientes) {
			if (cliente.getNombre().equals(nombre)) {
				mostrarMensaje("\n Bienvenido de nuevo, " + nombre);
				mostrarMenuCliente(scanner, cliente);
				return;
			}
		}
		mostrarMensaje("Cliente no encontrado" + "\n¿Deseas registrarse?");
		mostrarMensaje("1. Sí  2. No: ");
		int opcion = scanner.nextInt();
		scanner.nextLine();

		if (opcion == 1) {
			mostrarMensaje("Ingrese su apellido: ");
			String apellido = scanner.nextLine();
			mostrarMensaje("Ingrese su dirección: ");
			String direccion = scanner.nextLine();
			mostrarMensaje("Ingrese su dni: ");
			String dni = scanner.nextLine();
			mostrarMensaje("Ingrese su email: ");
			String email = scanner.nextLine();
			mostrarMensaje("Ingrese su telefono: ");
			String telefono = scanner.nextLine();

			Cliente nuevoCliente = new Cliente(nombre, direccion, apellido, dni, email, telefono);
			clientes.add(nuevoCliente);

			mostrarMensaje("Bienvenido " + nombre + " " + dni + "con gusto te atenderemos en lo que necesites");
			mostrarMenuCliente(scanner, nuevoCliente);
		} else {
			mostrarMensaje("Saliendo...");
		}
	}

	// Metodo para el menu del administrador
	private static void mostrarMenuAdministrador(Scanner scanner, Administrador admin) {
		boolean salir = false;

		for (;;) {
			if (salir) {
				break;
			}

			mostrarMensaje("\n--- Menú Administrador ---");
			mostrarMensaje("1. Registrar nuevo empleado");
			mostrarMensaje("2. Generar reporte de negocios");
			mostrarMensaje("3. Salir");
			mostrarMensaje("Seleccione una opción: ");
			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) {
				registrarEmpleado(scanner);
			} else if (opcion == 2) {
				admin.generarReporte();
			} else if (opcion == 3) {
				salir = true;
			} else {
				mostrarMensaje("Opción no válida. Intente nuevamente.");
			}
		}
	}

	// Metodo para el menu del empleado
	private static void mostrarMenuEmpleado(Scanner scanner, Empleado empleado) {
		boolean salir = false;

		for (;;) {
			if (salir) {
				break;
			}

			mostrarMensaje("\n--- Menú Empleado ---");
			mostrarMensaje("1. Registrar vehículo");
			mostrarMensaje("2. Registrar cliente");
			mostrarMensaje("3. Realizar venta");
			mostrarMensaje("4. Realizar alquiler");
			mostrarMensaje("5. Salir");
			mostrarMensaje("Seleccione una opción: ");
			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) {
				registrarVehiculo(scanner, empleado);
			} else if (opcion == 2) {
				registrarCliente(scanner);
			} else if (opcion == 3) {
				realizarVenta(scanner, empleado);
			} else if (opcion == 4) {
				realizarAlquiler(scanner, empleado);
			} else if (opcion == 5) {
				salir = true;
			} else {
				mostrarMensaje("Opción no válida. Intente nuevamente.");
			}
		}
	}

	// Metodo para el menu del cliente
	private static void mostrarMenuCliente(Scanner scanner, Cliente cliente) {
		boolean salir = false;

		for (;;) {
			if (salir) {
				break;
			}

			mostrarMensaje("\n--- Menú Cliente ---");
			mostrarMensaje("1. Buscar vehículos");
			mostrarMensaje("2. Comprar vehículo");
			mostrarMensaje("3. Alquilar vehículo");
			mostrarMensaje("4. Salir");
			mostrarMensaje("Seleccione una opción: ");

			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) {
				buscarVehiculos();
			} else if (opcion == 2) {
				comprarVehiculo(scanner, cliente);
			} else if (opcion == 3) {
				alquilarVehiculo(scanner, cliente);
			} else if (opcion == 4) {
				salir = true;
			} else {
				mostrarMensaje("Opción no válida. Intente nuevamente.");
			}
		}
	}

	// Metodo para registrar un emepleado
	private static void registrarEmpleado(Scanner scanner) {
		mostrarMensaje("Ingrese nombre del empleado: ");
		String nombre = scanner.nextLine();
		mostrarMensaje("Ingrese apellido del empleado: ");
		String apellido = scanner.nextLine();
		mostrarMensaje("Ingrese DNI del empleado: ");
		String dni = scanner.nextLine();
		mostrarMensaje("Ingrese email del empleado: ");
		String email = scanner.nextLine();
		mostrarMensaje("Ingrese teléfono del empleado: ");
		String telefono = scanner.nextLine();
		mostrarMensaje("Ingrese usuario del empleado: ");
		String usuario = scanner.nextLine();
		mostrarMensaje("Ingrese contraseña del empleado: ");
		String contraseña = scanner.nextLine();

		Empleado nuevoEmpleado = new Empleado(nombre, apellido, dni, email, telefono, usuario, contraseña);
		empleados.add(nuevoEmpleado);
		mostrarMensaje("Empleado registrado exitosamente.");
	}

	// Metodo para registrar un vehiculo
	private static void registrarVehiculo(Scanner scanner, Empleado empleado) {
		mostrarMensaje("Ingrese el tipo de vehiculo que desea agregar: ");
		String tipo = scanner.nextLine();

		Vehiculo vehiculoRegistrado = null;

		if (tipo.equalsIgnoreCase("camion".trim())) {
			vehiculoRegistrado = Camion.mensajeCamion(scanner, empleado);
		} else if (tipo.equalsIgnoreCase("bus".trim())) {
			vehiculoRegistrado = Bus.mensajeBus(scanner, empleado);
		} else if (tipo.equalsIgnoreCase("camioneta".trim())) {
			vehiculoRegistrado = Camioneta.mensajeCamioneta(scanner, empleado);
		} else if (tipo.equalsIgnoreCase("deportivo".trim())) {
			vehiculoRegistrado = Deportivo.mensajeDeportivo(scanner, empleado);
		} else if (tipo.equalsIgnoreCase("moto".trim())) {
			vehiculoRegistrado = Moto.mensajeMoto(scanner, empleado);
		} else if (tipo.equalsIgnoreCase("pickup".trim())) {
			vehiculoRegistrado = PickUp.mensajePickUp(scanner, empleado);
		} else if (tipo.equalsIgnoreCase("van".trim())) {
			vehiculoRegistrado = Van.mensajeVan(scanner, empleado);
		} else if (tipo.equalsIgnoreCase("sedan".trim())) {
			vehiculoRegistrado = Sedan.mensajeSedan(scanner, empleado);
		} else {
			mostrarMensaje("Intentelo de nuevo");
			return;
		}

		if (vehiculoRegistrado != null) {
			vehiculosDisponibles.add(vehiculoRegistrado);
			mostrarMensaje("Vehículo registrado exitosamente:");
			mostrarMensaje(vehiculoRegistrado.toString());
		}
	}

	// Metodo para registrar un cliente
	private static void registrarCliente(Scanner scanner) {
		mostrarMensaje("Ingrese el nombre del cliente: ");
		String nombre = scanner.nextLine();
		mostrarMensaje("Ingrese la dirección del cliente: ");
		String direccion = scanner.nextLine();
		mostrarMensaje("Ingrese apellido del cliente: ");
		String apellido = scanner.nextLine();
		mostrarMensaje("Ingrese DNI del cliente: ");
		String dni = scanner.nextLine();
		mostrarMensaje("Ingrese email del cliente: ");
		String email = scanner.nextLine();
		mostrarMensaje("Ingrese teléfono del cliente: ");
		String telefono = scanner.nextLine();
		mostrarMensaje("Ingrese usuario del empleado: ");

		Cliente nuevoCliente = new Cliente(nombre, direccion, apellido, dni, email, telefono);
		clientes.add(nuevoCliente);
		mostrarMensaje("Cliente registrado exitosamente");
	}

	// Metodo para realizar una venta
	private static void realizarVenta(Scanner scanner, Empleado empleado) {
		buscarVehiculos();
		mostrarMensaje("Ingrese el índice del vehículo que desea vender: ");
		int indice = scanner.nextInt();
		scanner.nextLine();

		if (indice >= 0 && indice < vehiculosDisponibles.size()) {
			Vehiculo vehiculo = vehiculosDisponibles.get(indice);
			empleado.realizarVenta(vehiculo);
			mostrarMensaje("Vehículo vendido exitosamente");
		} else {
			mostrarMensaje("Opción inválida.");
		}
	}

	// Metodo para relizar un alquiler
	private static void realizarAlquiler(Scanner scanner, Empleado empleado) {
		buscarVehiculos();
		mostrarMensaje("Ingrese el índice del vehículo que desea alquilar: ");
		int indice = scanner.nextInt();
		scanner.nextLine();

		if (indice >= 0 && indice < vehiculosDisponibles.size()) {
			Vehiculo vehiculo = vehiculosDisponibles.get(indice);
			empleado.realizarAlquiler(vehiculo);
			mostrarMensaje("Vehículo alquilado exitosamente");
		} else {
			mostrarMensaje("Opción inválida.");
		}
	}

	// Metodo para comprar un vehiuclo
	private static void comprarVehiculo(Scanner scanner, Cliente cliente) {
		buscarVehiculos();
		mostrarMensaje("Ingrese el indice del vehículo que desea comprar: ");
		int indice = scanner.nextInt();
		scanner.nextLine();

		if (indice >= 0 && indice < vehiculosDisponibles.size()) {
			Vehiculo vehiculo = vehiculosDisponibles.get(indice);
			cliente.comprarVehiculo(vehiculo);
			mostrarMensaje("Vehiculo comprado exitosamente");
		} else {
			mostrarMensaje("Opción invalida");
		}
	}

	// Metodo para alquilar un vehiculo
	private static void alquilarVehiculo(Scanner scanner, Cliente cliente) {
		buscarVehiculos();
		mostrarMensaje("Ingrese el indice del vehículo que desea alquilar: ");
		int indice = scanner.nextInt();
		scanner.nextLine();

		if (indice >= 0 && indice < vehiculosDisponibles.size()) {
			Vehiculo vehiculo = vehiculosDisponibles.get(indice);
			cliente.alquilarVehiculo(vehiculo);
			mostrarMensaje("Vehículo alquilado exitosamente");
		} else {
			mostrarMensaje("Opción invalida");
		}
	}

	// Metodo para ver los vehiculos disponibles
	private static void buscarVehiculos() {
		mostrarMensaje("\n--- Vehículos Disponibles ---");
		for (Vehiculo vehiculo : vehiculosDisponibles) {
			mostrarMensaje(vehiculo.toString());
		}
	}

	// Metodo para mostrar mensajes
	public static void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}
}