package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Carrera {
    private String nombre;
    private final List<Materia> listaMaterias;
    private final List<Profesor> listaProfesores;
    private final List<Estudiante> listaEstudiantes;

    public Carrera(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre invalido");
        this.nombre = nombre;
        this.listaMaterias = new ArrayList<>();
        this.listaProfesores = new ArrayList<>();
        this.listaEstudiantes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre invalido");
        this.nombre = nombre;
    }

    public List<Materia> getListaMaterias() {
        return listaMaterias;
    }

    public List<Profesor> getListaProfesores() {
        return listaProfesores;
    }

    public List<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void registrarMateria(Materia materia) {
        if (materia == null)
            throw new IllegalArgumentException("Materia nula");
        for (Materia aux : listaMaterias) {
            if (aux.getCodigo().equals(materia.getCodigo())) {
                throw new IllegalArgumentException("Materia ya registrada con codigo: " + materia.getCodigo());
            }
        }
        listaMaterias.add(materia);
    }

    public void eliminarMateria(String codigo) {
        for (int i = 0; i < listaMaterias.size(); i++) {
            if (listaMaterias.get(i).getCodigo().equals(codigo)) {
                listaMaterias.remove(i);
                return;
            }
        }
        throw new NoSuchElementException("Materia no encontrada: " + codigo);
    }

    public Optional<Materia> buscarMateria(String codigo) {
        for (Materia aux : listaMaterias) {
            if (aux.getCodigo().equals(codigo))
                return Optional.of(aux);
        }
        return Optional.empty();
    }

    public List<Materia> listarMateriasPorSemestre(int semestre) {
        List<Materia> resultado = new ArrayList<>();
        for (Materia aux : listaMaterias) {
            if (aux.getSemestre() == semestre)
                resultado.add(aux);
        }
        return resultado;
    }

    public List<Materia> obtenerTodasLasMaterias() {
        return new ArrayList<>(listaMaterias);
    }

    public void registrarProfesor(Profesor profesor) {
        if (profesor == null)
            throw new IllegalArgumentException("Profesor nulo");
        for (Profesor aux : listaProfesores) {
            if (aux.getId().equals(profesor.getId())) {
                throw new IllegalArgumentException("Profesor ya registrado: " + profesor.getId());
            }
        }
        listaProfesores.add(profesor);
    }

    public void eliminarProfesor(String idProfesor) {
        for (int i = 0; i < listaProfesores.size(); i++) {
            if (listaProfesores.get(i).getId().equals(idProfesor)) {
                listaProfesores.remove(i);
                return;
            }
        }
        throw new NoSuchElementException("Profesor no encontrado: " + idProfesor);
    }

    public Optional<Profesor> buscarProfesor(String idProfesor) {
        for (Profesor aux : listaProfesores) {
            if (aux.getId().equals(idProfesor))
                return Optional.of(aux);
        }
        return Optional.empty();
    }

    public List<Profesor> obtenerTodosLosProfesores() {
        return new ArrayList<>(listaProfesores);
    }

    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante == null)
            throw new IllegalArgumentException("Estudiante nulo");
        for (Estudiante aux : listaEstudiantes) {
            if (aux.getId().equals(estudiante.getId())) {
                throw new IllegalArgumentException("Estudiante ya registrado: " + estudiante.getId());
            }
        }
        listaEstudiantes.add(estudiante);
    }

    public void eliminarEstudiante(String idEstudiante) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            if (listaEstudiantes.get(i).getId().equals(idEstudiante)) {
                listaEstudiantes.remove(i);

                for (Materia aux : listaMaterias) {
                    aux.desinscribir(idEstudiante);
                }
                return;
            }
        }
        throw new NoSuchElementException("Estudiante no encontrado: " + idEstudiante);
    }

    public Optional<Estudiante> buscarEstudiante(String idEstudiante) {
        for (Estudiante aux : listaEstudiantes) {
            if (aux.getId().equals(idEstudiante))
                return Optional.of(aux);
        }
        return Optional.empty();
    }

    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return new ArrayList<>(listaEstudiantes);
    }

    public void asignarProfesor(String codigoMateria, String idProfesor) {
        Materia materia = buscarMateria(codigoMateria)
                .orElseThrow(() -> new NoSuchElementException("Materia no encontrada: " + codigoMateria));
        Profesor profesor = buscarProfesor(idProfesor)
                .orElseThrow(() -> new NoSuchElementException("Profesor no encontrado: " + idProfesor));
        materia.asignarProfesor(profesor);
    }

    public void inscribirEstudiante(String codigoMateria, String idEstudiante) {
        Materia materia = buscarMateria(codigoMateria)
                .orElseThrow(() -> new NoSuchElementException("Materia no encontrada: " + codigoMateria));
        Estudiante estudiante = buscarEstudiante(idEstudiante)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado: " + idEstudiante));
        materia.inscribir(estudiante);
    }

    public List<Estudiante> listarEstudiantesMatriculados(String codigoMateria) {
        Materia materia = buscarMateria(codigoMateria)
                .orElseThrow(() -> new NoSuchElementException("Materia no encontrada: " + codigoMateria));
        return materia.obtenerEstudiantesInscritos();
    }

    public int totalHorasSemanasMateria(String codigoMateria) {
        Materia materia = buscarMateria(codigoMateria)
                .orElseThrow(() -> new NoSuchElementException("Materia no encontrada: " + codigoMateria));
        return materia.getTotalHorasSem();
    }

    public int totalCreditosEstudiante(String idEstudiante) {
        Estudiante estudiante = buscarEstudiante(idEstudiante)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado: " + idEstudiante));
        int total = 0;
        for (Materia auxMateria : listaMaterias) {
            List<Estudiante> listaEstudiantesInscritos = auxMateria.obtenerEstudiantesInscritos();
            for (Estudiante auxEstudiante : listaEstudiantesInscritos) {
                if (auxEstudiante.getId().equals(estudiante.getId())) {
                    total += auxMateria.getCreditos();
                    break;
                }
            }
        }
        return total;
    }

    public boolean desinscribirEstudiante(String codigoMateria, String idEstudiante) {
        Materia materia = buscarMateria(codigoMateria)
                .orElseThrow(() -> new NoSuchElementException("Materia no encontrada: " + codigoMateria));
        boolean removed = materia.desinscribir(idEstudiante);
        if (!removed)
            throw new NoSuchElementException("Estudiante no inscrito en la materia: " + idEstudiante);
        return true;
    }

    public List<Materia> materiasConProfesorAsignado() {
        List<Materia> respuesta = new ArrayList<>();
        for (Materia aux : listaMaterias) {
            if (aux.getProfesor() != null)
                respuesta.add(aux);
        }
        return respuesta;
    }

    public List<Materia> materiasPorProfesor(String idProfesor) {
        List<Materia> respuesta = new ArrayList<>();
        for (Materia aux : listaMaterias) {
            if (aux.getProfesor() != null && aux.getProfesor().getId().equals(idProfesor))
                respuesta.add(aux);
        }
        return respuesta;
    }

    public List<Estudiante> estudiantesConBecaPregrado() {
        List<Estudiante> respuesta = new ArrayList<>();
        for (Estudiante aux : listaEstudiantes) {
            if (aux instanceof EstudiantePregrado) {
                EstudiantePregrado estudiantePregrado = (EstudiantePregrado) aux;
                if (estudiantePregrado.isTieneBeca())
                    respuesta.add(aux);
            }
        }
        return respuesta;
    }
}