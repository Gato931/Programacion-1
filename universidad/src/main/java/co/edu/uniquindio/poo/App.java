package co.edu.uniquindio.poo;

public class App {
    public static void main(String[] args) {
        try {
            Carrera carrera = new Carrera("Ingeniería de Sistemas");

            Materia m1 = new MateriaTeorica("MAT101", "Matemáticas", 4, 3, 1);
            Materia m2 = new MateriaPractica("PRG102", "Programación", 3, 4, 1, 2, 5);

            carrera.registrarMateria(m1);
            carrera.registrarMateria(m2);

            Profesor p1 = new ProfesorPlanta("P01", "Ana", "PhD", 10, "Tiempo completo", true);
            carrera.asignarProfesor("PRG102", p1);

            Estudiante e1 = new EstudiantePregrado("E01", "Carlos", "123", "ISC", 1, true, 4.2);
            carrera.inscribirEstudiante("MAT101", e1);
            carrera.inscribirEstudiante("PRG102", e1);

            System.out.println("Total créditos de Carlos: " + carrera.totalCreditosEstudiante(e1));
            System.out.println("Horas semanales Programación: " + carrera.totalHorasMateria("PRG102"));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
