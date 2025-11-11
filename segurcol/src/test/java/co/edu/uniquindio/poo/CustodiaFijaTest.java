package co.edu.uniquindio.poo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Pruebas unitarias para la clase CustodiaFija
 */
@DisplayName("Tests de la clase CustodiaFija")
public class CustodiaFijaTest {

    private CustodiaFija custodiaBasica;
    private CustodiaFija custodiaGrande;
    private Vigilante vigilante1;
    private Vigilante vigilante2;

    @BeforeAll
    static void setupInicial() {
        System.out.println("═══ INICIANDO TESTS DE CUSTODIA FIJA ═══");
    }

    @AfterAll
    static void limpiezaFinal() {
        System.out.println("═══ TESTS DE CUSTODIA FIJA COMPLETADOS ═══\n");
    }

    @BeforeEach
    void setUp() {
        custodiaBasica = new CustodiaFija("CF001", "Banco Nacional",
                5000000, 3, "Centro Comercial");

        custodiaGrande = new CustodiaFija("CF002", "Plaza Mayor",
                8000000, 8, "Centro Empresarial");

        vigilante1 = new Vigilante("Carlos Méndez", "1001", Turno.DIA,
                1500000, 101, TipoArma.NO_LETAL);

        vigilante2 = new Vigilante("Ana López", "1002", Turno.NOCHE,
                1500000, 102, TipoArma.LETAL);
    }

    @AfterEach
    void tearDown() {
        custodiaBasica = null;
        custodiaGrande = null;
        vigilante1 = null;
        vigilante2 = null;
    }

    // ═══════════════ TESTS DE COSTO MENSUAL ═══════════════

    @Test
    @DisplayName("Calcular costo mensual con 3 vigilantes (sin descuento)")
    void testCostoMensualSinDescuento() {
        // Fórmula: tarifa + (numVigilantes * 2,500,000)
        double costoEsperado = 5000000 + (3 * 2500000); // 12,500,000
        double costoCalculado = custodiaBasica.calcularCostoMensual();

        assertEquals(costoEsperado, costoCalculado, 0.01,
                "El costo con 3 vigilantes no debería tener descuento");
    }

    @Test
    @DisplayName("Calcular costo mensual con más de 5 vigilantes (con descuento 10%)")
    void testCostoMensualConDescuento() {
        // Fórmula: (tarifa + (numVigilantes * 2,500,000)) * 0.90
        double costoSinDescuento = 8000000 + (8 * 2500000); // 28,000,000
        double costoEsperado = costoSinDescuento * 0.90; // 25,200,000
        double costoCalculado = custodiaGrande.calcularCostoMensual();

        assertEquals(costoEsperado, costoCalculado, 0.01,
                "El costo con más de 5 vigilantes debería tener 10% de descuento");
    }

    @Test
    @DisplayName("Verificar que el costo nunca sea negativo")
    void testCostoNuncaNegativo() {
        double costo = custodiaBasica.calcularCostoMensual();

        assertTrue(costo > 0, "El costo mensual debe ser siempre positivo");
    }

    // ═══════════════ TESTS DE ESTADO ═══════════════

    @Test
    @DisplayName("Servicio recién creado debe estar ACTIVO")
    void testEstadoInicialActivo() {
        assertEquals(EstadoServicio.ACTIVO, custodiaBasica.getEstado(),
                "Un servicio recién creado debe estar ACTIVO");
    }

    @Test
    @DisplayName("Cambiar estado de servicio a SUSPENDIDO")
    void testCambiarEstadoSuspendido() {
        custodiaBasica.setEstado(EstadoServicio.SUSPENDIDO);

        assertNotEquals(EstadoServicio.ACTIVO, custodiaBasica.getEstado(),
                "El estado NO debería ser ACTIVO después de suspenderlo");

        assertEquals(EstadoServicio.SUSPENDIDO, custodiaBasica.getEstado(),
                "El estado debería ser SUSPENDIDO");
    }

    @Test
    @DisplayName("Cambiar estado de servicio a FINALIZADO")
    void testCambiarEstadoFinalizado() {
        custodiaBasica.setEstado(EstadoServicio.FINALIZADO);

        assertEquals(EstadoServicio.FINALIZADO, custodiaBasica.getEstado(),
                "El estado debería ser FINALIZADO");
    }

    // ═══════════════ TESTS DE PERSONAL ═══════════════

