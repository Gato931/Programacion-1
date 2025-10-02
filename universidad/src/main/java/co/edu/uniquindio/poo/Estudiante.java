package co.edu.uniquindio.poo;

public abstract class Estudiante {
    private String id;
    private String nombre;
    private String documento;
    private String programa;
    private int semestreActual;

    public Estudiante(String id, String nombre, String documento, String programa, int semestreActual) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("ID invalido");
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre invalido");
        if (semestreActual < 0)
            throw new IllegalArgumentException("Semestre invalido");
        if (documento == null || documento.isBlank())
            throw new IllegalArgumentException("Documento invalido");
        if (programa == null || programa.isBlank())
            throw new IllegalArgumentException("Programa invalido");
        this.id = id;
        this.nombre = nombre;
        this.documento = documento == null ? "" : documento;
        this.programa = programa == null ? "" : programa;
        this.semestreActual = semestreActual;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getPrograma() {
        return programa;
    }

    public int getSemestreActual() {
        return semestreActual;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre invalido");
        this.nombre = nombre;
    }

    public void setPrograma(String programa) {
        if (programa == null || programa.isBlank())
            throw new IllegalArgumentException("Programa invalido");
        this.programa = programa;
    }

    public void setSemestreActual(int semestre) {
        if (semestre < 0)
            throw new IllegalArgumentException("Semestre invalido");
        this.semestreActual = semestre;
    }

    @Override
    public String toString() {
        return String.format("%s (ID:%s) - %s, sem %d", nombre, id, programa, semestreActual);
    }
}
