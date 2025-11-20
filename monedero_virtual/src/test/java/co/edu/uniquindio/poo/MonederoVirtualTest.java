package co.edu.uniquindio.poo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uniquindio.poo.model.*;

public class MonederoVirtualTest {

  private SistemaMonedero sistema;
  private Cliente cliente1;
  private Cliente cliente2;

  /**
   * Configuracion inicial antes de cada test.
   * Reinicia el sistema y crea dos clientes de prueba.
   */
  @BeforeEach
  void setUp() {
    SistemaMonedero.setInstancia(null);
    sistema = SistemaMonedero.getInstance();

    cliente1 = sistema.registrarCliente("Juan Perez", "juan@test.com", "3001234567");
    cliente2 = sistema.registrarCliente("Maria Garcia", "maria@test.com", "3007654321");
  }

  // TESTS: CLIENTE

  /**
   * Verifica que un cliente se registre correctamente con todos sus datos
   * basicos: nombre, email, rango inicial (BRONCE), puntos iniciales (0)
   * y estado activo.
   */
  @Test
  void testClienteRegistroYDatosBasicos() {
    assertEquals("Juan Perez", cliente1.getNombre());
    assertEquals("juan@test.com", cliente1.getEmail());
    assertEquals(RangoCliente.BRONCE, cliente1.getRangoActual());
    assertEquals(0, cliente1.getPuntosAcumulados());
    assertTrue(cliente1.isActivo());
  }

  /**
   * Verifica que al acumular puntos, el cliente cambie automaticamente
   * de rango segun los umbrales definidos (BRONCE -> PLATA -> ORO).
   */
  @Test
  void testClienteAcumulacionPuntosYRango() {
    cliente1.acumularPuntos(600);
    assertEquals(600, cliente1.getPuntosAcumulados());
    assertEquals(RangoCliente.PLATA, cliente1.getRangoActual());

    cliente1.acumularPuntos(500);
    assertEquals(1100, cliente1.getPuntosAcumulados());
    assertEquals(RangoCliente.ORO, cliente1.getRangoActual());
  }

  // TESTS: MONEDERO

  /**
   * Verifica que al registrar un cliente se cree automaticamente su
   * monedero principal con saldo inicial cero y estado activo.
   */
  @Test
  void testMonederoCreacionYSaldoInicial() {
    Monedero monedero = cliente1.getMonederoPrincipal();

    assertNotNull(monedero);
    assertEquals(TipoMonedero.PRINCIPAL, monedero.getTipo());
    assertEquals(0.0, monedero.getSaldo());
    assertTrue(monedero.isActivo());
    assertEquals(cliente1, monedero.getPropietario());
  }

  /**
   * Verifica que un cliente pueda crear multiples monederos de
   * diferentes tipos (AHORRO, GASTOS_DIARIOS) ademas del principal.
   */
  @Test
  void testMonederoMultiples() {
    cliente1.crearMonedero(TipoMonedero.AHORRO);
    cliente1.crearMonedero(TipoMonedero.GASTOS_DIARIOS);

    assertEquals(3, cliente1.getMonederos().size());
    assertTrue(cliente1.obtenerMonedero(TipoMonedero.AHORRO).isPresent());
    assertTrue(cliente1.obtenerMonedero(TipoMonedero.GASTOS_DIARIOS).isPresent());
  }

  // TESTS: DEPOSITO

  /**
   * Verifica que un deposito valido se ejecute correctamente,
   * aumentando el saldo del monedero y generando puntos para el cliente.
   * Se espera 1 punto por cada 10,000 unidades depositadas.
   */
  @Test
  void testDepositoExitoso() {
    boolean resultado = sistema.realizarDeposito(
        cliente1, TipoMonedero.PRINCIPAL, 10000, "Deposito inicial");

    assertTrue(resultado);
    assertEquals(10000, cliente1.getMonederoPrincipal().getSaldo());
    assertEquals(1, cliente1.getPuntosAcumulados());
  }

  /**
   * Verifica que un deposito con monto negativo sea rechazado
   * y no modifique el saldo del monedero.
   */
  @Test
  void testDepositoInvalido() {
    boolean resultado = sistema.realizarDeposito(
        cliente1, TipoMonedero.PRINCIPAL, -5000, "Deposito negativo");

    assertFalse(resultado);
    assertEquals(0, cliente1.getMonederoPrincipal().getSaldo());
  }