    @Test
    @DisplayName("Asignar personal al servicio")
    void testAsignarPersonal() {
        custodiaBasica.asignarPersonal(vigilante1);

        List<Empleado> personal = custodiaBasica.getPersonalAsignado();

        assertNotNull(personal, "La lista de personal no debería ser null");
        assertEquals(1, personal.size(), "Debería haber 1 empleado asignado");
        assertTrue(personal.contains(vigilante1),
                "La lista debería contener al vigilante asignado");
    }

    @Test
    @DisplayName("Remover personal del servicio")
    void testRemoverPersonal() {
        custodiaBasica.asignarPersonal(vigilante1);
        custodiaBasica.removerPersonal(vigilante1);

        assertEquals(0, custodiaBasica.getPersonalAsignado().size(),
                "No debería quedar personal asignado después de removerlo");
    }

    @Test
    @DisplayName("Asignar múltiples empleados al servicio")
    void testAsignarMultiplesEmpleados() {
        custodiaBasica.asignarPersonal(vigilante1);
        custodiaBasica.asignarPersonal(vigilante2);

        assertEquals(2, custodiaBasica.getPersonalAsignado().size(),
                "Deberían haber 2 empleados asignados");
    }

    // ═══════════════ TESTS DE REEMPLAZO DE VIGILANTE ═══════════════

    @Test
    @DisplayName("Reemplazar vigilante exitosamente")
    void testReemplazarVigilanteExitoso() {
        // Asignar vigilante original
        custodiaBasica.asignarPersonal(vigilante1);

        // Crear vigilante reemplazo con misma configuración
        Vigilante reemplazo = new Vigilante("Luis Torres", "1003", Turno.DIA,
                1500000, 103, TipoArma.NO_LETAL);

        // Programar agenda para el incapacitado
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(30);
        vigilante1.programar(inicio, "Custodia turno 1");
        vigilante1.programar(inicio.plusDays(7), "Custodia turno 2");

        // Reemplazar
        boolean resultado = custodiaBasica.reemplazarVigilante(
                vigilante1, reemplazo, inicio, fin);

        assertTrue(resultado, "El reemplazo debería ser exitoso");
        assertFalse(custodiaBasica.getPersonalAsignado().contains(vigilante1),
                "El vigilante original no debería estar en el servicio");
        assertTrue(custodiaBasica.getPersonalAsignado().contains(reemplazo),
                "El reemplazo debería estar en el servicio");
    }

    @Test
    @DisplayName("Reemplazo falla si configuración no coincide (diferente arma)")
    void testReemplazarVigilanteFallaArmaDistinta() {
        custodiaBasica.asignarPersonal(vigilante1); // NO_LETAL

        // Reemplazo con arma diferente
        Vigilante reemplazo = new Vigilante("Luis Torres", "1003", Turno.DIA,
                1500000, 103, TipoArma.LETAL);

        boolean resultado = custodiaBasica.reemplazarVigilante(
                vigilante1, reemplazo, LocalDate.now(), LocalDate.now().plusDays(30));

        assertFalse(resultado,
                "El reemplazo debería fallar si el tipo de arma es diferente");
    }

    @Test
    @DisplayName("Reemplazo falla si configuración no coincide (diferente turno)")
    void testReemplazarVigilanteFallaTurnoDistinto() {
        custodiaBasica.asignarPersonal(vigilante1); // DIA

        // Reemplazo con turno diferente
        Vigilante reemplazo = new Vigilante("Luis Torres", "1003", Turno.NOCHE,
                1500000, 103, TipoArma.NO_LETAL);

        boolean resultado = custodiaBasica.reemplazarVigilante(
                vigilante1, reemplazo, LocalDate.now(), LocalDate.now().plusDays(30));

        assertFalse(resultado,
                "El reemplazo debería fallar si el turno es diferente");
    }

    @Test
    @DisplayName("Reemplazo falla si vigilante no está asignado al servicio")
    void testReemplazarVigilanteNoAsignado() {
        // NO asignar vigilante1
        Vigilante reemplazo = new Vigilante("Luis Torres", "1003", Turno.DIA,
                1500000, 103, TipoArma.NO_LETAL);

        boolean resultado = custodiaBasica.reemplazarVigilante(
                vigilante1, reemplazo, LocalDate.now(), LocalDate.now().plusDays(30));

        assertFalse(resultado,
                "El reemplazo debería fallar si el vigilante no está asignado");
    }

