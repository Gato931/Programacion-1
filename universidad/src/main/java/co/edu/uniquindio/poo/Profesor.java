package co.edu.uniquindio.poo;

public abstract class Profesor {
    private String id;
    private String nombre;
    private String titulo;
    private int añosExperiencia;

    public Profesor(String id, String nombre, String titulo, int aniosExperiencia) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("ID invalido");
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre invalido");
        if (aniosExperiencia < 0)
            throw new IllegalArgumentException("Años de experiencia invalidos");
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Titulo invalido");
        this.id = id;
        this.nombre = nombre;
        this.titulo = titulo;
        this.añosExperiencia = aniosExperiencia;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAñosExperiencia() {
        return añosExperiencia;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre invalido");
        this.nombre = nombre;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Titulo invalido");
        this.titulo = titulo == null ? "" : titulo;
    }

    public void setAñosExperiencia(int años) {
        if (años < 0)
            throw new IllegalArgumentException("Años invalidos");
        this.añosExperiencia = años;
    }

    public void setId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("ID invalido");
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s (ID:%s) - %s, %d años", nombre, id, titulo, añosExperiencia);
    }

}
