package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.*;
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
   * Obtiene la instancia única del sistema (patrón Singleton)
   * Crea la instancia si no existe
   * 
   * @return La única instancia de SistemaMonedero
   */
  public static SistemaMonedero getInstance() {
    if (instancia == null) {
      instancia = new SistemaMonedero();
    }
    return instancia;
  }

  /**
   * Registra un nuevo cliente en el sistema
   * Valida que el email no esté ya registrado
   * 
   * @param nombre   Nombre completo del cliente
   * @param email    Email del cliente (único)
   * @param telefono Teléfono de contacto
   * @return El cliente registrado
   * @throws IllegalArgumentException si el email ya existe
   */
  public Cliente registrarCliente(String nombre, String email, String telefono) {
    if (existeClientePorEmail(email)) {
      throw new IllegalArgumentException("El email ya está registrado");
    }

    Cliente cliente = new Cliente(nombre, email, telefono);
    clientes.put(cliente.getId(), cliente);
    return cliente;
  }

  /**
   * Busca un cliente por su ID único
   * 
   * @param id El ID del cliente
   * @return Optional con el cliente si existe
   */
  public Optional<Cliente> buscarClientePorId(String id) {
    return Optional.ofNullable(clientes.get(id));
  }

  /**
   * Busca un cliente por su email (ignora mayúsculas/minúsculas)
   * 
   * @param email El email a buscar
   * @return Optional con el cliente si existe
   */
  public Optional<Cliente> buscarClientePorEmail(String email) {
    return clientes.values().stream()
        .filter(c -> c.getEmail().equalsIgnoreCase(email))
        .findFirst();
  }

  /**
   * Verifica si existe un cliente con el email dado
   * 
   * @param email El email a verificar
   * @return true si existe un cliente con ese email
   */
  public boolean existeClientePorEmail(String email) {
    return buscarClientePorEmail(email).isPresent();
  }

  /**
   * Obtiene la lista de todos los clientes registrados
   * 
   * @return Lista con todos los clientes
   */
  public List<Cliente> obtenerTodosClientes() {
    return new ArrayList<>(clientes.values());
  }

  /**
   * Filtra y obtiene clientes por su rango actual
   * 
   * @param rango El rango a filtrar (BRONCE, PLATA, ORO, PLATINO)
   * @return Lista de clientes con ese rango
   */
  public List<Cliente> obtenerClientesPorRango(RangoCliente rango) {
    return clientes.values().stream()
        .filter(c -> c.getRangoActual() == rango)
        .collect(Collectors.toList());
  }

  /**
   * Realiza un depósito en el monedero especificado de un cliente
   * Genera puntos y verifica saldo bajo después del depósito
   * 
   * @param cliente      El cliente que realiza el depósito
   * @param tipoMonedero El tipo de monedero donde depositar
   * @param monto        El monto a depositar
   * @param descripcion  Descripción de la operación
   * @return true si el depósito fue exitoso
   */
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

  /**
   * Realiza un retiro del monedero especificado
   * Valida saldo suficiente y límites de retiro diario
   * 
   * @param cliente      El cliente que retira
   * @param tipoMonedero El monedero de donde retirar
   * @param monto        El monto a retirar
   * @param descripcion  Descripción del retiro
   * @return true si el retiro fue exitoso
   */
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

  /**
   * Realiza una transferencia entre dos clientes
   * Calcula comisiones, aplica beneficios y genera puntos
   * 
   * @param clienteOrigen   Cliente que envía el dinero
   * @param monederoOrigen  Tipo de monedero origen
   * @param clienteDestino  Cliente que recibe el dinero
   * @param monederoDestino Tipo de monedero destino
   * @param monto           Monto a transferir (sin incluir comisión)
   * @param descripcion     Descripción de la transferencia
   * @return true si la transferencia fue exitosa
   */
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

  /**
   * Programa una transacción para ejecutarse automáticamente
   * Soporta periodicidad (diaria, semanal, mensual, etc.)
   * 
   * @param tipo            Tipo de transacción (DEPOSITO, RETIRO, TRANSFERENCIA)
   * @param monto           Monto de la transacción
   * @param descripcion     Descripción
   * @param cliente         Cliente que programa
   * @param monederoOrigen  Monedero origen
   * @param clienteDestino  Cliente destino (null para depósitos/retiros)
   * @param monederoDestino Monedero destino (null para depósitos/retiros)
   * @param fechaEjecucion  Fecha y hora de primera ejecución
   * @param periodicidad    Con qué frecuencia se repite
   * @return La transacción programada creada
   */
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

  /**
   * Procesa todas las transacciones programadas que deben ejecutarse
   * Las ordena por fecha (requisito del enunciado) antes de ejecutar
   * Desactiva las transacciones únicas después de ejecutarlas
   */
  public void procesarTransaccionesProgramadas() {
    // ORDENAR por fecha de ejecución (requisito del enunciado)
    List<TransaccionProgramada> paraEjecutar = transaccionesProgramadas.stream()
        .filter(TransaccionProgramada::debeEjecutarse)
        .sorted(Comparator.comparing(TransaccionProgramada::getProximaEjecucion))
        .toList();

    System.out.println("Procesando " + paraEjecutar.size() + " transacciones programadas...");

    for (TransaccionProgramada programada : paraEjecutar) {
      programada.ejecutar().ifPresent(transaccion -> {
        historialGlobal.add(transaccion);
        System.out.println("Transacción programada ejecutada: " + transaccion.getId());
      });

      if (programada.getPeriodicidad() != Periodicidad.UNICA) {
        // Aquí se reprogramaría para la siguiente ejecución
      } else {
        programada.setActiva(false);
      }
    }
  }

  /**
   * Obtiene las transacciones programadas activas de un cliente
   * 
   * @param cliente El cliente del que obtener transacciones
   * @return Lista de transacciones programadas activas
   */
  public List<TransaccionProgramada> obtenerTransaccionesProgramadasCliente(Cliente cliente) {
    return transaccionesProgramadas.stream()
        .filter(t -> t.isActiva())
        .filter(t -> cliente.getMonederos().stream()
            .anyMatch(m -> m.equals(t.getOrigen())))
        .collect(Collectors.toList());
  }

  /**
   * Verifica recursivamente la integridad de una lista de transacciones
   * Requisito del enunciado: "utilizar algoritmos recursivos"
   * 
   * @param transacciones Lista de transacciones a verificar
   * @param indice        Índice actual en la recursión
   * @return true si todas las transacciones son válidas
   */
  public boolean verificarIntegridadTransacciones(List<Transaccion> transacciones, int indice) {
    // Caso base: llegamos al final
    if (indice >= transacciones.size()) {
      return true;
    }

    Transaccion actual = transacciones.get(indice);

    // Validar la transacción actual
    if (!actual.esValida()) {
      System.err.println("Transacción inválida: " + actual.getId());
      return false;
    }

    // Validaciones específicas por tipo
    boolean esValida = switch (actual.getTipo()) {
      case DEPOSITO -> verificarDeposito((Deposito) actual);
      case RETIRO -> verificarRetiro((Retiro) actual);
      case TRANSFERENCIA -> verificarTransferencia((Transferencia) actual);
      case CANJE_PUNTOS -> true;
    };

    if (!esValida) {
      System.err.println("Validación específica falló: " + actual.getId());
      return false;
    }

    // Llamada recursiva
    return verificarIntegridadTransacciones(transacciones, indice + 1);
  }

  /**
   * Valida que un depósito cumpla con las reglas de negocio
   * 
   * @param deposito El depósito a validar
   * @return true si es válido
   */
  private boolean verificarDeposito(Deposito deposito) {
    return deposito.getMonto() > 0 &&
        deposito.getMonederoDestino() != null &&
        deposito.getMonederoDestino().isActivo();
  }

  /**
   * Valida que un retiro cumpla con las reglas de negocio
   * 
   * @param retiro El retiro a validar
   * @return true si es válido
   */
  private boolean verificarRetiro(Retiro retiro) {
    return retiro.getMonto() > 0 &&
        retiro.getMonederoOrigen() != null &&
        retiro.getMonederoOrigen().isActivo();
  }

  /**
   * Valida que una transferencia cumpla con las reglas de negocio
   * 
   * @param transferencia La transferencia a validar
   * @return true si es válida
   */
  private boolean verificarTransferencia(Transferencia transferencia) {
    return transferencia.getMonto() > 0 &&
        transferencia.getMonederoOrigen() != null &&
        transferencia.getMonederoDestino() != null &&
        !transferencia.getMonederoOrigen().equals(transferencia.getMonederoDestino());
  }

  /**
   * Verifica la integridad de todas las transacciones de un cliente
   * Usa el método recursivo verificarIntegridadTransacciones
   * 
   * @param cliente El cliente a verificar
   * @return true si todas sus transacciones son válidas
   */
  public boolean verificarIntegridadCliente(Cliente cliente) {
    List<Transaccion> todasTransacciones = new ArrayList<>();
    cliente.getMonederos().forEach(m -> todasTransacciones.addAll(m.obtenerHistorial()));

    System.out.println("Verificando " + todasTransacciones.size() +
        " transacciones de " + cliente.getNombre());

    boolean integro = verificarIntegridadTransacciones(todasTransacciones, 0);

    if (integro) {
      System.out.println("Todas las transacciones son válidas");
    } else {
      System.out.println("Se encontraron transacciones inválidas");
    }

    return integro;
  }

  // Getters
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

  /**
   * Genera un reporte completo de un cliente
   * Incluye detalles, monederos y estadísticas
   * 
   * @param cliente El cliente del reporte
   * @return String con el reporte completo
   */
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

  /**
   * Genera un reporte general de todo el sistema
   * Incluye totales de clientes, saldos, transacciones y distribución por rangos
   * 
   * @return String con el reporte general del sistema
   */
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

        REPORTE GENERAL DEL SISTEMA

        Total Clientes: %d
        Saldo Total Sistema: $%,.0f COP
        Transacciones Totales: %d
        Transacciones Programadas Activas: %d

        Clientes por Rango:
        %s

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

  // Getters de colecciones
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

  public static SistemaMonedero getInstancia() {
    return instancia;
  }

  public static void setInstancia(SistemaMonedero instancia) {
    SistemaMonedero.instancia = instancia;
  }
}