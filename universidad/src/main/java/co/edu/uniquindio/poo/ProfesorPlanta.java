package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

public class ProfesorPlanta extends Profesor {
    private String dedicacion;
    private List<String> proyectosInvestigacion;

    public ProfesorPlanta(String id, String nombre, String titulo, int añosExperiencia,
            String dedicacion, List<String> proyectosInvestigacion) {
        super(id, nombre, titulo, añosExperiencia);
        if (dedicacion == null || dedicacion.isBlank())
            throw new IllegalArgumentException("Dedicacion invalida");
        if (proyectosInvestigacion == null)
            throw new IllegalArgumentException("Proyectos invalidos");
        this.dedicacion =dedicacion;
        this.proyectosInvestigacion = new ArrayList<>(proyectosInvestigacion);
    }

    public String getDedicacion() {
        return dedicacion;
    }

    public List<String> getProyectosInvestigacion() {
        return new ArrayList<>(proyectosInvestigacion);
    }

    public void setDedicacion(String dedicacion) {
        if (dedicacion == null || dedicacion.isBlank())
            throw new IllegalArgumentException("Dedicacion invalida");
        this.dedicacion = dedicacion;
    }

    public void setProyectosInvestigacion(List<String> proyectosInvestigacion) {
        if (proyectosInvestigacion == null)
            throw new IllegalArgumentException("Proyectos invalidos");
        this.proyectosInvestigacion = proyectosInvestigacion;
    }

    public void agregarProyecto(String proyecto) {
        if (proyecto != null && !proyecto.isBlank())
            proyectosInvestigacion.add(proyecto);
    }

    @Override
    public String toString() {
        return super.toString() + " [Planta: " + dedicacion + "]";
    }

}
