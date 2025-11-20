package co.edu.uniquindio.poo.model;

public record BeneficioDTO(
    String id,
    String nombre,
    String descripcion,
    int puntosRequeridos,
    boolean disponible) {
  /**
   * Genera una descripción completa del beneficio incluyendo nombre,
   * puntos requeridos y disponibilidad
   * 
   * @return String formateado con la descripción completa
   */
  public String getDescripcionCompleta() {
    return String.format("%s - %d puntos%s",
        nombre,
        puntosRequeridos,
        disponible ? " Disponible " : " No disponible ");
  }
}