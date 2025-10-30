package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Empleado;
import co.edu.uniquindio.poo.model.Empresa;
import java.util.LinkedList;

public class EmpleadoController {

    Empresa empresa;

    public EmpleadoController(Empresa empresa) {
        this.empresa = empresa;
    }

    public void registrarEmpleado(String cedula, String nombre, String apellido) {
    Empleado empleado = new Empleado("123", "Juan", "Perez");
    empresa.agregarEmpleado(empleado);
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public boolean crearEmpleado(Empleado empleado) {
        return empresa.agregarEmpleado(empleado);
    }

    public LinkedList<Empleado> obtenerListaEmpleados() {
        return empresa.getEmpleados();
    }

    public boolean eliminarEmpleado(String cedula) {
        return empresa.eliminarEmpleado(cedula);
    }

    public boolean actualizarEmpleado(String cedula, Empleado nuevoEmpleado) {
        return empresa.actualizarEmpleado(cedula, nuevoEmpleado);
    }
}
