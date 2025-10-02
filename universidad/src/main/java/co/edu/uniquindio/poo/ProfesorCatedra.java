package co.edu.uniquindio.poo;

public class ProfesorCatedra extends Profesor {
    private int horasContratadas;
    private String empresaExterna;

    public ProfesorCatedra(String id, String nombre, String titulo, int aniosExperiencia,
            int horasContratadas, String empresaExterna) {
        super(id, nombre, titulo, aniosExperiencia);
        if (horasContratadas < 0)
            throw new IllegalArgumentException("Horas contratadas invalidas");
        if (empresaExterna == null || empresaExterna.isBlank())
            throw new IllegalArgumentException("Empresa externa invalida");
        this.horasContratadas = horasContratadas;
        this.empresaExterna = empresaExterna;
    }

    public int getHorasContratadas() {
        return horasContratadas;
    }

    public String getEmpresaExterna() {
        return empresaExterna;
    }

    public void setHorasContratadas(int horas) {
        if (horas < 0)
            throw new IllegalArgumentException("Horas invalidas");
        this.horasContratadas = horas;
    }

    public void setEmpresaExterna(String empresa) {
        if (empresaExterna == null || empresaExterna.isBlank())
            throw new IllegalArgumentException("Empresa externa invalida");
        this.empresaExterna = empresa;
    }

    @Override
    public String toString() {
        return super.toString() + " [Catedra: " + horasContratadas + "h, " + empresaExterna + "]";
    }
}
