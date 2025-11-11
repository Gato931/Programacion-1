package co.edu.uniquindio.poo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Pruebas unitarias para la clase SistemaGestionSeguridad
 */
@DisplayName("Tests del Sistema de Gestión de Seguridad")
public class SistemaGestionSeguridadTest {

  private SistemaGestionSeguridad sistema;
  private Vigilante vigilante1;
  private Vigilante vigilante2;
  private Supervisor supervisor;
  private CustodiaFija servicio;
  private Equipo radio;
  private Equipo arma;

  @BeforeAll
  static void setupInicial() {
    System.out.println("═══ INICIANDO TESTS DEL SISTEMA ═══");
  }

  @AfterAll
  static void limpiezaFinal() {
    System.out.println("═══ TESTS DEL SISTEMA COMPLETADOS ═══\n");
  }

  @BeforeEach
  void setUp() {
    sistema = new SistemaGestionSeguridad();

    vigilante1 = new Vigilante("Carlos Méndez", "1001", Turno.DIA,
        1500000, 101, TipoArma.NO_LETAL);

    vigilante2 = new Vigilante("Ana López", "1002", Turno.NOCHE,
        1500000, 102, TipoArma.LETAL);

    supervisor = new Supervisor("Pedro Ramírez", "2001", Turno.DIA,
        2500000, 500000);

    servicio = new CustodiaFija("CF001", "Banco Nacional",
        5000000, 3, "Centro Comercial");

    radio = new Equipo("R001", TipoEquipo.RADIO, 500000);
    arma = new Equipo("A001", TipoEquipo.ARMA, 3000000);
  }

  @AfterEach
  void tearDown() {
    sistema = null;
    vigilante1 = null;
    vigilante2 = null;
    supervisor = null;
    servicio = null;
    radio = null;
    arma = null;
  }

  // ═══════════════ TESTS DE REGISTRO DE EMPLEADOS ═══════════════

  @Test
  @DisplayName("Registrar empleado en el sistema")
  void testRegistrarEmpleado() {
    sistema.registrarEmpleado(vigilante1);

    List<Empleado> empleados = sistema.getEmpleados();

    assertEquals(1, empleados.size(), "Debería haber 1 empleado registrado");
    assertTrue(empleados.contains(vigilante1),
        "La lista debería contener al vigilante registrado");
  }

  @Test
  @DisplayName("Registrar múltiples empleados")
  void testRegistrarMultiplesEmpleados() {
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarEmpleado(vigilante2);
    sistema.registrarEmpleado(supervisor);

    assertEquals(3, sistema.getEmpleados().size(),
        "Deberían haber 3 empleados registrados");
  }

  @Test
  @DisplayName("Listar solo vigilantes")
  void testListarVigilantes() {
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarEmpleado(vigilante2);
    sistema.registrarEmpleado(supervisor);

    List<Vigilante> vigilantes = sistema.listarVigilantes();

    assertEquals(2, vigilantes.size(), "Deberían haber 2 vigilantes");
    assertFalse(vigilantes.contains(supervisor),
        "La lista de vigilantes no debería contener al supervisor");
  }

  @Test
  @DisplayName("Listar solo supervisores")
  void testListarSupervisores() {
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarEmpleado(supervisor);

    List<Supervisor> supervisores = sistema.listarSupervisores();

    assertEquals(1, supervisores.size(), "Debería haber 1 supervisor");
    assertTrue(supervisores.contains(supervisor));
  }

  @Test
  @DisplayName("Listar operadores de monitoreo")
  void testListarOperadores() {
    OperadorMonitoreo operador = new OperadorMonitoreo(
        "María García", "3001", Turno.NOCHE, 2000000, 8);

    sistema.registrarEmpleado(vigilante1);
    sistema.registrarEmpleado(operador);

    List<OperadorMonitoreo> operadores = sistema.listarOperadores();

    assertEquals(1, operadores.size(), "Debería haber 1 operador");
    assertTrue(operadores.contains(operador));
  }

  // ═══════════════ TESTS DE TRANSFERENCIA DE EQUIPOS ═══════════════

  @Test
  @DisplayName("Transferir equipo exitosamente entre empleados")
  void testTransferirEquipoExitoso() {
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarEmpleado(vigilante2);

    vigilante1.asignarEquipo(radio);

    boolean resultado = sistema.transferirEquipo("R001", vigilante1, vigilante2);

    assertTrue(resultado, "La transferencia debería ser exitosa");
    assertFalse(vigilante1.getEquiposAsignados().contains(radio),
        "El vigilante1 no debería tener el equipo");
    assertTrue(vigilante2.getEquiposAsignados().contains(radio),
        "El vigilante2 debería tener el equipo");
  }

