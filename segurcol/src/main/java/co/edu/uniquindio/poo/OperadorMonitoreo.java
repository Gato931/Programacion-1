package co.edu.uniquindio.poo;

public class OperadorMonitoreo extends Empleado {
  private int numeroZonasMonitoreadas;

  public OperadorMonitoreo(String nombre, String documento, Turno turno, double salarioBase,
      int numeroZonasMonitoreadas) {
    super(nombre, documento, turno, salarioBase);
    this.numeroZonasMonitoreadas = numeroZonasMonitoreadas;
  }

  @Override
  public double calcularSalarioTotal() {
    double total = getSalarioBase() + calcularPagoHorasExtras();

    // Bono por zonas monitoreadas
    total += numeroZonasMonitoreadas * 30000; // $30,000 por zona

    return total;
  }

  public int getNumeroZonasMonitoreadas() {
    return numeroZonasMonitoreadas;
  }

  public void setNumeroZonasMonitoreadas(int numeroZonasMonitoreadas) {
    this.numeroZonasMonitoreadas = numeroZonasMonitoreadas;
  }
}
