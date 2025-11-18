package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SistemaMonedero {

  private static SistemaMonedero instancia;

  private final Map<String, Cliente> clientes;
  private final SistemaPuntos sistemaPuntos;
  private final List<TransaccionProgramada> transaccionesProgramadas;
  private final List<Transaccion> historialGlobal;

  private SistemaMonedero() {
    this.clientes = new HashMap<>();
    this.sistemaPuntos = new SistemaPuntos();
    this.transaccionesProgramadas = new ArrayList<>();
    this.historialGlobal = new ArrayList<>();
  }

  /**
   * Obtiene la instancia única del sistema (Singleton)
   */
  public static SistemaMonedero getInstance() {
    if (instancia == null) {
      instancia = new SistemaMonedero();
    }
    return instancia;
  }

  public Cliente registrarCliente(String nombre, String email, String telefono) {

    if (existeClientePorEmail(email)) {
      throw new IllegalArgumentException("El email ya está registrado");
    }

    Cliente cliente = new Cliente(nombre, email, telefono);
    clientes.put(cliente.getId(), cliente);

    return cliente;
  }

  public Optional<Cliente> buscarClientePorId(String id) {
    return Optional.ofNullable(clientes.get(id));
  }

  public Optional<Cliente> buscarClientePorEmail(String email) {
    return clientes.values().stream()
        .filter(c -> c.getEmail().equalsIgnoreCase(email))
        .findFirst();
  }

  public boolean existeClientePorEmail(String email) {
    return buscarClientePorEmail(email).isPresent();
  }

  public List<Cliente> obtenerTodosClientes() {
    return new ArrayList<>(clientes.values());
  }

  public List<Cliente> obtenerClientesPorRango(RangoCliente rango) {
    return clientes.values().stream()
        .filter(c -> c.getRangoActual() == rango)
        .collect(Collectors.toList());
  }

  public boolean realizarDeposito(Cliente cliente, TipoMonedero tipoMonedero,
      double monto, String descripcion) {
    Optional<Monedero> monederoOpt = cliente.obtenerMonedero(tipoMonedero);

    if (monederoOpt.isEmpty()) {
      return false;
    }

    boolean exitoso = monederoOpt.get().depositar(monto, descripcion);

    if (exitoso) {
      cliente.verificarSaldoBajo();
    }

    return exitoso;
  }

  public boolean realizarRetiro(Cliente cliente, TipoMonedero tipoMonedero,
      double monto, String descripcion) {
    Optional<Monedero> monederoOpt = cliente.obtenerMonedero(tipoMonedero);

    if (monederoOpt.isEmpty()) {
      return false;
    }

    boolean exitoso = monederoOpt.get().retirar(monto, descripcion);

    if (exitoso) {
      cliente.verificarSaldoBajo();
    }

    return exitoso;
  }

  public boolean realizarTransferencia(Cliente clienteOrigen, TipoMonedero monederoOrigen,
      Cliente clienteDestino, TipoMonedero monederoDestino,
      double monto, String descripcion) {

    Optional<Monedero> origenOpt = clienteOrigen.obtenerMonedero(monederoOrigen);
    Optional<Monedero> destinoOpt = clienteDestino.obtenerMonedero(monederoDestino);

    if (origenOpt.isEmpty() || destinoOpt.isEmpty()) {
      return false;
    }

    boolean exitoso = origenOpt.get().transferir(
        destinoOpt.get(), monto, descripcion);

    if (exitoso) {
      clienteOrigen.verificarSaldoBajo();
    }

    return exitoso;
  }

  public TransaccionProgramada programarTransaccion(
      TipoTransaccion tipo, double monto, String descripcion,
      Cliente cliente, TipoMonedero monederoOrigen,
      Cliente clienteDestino, TipoMonedero monederoDestino,
      LocalDateTime fechaEjecucion, Periodicidad periodicidad) {

    Optional<Monedero> origenOpt = cliente.obtenerMonedero(monederoOrigen);
    if (origenOpt.isEmpty()) {
      throw new IllegalArgumentException("Monedero origen no encontrado");
    }

    Monedero destino = null;
    if (clienteDestino != null && monederoDestino != null) {
      destino = clienteDestino.obtenerMonedero(monederoDestino).orElse(null);
    }

    TransaccionProgramada programada = new TransaccionProgramada(
        tipo, monto, descripcion, origenOpt.get(), destino,
        fechaEjecucion, periodicidad);

    transaccionesProgramadas.add(programada);

    cliente.enviarNotificacion(
        String.format("Transacción programada: %s - %s",
            tipo.getDescripcion(),
            fechaEjecucion.toLocalDate()),
        TipoNotificacion.TRANSACCION_PROGRAMADA);

    return programada;
  }

  public void procesarTransaccionesProgramadas() {
    List<TransaccionProgramada> paraEjecutar = transaccionesProgramadas.stream()
        .filter(TransaccionProgramada::debeEjecutarse)
        .sorted(Comparator.comparing(TransaccionProgramada::getProximaEjecucion))
        .toList();

    for (TransaccionProgramada programada : paraEjecutar) {
      programada.ejecutar().ifPresent(historialGlobal::add);

      if (programada.getPeriodicidad() != Periodicidad.UNICA) {

      } else {
        programada.setActiva(false);
      }
    }
  }

  public List<TransaccionProgramada> obtenerTransaccionesProgramadasCliente(Cliente cliente) {
    return transaccionesProgramadas.stream()
        .filter(t -> t.isActiva())
        .filter(t -> cliente.getMonederos().stream()
            .anyMatch(m -> m.equals(t.getOrigen())))
        .collect(Collectors.toList());
  }

  public List<Beneficio> obtenerBeneficiosDisponibles() {
    return sistemaPuntos.obtenerBeneficiosDisponibles();
  }

  public List<Beneficio> obtenerBeneficiosCanjeables(Cliente cliente) {
    return sistemaPuntos.obtenerBeneficiosCanjeables(cliente);
  }

  public boolean canjearBeneficio(Cliente cliente, String idBeneficio) {
    return sistemaPuntos.canjearBeneficio(cliente, idBeneficio);
  }

  public List<CanjeBeneficio> obtenerCanjesCliente(Cliente cliente) {
    return sistemaPuntos.obtenerCanjesCliente(cliente);
  }

  public AnalizadorPatrones.EstadisticasGasto analizarGastosCliente(Cliente cliente) {
    return AnalizadorPatrones.analizarGastos(cliente);
  }

  public Map<String, Double> analizarGastosPorPeriodo(
      Cliente cliente, LocalDateTime inicio, LocalDateTime fin) {
    return AnalizadorPatrones.analizarGastosPorPeriodo(cliente, inicio, fin);
  }

  public List<String> detectarPatronesInusuales(Cliente cliente) {
    return AnalizadorPatrones.detectarPatronesInusuales(cliente);
  }

  public String generarReporteCliente(Cliente cliente) {
    StringBuilder reporte = new StringBuilder();
    reporte.append(cliente.generarReporteDetallado()).append("\n");

    reporte.append("MONEDEROS:\n");
    for (Monedero m : cliente.getMonederos()) {
      reporte.append("  ").append(m.generarReporte()).append("\n");
    }

    reporte.append("\nESTADÍSTICAS:\n");
    AnalizadorPatrones.EstadisticasGasto stats = analizarGastosCliente(cliente);
    reporte.append(stats.generarReporte()).append("\n");

    return reporte.toString();
  }

  public String generarReporteGeneral() {
    int totalClientes = clientes.size();
    double saldoTotal = clientes.values().stream()
        .mapToDouble(Cliente::calcularSaldoTotal)
        .sum();

    int totalTransacciones = historialGlobal.size();

    Map<RangoCliente, Long> clientesPorRango = clientes.values().stream()
        .collect(Collectors.groupingBy(
            Cliente::getRangoActual,
            Collectors.counting()));

    return String.format("""
        ═══════════════════════════════════════
        REPORTE GENERAL DEL SISTEMA
        ═══════════════════════════════════════
        Total Clientes: %d
        Saldo Total Sistema: $%,.0f COP
        Transacciones Totales: %d
        Transacciones Programadas Activas: %d

        Clientes por Rango:
        %s
        ═══════════════════════════════════════
        """,
        totalClientes,
        saldoTotal,
        totalTransacciones,
        transaccionesProgramadas.stream().filter(TransaccionProgramada::isActiva).count(),
        formatearClientesPorRango(clientesPorRango));
  }

  private String formatearClientesPorRango(Map<RangoCliente, Long> rangos) {
    return rangos.entrySet().stream()
        .map(e -> String.format("  %s %s: %d clientes",
            e.getKey().getIcono(),
            e.getKey().getNombre(),
            e.getValue()))
        .collect(Collectors.joining("\n"));
  }

  public List<Transaccion> buscarTransaccionesPorCliente(Cliente cliente,
      LocalDateTime inicio,
      LocalDateTime fin) {
    List<Transaccion> transacciones = new ArrayList<>();

    for (Monedero m : cliente.getMonederos()) {
      transacciones.addAll(m.obtenerHistorialEnRango(inicio, fin));
    }

    return transacciones.stream()
        .sorted(Comparator.comparing(Transaccion::getFechaCreacion).reversed())
        .collect(Collectors.toList());
  }

  public Optional<Transaccion> buscarTransaccionPorId(String id) {
    return historialGlobal.stream()
        .filter(t -> t.getId().equals(id))
        .findFirst();
  }

  public List<Cliente> buscarClientesPorNombre(String nombre) {
    return clientes.values().stream()
        .filter(c -> c.getNombre().toLowerCase()
            .contains(nombre.toLowerCase()))
        .collect(Collectors.toList());
  }

  public void procesarNotificacionesAutomaticas() {
    for (Cliente cliente : clientes.values()) {

      cliente.verificarSaldoBajo();

      List<String> alertas = detectarPatronesInusuales(cliente);
      for (String alerta : alertas) {
        cliente.enviarNotificacion(alerta, TipoNotificacion.SALDO_BAJO);
      }

      recordarTransaccionesProgramadas(cliente);
    }
  }

  private void recordarTransaccionesProgramadas(Cliente cliente) {
    LocalDateTime proximasSemana = LocalDateTime.now().plusDays(7);

    obtenerTransaccionesProgramadasCliente(cliente).stream()
        .filter(t -> t.getProximaEjecucion().isBefore(proximasSemana))
        .forEach(t -> cliente.enviarNotificacion(
            String.format("Recordatorio: Transacción programada para %s",
                t.getProximaEjecucion().toLocalDate()),
            TipoNotificacion.TRANSACCION_PROGRAMADA));
  }

  public boolean validarTransferencia(Cliente origen, Cliente destino, double monto) {
    if (origen.equals(destino)) {
      return false;
    }

    if (monto <= 0) {
      return false;
    }

    Monedero monederoOrigen = origen.getMonederoPrincipal();
    return monederoOrigen.getSaldo() >= monto;
  }

  public Map<String, Object> obtenerEstadisticasSistema() {
    Map<String, Object> stats = new HashMap<>();

    stats.put("totalClientes", clientes.size());
    stats.put("totalTransacciones", historialGlobal.size());
    stats.put("saldoTotal", clientes.values().stream()
        .mapToDouble(Cliente::calcularSaldoTotal).sum());

    stats.put("transaccionesProgramadas",
        transaccionesProgramadas.stream()
            .filter(TransaccionProgramada::isActiva)
            .count());

    stats.put("clientesPorRango", clientes.values().stream()
        .collect(Collectors.groupingBy(
            Cliente::getRangoActual,
            Collectors.counting())));

    return stats;
  }

  public boolean verificarIntegridadTransacciones(List<Transaccion> transacciones, int indice) {

    if (indice >= transacciones.size()) {
      return true;
    }

    Transaccion actual = transacciones.get(indice);

    if (!actual.esValida()) {
      System.err.println(" Transacción inválida encontrada: " + actual.getId());
      return false;
    }

    boolean esValida = switch (actual.getTipo()) {
      case DEPOSITO -> verificarDeposito((Deposito) actual);
      case RETIRO -> verificarRetiro((Retiro) actual);
      case TRANSFERENCIA -> verificarTransferencia((Transferencia) actual);
      case CANJE_PUNTOS -> true;
    };

    return esValida && verificarIntegridadTransacciones(transacciones, indice + 1);
  }

  private boolean verificarDeposito(Deposito d) {
    return d.getMonto() > 0 &&
        d.getMonederoDestino() != null &&
        d.getMonederoDestino().isActivo();
  }

  private boolean verificarRetiro(Retiro r) {
    return r.getMonto() > 0 &&
        r.getMonederoOrigen() != null &&
        r.getMonederoOrigen().getSaldo() >= r.getMonto();
  }

  private boolean verificarTransferencia(Transferencia t) {
    return t.getMonto() > 0 &&
        t.getMonederoOrigen() != null &&
        t.getMonederoDestino() != null &&
        !t.getMonederoOrigen().equals(t.getMonederoDestino());
  }

  public boolean verificarIntegridadCliente(Cliente cliente) {
    List<Transaccion> todasTransacciones = new ArrayList<>();
    cliente.getMonederos().forEach(m -> todasTransacciones.addAll(m.obtenerHistorial()));

    return verificarIntegridadTransacciones(todasTransacciones, 0);
  }

  public static SistemaMonedero getInstancia() {
    return instancia;
  }

  public static void setInstancia(SistemaMonedero instancia) {
    SistemaMonedero.instancia = instancia;
  }

  public Map<String, Cliente> getClientes() {
    return clientes;
  }

  public SistemaPuntos getSistemaPuntos() {
    return sistemaPuntos;
  }

  public List<TransaccionProgramada> getTransaccionesProgramadas() {
    return new ArrayList<>(transaccionesProgramadas);
  }

  public List<Transaccion> getHistorialGlobal() {
    return new ArrayList<>(historialGlobal);
  }
}