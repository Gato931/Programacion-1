package co.edu.uniquindio.poo;

public class EstudiantePosgrado extends Estudiante {
    private String tipoPosgrado;
    private String temaInvestigacion;

    public EstudiantePosgrado(String id, String nombre, String documento, String programa, int semestre,
            String tipoPosgrado, String temaInvestigacion) {
        super(id, nombre, documento, programa, semestre);
        this.tipoPosgrado = tipoPosgrado;
        this.temaInvestigacion = temaInvestigacion;
    }

    public String getTipoPosgrado() {
        return tipoPosgrado;
    }

    public void setTipoPosgrado(String tipoPosgrado) {
        this.tipoPosgrado = tipoPosgrado;
    }

    public String getTemaInvestigacion() {
        return temaInvestigacion;
    }

    public void setTemaInvestigacion(String temaInvestigacion) {
        this.temaInvestigacion = temaInvestigacion;
    }

}
