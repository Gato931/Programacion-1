package co.edu.uniquindio.poo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uniquindio.poo.model.*;

/**
 * Universidad del Quindío
 */
public class MonederoVirtualTest {

  private SistemaMonedero sistema;
  private Cliente cliente1;
  private Cliente cliente2;

  @BeforeEach
  void setUp() {
    // Reiniciar sistema antes de cada test
    SistemaMonedero.setInstancia(null);
    sistema = SistemaMonedero.getInstance();

    cliente1 = sistema.registrarCliente("Juan Perez", "juan@test.com", "3001234567");
    cliente2 = sistema.registrarCliente("Maria Garcia", "maria@test.com", "3007654321");
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: CLIENTE
  // ═══════════════════════════════════════════════════════════

  @Test
  void testClienteRegistroYDatosBasicos() {
    assertEquals("Juan Perez", cliente1.getNombre());
    assertEquals("juan@test.com", cliente1.getEmail());
    assertEquals(RangoCliente.BRONCE, cliente1.getRangoActual());
    assertEquals(0, cliente1.getPuntosAcumulados());
    assertTrue(cliente1.isActivo());
  }

  @Test
  void testClienteAcumulacionPuntosYRango() {
    cliente1.acumularPuntos(600);
    assertEquals(600, cliente1.getPuntosAcumulados());
    assertEquals(RangoCliente.PLATA, cliente1.getRangoActual());

    cliente1.acumularPuntos(500);
    assertEquals(1100, cliente1.getPuntosAcumulados());
    assertEquals(RangoCliente.ORO, cliente1.getRangoActual());
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: MONEDERO
  // ═══════════════════════════════════════════════════════════

  @Test
  void testMonederoCreacionYSaldoInicial() {
    Monedero monedero = cliente1.getMonederoPrincipal();

    assertNotNull(monedero);
    assertEquals(TipoMonedero.PRINCIPAL, monedero.getTipo());
    assertEquals(0.0, monedero.getSaldo());
    assertTrue(monedero.isActivo());
    assertEquals(cliente1, monedero.getPropietario());
  }

  @Test
  void testMonederoMultiples() {
    cliente1.crearMonedero(TipoMonedero.AHORRO);
    cliente1.crearMonedero(TipoMonedero.GASTOS_DIARIOS);

    assertEquals(3, cliente1.getMonederos().size());
    assertTrue(cliente1.obtenerMonedero(TipoMonedero.AHORRO).isPresent());
    assertTrue(cliente1.obtenerMonedero(TipoMonedero.GASTOS_DIARIOS).isPresent());
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: DEPOSITO
  // ═══════════════════════════════════════════════════════════

  @Test
  void testDepositoExitoso() {
    boolean resultado = sistema.realizarDeposito(
        cliente1, TipoMonedero.PRINCIPAL, 10000, "Deposito inicial");

    assertTrue(resultado);
    assertEquals(10000, cliente1.getMonederoPrincipal().getSaldo());
    assertEquals(1, cliente1.getPuntosAcumulados()); // 1 punto por 10,000
  }

  @Test
  void testDepositoInvalido() {
    boolean resultado = sistema.realizarDeposito(
        cliente1, TipoMonedero.PRINCIPAL, -5000, "Deposito negativo");

    assertFalse(resultado);
    assertEquals(0, cliente1.getMonederoPrincipal().getSaldo());
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: RETIRO
  // ═══════════════════════════════════════════════════════════

  @Test
  void testRetiroExitoso() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 50000, "Setup");

    boolean resultado = sistema.realizarRetiro(
        cliente1, TipoMonedero.PRINCIPAL, 20000, "Retiro test");

    assertTrue(resultado);
    assertEquals(30000, cliente1.getMonederoPrincipal().getSaldo());
    assertTrue(cliente1.getPuntosAcumulados() >= 5); // 5 pts deposito + 4 pts retiro
  }

  @Test
  void testRetiroSaldoInsuficiente() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 10000, "Setup");

    boolean resultado = sistema.realizarRetiro(
        cliente1, TipoMonedero.PRINCIPAL, 20000, "Retiro excesivo");

    assertFalse(resultado);
    assertEquals(10000, cliente1.getMonederoPrincipal().getSaldo());
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: TRANSFERENCIA
  // ═══════════════════════════════════════════════════════════

  @Test
  void testTransferenciaExitosa() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Setup");

    boolean resultado = sistema.realizarTransferencia(
        cliente1, TipoMonedero.PRINCIPAL,
        cliente2, TipoMonedero.PRINCIPAL,
        50000, "Transferencia test");

    assertTrue(resultado);
    assertTrue(cliente1.getMonederoPrincipal().getSaldo() < 50000); // Menos por comisión
    assertEquals(50000, cliente2.getMonederoPrincipal().getSaldo());
  }

