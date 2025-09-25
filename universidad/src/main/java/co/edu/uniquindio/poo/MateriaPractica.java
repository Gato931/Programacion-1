package co.edu.uniquindio.poo;

public class MateriaPractica extends Materia {
    private int horasPractica;
    private int numeroLaboratorios;

    public MateriaPractica(String codigo, String nombre, int horasTeoria, int creditos, int semestre,
            int horasPractica, int numeroLaboratorios) {
        super(codigo, nombre, horasTeoria, creditos, semestre);
        this.horasPractica = horasPractica;
        this.numeroLaboratorios = numeroLaboratorios;
    }

    public int getHorasPractica() {
        return horasPractica;
    }

    public void setHorasPractica(int horasPractica) {
        this.horasPractica = horasPractica;
    }

    public int getNumeroLaboratorios() {
        return numeroLaboratorios;
    }

    public void setNumeroLaboratorios(int numeroLaboratorios) {
        this.numeroLaboratorios = numeroLaboratorios;
    }

    @Override
    public int getTotalHoras() {
        return getHorasSemanales();
    }
}
