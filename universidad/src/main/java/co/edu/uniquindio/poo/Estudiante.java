package co.edu.uniquindio.poo;

public abstract class Estudiante {
    private String id;
    private String nombre;
    private String documento;
    private String programa;
    private int semestre;

    public Estudiante(String id, String nombre, String documento, String programa, int semestre) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.programa = programa;
        this.semestre = semestre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return nombre + " (" + programa + ")";
    }
}
