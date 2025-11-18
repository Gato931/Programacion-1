package co.edu.uniquindio.poo.model;

import java.util.List;

public interface Validable {
    boolean esValida();
    List<String> obtenerErroresValidacion();
}