package co.edu.uniquindio.poo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Supervisor extends Empleado {
  private List<Vigilante> vigilantesSupervisados;
  private double bonoCoordinacion;

  public Supervisor(String nombre, String documento, Turno turno, double salarioBase,
      double bonoCoordinacion) {
    super(nombre, documento, turno, salarioBase);
    this.vigilantesSupervisados = new ArrayList<>();
    this.bonoCoordinacion = bonoCoordinacion;
  }

  @Override
  public double calcularSalarioTotal() {
    double total = getSalarioBase() + calcularPagoHorasExtras() + bonoCoordinacion;

    // Bono adicional por cantidad de vigilantes supervisados
    total += vigilantesSupervisados.size() * 50000; // $50,000 por vigilante

    return total;
  }

  // Ejercicio 8: Verificar si cumple con mínimo de vigilantes
  public boolean cumpleConMinimoVigilantes(int minimoRequerido) {
    return vigilantesSupervisados.size() >= minimoRequerido;
  }

  public void agregarVigilante(Vigilante vigilante) {
    vigilantesSupervisados.add(vigilante);
    registrarNovedad(new RegistroNovedad(
        LocalDateTime.now(),
        "ASIGNACION_VIGILANTE",
        "Vigilante asignado: " + vigilante.getNombre(),
        getNombre()));
  }

  public void removerVigilante(Vigilante vigilante) {
    vigilantesSupervisados.remove(vigilante);
  }

  public List<Vigilante> getVigilantesSupervisados() {
    return new ArrayList<>(vigilantesSupervisados);
  }

  public double getBonoCoordinacion() {
    return bonoCoordinacion;
  }

  public void setBonoCoordinacion(double bonoCoordinacion) {
    this.bonoCoordinacion = bonoCoordinacion;
  }
}