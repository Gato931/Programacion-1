package co.edu.uniquindio.poo;

public class Niño {
  private String nombre;
  private String edad;
  private String genero;
  private String documento;
  private String alergias;
  private String acudiente;
  private String numeroAcudiente;

  public Niño(String nombre, String edad, String genero, String documento, String alergias, String acudiente,
      String numeroAcudiente) {
    this.nombre = nombre;
    this.edad = edad;
    this.genero = genero;
    this.documento = documento;
    this.alergias = alergias;
    this.acudiente = acudiente;
    this.numeroAcudiente = numeroAcudiente;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEdad() {
    return edad;
  }

  public void setEdad(String edad) {
    this.edad = edad;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public String getAlergias() {
    return alergias;
  }

  public void setAlergias(String alergias) {
    this.alergias = alergias;
  }

  public String getAcudiente() {
    return acudiente;
  }

  public void setAcudiente(String acudiente) {
    this.acudiente = acudiente;
  }

  public String getNumeroAcudiente() {
    return numeroAcudiente;
  }

  public void setNumeroAcudiente(String numeroAcudiente) {
    this.numeroAcudiente = numeroAcudiente;
  }

  @Override
  public String toString() {
    return "Nombre = " + nombre + ", Edad = " + edad + ", Genero = " + genero + ", Documento = " + documento
        + ", Alergias = " + alergias + ", Acudiente = " + acudiente + ", Numero del Acudiente = " + numeroAcudiente + "\n";
  }

}
