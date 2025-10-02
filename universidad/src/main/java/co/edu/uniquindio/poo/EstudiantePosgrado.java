package co.edu.uniquindio.poo;

public class EstudiantePosgrado extends Estudiante {
    private String tipoPosgrado;
    private String temaInvestigacion;

    public EstudiantePosgrado(String id, String nombre, String documento, String programa, int semestreActual,
            String tipoPosgrado, String temaInvestigacion) {
        super(id, nombre, documento, programa, semestreActual);
        if (tipoPosgrado == null || tipoPosgrado.isBlank())
            throw new IllegalArgumentException("Tipo posgrado invalido");
        if (temaInvestigacion == null || temaInvestigacion.isBlank())
            throw new IllegalArgumentException("Tema investigacion invalido");
        this.tipoPosgrado = tipoPosgrado;
        this.temaInvestigacion = temaInvestigacion;
    }

    public String getTipoPosgrado() {
        if (tipoPosgrado == null || tipoPosgrado.isBlank())
            throw new IllegalArgumentException("Tipo posgrado invalido");
        return tipoPosgrado;
    }

    public String getTemaInvestigacion() {
        if (temaInvestigacion == null || temaInvestigacion.isBlank())
            throw new IllegalArgumentException("Tema investigacion invalido");
        return temaInvestigacion;
    }

    public void setTipoPosgrado(String tipo) {
        this.tipoPosgrado = tipo;
    }

    public void setTemaInvestigacion(String tema) {
        this.temaInvestigacion = tema;
    }

    @Override
    public String toString() {
        return super.toString() + " [Posgrado: " + tipoPosgrado + ", tema:" + temaInvestigacion + "]";
    }
}
