package co.edu.uniquindio.poo;

import java.time.LocalDateTime;

public record RegistroNovedad(
    LocalDateTime fechaHora,
    String tipo,
    String descripcion,
    String responsable) {
}
