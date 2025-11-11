package co.edu.uniquindio.poo;

public class MonitoreoRemoto extends Servicio {
  private int numeroDispositivos;
  private boolean monitoreo24h;

  public MonitoreoRemoto(String codigoContrato, String cliente, double tarifaMensual,
      int numeroDispositivos, boolean monitoreo24h) {
    super(codigoContrato, cliente, tarifaMensual);
    this.numeroDispositivos = numeroDispositivos;
    this.monitoreo24h = monitoreo24h;
  }

  @Override
  public double calcularCostoMensual() {
    double costo = getTarifaMensual();

    // Costo por dispositivo
    costo += numeroDispositivos * 150000; // $150,000 por dispositivo

    // Incremento por monitoreo 24h
    if (monitoreo24h) {
      costo *= 1.5; // 50% adicional
    }

    return costo;
  }

  // Ejercicio 13: Verificar si es servicio "grande"
  public boolean esServicioGrande(int umbralDispositivos) {
    return numeroDispositivos > umbralDispositivos;
  }

  public int getNumeroDispositivos() {
    return numeroDispositivos;
  }

  public void setNumeroDispositivos(int numeroDispositivos) {
    this.numeroDispositivos = numeroDispositivos;
  }

  public boolean isMonitoreo24h() {
    return monitoreo24h;
  }

  public void setMonitoreo24h(boolean monitoreo24h) {
    this.monitoreo24h = monitoreo24h;
  }
}