  @Test
  void testTransferenciaInvalidaMismoCliente() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 50000, "Setup");

    boolean resultado = sistema.realizarTransferencia(
        cliente1, TipoMonedero.PRINCIPAL,
        cliente1, TipoMonedero.AHORRO,
        10000, "Transferencia invalida");

    assertFalse(resultado);
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: SISTEMA DE PUNTOS
  // ═══════════════════════════════════════════════════════════

  @Test
  void testCalculoPuntosDeposito() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Test puntos");

    // 100,000 / 10,000 = 10 unidades → 10 * 1 punto = 10 puntos
    assertEquals(10, cliente1.getPuntosAcumulados());
  }

  @Test
  void testCalculoPuntosRetiro() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Setup");
    int puntosInicio = cliente1.getPuntosAcumulados();

    sistema.realizarRetiro(cliente1, TipoMonedero.PRINCIPAL, 50000, "Test puntos");

    // 50,000 / 10,000 = 5 unidades → 5 * 2 puntos = 10 puntos adicionales
    assertEquals(puntosInicio + 10, cliente1.getPuntosAcumulados());
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: RANGOS DE CLIENTES
  // ═══════════════════════════════════════════════════════════

  @Test
  void testRangosBroncePlata() {
    assertEquals(RangoCliente.BRONCE, RangoCliente.obtenerRangoPorPuntos(0));
    assertEquals(RangoCliente.BRONCE, RangoCliente.obtenerRangoPorPuntos(500));
    assertEquals(RangoCliente.PLATA, RangoCliente.obtenerRangoPorPuntos(501));
    assertEquals(RangoCliente.PLATA, RangoCliente.obtenerRangoPorPuntos(1000));
  }

  @Test
  void testRangosOroPlatino() {
    assertEquals(RangoCliente.ORO, RangoCliente.obtenerRangoPorPuntos(1001));
    assertEquals(RangoCliente.ORO, RangoCliente.obtenerRangoPorPuntos(5000));
    assertEquals(RangoCliente.PLATINO, RangoCliente.obtenerRangoPorPuntos(5001));
    assertEquals(RangoCliente.PLATINO, RangoCliente.obtenerRangoPorPuntos(10000));
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: SISTEMA DE BENEFICIOS
  // ═══════════════════════════════════════════════════════════

  @Test
  void testCanjeBeneficioExitoso() {
    cliente1.acumularPuntos(150);

    Beneficio beneficio = sistema.obtenerBeneficiosCanjeables(cliente1).get(0);
    boolean canjeado = sistema.canjearBeneficio(cliente1, beneficio.getId());

    assertTrue(canjeado);
    assertTrue(cliente1.getPuntosAcumulados() < 150);
  }

  @Test
  void testCanjeBeneficioSinPuntos() {
    // Cliente sin puntos no puede canjear
    assertTrue(sistema.obtenerBeneficiosCanjeables(cliente1).isEmpty());
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: TRANSACCIONES PROGRAMADAS
  // ═══════════════════════════════════════════════════════════

  @Test
  void testCreacionTransaccionProgramada() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Setup");

    TransaccionProgramada programada = sistema.programarTransaccion(
        TipoTransaccion.RETIRO,
        5000,
        "Retiro programado",
        cliente1,
        TipoMonedero.PRINCIPAL,
        null,
        null,
        java.time.LocalDateTime.now().plusDays(1),
        Periodicidad.MENSUAL);

    assertNotNull(programada);
    assertTrue(programada.isActiva());
    assertEquals(5000, programada.getMonto());
  }

  @Test
  void testListarTransaccionesProgramadas() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Setup");

    sistema.programarTransaccion(
        TipoTransaccion.RETIRO, 5000, "Test 1",
        cliente1, TipoMonedero.PRINCIPAL, null, null,
        java.time.LocalDateTime.now().plusDays(1), Periodicidad.DIARIA);

    sistema.programarTransaccion(
        TipoTransaccion.RETIRO, 3000, "Test 2",
        cliente1, TipoMonedero.PRINCIPAL, null, null,
        java.time.LocalDateTime.now().plusDays(2), Periodicidad.SEMANAL);

    assertEquals(2, sistema.obtenerTransaccionesProgramadasCliente(cliente1).size());
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: ANÁLISIS DE PATRONES
  // ═══════════════════════════════════════════════════════════

  @Test
  void testEstadisticasGasto() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Setup");
    sistema.realizarRetiro(cliente1, TipoMonedero.PRINCIPAL, 20000, "Gasto 1");
    sistema.realizarRetiro(cliente1, TipoMonedero.PRINCIPAL, 30000, "Gasto 2");

    AnalizadorPatrones.EstadisticasGasto stats = AnalizadorPatrones.analizarGastos(cliente1);

    assertTrue(stats.getTotalGastado() > 0);
    assertTrue(stats.getCantidadTransacciones() >= 3);
  }

  @Test
  void testDeteccionPatronesInusuales() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 1000000, "Setup");

    // Realizar muchas transacciones pequeñas
    for (int i = 0; i < 25; i++) {
      sistema.realizarRetiro(cliente1, TipoMonedero.PRINCIPAL, 1000, "Test " + i);
    }

    var alertas = sistema.detectarPatronesInusuales(cliente1);
    assertFalse(alertas.isEmpty()); // Debería detectar alta actividad
  }

  // ═══════════════════════════════════════════════════════════
  // TESTS: VALIDACIONES
  // ═══════════════════════════════════════════════════════════

  @Test
  void testValidacionEmailDuplicado() {
    assertThrows(IllegalArgumentException.class, () -> {
      sistema.registrarCliente("Pedro Lopez", "juan@test.com", "3009876543");
    });
  }

  @Test
  void testValidacionMontoNegativo() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Deposito(-1, "Test", cliente1.getMonederoPrincipal());
    });
  }
}