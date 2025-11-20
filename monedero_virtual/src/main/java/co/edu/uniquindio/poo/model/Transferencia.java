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

  /**
   * Calcula la comisión de la transferencia aplicando descuentos por rango
   * y beneficios activos del cliente (sin cargos o descuento en comisiones)
   * 
   * @return El monto de la comisión después de aplicar todos los descuentos
   */
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

  /**
   * Ejecuta la transferencia entre monederos, descontando el monto más la
   * comisión
   * del origen y acreditando solo el monto al destino. Acumula puntos y envía
   * notificaciones.
   * 
   * @return true si la transferencia se completó exitosamente, false si fue
   *         rechazada
   */
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

  /**
   * Revierte una transferencia completada, devolviendo el monto y la comisión al
   * origen
   * y descontando los puntos generados si es posible
   * 
   * @return true si la reversión fue exitosa, false en caso contrario
   */
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

  /**
   * Obtiene la lista de errores de validación específicos de una transferencia,
   * incluyendo validaciones de monederos, estado activo y saldo suficiente
   * 
   * @return Lista de mensajes de error encontrados
   */
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

  /**
   * Valida que un monedero exista y esté activo
   * 
   * @param monedero       El monedero a validar
   * @param nombreMonedero Nombre descriptivo del monedero para mensajes de error
   * @param errores        Lista donde se agregarán los errores encontrados
   */
  protected void validarMonedero(Monedero monedero, String nombreMonedero, List<String> errores) {
    if (monedero == null) {
      errores.add("El monedero " + nombreMonedero + " es requerido");
    } else if (!monedero.isActivo()) {
      errores.add("El monedero " + nombreMonedero + " no está activo");
    }
  }

  /**
   * Valida que un monedero tenga saldo suficiente para realizar una operación
   * 
   * @param monedero       El monedero a validar
   * @param montoRequerido Monto mínimo necesario
   * @param errores        Lista donde se agregarán los errores encontrados
   */
  protected void validarSaldoSuficiente(Monedero monedero, double montoRequerido, List<String> errores) {
    if (monedero != null && monedero.getSaldo() < montoRequerido) {
      errores.add(String.format("Saldo insuficiente. Disponible: %s, Requerido: %s",
          Cliente.formatearCOP(monedero.getSaldo()),
          Cliente.formatearCOP(montoRequerido)));
    }
  }

  /**
   * Genera un reporte detallado de la transferencia incluyendo información
   * de los monederos origen y destino, comisión y monto total
   * 
   * @return String formateado con todos los detalles de la transferencia
   */
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

  public Monedero getMonederoOrigen() {
    return monederoOrigen;
  }

  public Monedero getMonederoDestino() {
    return monederoDestino;
  }

  public double getComision() {
    return comision;
  }

  public void setComision(double comision) {
    this.comision = comision;
  }
}