package co.edu.uniquindio.poo;

public class MateriaPractica extends Materia {
    private int horasPracticaSem;
    private int numeroLaboratorios;

    public MateriaPractica(String codigo, String nombre, int horasTeoriaSem, int creditos, int semestre,
            int horasPracticaSem, int numeroLaboratorios) {
        super(codigo, nombre, horasTeoriaSem, creditos, semestre);
        if (horasPracticaSem < 0)
            throw new IllegalArgumentException("Horas practicas invalidas");
        if (numeroLaboratorios < 0)
            throw new IllegalArgumentException("Numero laboratorios invalido");
        this.horasPracticaSem = horasPracticaSem;
        this.numeroLaboratorios = numeroLaboratorios;
    }

    public int getHorasPracticaSem() {
        return horasPracticaSem;
    }

    public int getNumeroLaboratorios() {
        return numeroLaboratorios;
    }

    public void setHorasPracticaSem(int horasPracticaSem) {
        if (horasPracticaSem < 0)
            throw new IllegalArgumentException("Horas practicas invalidas");
        this.horasPracticaSem = horasPracticaSem;
    }

    public void setNumeroLaboratorios(int numeroLaboratorios) {
        if (numeroLaboratorios < 0)
            throw new IllegalArgumentException("Numero laboratorios invalido");
        this.numeroLaboratorios = numeroLaboratorios;
    }

    @Override
    public int getTotalHorasSem() {
        return getHorasTeoriaSem() + horasPracticaSem;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (practica:%dh/sem, labs:%d)", horasPracticaSem, numeroLaboratorios);
    }
}