    // ═══════════════ TESTS DE EQUIPOS ═══════════════

    @Test
    @DisplayName("Asignar equipo al servicio")
    void testAsignarEquipo() {
        Equipo radio = new Equipo("R001", TipoEquipo.RADIO, 500000);
        custodiaBasica.asignarEquipo(radio);

        List<Equipo> equipos = custodiaBasica.getEquiposAsignados();

        assertEquals(1, equipos.size(), "Debería haber 1 equipo asignado");
        assertTrue(equipos.contains(radio), "La lista debería contener el radio");
    }

    // ═══════════════ TESTS DE AGENDA ═══════════════

    @Test
    @DisplayName("Programar evento en agenda del servicio")
    void testProgramarEvento() {
        LocalDate fecha = LocalDate.now();
        custodiaBasica.programar(fecha, "Inspección de seguridad");

        List<AgendaItem> agenda = custodiaBasica.obtenerAgenda(
                fecha.minusDays(1), fecha.plusDays(1));

        assertEquals(1, agenda.size(), "Debería haber 1 evento programado");
    }

    @Test
    @DisplayName("Filtrar agenda por palabra clave")
    void testFiltrarAgenda() {
        custodiaBasica.programar(LocalDate.now(), "Mantenimiento de equipos");
        custodiaBasica.programar(LocalDate.now().plusDays(1), "Auditoría mensual");
        custodiaBasica.programar(LocalDate.now().plusDays(2), "Revisión de equipos");

        List<AgendaItem> filtrados = custodiaBasica.filtrarAgendaPorPalabra("equipos");

        assertEquals(2, filtrados.size(),
                "Debería encontrar 2 eventos con 'equipos'");
    }

    // ═══════════════ TESTS DE REPORTE ═══════════════

    @Test
    @DisplayName("Generar reporte del servicio")
    void testGenerarReporte() {
        String reporte = custodiaBasica.generarReporte();

        assertNotNull(reporte, "El reporte no debería ser null");
        assertTrue(reporte.contains("CustodiaFija"),
                "El reporte debería mencionar el tipo de servicio");
        assertTrue(reporte.contains(custodiaBasica.getCliente()),
                "El reporte debería incluir el nombre del cliente");
        assertTrue(reporte.contains(custodiaBasica.getCodigoContrato()),
                "El reporte debería incluir el código de contrato");
    }

    // ═══════════════ TESTS DE GETTERS/SETTERS ═══════════════

    @Test
    @DisplayName("Verificar getters básicos")
    void testGettersBasicos() {
        assertEquals("CF001", custodiaBasica.getCodigoContrato());
        assertEquals("Banco Nacional", custodiaBasica.getCliente());
        assertEquals(5000000, custodiaBasica.getTarifaMensual(), 0.01);
        assertEquals(3, custodiaBasica.getNumeroVigilantes());
        assertEquals("Centro Comercial", custodiaBasica.getUbicacion());
    }

    @Test
    @DisplayName("Modificar tarifa mensual")
    void testModificarTarifaMensual() {
        double tarifaOriginal = custodiaBasica.getTarifaMensual();
        double nuevaTarifa = 6000000;

        custodiaBasica.setTarifaMensual(nuevaTarifa);

        assertNotEquals(tarifaOriginal, custodiaBasica.getTarifaMensual(),
                "La tarifa debería haber cambiado");
        assertEquals(nuevaTarifa, custodiaBasica.getTarifaMensual(), 0.01,
                "La nueva tarifa debería estar actualizada");
    }

    // ═══════════════ TEST DE INTERFAZ COSTEABLE ═══════════════

    @Test
    @DisplayName("Verificar implementación de interfaz Costeable")
    void testInterfazCosteable() {
        double costoMensual = custodiaBasica.obtenerCostoMensual();
        double costoCalculado = custodiaBasica.calcularCostoMensual();

        assertEquals(costoCalculado, costoMensual, 0.01,
                "obtenerCostoMensual() debería retornar el mismo valor que calcularCostoMensual()");
    }

    @Test
    @Disabled("Pendiente: implementar validación de tarifa negativa")
    @DisplayName("Validar que la tarifa no pueda ser negativa")
    void testTarifaNoPuedeSerNegativa() {
        // Este test se implementará cuando se agregue validación en el setter
        assertThrows(IllegalArgumentException.class, () -> {
            custodiaBasica.setTarifaMensual(-1000);
        });
    }
}