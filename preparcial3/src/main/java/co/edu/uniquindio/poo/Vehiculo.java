package co.edu.uniquindio.poo;

public abstract class Vehiculo implements IPeajeCalculable {
  protected String placa;
  protected int peajesPagados;

  public Vehiculo(String placa, int peajesPagados) {
    this.placa = placa;
    this.peajesPagados = peajesPagados;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public int getPeajesPagados() {
    return peajesPagados;
  }

  public void setPeajesPagados(int peajesPagados) {
    this.peajesPagados = peajesPagados;
  }
}
