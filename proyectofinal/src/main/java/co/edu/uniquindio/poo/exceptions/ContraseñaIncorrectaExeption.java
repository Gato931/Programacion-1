package co.edu.uniquindio.poo.exceptions;

public class ContraseñaIncorrectaExeption extends RuntimeException {
  public ContraseñaIncorrectaExeption(String mensaje){
    super("Su contraseña es incorrecta" + mensaje);
  }
}
//throw new ContraseñaIncorrectaExeption(contraseña);