  @Test
  @DisplayName("Transferir equipo falla si no existe en origen")
  void testTransferirEquipoFallaNoExiste() {
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarEmpleado(vigilante2);

    boolean resultado = sistema.transferirEquipo("NOEXISTE", vigilante1, vigilante2);

    assertFalse(resultado, "La transferencia debería fallar");
  }

  // ═══════════════ TESTS DE CONTAR VIGILANTES POR ARMA ═══════════════

  @Test
  @DisplayName("Contar vigilantes por tipo de arma LETAL")
  void testContarVigilantesArmaLetal() {
    sistema.registrarEmpleado(vigilante1); // NO_LETAL
    sistema.registrarEmpleado(vigilante2); // LETAL

    Vigilante vigilante3 = new Vigilante("Luis Torres", "1003",
        Turno.NOCHE, 1500000, 103, TipoArma.LETAL);
    sistema.registrarEmpleado(vigilante3); // LETAL

    int cantidad = sistema.contarVigilantesPorTipoArma(TipoArma.LETAL);

    assertEquals(2, cantidad, "Deberían haber 2 vigilantes con arma LETAL");
  }

  @Test
  @DisplayName("Contar vigilantes por tipo de arma NO_LETAL")
  void testContarVigilantesArmaNoLetal() {
    sistema.registrarEmpleado(vigilante1); // NO_LETAL
    sistema.registrarEmpleado(vigilante2); // LETAL

    int cantidad = sistema.contarVigilantesPorTipoArma(TipoArma.NO_LETAL);

    assertEquals(1, cantidad, "Debería haber 1 vigilante con arma NO_LETAL");
  }

  @Test
  @DisplayName("Contar vigilantes SIN_ARMA retorna 0 si no hay")
  void testContarVigilantesSinArmaCero() {
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarEmpleado(vigilante2);

    int cantidad = sistema.contarVigilantesPorTipoArma(TipoArma.SIN_ARMA);

    assertEquals(0, cantidad, "No deberían haber vigilantes SIN_ARMA");
  }

  // ═══════════════ TESTS DE BUSCAR VIGILANTE ═══════════════

  @Test
  @DisplayName("Buscar vigilante por puesto existente")
  void testBuscarVigilantePorPuestoExistente() {
    sistema.registrarEmpleado(vigilante1); // Puesto 101

    Optional<Vigilante> resultado = sistema.buscarVigilantePorPuesto(101);

    assertTrue(resultado.isPresent(), "Debería encontrar el vigilante");
    assertEquals(vigilante1, resultado.get(),
        "Debería retornar el vigilante correcto");
  }

  @Test
  @DisplayName("Buscar vigilante por puesto inexistente retorna empty")
  void testBuscarVigilantePorPuestoInexistente() {
    sistema.registrarEmpleado(vigilante1);

    Optional<Vigilante> resultado = sistema.buscarVigilantePorPuesto(999);

    assertFalse(resultado.isPresent(),
        "No debería encontrar vigilante en puesto inexistente");

    // También podemos usar assertTrue con isEmpty()
    assertTrue(resultado.isEmpty(),
        "El Optional debería estar vacío");
  }

  // ═══════════════ TESTS DE SERVICIOS ═══════════════

  @Test
  @DisplayName("Registrar servicio en el sistema")
  void testRegistrarServicio() {
    sistema.registrarServicio(servicio);

    List<Servicio> servicios = sistema.getServicios();

    assertEquals(1, servicios.size(), "Debería haber 1 servicio registrado");
    assertTrue(servicios.contains(servicio));
  }

  @Test
  @DisplayName("Listar solo servicios activos")
  void testListarServiciosActivos() {
    CustodiaFija servicio2 = new CustodiaFija("CF002", "Plaza Mayor",
        8000000, 8, "Centro Empresarial");
    servicio2.setEstado(EstadoServicio.SUSPENDIDO);

    sistema.registrarServicio(servicio); // ACTIVO
    sistema.registrarServicio(servicio2); // SUSPENDIDO

    List<Servicio> activos = sistema.listarServiciosActivos();

    assertEquals(1, activos.size(), "Debería haber 1 servicio activo");
    assertTrue(activos.contains(servicio),
        "La lista debería contener el servicio activo");
    assertFalse(activos.contains(servicio2),
        "La lista NO debería contener el servicio suspendido");
  }

