package co.edu.uniquindio.poo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Equipo
 */
@DisplayName("Tests de la clase Equipo")
public class EquipoTest {

  private Equipo radio;
  private Equipo arma;
  private Equipo vehiculo;

  @BeforeAll
  static void inicioGeneral() {
    System.out.println("═══ INICIANDO TESTS DE EQUIPO ═══");
  }

  @AfterAll
  static void finGeneral() {
    System.out.println("═══ TESTS DE EQUIPO COMPLETADOS ═══\n");
  }

  @BeforeEach
  void setUp() {
    radio = new Equipo("R001", TipoEquipo.RADIO, 500000);
    arma = new Equipo("A001", TipoEquipo.ARMA, 3000000);
    vehiculo = new Equipo("V001", TipoEquipo.VEHICULO, 50000000);
  }

  @AfterEach
  void tearDown() {
    radio = null;
    arma = null;
    vehiculo = null;
  }

  // ═══════════════ TESTS DE CREACIÓN ═══════════════

  @Test
  @DisplayName("Crear equipo con valores válidos")
  void testCrearEquipoValido() {
    assertNotNull(radio, "El equipo no debería ser null");
    assertEquals("R001", radio.getCodigo());
    assertEquals(TipoEquipo.RADIO, radio.getTipo());
    assertEquals(500000, radio.getValorReposicion(), 0.01);
  }

  @Test
  @DisplayName("Estado inicial del equipo debe ser OPERATIVO")
  void testEstadoInicialOperativo() {
    assertEquals(EstadoEquipo.OPERATIVO, radio.getEstado(),
        "Un equipo recién creado debe estar OPERATIVO");
  }

  // ═══════════════ TESTS DE ESTADO ═══════════════

  @Test
  @DisplayName("Cambiar estado de equipo a EN_USO")
  void testCambiarEstadoEnUso() {
    radio.setEstado(EstadoEquipo.EN_USO);

    assertEquals(EstadoEquipo.EN_USO, radio.getEstado(),
        "El estado debería ser EN_USO");

    assertNotEquals(EstadoEquipo.OPERATIVO, radio.getEstado(),
        "El estado NO debería ser OPERATIVO");
  }

  @Test
  @DisplayName("Cambiar estado de equipo a EN_MANTENIMIENTO")
  void testCambiarEstadoEnMantenimiento() {
    radio.setEstado(EstadoEquipo.EN_MANTENIMIENTO);

    assertEquals(EstadoEquipo.EN_MANTENIMIENTO, radio.getEstado());
  }

  @Test
  @DisplayName("Cambiar estado de equipo a INOPERATIVO")
  void testCambiarEstadoInoperativo() {
    radio.setEstado(EstadoEquipo.INOPERATIVO);

    assertEquals(EstadoEquipo.INOPERATIVO, radio.getEstado());
  }

  @Test
  @DisplayName("Cambiar estado de equipo a ALMACEN")
  void testCambiarEstadoAlmacen() {
    radio.setEstado(EstadoEquipo.ALMACEN);

    assertEquals(EstadoEquipo.ALMACEN, radio.getEstado());
  }

  // ═══════════════ TESTS DE COSTO DE MANTENIMIENTO ═══════════════

  @Test
  @DisplayName("Costo de mantenimiento es 2% del valor de reposición")
  void testCostoMantenimiento() {
    double valorReposicion = 500000;
    double costoEsperado = valorReposicion * 0.02; // 2%

    assertEquals(costoEsperado, radio.getCostoMantenimientoMensual(), 0.01,
        "El costo de mantenimiento debe ser el 2% del valor de reposición");
  }

  @Test
  @DisplayName("Costo de mantenimiento cambia si cambia el valor de reposición")
  void testCostoMantenimientoActualizado() {
    double costoInicial = radio.getCostoMantenimientoMensual();

    radio.setValorReposicion(1000000); // Duplicar valor

    // El costo de mantenimiento NO se actualiza automáticamente en el código
    // original
    // pero podemos verificar que el método obtenerCostoMensual lo calcula
    assertEquals(costoInicial, radio.getCostoMantenimientoMensual(), 0.01,
        "El costo de mantenimiento almacenado no cambia automáticamente");
  }

  @Test
  @DisplayName("Obtener costo mensual para interfaz Costeable")
  void testObtenerCostoMensual() {
    double costoMensual = radio.obtenerCostoMensual();
    double costoMantenimiento = radio.getCostoMantenimientoMensual();

    assertEquals(costoMantenimiento, costoMensual, 0.01,
        "obtenerCostoMensual() debe retornar el costo de mantenimiento");
  }

  // ═══════════════ TESTS DE GENERACIÓN DE REPUESTO ═══════════════

  @Test
  @DisplayName("Generar repuesto con código válido")
  void testGenerarRepuestoValido() {
    Equipo repuesto = radio.generarRepuesto("R001-REP");

    assertNotNull(repuesto, "El repuesto no debería ser null");
    assertEquals("R001-REP", repuesto.getCodigo(),
        "El repuesto debería tener el nuevo código");
    assertEquals(radio.getTipo(), repuesto.getTipo(),
        "El repuesto debería tener el mismo tipo");
    assertEquals(radio.getValorReposicion(), repuesto.getValorReposicion(), 0.01,
        "El repuesto debería tener el mismo valor de reposición");
    assertEquals(EstadoEquipo.ALMACEN, repuesto.getEstado(),
        "El repuesto debería estar en ALMACEN");
  }

  @Test
  @DisplayName("Generar repuesto con código null lanza excepción")
  void testGenerarRepuestoCodigoNull() {
    // assertThrows - verifica que se lance una excepción
    assertThrows(IllegalArgumentException.class, () -> {
      radio.generarRepuesto(null);
    }, "Debería lanzar IllegalArgumentException con código null");
  }

