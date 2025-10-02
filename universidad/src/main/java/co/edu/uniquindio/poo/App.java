package co.edu.uniquindio.poo;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;

public class App {

    private static Carrera carrera = new Carrera("Ingenieria de Sistemas");

    public static void main(String[] args) {
        while (true) {
            try {
                String menu = "Universidad\n\n" +
                        "1. Registrar profesor\n" +
                        "2. Registrar estudiante\n" +
                        "3. Registrar materia\n" +
                        "4. Asignar profesor a materia\n" +
                        "5. Inscribir estudiante en materia\n" +
                        "6. Desinscribir estudiante de materia\n" +
                        "7. Listar materias por semestre\n" +
                        "8. Listar estudiantes matriculados en una materia\n" +
                        "9. Total horas semanales de una materia\n" +
                        "10. Total creditos que cursa un estudiante\n" +
                        "11. Listar todas las materias / profesores / estudiantes\n" +
                        "12. Eliminar materia / profesor / estudiante\n" +
                        "0. Salir\n\n" +
                        "Ingrese el numero de la opcion: ";
                String input = JOptionPane.showInputDialog(null, menu, "Menu Universidad",
                        JOptionPane.QUESTION_MESSAGE);
                if (input == null)
                    break;
                int opcion = Integer.parseInt(input.trim());
                switch (opcion) {
                    case 1 -> registrarProfesor();
                    case 2 -> registrarEstudiante();
                    case 3 -> registrarMateria();
                    case 4 -> asignarProfesor();
                    case 5 -> inscribirEstudiante();
                    case 6 -> desinscribirEstudiante();
                    case 7 -> listarMateriasPorSemestre();
                    case 8 -> listarEstudiantesMateria();
                    case 9 -> totalHorasMateria();
                    case 10 -> totalCreditosEstudiante();
                    case 11 -> listarTodo();
                    case 12 -> eliminarEntidad();
                    case 0 -> {
                        JOptionPane.showMessageDialog(null, "Saliendo");
                        System.exit(0);
                    }
                    default -> JOptionPane.showMessageDialog(null, "Opcion invalida");
                }
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Entrada invalida. Debes ingresar un numero.");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
            }
        }
    }