  @Test
  @DisplayName("Obtener contratos activos por cliente")
  void testObtenerContratosActivosPorCliente() {
    sistema.registrarServicio(servicio); // Cliente: "Banco Nacional"

    CustodiaFija servicio2 = new CustodiaFija("CF002", "Banco Nacional",
        6000000, 5, "Sucursal Norte");
    sistema.registrarServicio(servicio2);

    List<String> contratos = sistema.obtenerContratosActivosPorCliente("Banco Nacional");

    assertEquals(2, contratos.size(),
        "Debería haber 2 contratos para Banco Nacional");
    assertTrue(contratos.contains("CF001"));
    assertTrue(contratos.contains("CF002"));
  }

  @Test
  @DisplayName("Cliente sin contratos retorna lista vacía")
  void testClienteSinContratos() {
    sistema.registrarServicio(servicio);

    List<String> contratos = sistema.obtenerContratosActivosPorCliente("Cliente Inexistente");

    assertNotNull(contratos, "La lista no debería ser null");
    assertEquals(0, contratos.size(), "La lista debería estar vacía");
    assertTrue(contratos.isEmpty(), "La lista debería estar vacía");
  }

  // ═══════════════ TESTS DE VALIDACIÓN DE ASIGNACIÓN ═══════════════

  @Test
  @DisplayName("Validar asignación exitosa de empleado a servicio activo")
  void testValidarAsignacionExitosa() {
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarServicio(servicio);

    boolean valido = sistema.validarAsignacionEmpleadoServicio(vigilante1, servicio);

    assertTrue(valido, "La asignación debería ser válida");
  }

  @Test
  @DisplayName("Validar asignación falla si servicio no está activo")
  void testValidarAsignacionFallaServicioInactivo() {
    sistema.registrarEmpleado(vigilante1);
    servicio.setEstado(EstadoServicio.SUSPENDIDO);
    sistema.registrarServicio(servicio);

    boolean valido = sistema.validarAsignacionEmpleadoServicio(vigilante1, servicio);

    assertFalse(valido,
        "La asignación debería fallar si el servicio no está activo");
  }

  @Test
  @DisplayName("Validar asignación falla si empleado no está registrado")
  void testValidarAsignacionFallaEmpleadoNoRegistrado() {
    // NO registrar el empleado
    sistema.registrarServicio(servicio);

    boolean valido = sistema.validarAsignacionEmpleadoServicio(vigilante1, servicio);

    assertFalse(valido,
        "La asignación debería fallar si el empleado no está registrado");
  }

  @Test
  @DisplayName("Validar asignación falla si vigilante tiene configuración inválida")
  void testValidarAsignacionFallaConfiguracionInvalida() {
    // Vigilante con arma LETAL en turno DÍA (configuración inválida)
    Vigilante vigilanteInvalido = new Vigilante("Mario Pérez", "1006",
        Turno.DIA, 1500000, 106, TipoArma.LETAL);

    sistema.registrarEmpleado(vigilanteInvalido);
    sistema.registrarServicio(servicio);

    boolean valido = sistema.validarAsignacionEmpleadoServicio(
        vigilanteInvalido, servicio);

    assertFalse(valido,
        "La asignación debería fallar si la configuración es inválida");
  }

  // ═══════════════ TESTS DE COSTOS ═══════════════

  @Test
  @DisplayName("Sumar costos heterogéneos (servicios y equipos)")
  void testSumarCostosHeterogeneos() {
    List<Costeable> costeables = new ArrayList<>();
    costeables.add(servicio);
    costeables.add(radio);
    costeables.add(arma);

    double total = sistema.sumarCostosHeterogeneos(costeables);

    double esperado = servicio.calcularCostoMensual()
        + radio.obtenerCostoMensual()
        + arma.obtenerCostoMensual();

    assertEquals(esperado, total, 0.01,
        "La suma de costos debería ser correcta");
  }

  @Test
  @DisplayName("Calcular costo total de servicios activos")
  void testCalcularCostoTotalServicios() {
    sistema.registrarServicio(servicio);

    CustodiaFija servicio2 = new CustodiaFija("CF002", "Plaza Mayor",
        8000000, 8, "Centro Empresarial");
    sistema.registrarServicio(servicio2);

    double total = sistema.calcularCostoTotalServicios();

    double esperado = servicio.calcularCostoMensual()
        + servicio2.calcularCostoMensual();

    assertEquals(esperado, total, 0.01,
        "El costo total debería incluir ambos servicios");
  }