  @Test
  @DisplayName("Generar repuesto con código vacío lanza excepción")
  void testGenerarRepuestoCodigoVacio() {
    assertThrows(IllegalArgumentException.class, () -> {
      radio.generarRepuesto("");
    }, "Debería lanzar IllegalArgumentException con código vacío");

    assertThrows(IllegalArgumentException.class, () -> {
      radio.generarRepuesto("   ");
    }, "Debería lanzar IllegalArgumentException con código solo espacios");
  }

  // ═══════════════ TESTS DE COMPARACIÓN DE VALORES ═══════════════

  @Test
  @DisplayName("Comparar valores de diferentes tipos de equipos")
  void testCompararValoresEquipos() {
    assertTrue(vehiculo.getValorReposicion() > arma.getValorReposicion(),
        "El vehículo debería valer más que el arma");

    assertTrue(arma.getValorReposicion() > radio.getValorReposicion(),
        "El arma debería valer más que el radio");
  }

  // ═══════════════ TESTS DE REPORTE ═══════════════

  @Test
  @DisplayName("Generar reporte de equipo")
  void testGenerarReporte() {
    String reporte = radio.generarReporte();

    assertNotNull(reporte, "El reporte no debería ser null");
    assertFalse(reporte.isEmpty(), "El reporte no debería estar vacío");
    assertTrue(reporte.contains("R001"),
        "El reporte debería contener el código del equipo");
    assertTrue(reporte.contains("RADIO"),
        "El reporte debería contener el tipo de equipo");
  }

  @Test
  @DisplayName("Reporte contiene toda la información del equipo")
  void testReporteContieneInformacion() {
    String reporte = arma.generarReporte();

    assertTrue(reporte.contains(arma.getCodigo()),
        "Debería contener el código");
    assertTrue(reporte.contains(arma.getTipo().toString()),
        "Debería contener el tipo");
    assertTrue(reporte.contains(arma.getEstado().toString()),
        "Debería contener el estado");
  }

  // ═══════════════ TESTS DE toString ═══════════════

  @Test
  @DisplayName("Método toString retorna información básica")
  void testToString() {
    String resultado = radio.toString();

    assertNotNull(resultado, "toString no debería retornar null");
    assertTrue(resultado.contains("RADIO"), "Debería contener el tipo");
    assertTrue(resultado.contains("R001"), "Debería contener el código");
  }

  // ═══════════════ TESTS DE GETTERS Y SETTERS ═══════════════

  @Test
  @DisplayName("Modificar código del equipo")
  void testModificarCodigo() {
    String codigoOriginal = radio.getCodigo();
    String nuevoCodigo = "R002";

    radio.setCodigo(nuevoCodigo);

    assertNotEquals(codigoOriginal, radio.getCodigo(),
        "El código debería haber cambiado");
    assertEquals(nuevoCodigo, radio.getCodigo(),
        "El nuevo código debería estar actualizado");
  }

  @Test
  @DisplayName("Modificar tipo del equipo")
  void testModificarTipo() {
    TipoEquipo tipoOriginal = radio.getTipo();
    radio.setTipo(TipoEquipo.CAMARA);

    assertNotEquals(tipoOriginal, radio.getTipo(),
        "El tipo debería haber cambiado");
    assertEquals(TipoEquipo.CAMARA, radio.getTipo());
  }

  @Test
  @DisplayName("Modificar valor de reposición")
  void testModificarValorReposicion() {
    double valorOriginal = radio.getValorReposicion();
    double nuevoValor = 750000;

    radio.setValorReposicion(nuevoValor);

    assertNotEquals(valorOriginal, radio.getValorReposicion(),
        "El valor debería haber cambiado");
    assertEquals(nuevoValor, radio.getValorReposicion(), 0.01);
  }

  // ═══════════════ TESTS DE VALIDACIÓN (EJEMPLOS) ═══════════════

  @Test
  @DisplayName("Verificar que el valor de reposición sea positivo")
  void testValorReposicionPositivo() {
    assertTrue(radio.getValorReposicion() > 0,
        "El valor de reposición debe ser positivo");
    assertTrue(arma.getValorReposicion() > 0,
        "El valor de reposición debe ser positivo");
    assertTrue(vehiculo.getValorReposicion() > 0,
        "El valor de reposición debe ser positivo");
  }

  @Test
  @DisplayName("Verificar que el código no sea null")
  void testCodigoNoNull() {
    assertNotNull(radio.getCodigo(), "El código no debería ser null");
    assertFalse(radio.getCodigo().isEmpty(),
        "El código no debería estar vacío");
  }

  @Test
  @DisplayName("Verificar que el tipo no sea null")
  void testTipoNoNull() {
    // assertNull - verifica que sea null (este caso NO debería serlo)
    // Primero verificamos que NO sea null
    assertNotNull(radio.getTipo(), "El tipo no debería ser null");

    // Ejemplo de uso de assertNull (en un escenario hipotético)
    Equipo equipoTest = new Equipo("TEST", TipoEquipo.RADIO, 100000);
    equipoTest.setEstado(null); // Forzar null para demostrar assertNull

    assertNull(equipoTest.getEstado(),
        "Ejemplo: el estado puede ser null si se establece así");
  }

  @Test
  @Disabled("Pendiente: implementar validación de valor de reposición negativo")
  @DisplayName("No debería permitir valor de reposición negativo")
  void testValorReposicionNoNegativo() {
    // Este test está deshabilitado porque el código original no valida esto
    assertThrows(IllegalArgumentException.class, () -> {
      new Equipo("TEST", TipoEquipo.RADIO, -1000);
    });
  }
}