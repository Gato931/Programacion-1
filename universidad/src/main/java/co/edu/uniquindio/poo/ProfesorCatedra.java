package co.edu.uniquindio.poo;

public class ProfesorCatedra extends Profesor {
    private int horasContratadas;
    private String empresaExterna;

    public ProfesorCatedra(String id, String nombre, String titulo, int aniosExperiencia,
            int horasContratadas, String empresaExterna) {
        super(id, nombre, titulo, aniosExperiencia);
        this.horasContratadas = horasContratadas;
        this.empresaExterna = empresaExterna;
    }

    public int getHorasContratadas() {
        return horasContratadas;
    }

    public String getEmpresaExterna() {
        return empresaExterna;
    }
}
