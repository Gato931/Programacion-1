package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Monedero implements Transaccionable, Reporteable {

  private final String id;
  private final TipoMonedero tipo;
  private double saldo;
  private final Cliente propietario;
  private final List<Transaccion> historialTransacciones;
  private double limiteRetiroDiario;
  private double saldoMinimo;
  private boolean activo;

  public Monedero(TipoMonedero tipo, Cliente propietario) {
    this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.tipo = tipo;
    this.propietario = propietario;
    this.saldo = 0.0;
    this.historialTransacciones = new ArrayList<>();
    this.limiteRetiroDiario = 5000.0;
    this.saldoMinimo = 0.0;
    this.activo = true;

    configurarLimitesPorTipo();
  }

  /**
   * Configura los límites de retiro diario y saldo mínimo según el tipo de
   * monedero
   * Principal: 5M límite, 0 mínimo
   * Ahorro: 2M límite, 50K mínimo
   * Gastos Diarios: 3M límite, 0 mínimo
   * Emergencia: 1M límite, 100K mínimo
   * Inversión: 10M límite, 500K mínimo
   */
  private void configurarLimitesPorTipo() {
    switch (tipo) {
      case PRINCIPAL -> {
        this.limiteRetiroDiario = 5_000_000.0;
        this.saldoMinimo = 0.0;
      }
      case AHORRO -> {
        this.limiteRetiroDiario = 2_000_000.0;
        this.saldoMinimo = 50_000.0;
      }
      case GASTOS_DIARIOS -> {
        this.limiteRetiroDiario = 3_000_000.0;
        this.saldoMinimo = 0.0;
      }
      case EMERGENCIA -> {
        this.limiteRetiroDiario = 1_000_000.0;
        this.saldoMinimo = 100_000.0;
      }
      case INVERSION -> {
        this.limiteRetiroDiario = 10_000_000.0;
        this.saldoMinimo = 500_000.0;
      }
    }
  }

  /**
   * Realiza un depósito en el monedero
   * Crea una transacción Deposito, la ejecuta y la registra
   * 
   * @param monto       Cantidad a depositar
   * @param descripcion Descripción del depósito
   * @return true si fue exitoso
   */
  @Override
  public boolean depositar(double monto, String descripcion) {
    if (monto <= 0 || !activo) {
      return false;
    }

    Deposito deposito = new Deposito(monto, descripcion, this);
    boolean exitoso = deposito.ejecutar();

    if (exitoso) {
      registrarTransaccion(deposito);
    }

    return exitoso;
  }

  /**
   * Realiza un retiro del monedero
   * Valida límites y saldo antes de crear la transacción
   * 
   * @param monto       Cantidad a retirar
   * @param descripcion Descripción del retiro
   * @return true si fue exitoso
   */
  @Override
  public boolean retirar(double monto, String descripcion) {
    if (!puedeRetirar(monto)) {
      return false;
    }

    Retiro retiro = new Retiro(monto, descripcion, this);
    boolean exitoso = retiro.ejecutar();

    if (exitoso) {
      registrarTransaccion(retiro);
    }

    return exitoso;
  }

  /**
   * Realiza una transferencia a otro monedero
   * Crea una transacción Transferencia con comisiones
   * 
   * @param destino     El monedero destino (debe ser Transaccionable)
   * @param monto       Cantidad a transferir
   * @param descripcion Descripción de la transferencia
   * @return true si fue exitosa
   */
  @Override
  public boolean transferir(Transaccionable destino, double monto, String descripcion) {
    if (!puedeRetirar(monto)) {
      return false;
    }

    if (destino instanceof Monedero monederoDestino) {
      Transferencia transferencia = new Transferencia(
          monto, descripcion, this, monederoDestino);
      boolean exitoso = transferencia.ejecutar();

      if (exitoso) {
        registrarTransaccion(transferencia);
      }

      return exitoso;
    }

    return false;
  }

  /**
   * Valida si se puede realizar un retiro
   * Verifica: monedero activo, monto positivo, saldo mínimo y límite diario
   * 
   * @param monto El monto a retirar
   * @return true si se puede retirar
   */
  private boolean puedeRetirar(double monto) {
    if (!activo || monto <= 0) {
      return false;
    }

    if (saldo - monto < saldoMinimo) {
      return false;
    }

    double retirosHoy = calcularRetirosHoy();
    if (retirosHoy + monto > limiteRetiroDiario) {
      return false;
    }

    return saldo >= monto;
  }

  /**
   * Calcula el total retirado en el día actual
   * Suma retiros y transferencias completadas desde medianoche
   * 
   * @return Total retirado hoy
   */
  public double calcularRetirosHoy() {
    LocalDateTime inicioDia = LocalDateTime.now().toLocalDate().atStartOfDay();

    return historialTransacciones.stream()
        .filter(t -> t.getTipo() == TipoTransaccion.RETIRO ||
            t.getTipo() == TipoTransaccion.TRANSFERENCIA)
        .filter(t -> t.getEstado() == EstadoTransaccion.COMPLETADA)
        .filter(t -> t.getFechaEjecucion() != null &&
            t.getFechaEjecucion().isAfter(inicioDia))
        .mapToDouble(Transaccion::getMonto)
        .sum();
  }

  /**
   * Agrega saldo al monedero
   * Solo permite montos positivos
   * 
   * @param monto Cantidad a agregar
   */
  public void agregarSaldo(double monto) {
    if (monto > 0) {
      this.saldo += monto;
    }
  }

  /**
   * Resta saldo del monedero
   * Solo permite montos positivos y si hay saldo suficiente
   * 
   * @param monto Cantidad a restar
   */
  public void restarSaldo(double monto) {
    if (monto > 0 && saldo >= monto) {
      this.saldo -= monto;
    }
  }

  /**
   * Registra una transacción en el historial
   * 
   * @param transaccion La transacción a registrar
   */
  private void registrarTransaccion(Transaccion transaccion) {
    historialTransacciones.add(transaccion);
  }

  /**
   * Obtiene todo el historial de transacciones
   * 
   * @return Lista con todas las transacciones
   */
  public List<Transaccion> obtenerHistorial() {
    return new ArrayList<>(historialTransacciones);
  }

  /**
   * Filtra el historial por tipo de transacción
   * 
   * @param tipo El tipo a filtrar
   * @return Lista de transacciones del tipo especificado
   */
  public List<Transaccion> obtenerHistorialPorTipo(TipoTransaccion tipo) {
    return historialTransacciones.stream()
        .filter(t -> t.getTipo() == tipo)
        .collect(Collectors.toList());
  }

  /**
   * Obtiene transacciones en un rango de fechas
   * 
   * @param inicio Fecha/hora inicial
   * @param fin    Fecha/hora final
   * @return Lista de transacciones en el rango
   */
  public List<Transaccion> obtenerHistorialEnRango(LocalDateTime inicio, LocalDateTime fin) {
    return historialTransacciones.stream()
        .filter(t -> !t.getFechaCreacion().isBefore(inicio) &&
            !t.getFechaCreacion().isAfter(fin))
        .collect(Collectors.toList());
  }

  /**
   * Genera un reporte resumido del monedero
   * 
   * @return String con tipo, nombre y saldo
   */
  @Override
  public String generarReporte() {
    return String.format("%s %s - Saldo: %s",
        tipo.getIcono(),
        tipo.getNombre(),
        Cliente.formatearCOP(saldo));
  }

  /**
   * Genera un reporte detallado del monedero
   * Incluye estadísticas de transacciones y saldos
   * 
   * @return String con reporte completo
   */
  @Override
  public String generarReporteDetallado() {
    int totalTransacciones = historialTransacciones.size();
    double totalDepositado = historialTransacciones.stream()
        .filter(t -> t.getTipo() == TipoTransaccion.DEPOSITO)
        .filter(t -> t.getEstado() == EstadoTransaccion.COMPLETADA)
        .mapToDouble(Transaccion::getMonto)
        .sum();

    double totalRetirado = historialTransacciones.stream()
        .filter(t -> t.getTipo() == TipoTransaccion.RETIRO)
        .filter(t -> t.getEstado() == EstadoTransaccion.COMPLETADA)
        .mapToDouble(Transaccion::getMonto)
        .sum();

    return String.format("""

        REPORTE DE MONEDERO

        ID: %s
        Tipo: %s %s
        Propietario: %s
        Saldo Actual: %s
        Saldo Minimo: %s
        Limite Retiro Diario: %s
        Total Transacciones: %d
        Total Depositado: %s
        Total Retirado: %s
        Estado: %s

        """,
        id, tipo.getIcono(), tipo.getNombre(),
        propietario.getNombre(),
        Cliente.formatearCOP(saldo),
        Cliente.formatearCOP(saldoMinimo),
        Cliente.formatearCOP(limiteRetiroDiario),
        totalTransacciones,
        Cliente.formatearCOP(totalDepositado),
        Cliente.formatearCOP(totalRetirado),
        activo ? "Activo" : "Inactivo");
  }

  public String getId() {
    return id;
  }

  public TipoMonedero getTipo() {
    return tipo;
  }

  @Override
  public double getSaldo() {
    return saldo;
  }

  public Cliente getPropietario() {
    return propietario;
  }

  public double getLimiteRetiroDiario() {
    return limiteRetiroDiario;
  }

  public double getSaldoMinimo() {
    return saldoMinimo;
  }

  public boolean isActivo() {
    return activo;
  }

  public List<Transaccion> getHistorialTransacciones() {
    return historialTransacciones;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }

  public void setLimiteRetiroDiario(double limite) {
    if (limite > 0)
      this.limiteRetiroDiario = limite;
  }

  public void setSaldoMinimo(double minimo) {
    if (minimo >= 0)
      this.saldoMinimo = minimo;
  }

  @Override
  public String toString() {
    return generarReporte();
  }
}
