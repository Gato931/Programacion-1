package co.edu.uniquindio.poo;

public class Paquete {
  private String codigo;
  private double peso;

  public Paquete(String codigo, double peso) {
    this.codigo = codigo;
    this.peso = peso;
  }

  public double getPeso() {
    return peso;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public void setPeso(double peso) {
    this.peso = peso;
  }
}
