package co.edu.uniquindio.poo;

import java.time.LocalDateTime;
import java.util.List;

public interface Auditable {
    void registrarNovedad(RegistroNovedad novedad);
    List<RegistroNovedad> obtenerNovedades(LocalDateTime desde, LocalDateTime hasta);
}