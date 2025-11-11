package co.edu.uniquindio.poo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Pruebas unitarias para la clase Vigilante
 */
@DisplayName("Tests de la clase Vigilante")
public class VigilanteTest {

  private Vigilante vigilanteDia;
  private Vigilante vigilanteNoche;
  private Equipo radio;
  private Equipo arma;

  @BeforeAll
  static void setupAntesDeTodo() {
    System.out.println("═══ INICIANDO TESTS DE VIGILANTE ═══");
  }

  @AfterAll
  static void limpiarDespuesDeTodo() {
    System.out.println("═══ TESTS DE VIGILANTE COMPLETADOS ═══\n");
  }

  @BeforeEach
  void setUp() {
    // Se ejecuta antes de cada test
    vigilanteDia = new Vigilante("Carlos Méndez", "1001", Turno.DIA,
        1500000, 101, TipoArma.NO_LETAL);

    vigilanteNoche = new Vigilante("Ana López", "1002", Turno.NOCHE,
        1500000, 102, TipoArma.LETAL);

    radio = new Equipo("R001", TipoEquipo.RADIO, 500000);
    arma = new Equipo("A001", TipoEquipo.ARMA, 3000000);
  }

  @AfterEach
  void tearDown() {
    // Se ejecuta después de cada test
    vigilanteDia = null;
    vigilanteNoche = null;
    radio = null;
    arma = null;
  }

  // ═══════════════ TESTS DE TURNO ═══════════════

  @Test
  @DisplayName("Verificar que vigilante de día NO tiene turno nocturno")
  void testVigilanteDiaNoTieneTurnoNocturno() {
    // assertFalse - verifica que la condición sea false
    assertFalse(vigilanteDia.tieneTurnoNocturno(),
        "Un vigilante de día no debería tener turno nocturno");
  }

  @Test
  @DisplayName("Verificar que vigilante de noche SÍ tiene turno nocturno")
  void testVigilanteNocheTieneTurnoNocturno() {
    // assertTrue - verifica que la condición sea true
    assertTrue(vigilanteNoche.tieneTurnoNocturno(),
        "Un vigilante de noche debería tener turno nocturno");
  }

  // ═══════════════ TESTS DE CONFIGURACIÓN ═══════════════

  @Test
  @DisplayName("Configuración válida: Arma NO_LETAL en turno DÍA")
  void testConfiguracionValidaArmaNoLetalDia() {
    assertTrue(vigilanteDia.esConfiguracionValida(),
        "Arma NO_LETAL en turno DÍA debería ser válida");
  }

  @Test
  @DisplayName("Configuración válida: Arma LETAL en turno NOCHE")
  void testConfiguracionValidaArmaLetalNoche() {
    assertTrue(vigilanteNoche.esConfiguracionValida(),
        "Arma LETAL en turno NOCHE debería ser válida");
  }

  @Test
  @DisplayName("Configuración INVÁLIDA: Arma LETAL en turno DÍA")
  void testConfiguracionInvalidaArmaLetalDia() {
    Vigilante vigilanteInvalido = new Vigilante("Juan Pérez", "1003",
        Turno.DIA, 1500000, 103, TipoArma.LETAL);

    assertFalse(vigilanteInvalido.esConfiguracionValida(),
        "Arma LETAL en turno DÍA NO debería ser válida");
  }

  // ═══════════════ TESTS DE CAMBIO DE TURNO ═══════════════

  @Test
  @DisplayName("Cambio de turno exitoso cuando la configuración es válida")
  void testCambioTurnoExitoso() {
    // Vigilante con NO_LETAL puede cambiar de DIA a NOCHE
    boolean resultado = vigilanteDia.cambiarTurnoConValidacion(Turno.NOCHE);

    assertTrue(resultado, "El cambio de turno debería ser exitoso");
    assertEquals(Turno.NOCHE, vigilanteDia.getTurno(),
        "El turno debería haberse actualizado a NOCHE");
  }

  @Test
  @DisplayName("Cambio de turno rechazado cuando la configuración es inválida")
  void testCambioTurnoRechazado() {
    // Vigilante con LETAL no puede cambiar de NOCHE a DIA
    Turno turnoOriginal = vigilanteNoche.getTurno();
    boolean resultado = vigilanteNoche.cambiarTurnoConValidacion(Turno.DIA);

    assertFalse(resultado, "El cambio de turno debería ser rechazado");
    assertEquals(turnoOriginal, vigilanteNoche.getTurno(),
        "El turno NO debería haber cambiado");
  }

  // ═══════════════ TESTS DE SALARIO ═══════════════

  @Test
  @DisplayName("Calcular salario con bono por arma LETAL (15%)")
  void testSalarioConBonoArmaLetal() {
    double salarioBase = 1500000;
    double bonoEsperado = salarioBase * 0.15; // 15% por arma letal
    double salarioEsperado = salarioBase + bonoEsperado;

    double salarioCalculado = vigilanteNoche.calcularSalarioTotal();

    assertEquals(salarioEsperado, salarioCalculado, 0.01,
        "El salario con arma LETAL debería incluir bono del 15%");
  }

  @Test
  @DisplayName("Calcular salario con bono por arma NO_LETAL (5%)")
  void testSalarioConBonoArmaNoLetal() {
    double salarioBase = 1500000;
    double bonoEsperado = salarioBase * 0.05; // 5% por arma no letal
    double salarioEsperado = salarioBase + bonoEsperado;

    double salarioCalculado = vigilanteDia.calcularSalarioTotal();

    assertEquals(salarioEsperado, salarioCalculado, 0.01,
        "El salario con arma NO_LETAL debería incluir bono del 5%");
  }

