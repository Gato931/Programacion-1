package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.List;

public interface Agendable {
    void programar(LocalDate fecha, String descripcion);

    List<AgendaItem> obtenerAgenda(LocalDate desde, LocalDate hasta);
}
