package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
	public static void main(String[] args) {
		SistemaGestionSeguridad sistema = new SistemaGestionSeguridad();

		System.out.println("╔════════════════════════════════════════════╗");
		System.out.println("║   SISTEMA DE GESTIÓN SEGURCOL S.A.        ║");
		System.out.println("║        DEMO CON TODOS LOS EJERCICIOS      ║");
		System.out.println("╚════════════════════════════════════════════╝\n");

		// Crear empleados
		System.out.println("═══ REGISTRANDO EMPLEADOS ═══");
		Vigilante v1 = new Vigilante("Carlos Méndez", "1001", Turno.DIA, 1500000, 101, TipoArma.NO_LETAL);
		Vigilante v2 = new Vigilante("Ana López", "1002", Turno.NOCHE, 1500000, 102, TipoArma.LETAL);
		Vigilante v3 = new Vigilante("Luis Torres", "1003", Turno.NOCHE, 1500000, 103, TipoArma.NO_LETAL);
		Supervisor s1 = new Supervisor("Pedro Ramírez", "2001", Turno.DIA, 2500000, 500000);
		OperadorMonitoreo o1 = new OperadorMonitoreo("María García", "3001", Turno.NOCHE, 2000000, 8);

		sistema.registrarEmpleado(v1);
		sistema.registrarEmpleado(v2);
		sistema.registrarEmpleado(v3);
		sistema.registrarEmpleado(s1);
		sistema.registrarEmpleado(o1);

		// Supervisor asigna vigilantes
		s1.agregarVigilante(v1);
		s1.agregarVigilante(v2);
		s1.agregarVigilante(v3);

		// Crear equipos
		System.out.println("\n═══ CREANDO EQUIPOS ═══");
		Equipo radio1 = new Equipo("R001", TipoEquipo.RADIO, 500000);
		Equipo arma1 = new Equipo("A001", TipoEquipo.ARMA, 3000000);
		Equipo vehiculo1 = new Equipo("V001", TipoEquipo.VEHICULO, 50000000);
		Equipo radio2 = new Equipo("R002", TipoEquipo.RADIO, 500000);

		sistema.registrarEquipo(radio1);
		sistema.registrarEquipo(arma1);
		sistema.registrarEquipo(vehiculo1);
		sistema.registrarEquipo(radio2);

		// Asignar equipos a empleados
		v1.asignarEquipo(radio1);
		v1.asignarEquipo(arma1);
		v2.asignarEquipo(radio2);

		// Crear servicios
		System.out.println("\n═══ REGISTRANDO SERVICIOS ═══");
		CustodiaFija cf1 = new CustodiaFija("CF001", "Banco Nacional", 5000000, 3, "Centro Comercial");
		PatrullajeMovil pm1 = new PatrullajeMovil("PM001", "Zona Industrial", 3000000, 5, 500);
		MonitoreoRemoto mr1 = new MonitoreoRemoto("MR001", "Conjunto Residencial", 2000000, 120, true);

		sistema.registrarServicio(cf1);
		sistema.registrarServicio(pm1);
		sistema.registrarServicio(mr1);

		// ══════════════════════════════════════════════
		// DEMOSTRACIÓN DE LOS EJERCICIOS
		// ══════════════════════════════════════════════

		System.out.println("\n╔════════════════════════════════════════════╗");
		System.out.println("║     DEMOSTRACIÓN DE EJERCICIOS            ║");
		System.out.println("╚════════════════════════════════════════════╝\n");

		// EJERCICIO 1: Verificar turno nocturno
		System.out.println("═══ EJERCICIO 1: Verificar Turno Nocturno ═══");
		System.out.println(v1.getNombre() + " tiene turno nocturno: " + v1.tieneTurnoNocturno());
		System.out.println(v2.getNombre() + " tiene turno nocturno: " + v2.tieneTurnoNocturno());

		// EJERCICIO 2: Transferir equipo
		System.out.println("\n═══ EJERCICIO 2: Transferir Equipo ═══");
		sistema.transferirEquipo("R001", v1, v3);

		// EJERCICIO 3: Reporte consolidado heterogéneo
		System.out.println("\n═══ EJERCICIO 3: Reporte Consolidado ═══");
		List<Reporteable> reporteables = new ArrayList<>();
		reporteables.add(v1);
		reporteables.add(v2);
		reporteables.add(s1);
		reporteables.add(cf1);
		reporteables.add(radio1);

		String reporteConsolidado = sistema.generarReporteConsolidado(reporteables);
		System.out.println(reporteConsolidado);

		// EJERCICIO 4: Contar vigilantes por tipo de arma
		System.out.println("═══ EJERCICIO 4: Contar Vigilantes por Tipo Arma ═══");
		System.out.println("Vigilantes con arma LETAL: " + sistema.contarVigilantesPorTipoArma(TipoArma.LETAL));
		System.out.println("Vigilantes con arma NO_LETAL: "
				+ sistema.contarVigilantesPorTipoArma(TipoArma.NO_LETAL));
		System.out.println("Vigilantes SIN_ARMA: " + sistema.contarVigilantesPorTipoArma(TipoArma.SIN_ARMA));

		// EJERCICIO 5: Sumar costos heterogéneos
		System.out.println("\n═══ EJERCICIO 5: Sumar Costos Heterogéneos ═══");
		List<Costeable> costeables = new ArrayList<>();
		costeables.add(cf1);
		costeables.add(pm1);
		costeables.add(mr1);
		costeables.add(radio1);
		costeables.add(arma1);
		costeables.add(vehiculo1);

		double totalCostos = sistema.sumarCostosHeterogeneos(costeables);
		System.out.printf("Costo total mensual combinado: $%,.2f%n", totalCostos);

		// EJERCICIO 6: Buscar vigilante por puesto
		System.out.println("\n═══ EJERCICIO 6: Buscar Vigilante por Puesto ═══");
		Optional<Vigilante> vigilanteEncontrado = sistema.buscarVigilantePorPuesto(101);
		if (vigilanteEncontrado.isPresent()) {
			System.out.println("Vigilante en puesto 101: " + vigilanteEncontrado.get().getNombre());
		} else {
			System.out.println("No se encontró vigilante en puesto 101");
		}

		// EJERCICIO 7: Calcular valor equipos por tipo
		System.out.println("\n═══ EJERCICIO 7: Valor Equipos por Tipo ═══");
		System.out.printf("Valor RADIOS de %s: $%,.2f%n", v1.getNombre(),
				v1.calcularValorEquiposPorTipo(TipoEquipo.RADIO));
		System.out.printf("Valor ARMAS de %s: $%,.2f%n", v1.getNombre(),
				v1.calcularValorEquiposPorTipo(TipoEquipo.ARMA));
		System.out.printf("Valor RADIOS de %s: $%,.2f%n", v2.getNombre(),
				v2.calcularValorEquiposPorTipo(TipoEquipo.RADIO));

		// EJERCICIO 8: Verificar mínimo vigilantes
		System.out.println("\n═══ EJERCICIO 8: Verificar Mínimo Vigilantes ═══");
		boolean cumpleMinimo = s1.cumpleConMinimoVigilantes(3);
		System.out.println("Supervisor " + s1.getNombre() + " cumple con mínimo de 3 vigilantes: " +
				(cumpleMinimo ? "SÍ" : "NO"));
		System.out.println("Cantidad de vigilantes supervisados: " + s1.getVigilantesSupervisados().size());

		// EJERCICIO 9: Calcular salario con horas extras validadas
		System.out.println("\n═══ EJERCICIO 9: Salario con Horas Extras Validadas ═══");
		double salarioSinExtras = v1.calcularSalarioTotal();
		System.out.printf("Salario base de %s: $%,.2f%n", v1.getNombre(), salarioSinExtras);

		double salarioConExtras = v1.calcularSalarioConHorasExtras(20);
		System.out.printf("Salario con 20 horas extras: $%,.2f%n", salarioConExtras);

		double salarioConExtrasNegativas = v1.calcularSalarioConHorasExtras(-10);
		System.out.printf("Salario con -10 horas extras (validado a 0): $%,.2f%n", salarioConExtrasNegativas);

		// EJERCICIO 10: Obtener contratos activos por cliente
		System.out.println("\n═══ EJERCICIO 10: Contratos Activos por Cliente ═══");
		List<String> contratosBanco = sistema.obtenerContratosActivosPorCliente("Banco Nacional");
		System.out.println("Contratos activos de 'Banco Nacional': " + contratosBanco);

		List<String> contratosZona = sistema.obtenerContratosActivosPorCliente("Zona Industrial");
		System.out.println("Contratos activos de 'Zona Industrial': " + contratosZona);

		// EJERCICIO 11: Filtrar agenda por palabra
		System.out.println("\n═══ EJERCICIO 11: Filtrar Agenda por Palabra ═══");
		v1.programar(LocalDate.now(), "Entrenamiento de tiro");
		v1.programar(LocalDate.now().plusDays(1), "Revisión de equipos");
		v1.programar(LocalDate.now().plusDays(2), "Patrullaje nocturno");
		v1.programar(LocalDate.now().plusDays(3), "Capacitación en primeros auxilios");

		List<AgendaItem> agendaFiltrada = v1.filtrarAgendaPorPalabra("equipos");
		System.out.println("Items de agenda con la palabra 'equipos': " + agendaFiltrada.size());
		agendaFiltrada.forEach(item -> System.out.println("  - " + item.descripcion()));

		// EJERCICIO 12: Retirar equipo por código
		System.out.println("\n═══ EJERCICIO 12: Retirar Equipo por Código ═══");
		System.out.println("Equipos de " + v1.getNombre() + " antes: " + v1.getEquiposAsignados().size());
		boolean retirado = v1.retirarEquipoPorCodigo("A001");
		System.out.println("Equipo A001 retirado: " + (retirado ? "SÍ" : "NO"));
		System.out.println("Equipos de " + v1.getNombre() + " después: " + v1.getEquiposAsignados().size());

		// EJERCICIO 13: Verificar servicio grande
		System.out.println("\n═══ EJERCICIO 13: Verificar Servicio Grande ═══");
		boolean esGrande = mr1.esServicioGrande(100);
		System.out.println("Servicio " + mr1.getCodigoContrato() + " es grande (>100 dispositivos): " +
				(esGrande ? "SÍ" : "NO"));
		System.out.println("Número de dispositivos: " + mr1.getNumeroDispositivos());

		// EJERCICIO 14: Reemplazar vigilante
		System.out.println("\n═══ EJERCICIO 14: Reemplazar Vigilante ═══");
		cf1.asignarPersonal(v2);

		Vigilante v4 = new Vigilante("Roberto Silva", "1004", Turno.NOCHE, 1500000, 104, TipoArma.LETAL);
		sistema.registrarEmpleado(v4);

		LocalDate fechaInicio = LocalDate.now();
		LocalDate fechaFin = LocalDate.now().plusDays(30);
		v2.programar(fechaInicio, "Custodia turno 1");
		v2.programar(fechaInicio.plusDays(7), "Custodia turno 2");

		boolean reemplazado = cf1.reemplazarVigilante(v2, v4, fechaInicio, fechaFin);
		System.out.println("Vigilante reemplazado: " + (reemplazado ? "SÍ" : "NO"));
		System.out.println("Personal en servicio: " + cf1.getPersonalAsignado().size());

		// EJERCICIO 15: Cambiar turno con validación
		System.out.println("\n═══ EJERCICIO 15: Cambiar Turno con Validación ═══");
		Vigilante v5 = new Vigilante("Sandra Gómez", "1005", Turno.DIA, 1500000, 105, TipoArma.LETAL);
		sistema.registrarEmpleado(v5);

		System.out.println("Configuración inicial de " + v5.getNombre() + ": " +
				(v5.esConfiguracionValida() ? "VÁLIDA" : "INVÁLIDA"));
		System.out.println("Turno actual: " + v5.getTurno() + ", Arma: " + v5.getTipoArma());

		boolean cambioExitoso = v5.cambiarTurnoConValidacion(Turno.NOCHE);
		System.out.println("Cambio a turno NOCHE: " + (cambioExitoso ? "EXITOSO" : "RECHAZADO"));
		System.out.println("Turno actual después del cambio: " + v5.getTurno());

		// EJERCICIO 16: Generar equipo de repuesto
		System.out.println("\n═══ EJERCICIO 16: Generar Equipo de Repuesto ═══");
		Equipo equipoOriginal = radio1;
		System.out.println("Equipo original: " + equipoOriginal.getCodigo());

		Equipo repuesto = equipoOriginal.generarRepuesto("R001-REP");
		System.out.println("Repuesto generado: " + repuesto.getCodigo());
		System.out.println("Estado del repuesto: " + repuesto.getEstado());
		System.out.println("Tipo: " + repuesto.getTipo());
		System.out.println("Valor: $" + String.format("%,.2f", repuesto.getValorReposicion()));

		// EJERCICIO 17: Validar asignación empleado-servicio
		System.out.println("\n═══ EJERCICIO 17: Validar Asignación Empleado-Servicio ═══");

		// Caso válido
		boolean asignacionValida1 = sistema.validarAsignacionEmpleadoServicio(v3, pm1);
		System.out.println("Asignación de " + v3.getNombre() + " a " + pm1.getCodigoContrato() + ": " +
				(asignacionValida1 ? "VÁLIDA" : "INVÁLIDA"));

		// Caso inválido: vigilante con arma letal en turno día
		Vigilante v6 = new Vigilante("Mario Pérez", "1006", Turno.DIA, 1500000, 106, TipoArma.LETAL);
		boolean asignacionValida2 = sistema.validarAsignacionEmpleadoServicio(v6, pm1);
		System.out.println("Asignación de vigilante con arma letal en turno día: " +
				(asignacionValida2 ? "VÁLIDA" : "INVÁLIDA"));

		// EJERCICIO 18: Calcular costo con BigDecimal
		System.out.println("\n═══ EJERCICIO 18: Calcular Costo con BigDecimal ═══");
		System.out.printf("Costo estándar (double): $%,.2f%n", pm1.calcularCostoMensual());
		System.out.println("Costo preciso (BigDecimal): $" + pm1.calcularCostoMensualPreciso());

		// EJERCICIO 19: Validar configuración arma-turno
		System.out.println("\n═══ EJERCICIO 19: Validar Configuración Arma-Turno ═══");
		System.out.println("Validación de configuraciones:");

		List<Vigilante> todosVigilantes = sistema.listarVigilantes();
		for (Vigilante vig : todosVigilantes) {
			boolean configValida = vig.esConfiguracionValida();
			System.out.printf("  %s - Turno: %s, Arma: %s → %s%n",
					vig.getNombre(),
					vig.getTurno(),
					vig.getTipoArma(),
					configValida ? "✓ VÁLIDA" : "✗ INVÁLIDA");
		}

		// ══════════════════════════════════════════════
		// REPORTES FINALES
		// ══════════════════════════════════════════════

		System.out.println("\n╔════════════════════════════════════════════╗");
		System.out.println("║          REPORTES FINALES                 ║");
		System.out.println("╚════════════════════════════════════════════╝\n");

		// Informe de dotación
		sistema.generarInformeDotacion("1001");
		sistema.generarInformeDotacion("1002");

		// Gasto total
		sistema.calcularGastoTotal();

		// Resumen de servicios
		System.out.println("═══ RESUMEN DE SERVICIOS ═══");
		List<Servicio> serviciosActivos = sistema.listarServiciosActivos();
		System.out.println("Total de servicios activos: " + serviciosActivos.size());
		System.out.printf("Costo total de servicios activos: $%,.2f%n", sistema.calcularCostoTotalServicios());

		System.out.println("\n═══ LISTA DE SERVICIOS ═══");
		for (Servicio servicio : serviciosActivos) {
			System.out.println("  - " + servicio);
			System.out.printf("    Costo mensual: $%,.2f%n", servicio.calcularCostoMensual());
		}

		System.out.println("\n╔════════════════════════════════════════════╗");
		System.out.println("║   DEMOSTRACIÓN COMPLETADA EXITOSAMENTE    ║");
		System.out.println("╚════════════════════════════════════════════╝");
	}
}