  @Test
  @DisplayName("Calcular salario con horas extras positivas")
  void testSalarioConHorasExtras() {
    double salarioSinExtras = vigilanteDia.calcularSalarioTotal();
    double salarioConExtras = vigilanteDia.calcularSalarioConHorasExtras(20);

    // assertNotEquals - verifica que dos valores NO sean iguales
    assertNotEquals(salarioSinExtras, salarioConExtras,
        "El salario con horas extras debe ser diferente al salario base");

    assertTrue(salarioConExtras > salarioSinExtras,
        "El salario con horas extras debe ser mayor al salario base");
  }

  @Test
  @DisplayName("Calcular salario con horas extras negativas (validado a 0)")
  void testSalarioConHorasExtrasNegativas() {
    double salarioEsperado = vigilanteDia.calcularSalarioTotal();
    double salarioConExtrasNegativas = vigilanteDia.calcularSalarioConHorasExtras(-10);

    assertEquals(salarioEsperado, salarioConExtrasNegativas, 0.01,
        "Las horas extras negativas deberían validarse a 0");
  }

  // ═══════════════ TESTS DE EQUIPOS ═══════════════

  @Test
  @DisplayName("Asignar equipo y verificar que no es null")
  void testAsignarEquipo() {
    vigilanteDia.asignarEquipo(radio);

    List<Equipo> equipos = vigilanteDia.getEquiposAsignados();

    // assertNotNull - verifica que el objeto no sea null
    assertNotNull(equipos, "La lista de equipos no debería ser null");
    assertEquals(1, equipos.size(), "Debería haber 1 equipo asignado");
    assertTrue(equipos.contains(radio), "La lista debería contener el radio");
  }

  @Test
  @DisplayName("Retirar equipo existente retorna true")
  void testRetirarEquipoExistente() {
    vigilanteDia.asignarEquipo(radio);

    boolean resultado = vigilanteDia.retirarEquipoPorCodigo("R001");

    assertTrue(resultado, "Retirar un equipo existente debería retornar true");
    assertEquals(0, vigilanteDia.getEquiposAsignados().size(),
        "No debería quedar ningún equipo asignado");
  }

  @Test
  @DisplayName("Retirar equipo inexistente retorna false")
  void testRetirarEquipoInexistente() {
    boolean resultado = vigilanteDia.retirarEquipoPorCodigo("INEXISTENTE");

    assertFalse(resultado, "Retirar un equipo inexistente debería retornar false");
  }

  @Test
  @DisplayName("Calcular valor de equipos por tipo RADIO")
  void testCalcularValorEquiposPorTipoRadio() {
    vigilanteDia.asignarEquipo(radio);
    vigilanteDia.asignarEquipo(arma);

    double valorRadios = vigilanteDia.calcularValorEquiposPorTipo(TipoEquipo.RADIO);

    assertEquals(500000, valorRadios, 0.01,
        "El valor de los radios debería ser 500,000");
  }

  @Test
  @DisplayName("Calcular valor de equipos por tipo ARMA")
  void testCalcularValorEquiposPorTipoArma() {
    vigilanteDia.asignarEquipo(radio);
    vigilanteDia.asignarEquipo(arma);

    double valorArmas = vigilanteDia.calcularValorEquiposPorTipo(TipoEquipo.ARMA);

    assertEquals(3000000, valorArmas, 0.01,
        "El valor de las armas debería ser 3,000,000");
  }

  // ═══════════════ TESTS DE AGENDA ═══════════════

  @Test
  @DisplayName("Programar evento en agenda")
  void testProgramarEvento() {
    LocalDate fecha = LocalDate.now();
    vigilanteDia.programar(fecha, "Entrenamiento de seguridad");

    List<AgendaItem> agenda = vigilanteDia.obtenerAgenda(
        fecha.minusDays(1),
        fecha.plusDays(1));

    assertEquals(1, agenda.size(), "Debería haber 1 evento en la agenda");
  }

  @Test
  @DisplayName("Filtrar agenda por palabra clave")
  void testFiltrarAgendaPorPalabra() {
    vigilanteDia.programar(LocalDate.now(), "Revisión de equipos");
    vigilanteDia.programar(LocalDate.now().plusDays(1), "Patrullaje nocturno");
    vigilanteDia.programar(LocalDate.now().plusDays(2), "Mantenimiento de equipos");

    List<AgendaItem> filtrados = vigilanteDia.filtrarAgendaPorPalabra("equipos");

    assertEquals(2, filtrados.size(),
        "Debería encontrar 2 eventos que contengan 'equipos'");
  }

  // ═══════════════ TESTS DE REPORTES ═══════════════

  @Test
  @DisplayName("Generar reporte no retorna null")
  void testGenerarReporteNoNull() {
    String reporte = vigilanteDia.generarReporte();

    assertNotNull(reporte, "El reporte no debería ser null");
    assertTrue(reporte.contains("VIGILANTE"),
        "El reporte debería contener la palabra VIGILANTE");
    assertTrue(reporte.contains(vigilanteDia.getNombre()),
        "El reporte debería contener el nombre del vigilante");
  }

  // ═══════════════ TEST DESHABILITADO ═══════════════

  @Test
  @Disabled("Test en desarrollo - pendiente de implementar validación adicional")
  @DisplayName("Validar límite máximo de equipos asignados")
  void testLimiteMaximoEquipos() {
    // Este test está deshabilitado temporalmente
    // Se implementará cuando se defina el límite máximo de equipos
    fail("Test no implementado aún");
  }
}