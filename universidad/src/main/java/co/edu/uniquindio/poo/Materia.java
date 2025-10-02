package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Materia {
    private final String codigo;
    private String nombre;
    private int horasTeoriaSem;
    private int creditos;
    private int semestre;
    private Profesor profesor;
    private List<Estudiante> estudiantesInscritos = new ArrayList<>();

    public Materia(String codigo, String nombre, int horasTeoriaSem, int creditos, int semestre) {
        if (codigo == null || codigo.isBlank())
            throw new IllegalArgumentException("Codigo invalido");
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre invalido");
        if (horasTeoriaSem < 0)
            throw new IllegalArgumentException("Horas teoria invalidas");
        if (creditos <= 0)
            throw new IllegalArgumentException("Creditos invalidos");
        if (semestre <= 0)
            throw new IllegalArgumentException("ID invalido");
        this.codigo = codigo;
        this.nombre = nombre;
        this.horasTeoriaSem = horasTeoriaSem;
        this.creditos = creditos;
        this.semestre = semestre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getHorasTeoriaSem() {
        return horasTeoriaSem;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getSemestre() {
        return semestre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre invalido");
        this.nombre = nombre;
    }

    public void setHorasTeoriaSem(int horas) {
        if (horas < 0)
            throw new IllegalArgumentException("Horas teoria invalidas");
        this.horasTeoriaSem = horas;
    }

    public void setCreditos(int creditos) {
        if (creditos <= 0)
            throw new IllegalArgumentException("Creditos invalidos");
        this.creditos = creditos;
    }

    public void setSemestre(int semestre) {
        if (semestre <= 0)
            throw new IllegalArgumentException("Semestre invalido");
        this.semestre = semestre;
    }

    public void asignarProfesor(Profesor profesor) {
        if (profesor == null)
            throw new IllegalArgumentException("Profesor nulo");
        this.profesor = profesor;
    }

    public List<Estudiante> obtenerEstudiantesInscritos() {
        return Collections.unmodifiableList(new ArrayList<>(estudiantesInscritos));
    }

    public void setEstudiantesInscritos(List<Estudiante> inscritos) {
        this.estudiantesInscritos = inscritos;
    }

    public void inscribir(Estudiante estudiante) {
        if (estudiante == null)
            throw new IllegalArgumentException("Estudiante nulo");
        for (Estudiante aux : estudiantesInscritos) {
            if (aux.getId().equals(estudiante.getId())) {
                throw new IllegalArgumentException("Estudiante ya inscrito en la materia");
            }
        }
        estudiantesInscritos.add(estudiante);
    }

    public boolean desinscribir(String idEstudiante) {
        if (idEstudiante == null || idEstudiante.isBlank())
            return false;
        for (int i = 0; i < estudiantesInscritos.size(); i++) {
            if (estudiantesInscritos.get(i).getId().equals(idEstudiante)) {
                estudiantesInscritos.remove(i);
                return true;
            }
        }
        return false;
    }

    public abstract int getTotalHorasSem();

    @Override
    public String toString() {
        return String.format("%s [%s] - sem:%d - hrsTeoria:%d - creditos:%d - prof:%s",
                nombre, codigo, semestre, horasTeoriaSem, creditos,
                (profesor == null ? "SIN" : profesor.getNombre()));
    }

}
