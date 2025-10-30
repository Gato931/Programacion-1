package co.edu.uniquindio.poo.model;

import java.util.LinkedList;

public class Empresa {
    private String nombre;
    private LinkedList<Empleado> listaEmpleados;

    public Empresa(String nombre) {
        this.nombre = nombre;
        listaEmpleados = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<Empleado> getEmpleados() {
        return listaEmpleados;
    }

    public void setEmpleados(LinkedList<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public boolean agregarEmpleado(Empleado empleado) {
        boolean centinela = false;
        if (!verificarEmpleado(empleado.getCedula())) {
            listaEmpleados.add(empleado);
            centinela = true;
        }
        return centinela;
    }

    public boolean eliminarEmpleado(String cedula) {
        boolean centinela = false;
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getCedula().equals(cedula)) {
                listaEmpleados.remove(empleado);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean actualizarEmpleado(String cedula, Empleado nuevoEmpleado) {
        boolean centinela = false;
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getCedula().equals(cedula)) {
                empleado.setCedula(nuevoEmpleado.getCedula());
                empleado.setNombre(nuevoEmpleado.getNombre());
                empleado.setApellido(nuevoEmpleado.getApellido());
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean verificarEmpleado(String cedula) {
        boolean centinela = false;
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getCedula().equals(cedula)) {
                centinela = true;
            }
        }
        return centinela;
    }

    public boolean validarUsuario(String usuario, String contraseña) {
        for (Empleado empleado : listaEmpleados) {
            if (empleado.validarUsuario(usuario, contraseña)) {
                return true;
            }
        }
        return false;
    }
}
