package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SistemaPuntos {

  private final List<Beneficio> beneficiosDisponibles;
  private final Map<Cliente, List<CanjeBeneficio>> canjesClientes;

  public SistemaPuntos() {
    this.beneficiosDisponibles = new ArrayList<>();
    this.canjesClientes = new HashMap<>();
    inicializarBeneficios();
  }

  /**
   * Inicializa los beneficios predefinidos del sistema según la especificación:
   * descuento en comisiones, mes sin cargos y bono de saldo
   */
  private void inicializarBeneficios() {

    beneficiosDisponibles.add(new Beneficio(
        "Descuento 10% Comisiones",
        "Reducción del 10% en comisión por transferencias",
        100,
        Beneficio.TipoBeneficio.DESCUENTO_COMISION,
        0.10));

    beneficiosDisponibles.add(new Beneficio(
        "Mes Sin Cargos",
        "Un mes sin cargos por retiros",
        500,
        Beneficio.TipoBeneficio.SIN_CARGOS,
        1.0));

    beneficiosDisponibles.add(new Beneficio(
        "Bono de Saldo",
        "Bono de $50.000 COP unidades en el monedero",
        1000,
        Beneficio.TipoBeneficio.BONO_SALDO,
        50000.0));
  }

  /**
   * Obtiene todos los beneficios activos disponibles en el sistema
   * 
   * @return Lista de beneficios que están activos
   */
  public List<Beneficio> obtenerBeneficiosDisponibles() {
    return beneficiosDisponibles.stream()
        .filter(Beneficio::isActivo)
        .collect(Collectors.toList());
  }

  /**
   * Obtiene los beneficios que el cliente puede canjear según sus puntos
   * acumulados
   * 
   * @param cliente El cliente a evaluar
   * @return Lista de beneficios disponibles para canje
   */
  public List<Beneficio> obtenerBeneficiosCanjeables(Cliente cliente) {
    return beneficiosDisponibles.stream()
        .filter(b -> b.puedeSerCanjeado(cliente))
        .collect(Collectors.toList());
  }

  /**
   * Canjea un beneficio por puntos, descontando los puntos del cliente,
   * aplicando el beneficio y registrando el canje
   * 
   * @param cliente     El cliente que canjea el beneficio
   * @param idBeneficio ID del beneficio a canjear
   * @return true si el canje fue exitoso, false en caso contrario
   */
  public boolean canjearBeneficio(Cliente cliente, String idBeneficio) {
    Optional<Beneficio> beneficioOpt = beneficiosDisponibles.stream()
        .filter(b -> b.getId().equals(idBeneficio))
        .findFirst();

    if (beneficioOpt.isEmpty()) {
      return false;
    }

    Beneficio beneficio = beneficioOpt.get();

    if (!beneficio.puedeSerCanjeado(cliente)) {
      return false;
    }

    if (cliente.canjearPuntos(beneficio.getPuntosRequeridos())) {
      CanjeBeneficio canje = new CanjeBeneficio(cliente, beneficio);

      canjesClientes.computeIfAbsent(cliente, k -> new ArrayList<>()).add(canje);

      cliente.agregarBeneficioActivo(canje);

      aplicarBeneficio(cliente, beneficio, canje);

      cliente.enviarNotificacion(
          String.format("Beneficio canjeado: %s - %s",
              beneficio.getNombre(),
              canje.obtenerEstado()),
          TipoNotificacion.BENEFICIO_CANJEADO);

      return true;
    }

    return false;
  }

  /**
   * Aplica los efectos del beneficio canjeado según su tipo:
   * bono inmediato, puntos extra o activación de descuentos
   * 
   * @param cliente   El cliente que recibe el beneficio
   * @param beneficio El beneficio a aplicar
   * @param canje     El registro del canje
   */
  private void aplicarBeneficio(Cliente cliente, Beneficio beneficio,
      CanjeBeneficio canje) {
    switch (beneficio.getTipo()) {
      case BONO_SALDO -> {
        Monedero principal = cliente.getMonederoPrincipal();
        double bonus = beneficio.getValorDescuento();
        principal.agregarSaldo(bonus);
        canje.setAplicado(true);

        cliente.enviarNotificacion(
            String.format("Bono de %s agregado a tu monedero principal",
                Cliente.formatearCOP(bonus)),
            TipoNotificacion.BENEFICIO_CANJEADO);
      }
      case PUNTOS_EXTRA -> {
        int puntosExtra = (int) beneficio.getValorDescuento();
        cliente.acumularPuntos(puntosExtra);
        canje.setAplicado(true);
      }
      case DESCUENTO_COMISION, SIN_CARGOS -> {

        canje.setAplicado(false);
      }
    }
  }

  /**
   * Obtiene el historial de canjes de beneficios de un cliente
   * 
   * @param cliente El cliente a consultar
   * @return Lista de canjes realizados por el cliente
   */
  public List<CanjeBeneficio> obtenerCanjesCliente(Cliente cliente) {
    return canjesClientes.getOrDefault(cliente, new ArrayList<>());
  }

  /**
   * Agrega un nuevo beneficio al sistema
   * 
   * @param beneficio El beneficio a agregar
   */
  public void agregarBeneficio(Beneficio beneficio) {
    beneficiosDisponibles.add(beneficio);
  }

  public List<Beneficio> getBeneficiosDisponibles() {
    return beneficiosDisponibles;
  }

  public Map<Cliente, List<CanjeBeneficio>> getCanjesClientes() {
    return canjesClientes;
  }

  /**
   * Genera un reporte formateado con todos los beneficios disponibles
   * 
   * @return String con el listado de beneficios activos
   */
  public String generarReporteBeneficios() {
    StringBuilder reporte = new StringBuilder();
    reporte.append("\n");
    reporte.append("BENEFICIOS DISPONIBLES\n");
    reporte.append("\n");

    beneficiosDisponibles.stream()
        .filter(Beneficio::isActivo)
        .forEach(b -> reporte.append(b.generarReporte()).append("\n"));

    reporte.append("\n");
    return reporte.toString();
  }
}