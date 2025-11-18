package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

  public void agregarSaldo(double monto) {
    if (monto > 0) {
      this.saldo += monto;
    }
  }

  public void restarSaldo(double monto) {
    if (monto > 0 && saldo >= monto) {
      this.saldo -= monto;
    }
  }

  private void registrarTransaccion(Transaccion transaccion) {
    historialTransacciones.add(transaccion);
  }

  public List<Transaccion> obtenerHistorial() {
    return new ArrayList<>(historialTransacciones);
  }

  public List<Transaccion> obtenerHistorialPorTipo(TipoTransaccion tipo) {
    return historialTransacciones.stream()
        .filter(t -> t.getTipo() == tipo)
        .collect(Collectors.toList());
  }

  public List<Transaccion> obtenerHistorialEnRango(LocalDateTime inicio, LocalDateTime fin) {
    return historialTransacciones.stream()
        .filter(t -> !t.getFechaCreacion().isBefore(inicio) &&
            !t.getFechaCreacion().isAfter(fin))
        .collect(Collectors.toList());
  }

  @Override
  public String generarReporte() {
    return String.format("%s %s - Saldo: %s",
        tipo.getIcono(),
        tipo.getNombre(),
        Cliente.formatearCOP(saldo));
  }

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
        ═══════════════════════════════════
        REPORTE DE MONEDERO
        ═══════════════════════════════════
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
        ═══════════════════════════════════
        """,
        id,
        tipo.getIcono(),
        tipo.getNombre(),
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

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public Cliente getPropietario() {
    return propietario;
  }

  public List<Transaccion> getHistorialTransacciones() {
    return historialTransacciones;
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