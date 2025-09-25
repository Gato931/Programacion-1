package co.edu.uniquindio.poo;

public abstract class Profesor {
    private String id;
    private String nombre;
    private String titulo;
    private int aniosExperiencia;

    public Profesor(String id, String nombre, String titulo, int aniosExperiencia) {
        this.id = id;
        this.nombre = nombre;
        this.titulo = titulo;
        this.aniosExperiencia = aniosExperiencia;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    @Override
    public String toString() {
        return nombre + " (" + titulo + ")";
    }
}
