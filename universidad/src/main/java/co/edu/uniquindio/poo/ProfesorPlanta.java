package co.edu.uniquindio.poo;

public class ProfesorPlanta extends Profesor {
    private String dedicacion;
    private boolean participaInvestigacion;

    public ProfesorPlanta(String id, String nombre, String titulo, int aniosExperiencia,
            String dedicacion, boolean participaInvestigacion) {
        super(id, nombre, titulo, aniosExperiencia);
        this.dedicacion = dedicacion;
        this.participaInvestigacion = participaInvestigacion;
    }

    public String getDedicacion() {
        return dedicacion;
    }

    public boolean isParticipaInvestigacion() {
        return participaInvestigacion;
    }
}
