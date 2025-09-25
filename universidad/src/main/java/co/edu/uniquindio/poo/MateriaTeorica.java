package co.edu.uniquindio.poo;

public class MateriaTeorica extends Materia {
    public MateriaTeorica(String codigo, String nombre, int horasTeoria, int creditos, int semestre) {
        super(codigo, nombre, horasTeoria, creditos, semestre);
    }

    @Override
    public int getTotalHoras() {
        return getHorasSemanales();
    }
}
