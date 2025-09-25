package co.edu.uniquindio.poo;

public class EstudiantePregrado extends Estudiante {
    private boolean tieneBeca;
    private double promedio;

    public EstudiantePregrado(String id, String nombre, String documento, String programa, int semestre,
            boolean tieneBeca, double promedio) {
        super(id, nombre, documento, programa, semestre);
        this.tieneBeca = tieneBeca;
        this.promedio = promedio;
    }

    public boolean isTieneBeca() {
        return tieneBeca;
    }

    public double getPromedio() {
        return promedio;
    }
}
