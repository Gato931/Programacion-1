package co.edu.uniquindio.poo.model;

public record ResumenClienteDTO(
    String nombreCliente,
    int puntosAcumulados,
    RangoCliente rango,
    int cantidadMonederos,
    double saldoTotal,
    int cantidadTransacciones) {
  public String generarReporte() {
    return String.format("""
        ═══════════════════════════════════
        RESUMEN DE CLIENTE
        ═══════════════════════════════════
        Cliente: %s
        Rango: %s %s
        Puntos: %d
        Monederos: %d
        Saldo Total: $%.2f
        Transacciones: %d
        ═══════════════════════════════════
        """,
        nombreCliente,
        rango.getIcono(),
        rango.getNombre(),
        puntosAcumulados,
        cantidadMonederos,
        saldoTotal,
        cantidadTransacciones);
  }
}