    private static void registrarProfesor() {
        String tipo = JOptionPane.showInputDialog(null, "Tipo de profesor (1 = Planta, 2 = Cátedra):");
        if (tipo == null)
            return;
        try {
            int opcion = Integer.parseInt(tipo.trim());
            String id = solicitar("ID del profesor: ");
            String nombre = solicitar("Nombre completo: ");
            String titulo = solicitar("Título academico: ");
            int años = Integer.parseInt(solicitar("Años de experiencia: "));

            if (opcion == 1) {
                String dedicacion = solicitar("Dedicacion (Tiempo completo / Medio tiempo):");
                String proyectos = solicitar("Proyectos de investigación (separados por coma):");
                java.util.List<String> lista = new java.util.ArrayList<>();
                if (proyectos != null && !proyectos.isBlank()) {
                    for (String proyecto : proyectos.split(","))
                        lista.add(proyecto.trim());
                }
                ProfesorPlanta profesor = new ProfesorPlanta(id, nombre, titulo, años, dedicacion, lista);
                carrera.registrarProfesor(profesor);
                JOptionPane.showMessageDialog(null, "Profesor de planta registrado: " + profesor);
            } else if (opcion == 2) {
                int horas = Integer.parseInt(solicitar("Horas contratadas: "));
                String empresa = solicitar("Empresa externa: ");
                ProfesorCatedra profesor = new ProfesorCatedra(id, nombre, titulo, años, horas, empresa);
                carrera.registrarProfesor(profesor);
                JOptionPane.showMessageDialog(null, "Profesor de catedra registrado: " + profesor);
            } else {
                JOptionPane.showMessageDialog(null, "Tipo invalido");
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Número inválido");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null, "Error al registrar profesor: " + exception.getMessage());
        }
    }

    private static void registrarEstudiante() {
        String tipo = JOptionPane.showInputDialog(null, "Tipo de estudiante (1 = Pregrado, 2 = Posgrado):");
        if (tipo == null)
            return;
        try {
            int opcion = Integer.parseInt(tipo.trim());
            String id = solicitar("ID del estudiante: ");
            String nombre = solicitar("Nombre completo: ");
            String documento = solicitar("Documento de identidad: ");
            String programa = solicitar("Programa académico: ");
            int semestre = Integer.parseInt(solicitar("Semestre actual (numero):"));

            if (opcion == 1) {
                String becaString = solicitar("Tiene beca? (true/false): ");
                boolean tieneBeca = Boolean.parseBoolean(becaString.trim());
                double promedio = Double.parseDouble(solicitar("Promedio acumulado: "));
                EstudiantePregrado estudiantePregrado = new EstudiantePregrado(id, nombre, documento, programa, semestre, tieneBeca,
                        promedio);
                carrera.registrarEstudiante(estudiantePregrado);
                JOptionPane.showMessageDialog(null, "Estudiante de pregrado registrado: " + estudiantePregrado);
            } else if (opcion == 2) {
                String tipoPosgrado = solicitar("Tipo (Maestría/Doctorado): ");
                String tema = solicitar("Tema de investigacion: ");
                EstudiantePosgrado estudiantePosgrado = new EstudiantePosgrado(id, nombre, documento, programa, semestre, tipoPosgrado,
                        tema);
                carrera.registrarEstudiante(estudiantePosgrado);
                JOptionPane.showMessageDialog(null, "Estudiante de posgrado registrado: " + estudiantePosgrado);
            } else {
                JOptionPane.showMessageDialog(null, "Tipo invalido");
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Numero invalido");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null, "Error al registrar estudiante: " + exception.getMessage());
        }
    }

    private static void registrarMateria() {
        String tipo = JOptionPane.showInputDialog(null, "Tipo de materia (1 = Teórica, 2 = Practica):");
        if (tipo == null)
            return;
        try {
            int opcion = Integer.parseInt(tipo.trim());
            String codigo = solicitar("Codigo de la materia: ");
            String nombre = solicitar("Nombre de la materia: ");
            int horasTeoria = Integer.parseInt(solicitar("Horas de teoria por semana: "));
            int creditos = Integer.parseInt(solicitar("Créditos: "));
            int semestre = Integer.parseInt(solicitar("Semestre en que se dicta: "));

            if (opcion == 1) {
                Materia materia = new MateriaTeorica(codigo, nombre, horasTeoria, creditos, semestre);
                carrera.registrarMateria(materia);
                JOptionPane.showMessageDialog(null, "Materia teorica registrada: " + materia);
            } else if (opcion == 2) {
                int horasPractica = Integer.parseInt(solicitar("Horas de practica por semana: "));
                int laboratorios = Integer.parseInt(solicitar("Numero de laboratorios durante el semestre: "));
                Materia materia = new MateriaPractica(codigo, nombre, horasTeoria, creditos, semestre, horasPractica, laboratorios);
                carrera.registrarMateria(materia);
                JOptionPane.showMessageDialog(null, "Materia practica registrada: " + materia);
            } else {
                JOptionPane.showMessageDialog(null, "Tipo invalido.");
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Numero invalido");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null, "Error al registrar materia: " + exception.getMessage());
        }
    }

    private static void asignarProfesor() {
        try {
            String codigo = solicitar("Codigo de la materia: ");
            String idProfesor = solicitar("ID del profesor: ");
            carrera.asignarProfesor(codigo, idProfesor);
            JOptionPane.showMessageDialog(null, "Profesor asignado correctamente");
        } catch (NoSuchElementException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
        }
    }

    private static void inscribirEstudiante() {
        try {
            String codigo = solicitar("Codigo de la materia: ");
            String idEstudiante = solicitar("ID del estudiante: ");
            carrera.inscribirEstudiante(codigo, idEstudiante);
            JOptionPane.showMessageDialog(null, "Estudiante inscrito correctamente");
        } catch (NoSuchElementException | IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
        }
    }

    private static void desinscribirEstudiante() {
        try {
            String codigo = solicitar("Codigo de la materia: ");
            String idEstudiante = solicitar("ID del estudiante: ");
            carrera.desinscribirEstudiante(codigo, idEstudiante);
            JOptionPane.showMessageDialog(null, "Estudiante desinscrito correctamente");
        } catch (NoSuchElementException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
        } catch (UnsupportedOperationException exception) {

            JOptionPane.showMessageDialog(null, "Operación no soportada: " + exception.getMessage());
        }
    }

    private static void listarMateriasPorSemestre() {
        try {
            int semestre = Integer.parseInt(solicitar("Semestre a consultar:"));
            List<Materia> lista = carrera.listarMateriasPorSemestre(semestre);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay materias para el semestre " + semestre);
            } else {
                StringBuilder stringBuilder = new StringBuilder("Materias semestre " + semestre + ":\n");
                for (Materia materia : lista)
                    stringBuilder.append(materia).append("\n");
                JOptionPane.showMessageDialog(null, stringBuilder.toString());
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Numero invalido");
        }
    }

    private static void listarEstudiantesMateria() {
        try {
            String codigo = solicitar("Codigo de la materia: ");
            List<Estudiante> inscritos = carrera.listarEstudiantesMatriculados(codigo);
            if (inscritos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay estudiantes inscritos en " + codigo);
            } else {
                StringBuilder stringBuilder = new StringBuilder("Estudiantes en " + codigo + ":\n");
                for (Estudiante estudiante : inscritos)
                    stringBuilder.append(estudiante).append("\n");
                JOptionPane.showMessageDialog(null, stringBuilder.toString());
            }
        } catch (NoSuchElementException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
        }
    }

    private static void totalHorasMateria() {
        try {
            String codigo = solicitar("Codigo de la materia: ");
            int horas = carrera.totalHorasSemanasMateria(codigo);
            JOptionPane.showMessageDialog(null, "Horas semanales totales de " + codigo + ": " + horas);
        } catch (NoSuchElementException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
        }
    }

    private static void totalCreditosEstudiante() {
        try {
            String id = solicitar("ID del estudiante:");
            int creditos = carrera.totalCreditosEstudiante(id);
            JOptionPane.showMessageDialog(null, "Créditos totales que cursa " + id + ": " + creditos);
        } catch (NoSuchElementException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
        }
    }

    private static void listarTodo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Materias \n");
        for (Materia materia : carrera.obtenerTodasLasMaterias())
            stringBuilder.append(materia).append("\n");
        stringBuilder.append("\n Profesores \n");
        for (Profesor profesor : carrera.obtenerTodosLosProfesores())
            stringBuilder.append(profesor).append("\n");
        stringBuilder.append("\n Estudiantes \n");
        for (Estudiante estudiante : carrera.obtenerTodosLosEstudiantes())
            stringBuilder.append(estudiante).append("\n");
        JOptionPane.showMessageDialog(null, stringBuilder.toString());
    }

    private static void eliminarEntidad() {
        String tipo = JOptionPane.showInputDialog(null, "Eliminar (1 = Materia, 2 = Profesor, 3 = Estudiante):");
        if (tipo == null)
            return;
        try {
            int opcion = Integer.parseInt(tipo.trim());
            if (opcion == 1) {
                String codigo = solicitar("Codigo de la materia a eliminar: ");
                carrera.eliminarMateria(codigo);
                JOptionPane.showMessageDialog(null, "Materia eliminada: " + codigo);
            } else if (opcion == 2) {
                String id = solicitar("ID del profesor a eliminar: ");
                carrera.eliminarProfesor(id);
                JOptionPane.showMessageDialog(null, "Profesor eliminado: " + id);
            } else if (opcion == 3) {
                String id = solicitar("ID del estudiante a eliminar: ");
                carrera.eliminarEstudiante(id);
                JOptionPane.showMessageDialog(null, "Estudiante eliminado: " + id);
            } else {
                JOptionPane.showMessageDialog(null, "Opcion invalida");
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Numero inválido");
        } catch (NoSuchElementException | IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
        }
    }

    private static String solicitar(String mensaje) {
        String respuesta = JOptionPane.showInputDialog(null, mensaje);
        if (respuesta == null)
            throw new IllegalArgumentException("Operación cancelada");
        return respuesta.trim();
    }
}
