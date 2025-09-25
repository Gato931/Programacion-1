package co.edu.uniquindio.poo;

import java.util.ArrayList;

public class Carrera {
    private String nombre;
    private ArrayList<Materia> materias;

    public Carrera(String nombre) {
        this.nombre = nombre;
        this.materias = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

    public void registrarMateria(Materia materia) throws Exception {
        for (Materia aux : materias) {
            if (aux.getCodigo().equals(aux.getCodigo())) {
                throw new Exception("La materia ya existe con el codigo: " + aux.getCodigo());
            }
        }
        materias.add(materia);
    }

    public ArrayList<Materia> listarMateriasPorSemestre(int semestre) {
        ArrayList<Materia> resultado = new ArrayList<>();
        for (Materia aux : materias) {
            if (aux.getSemestre() == semestre) {
                resultado.add(aux);
            }
        }
        return resultado;
    }

    public void asignarProfesor(String codigo, Profesor profesor) throws Exception {
        Materia materia = buscarMateria(codigo);
        materia.setProfesor(profesor);
    }

    public void inscribirEstudiante(String codigo, Estudiante estudiante) throws Exception {
        Materia materia = buscarMateria(codigo);
        materia.inscribirEstudiante(estudiante);
    }

    public ArrayList<Estudiante> listarEstudiantes(String codigo) throws Exception {
        Materia materia = buscarMateria(codigo);
        return materia.getListaEstudiantes();
    }

    public int totalHorasMateria(String codigo) throws Exception {
        Materia materia = buscarMateria(codigo);
        return materia.getTotalHoras();
    }

    public int totalCreditosEstudiante(Estudiante estudiante) {
        int total = 0;
        for (Materia aux : materias) {
            if (aux.getListaEstudiantes().contains(estudiante)) {
                total += aux.getCreditos();
            }
        }
        return total;
    }

    private Materia buscarMateria(String codigo) throws Exception {
        for (Materia aux : materias) {
            if (aux.getCodigo().equals(codigo)) {
                return aux;
            }
        }
        throw new Exception("Materia no encontrada: " + codigo);
    }
}
