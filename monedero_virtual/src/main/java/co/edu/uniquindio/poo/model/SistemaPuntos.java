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

  public List<Beneficio> obtenerBeneficiosDisponibles() {
    return beneficiosDisponibles.stream()
        .filter(Beneficio::isActivo)
        .collect(Collectors.toList());
  }

  public List<Beneficio> obtenerBeneficiosCanjeables(Cliente cliente) {
    return beneficiosDisponibles.stream()
        .filter(b -> b.puedeSerCanjeado(cliente))
        .collect(Collectors.toList());
  }

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

  public List<CanjeBeneficio> obtenerCanjesCliente(Cliente cliente) {
    return canjesClientes.getOrDefault(cliente, new ArrayList<>());
  }

  public void agregarBeneficio(Beneficio beneficio) {
    beneficiosDisponibles.add(beneficio);
  }

  public List<Beneficio> getBeneficiosDisponibles() {
    return beneficiosDisponibles;
  }

  public Map<Cliente, List<CanjeBeneficio>> getCanjesClientes() {
    return canjesClientes;
  }

  public String generarReporteBeneficios() {
    StringBuilder reporte = new StringBuilder();
    reporte.append("═══════════════════════════════════\n");
    reporte.append("BENEFICIOS DISPONIBLES\n");
    reporte.append("═══════════════════════════════════\n");

    beneficiosDisponibles.stream()
        .filter(Beneficio::isActivo)
        .forEach(b -> reporte.append(b.generarReporte()).append("\n"));

    reporte.append("═══════════════════════════════════\n");
    return reporte.toString();
  }
}