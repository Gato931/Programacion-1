package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalizadorPatrones {

  public static class EstadisticasGasto {
    private final double totalGastado;
    private final double promedioTransaccion;
    private final int cantidadTransacciones;
    private final double gastoMaximo;
    private final double gastoMinimo;
    private final Map<TipoTransaccion, Double> gastoPorTipo;

    public EstadisticasGasto(List<Transaccion> transacciones) {
      this.cantidadTransacciones = transacciones.size();

      List<Transaccion> completadas = transacciones.stream()
          .filter(t -> t.getEstado() == EstadoTransaccion.COMPLETADA)
          .toList();

      this.totalGastado = completadas.stream()
          .mapToDouble(Transaccion::getMonto)
          .sum();

      this.promedioTransaccion = cantidadTransacciones > 0 ? totalGastado / cantidadTransacciones : 0;

      this.gastoMaximo = completadas.stream()
          .mapToDouble(Transaccion::getMonto)
          .max()
          .orElse(0);

      this.gastoMinimo = completadas.stream()
          .mapToDouble(Transaccion::getMonto)
          .min()
          .orElse(0);

      this.gastoPorTipo = completadas.stream()
          .collect(Collectors.groupingBy(
              Transaccion::getTipo,
              Collectors.summingDouble(Transaccion::getMonto)));
    }

    public String generarReporte() {
      return String.format("""
          ═══════════════════════════════════
          ESTADISTICAS DE GASTO
          ═══════════════════════════════════
          Total Gastado: $%,.0f COP
          Promedio por Transaccion: $%,.0f COP
          Cantidad de Transacciones: %d
          Gasto Maximo: $%,.0f COP
          Gasto Minimo: $%,.0f COP

          Gasto por Tipo:
          %s
          ═══════════════════════════════════
          """,
          totalGastado,
          promedioTransaccion,
          cantidadTransacciones,
          gastoMaximo,
          gastoMinimo,
          formatearGastoPorTipo());
    }

    private String formatearGastoPorTipo() {
      return gastoPorTipo.entrySet().stream()
          .map(e -> String.format("  - %s: $%,.0f COP",
              e.getKey().getDescripcion(), e.getValue()))
          .collect(Collectors.joining("\n"));
    }

    public double getTotalGastado() {
      return totalGastado;
    }

    public double getPromedioTransaccion() {
      return promedioTransaccion;
    }

    public int getCantidadTransacciones() {
      return cantidadTransacciones;
    }

    public Map<TipoTransaccion, Double> getGastoPorTipo() {
      return new HashMap<>(gastoPorTipo);
    }
  }

  public static EstadisticasGasto analizarGastos(Cliente cliente) {
    List<Transaccion> todasTransacciones = new ArrayList<>();

    for (Monedero monedero : cliente.getMonederos()) {
      todasTransacciones.addAll(monedero.obtenerHistorial());
    }

    return new EstadisticasGasto(todasTransacciones);
  }

  public static Map<String, Double> analizarGastosPorPeriodo(
      Cliente cliente, LocalDateTime inicio, LocalDateTime fin) {

    Map<String, Double> gastosPorMes = new HashMap<>();

    for (Monedero monedero : cliente.getMonederos()) {
      List<Transaccion> enRango = monedero.obtenerHistorialEnRango(inicio, fin);

      for (Transaccion t : enRango) {
        if (t.getEstado() == EstadoTransaccion.COMPLETADA) {
          String mes = t.getFechaCreacion().getMonth().toString();
          gastosPorMes.merge(mes, t.getMonto(), Double::sum);
        }
      }
    }

    return gastosPorMes;
  }

  public static List<String> detectarPatronesInusuales(Cliente cliente) {
    List<String> alertas = new ArrayList<>();
    EstadisticasGasto stats = analizarGastos(cliente);

    if (stats.gastoMaximo > stats.promedioTransaccion * 5) {
      alertas.add(String.format(
          " Transacción inusualmente alta detectada: $%.2f",
          stats.gastoMaximo));
    }

    LocalDateTime hace24h = LocalDateTime.now().minusDays(1);
    long transaccionesRecientes = cliente.getMonederos().stream()
        .flatMap(m -> m.obtenerHistorial().stream())
        .filter(t -> t.getFechaCreacion().isAfter(hace24h))
        .count();

    if (transaccionesRecientes > 20) {
      alertas.add(String.format(
          " Alta actividad: %d transacciones en 24 horas",
          transaccionesRecientes));
    }

    return alertas;
  }
}