  // TESTS: RETIRO

  /**
   * Verifica que un retiro valido se ejecute correctamente cuando hay
   * saldo suficiente, disminuyendo el saldo y generando puntos.
   * Se esperan 2 puntos por cada 10,000 unidades retiradas.
   */
  @Test
  void testRetiroExitoso() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 50000, "Setup");

    boolean resultado = sistema.realizarRetiro(
        cliente1, TipoMonedero.PRINCIPAL, 20000, "Retiro test");

    assertTrue(resultado);
    assertEquals(30000, cliente1.getMonederoPrincipal().getSaldo());
    assertTrue(cliente1.getPuntosAcumulados() >= 5);
  }

  /**
   * Verifica que un retiro sea rechazado cuando el saldo disponible
   * es insuficiente, manteniendo el saldo sin cambios.
   */
  @Test
  void testRetiroSaldoInsuficiente() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 10000, "Setup");

    boolean resultado = sistema.realizarRetiro(
        cliente1, TipoMonedero.PRINCIPAL, 20000, "Retiro excesivo");

    assertFalse(resultado);
    assertEquals(10000, cliente1.getMonederoPrincipal().getSaldo());
  }

  // TESTS: TRANSFERENCIA

  /**
   * Verifica que una transferencia entre dos clientes diferentes se ejecute
   * correctamente, descontando el monto mas la comision del origen y
   * acreditando el monto completo al destino.
   */
  @Test
  void testTransferenciaExitosa() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Setup");

    boolean resultado = sistema.realizarTransferencia(
        cliente1, TipoMonedero.PRINCIPAL,
        cliente2, TipoMonedero.PRINCIPAL,
        50000, "Transferencia test");

    assertTrue(resultado);
    assertTrue(cliente1.getMonederoPrincipal().getSaldo() < 50000);
    assertEquals(50000, cliente2.getMonederoPrincipal().getSaldo());
  }

  /**
   * Verifica que una transferencia entre monederos del mismo cliente
   * sea rechazada para evitar transferencias invalidas.
   */
  @Test
  void testTransferenciaInvalidaMismoCliente() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 50000, "Setup");

    boolean resultado = sistema.realizarTransferencia(
        cliente1, TipoMonedero.PRINCIPAL,
        cliente1, TipoMonedero.AHORRO,
        10000, "Transferencia invalida");

    assertFalse(resultado);
  }

  // TESTS: SISTEMA DE PUNTOS

  /**
   * Verifica que el calculo de puntos en depositos sea correcto.
   * Formula: 1 punto por cada 10,000 unidades depositadas.
   */
  @Test
  void testCalculoPuntosDeposito() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Test puntos");

    assertEquals(10, cliente1.getPuntosAcumulados());
  }

  /**
   * Verifica que el calculo de puntos en retiros sea correcto.
   * Formula: 2 puntos por cada 10,000 unidades retiradas.
   */
  @Test
  void testCalculoPuntosRetiro() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Setup");
    int puntosInicio = cliente1.getPuntosAcumulados();

    sistema.realizarRetiro(cliente1, TipoMonedero.PRINCIPAL, 50000, "Test puntos");

    assertEquals(puntosInicio + 10, cliente1.getPuntosAcumulados());
  }

  // TESTS: RANGOS DE CLIENTES

  /**
   * Verifica que la clasificacion de rangos BRONCE y PLATA funcione
   * correctamente segun los umbrales definidos (0-500 y 501-1000 puntos).
   */
  @Test
  void testRangosBroncePlata() {
    assertEquals(RangoCliente.BRONCE, RangoCliente.obtenerRangoPorPuntos(0));
    assertEquals(RangoCliente.BRONCE, RangoCliente.obtenerRangoPorPuntos(500));
    assertEquals(RangoCliente.PLATA, RangoCliente.obtenerRangoPorPuntos(501));
    assertEquals(RangoCliente.PLATA, RangoCliente.obtenerRangoPorPuntos(1000));
  }

  /**
   * Verifica que la clasificacion de rangos ORO y PLATINO funcione
   * correctamente segun los umbrales definidos (1001-5000 y mas de 5000 puntos).
   */
  @Test
  void testRangosOroPlatino() {
    assertEquals(RangoCliente.ORO, RangoCliente.obtenerRangoPorPuntos(1001));
    assertEquals(RangoCliente.ORO, RangoCliente.obtenerRangoPorPuntos(5000));
    assertEquals(RangoCliente.PLATINO, RangoCliente.obtenerRangoPorPuntos(5001));
    assertEquals(RangoCliente.PLATINO, RangoCliente.obtenerRangoPorPuntos(10000));
  }

  // TESTS: SISTEMA DE BENEFICIOS

  /**
   * Verifica que un cliente con suficientes puntos pueda canjear
   * un beneficio exitosamente, descontando los puntos correspondientes.
   */
  @Test
  void testCanjeBeneficioExitoso() {
    cliente1.acumularPuntos(150);

    Beneficio beneficio = sistema.obtenerBeneficiosCanjeables(cliente1).get(0);
    boolean canjeado = sistema.canjearBeneficio(cliente1, beneficio.getId());

    assertTrue(canjeado);
    assertTrue(cliente1.getPuntosAcumulados() < 150);
  }

  /**
   * Verifica que un cliente sin puntos suficientes no pueda canjear
   * ningun beneficio. La lista de beneficios canjeables debe estar vacia.
   */
  @Test
  void testCanjeBeneficioSinPuntos() {

    assertTrue(sistema.obtenerBeneficiosCanjeables(cliente1).isEmpty());
  }

  // TESTS: TRANSACCIONES PROGRAMADAS

  /**
   * Verifica que se pueda crear correctamente una transaccion programada
   * con todos sus atributos: monto, periodicidad, fecha de ejecucion y
   * estado activo.
   */
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

  /**
   * Verifica que el sistema mantenga un registro correcto de todas las
   * transacciones programadas activas de un cliente especifico.
   */
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

  // TESTS: ANÁLISIS DE PATRONES

  /**
   * Verifica que el analizador de patrones genere correctamente
   * estadisticas de gasto incluyendo total gastado y cantidad de
   * transacciones realizadas por el cliente.
   */
  @Test
  void testEstadisticasGasto() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 100000, "Setup");
    sistema.realizarRetiro(cliente1, TipoMonedero.PRINCIPAL, 20000, "Gasto 1");
    sistema.realizarRetiro(cliente1, TipoMonedero.PRINCIPAL, 30000, "Gasto 2");

    AnalizadorPatrones.EstadisticasGasto stats = AnalizadorPatrones.analizarGastos(cliente1);

    assertTrue(stats.getTotalGastado() > 0);
    assertTrue(stats.getCantidadTransacciones() >= 3);
  }

  /**
   * Verifica que el sistema detecte patrones de gasto inusuales,
   * como alta frecuencia de transacciones, y genere alertas correspondientes.
   */
  @Test
  void testDeteccionPatronesInusuales() {
    sistema.realizarDeposito(cliente1, TipoMonedero.PRINCIPAL, 1000000, "Setup");

    for (int i = 0; i < 25; i++) {
      sistema.realizarRetiro(cliente1, TipoMonedero.PRINCIPAL, 1000, "Test " + i);
    }

    var alertas = sistema.detectarPatronesInusuales(cliente1);
    assertFalse(alertas.isEmpty());
  }

  // TESTS: VALIDACIONES

  /**
   * Verifica que el sistema rechace el registro de un cliente con un
   * email que ya existe en el sistema, lanzando una excepcion apropiada.
   */
  @Test
  void testValidacionEmailDuplicado() {
    assertThrows(IllegalArgumentException.class, () -> {
      sistema.registrarCliente("Pedro Lopez", "juan@test.com", "3009876543");
    });
  }

  /**
   * Verifica que el sistema rechace la creacion de transacciones con
   * montos negativos, lanzando una excepcion de argumento invalido.
   */
  @Test
  void testValidacionMontoNegativo() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Deposito(-1, "Test", cliente1.getMonederoPrincipal());
    });
  }
}