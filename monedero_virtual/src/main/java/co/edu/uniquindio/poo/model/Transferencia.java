package co.edu.uniquindio.poo.model;

import java.util.List;
import java.util.Optional;

public class Transferencia extends Transaccion {

  private final Monedero monederoOrigen;
  private final Monedero monederoDestino;
  private double comision;

  public Transferencia(double monto, String descripcion,
      Monedero origen, Monedero destino) {
    super(TipoTransaccion.TRANSFERENCIA, monto, descripcion);

    if (origen == null) {
      throw new IllegalArgumentException("El monedero origen es requerido");
    }
    if (destino == null) {
      throw new IllegalArgumentException("El monedero destino es requerido");
    }
    if (origen.equals(destino)) {
      throw new IllegalArgumentException("El origen y destino no pueden ser iguales");
    }

    this.monederoOrigen = origen;
    this.monederoDestino = destino;
    this.comision = calcularComision();
  }

  private double calcularComision() {
    double comisionBase = getMonto() * 0.01;

    Cliente cliente = monederoOrigen.getPropietario();
    RangoCliente rango = cliente.getRangoActual();
    double comision = comisionBase * rango.getDescuentoComision();

    boolean beneficioAplicado = false;

    if (cliente.tieneBeneficioActivo("SIN_CARGOS")) {
      Optional<CanjeBeneficio> beneficioOpt = cliente.obtenerBeneficioActivo("SIN_CARGOS");

      if (beneficioOpt.isPresent() && beneficioOpt.get().puedeUsarse()) {
        comision = 0.0;
        beneficioOpt.get().usar();
        beneficioAplicado = true;

        String comisionEliminada = Cliente.formatearCOP(comisionBase);

        cliente.enviarNotificacion(
            String.format("Beneficio aplicado: Sin cargos - Comision de %s eliminada (usos restantes: %d)",
                comisionEliminada,
                beneficioOpt.get().getUsosMaximos() - beneficioOpt.get().getVecesUsado()),
            TipoNotificacion.BENEFICIO_CANJEADO);
      }
    }

    if (!beneficioAplicado && cliente.tieneBeneficioActivo("DESCUENTO_COMISION")) {
      Optional<CanjeBeneficio> beneficioOpt = cliente.obtenerBeneficioActivo("DESCUENTO_COMISION");

      if (beneficioOpt.isPresent() && beneficioOpt.get().puedeUsarse()) {
        CanjeBeneficio canje = beneficioOpt.get();
        double descuento = canje.getBeneficio().getValorDescuento();
        double comisionAntes = comision;
        comision = comision * (1.0 - descuento);
        canje.usar();
        beneficioAplicado = true;

        String ahorroFormateado = Cliente.formatearCOP(comisionAntes - comision);

        cliente.enviarNotificacion(
            String.format("Beneficio aplicado: Descuento %.0f%% - Ahorro de %s (usos restantes: %d)",
                descuento * 100,
                ahorroFormateado,
                canje.getUsosMaximos() - canje.getVecesUsado()),
            TipoNotificacion.BENEFICIO_CANJEADO);
      }
    }

    return comision;
  }

  @Override
  public boolean ejecutar() {
    if (!esValida()) {
      rechazar();
      return false;
    }

    double montoTotal = getMonto() + comision;

    if (monederoOrigen.getSaldo() >= montoTotal) {
      monederoOrigen.restarSaldo(montoTotal);
      monederoDestino.agregarSaldo(getMonto());
      completar();

      aplicarBonusRango(monederoOrigen.getPropietario().getRangoActual());
      monederoOrigen.getPropietario().acumularPuntos(getPuntosGenerados());

      String montoFormateado = Cliente.formatearCOP(getMonto());
      String comisionFormateada = Cliente.formatearCOP(comision);

      monederoOrigen.getPropietario().enviarNotificacion(
          String.format("Transferencia enviada: %s + %s (comision)",
              montoFormateado, comisionFormateada),
          TipoNotificacion.TRANSACCION_COMPLETADA);

      monederoDestino.getPropietario().enviarNotificacion(
          String.format("Transferencia recibida: %s de %s",
              montoFormateado, monederoOrigen.getPropietario().getNombre()),
          TipoNotificacion.TRANSACCION_COMPLETADA);

      return true;
    }

    rechazar();
    return false;
  }

  @Override
  public boolean revertir() {
    if (getEstado() != EstadoTransaccion.COMPLETADA) {
      return false;
    }

    if (monederoDestino.getSaldo() >= getMonto()) {
      monederoDestino.restarSaldo(getMonto());
      monederoOrigen.agregarSaldo(getMonto() + comision);
      setEstado(EstadoTransaccion.REVERTIDA);

      int puntosActuales = monederoOrigen.getPropietario().getPuntosAcumulados();
      if (puntosActuales >= getPuntosGenerados()) {
        monederoOrigen.getPropietario().canjearPuntos(getPuntosGenerados());
      }

      return true;
    }

    return false;
  }

  @Override
  public List<String> obtenerErroresValidacion() {
    List<String> errores = super.obtenerErroresValidacion();

    if (monederoOrigen == null) {
      errores.add("El monedero origen es requerido");
    }
    if (monederoDestino == null) {
      errores.add("El monedero destino es requerido");
    }
    if (monederoOrigen != null && monederoDestino != null &&
        monederoOrigen.equals(monederoDestino)) {
      errores.add("El origen y destino no pueden ser el mismo monedero");
    }
    if (monederoOrigen != null && !monederoOrigen.isActivo()) {
      errores.add("El monedero origen no está activo");
    }
    if (monederoDestino != null && !monederoDestino.isActivo()) {
      errores.add("El monedero destino no está activo");
    }
    if (monederoOrigen != null &&
        monederoOrigen.getSaldo() < (getMonto() + comision)) {
      errores.add("Saldo insuficiente (incluye comisión)");
    }

    return errores;
  }

  protected void validarMonedero(Monedero monedero, String nombreMonedero, List<String> errores) {
    if (monedero == null) {
      errores.add("El monedero " + nombreMonedero + " es requerido");
    } else if (!monedero.isActivo()) {
      errores.add("El monedero " + nombreMonedero + " no está activo");
    }
  }

  protected void validarSaldoSuficiente(Monedero monedero, double montoRequerido, List<String> errores) {
    if (monedero != null && monedero.getSaldo() < montoRequerido) {
      errores.add(String.format("Saldo insuficiente. Disponible: %s, Requerido: %s",
          Cliente.formatearCOP(monedero.getSaldo()),
          Cliente.formatearCOP(montoRequerido)));
    }
  }

  public Monedero getMonederoOrigen() {
    return monederoOrigen;
  }

  public Monedero getMonederoDestino() {
    return monederoDestino;
  }

  public double getComision() {
    return comision;
  }

  @Override
  public String generarReporteDetallado() {
    return super.generarReporteDetallado() +
        String.format("""
            Monedero Origen: %s (%s)
            Monedero Destino: %s (%s)
            Comisión: $%.2f
            Monto Total: $%.2f
            """,
            monederoOrigen.getTipo().getNombre(),
            monederoOrigen.getPropietario().getNombre(),
            monederoDestino.getTipo().getNombre(),
            monederoDestino.getPropietario().getNombre(),
            comision,
            getMonto() + comision);
  }

  public void setComision(double comision) {
    this.comision = comision;
  }
}