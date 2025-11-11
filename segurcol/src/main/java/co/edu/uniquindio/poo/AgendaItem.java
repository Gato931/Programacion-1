package co.edu.uniquindio.poo;

import java.time.LocalDate;

public record AgendaItem(
    LocalDate fecha,
    String descripcion,
    String responsable) {
}
