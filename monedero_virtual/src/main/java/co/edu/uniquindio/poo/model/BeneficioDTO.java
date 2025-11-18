package co.edu.uniquindio.poo.model;

public record BeneficioDTO(
    String id,
    String nombre,
    String descripcion,
    int puntosRequeridos,
    boolean disponible) {
  public String getDescripcionCompleta() {
    return String.format("%s - %d puntos%s",
        nombre,
        puntosRequeridos,
        disponible ? " Disponible " : " No disponible ");
  }
}