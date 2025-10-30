package co.edu.uniquindio.poo.model;

public class Empleado {
    private String cedula;
    private String nombre;
    private String apellido;
    private Usuario usuarioAsociado;

    public Empleado(String cedula, String nombre, String apellido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Usuario getUsuario() {
        return usuarioAsociado;
    }

    public void setUsuario(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }

    public boolean validarUsuario(String usuario, String contraseña) {
        return usuarioAsociado != null && usuarioAsociado.validarUsuario(usuario, contraseña);
    }
}