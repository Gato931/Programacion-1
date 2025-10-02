package co.edu.uniquindio.poo;

public class EstudiantePregrado extends Estudiante {
    private boolean tieneBeca;
    private double promedioAcumulado;

    public EstudiantePregrado(String id, String nombre, String documento, String programa, int semestreActual,
            boolean tieneBeca, double promedioAcumulado) {
        super(id, nombre, documento, programa, semestreActual);
        if (promedioAcumulado < 0.0)
            throw new IllegalArgumentException("Promedio invalido");
        this.tieneBeca = tieneBeca;
        this.promedioAcumulado = promedioAcumulado;
    }

    public boolean isTieneBeca() {
        return tieneBeca;
    }

    public double getPromedioAcumulado() {
        return promedioAcumulado;
    }

    public void setTieneBeca(boolean tieneBeca) {
        this.tieneBeca = tieneBeca;
    }

    public void setPromedioAcumulado(double promedio) {
        if (promedio < 0.0)
            throw new IllegalArgumentException("Promedio invalido");
        this.promedioAcumulado = promedio;
    }

    @Override
    public String toString() {
        return super.toString() + " [Pregrado, beca:" + tieneBeca + ", prom:" + promedioAcumulado + "]";
    }
}
