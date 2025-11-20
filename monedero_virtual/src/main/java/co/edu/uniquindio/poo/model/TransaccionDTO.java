package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;

public record TransaccionDTO(
    String id,
    TipoTransaccion tipo,
    double monto,
    LocalDateTime fecha,
    EstadoTransaccion estado,
    String origen,
    String destino,
    String descripcion,
    int puntosGenerados) {
  /**
   * Genera un resumen corto de la transacción con ID, tipo, monto y estado
   * 
   * @return String formateado con información resumida
   */
  public String getResumen() {
    return String.format("[%s] %s: $%,.0f COP - %s",
        id, tipo.getDescripcion(), monto, estado.getIcono());
  }
}