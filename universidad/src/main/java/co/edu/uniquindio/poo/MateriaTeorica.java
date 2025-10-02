package co.edu.uniquindio.poo;

public class MateriaTeorica extends Materia {
    public MateriaTeorica(String codigo, String nombre, int horasTeoriaSem, int creditos, int semestre) {
        super(codigo, nombre, horasTeoriaSem, creditos, semestre);
    }

    @Override
    public int getTotalHorasSem() {
        return getHorasTeoriaSem();
    }
}
