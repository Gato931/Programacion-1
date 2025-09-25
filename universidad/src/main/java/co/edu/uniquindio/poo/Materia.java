package co.edu.uniquindio.poo;

import java.util.ArrayList;

public abstract class Materia {
    private String codigo;
    private String nombre;
    private int horasSemanales;
    private int creditos;
    private int semestre;
    private Profesor profesor;
    private ArrayList<Estudiante> listaEstudiantes;

    public Materia(String codigo, String nombre, int horasSemanales, int creditos, int semestre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.horasSemanales = horasSemanales;
        this.creditos = creditos;
        this.semestre = semestre;
        this.listaEstudiantes = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public ArrayList<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(ArrayList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public void inscribirEstudiante(Estudiante estudiante) {
        listaEstudiantes.add(estudiante);
    }

    public int calcularHorasTotales() {
        return horasSemanales;
    }
    
    public abstract int getTotalHoras();
}