  @Test
  @DisplayName("Costo total no incluye servicios suspendidos")
  void testCostoTotalNoIncluyeSuspendidos() {
    sistema.registrarServicio(servicio); // ACTIVO

    CustodiaFija servicioSuspendido = new CustodiaFija("CF002", "Plaza Mayor",
        8000000, 8, "Centro Empresarial");
    servicioSuspendido.setEstado(EstadoServicio.SUSPENDIDO);
    sistema.registrarServicio(servicioSuspendido);

    double total = sistema.calcularCostoTotalServicios();

    assertEquals(servicio.calcularCostoMensual(), total, 0.01,
        "Solo debería incluir el costo del servicio activo");
  }

  // ═══════════════ TESTS DE REPORTE CONSOLIDADO ═══════════════

  @Test
  @DisplayName("Generar reporte consolidado con elementos heterogéneos")
  void testGenerarReporteConsolidado() {
    List<Reporteable> elementos = new ArrayList<>();
    elementos.add(vigilante1);
    elementos.add(servicio);
    elementos.add(radio);

    String reporte = sistema.generarReporteConsolidado(elementos);

    assertNotNull(reporte, "El reporte no debería ser null");
    assertFalse(reporte.isEmpty(), "El reporte no debería estar vacío");
    assertTrue(reporte.contains("REPORTE CONSOLIDADO"),
        "El reporte debería tener un encabezado");
  }

  @Test
  @DisplayName("Reporte consolidado con lista vacía")
  void testReporteConsolidadoListaVacia() {
    List<Reporteable> elementos = new ArrayList<>();

    String reporte = sistema.generarReporteConsolidado(elementos);

    assertNotNull(reporte, "El reporte no debería ser null");
    assertTrue(reporte.contains("REPORTE CONSOLIDADO"),
        "Debería tener el encabezado aunque esté vacío");
  }

  // ═══════════════ TESTS DE ASIGNACIÓN A SERVICIOS ═══════════════

  @Test
  @DisplayName("Asignar personal a servicio")
  void testAsignarPersonalAServicio() {
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarServicio(servicio);

    sistema.asignarPersonalAServicio("CF001", vigilante1);

    assertTrue(servicio.getPersonalAsignado().contains(vigilante1),
        "El vigilante debería estar asignado al servicio");
  }

  @Test
  @DisplayName("Asignar equipo a servicio")
  void testAsignarEquipoAServicio() {
    sistema.registrarServicio(servicio);

    sistema.asignarEquipoAServicio("CF001", radio);

    assertTrue(servicio.getEquiposAsignados().contains(radio),
        "El equipo debería estar asignado al servicio");
  }

  // ═══════════════ TESTS DE EQUIPOS ═══════════════

  @Test
  @DisplayName("Registrar equipo en el sistema")
  void testRegistrarEquipo() {
    sistema.registrarEquipo(radio);

    List<Equipo> equipos = sistema.getEquipos();

    assertEquals(1, equipos.size(), "Debería haber 1 equipo registrado");
    assertTrue(equipos.contains(radio));
  }

  // ═══════════════ TEST CON assertThrows ═══════════════

  @Test
  @DisplayName("Demostración de assertThrows con operación inválida")
  void testDemostracionAssertThrows() {
    // Ejemplo: intentar generar un repuesto con código null
    assertThrows(IllegalArgumentException.class, () -> {
      radio.generarRepuesto(null);
    }, "Debería lanzar excepción con código null");

    // También se puede verificar el mensaje
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      radio.generarRepuesto("");
    });

    assertNotNull(exception.getMessage(),
        "La excepción debería tener un mensaje");
  }

  @Test
  @Disabled("Test de integración - ejecutar manualmente")
  @DisplayName("Test de integración completo del sistema")
  void testIntegracionCompleto() {
    // Este test complejo se ejecutaría manualmente
    // o en un ambiente de testing de integración
    sistema.registrarEmpleado(vigilante1);
    sistema.registrarEmpleado(vigilante2);
    sistema.registrarServicio(servicio);

    // Realizar operaciones complejas...
    fail("Test de integración no implementado en tests unitarios");
  }
}