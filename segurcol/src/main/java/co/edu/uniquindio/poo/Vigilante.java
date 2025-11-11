package co.edu.uniquindio.poo;

import java.time.LocalDateTime;

public class Vigilante extends Empleado {
  private int numeroPuesto;
  private TipoArma tipoArma;

  public Vigilante(String nombre, String documento, Turno turno, double salarioBase,
      int numeroPuesto, TipoArma tipoArma) {
    super(nombre, documento, turno, salarioBase);
    this.numeroPuesto = numeroPuesto;
    this.tipoArma = tipoArma;
  }

  @Override
  public double calcularSalarioTotal() {
    double total = getSalarioBase() + calcularPagoHorasExtras();

    // Bono por tipo de arma
    if (tipoArma == TipoArma.LETAL) {
      total += getSalarioBase() * 0.15; // 15% adicional por arma letal
    } else if (tipoArma == TipoArma.NO_LETAL) {
      total += getSalarioBase() * 0.05; // 5% adicional por arma no letal
    }

    return total;
  }

  // Ejercicio 19: Validar configuración arma-turno
  public boolean esConfiguracionValida() {
    // Un vigilante con arma letal solo puede trabajar en turno noche
    if (tipoArma == TipoArma.LETAL && getTurno() == Turno.DIA) {
      return false;
    }
    return true;
  }

  // Ejercicio 15: Cambiar turno con validación
  public boolean cambiarTurnoConValidacion(Turno nuevoTurno) {
    Turno turnoAnterior = getTurno();
    setTurno(nuevoTurno);

    // Verificar si la nueva configuración es válida
    if (!esConfiguracionValida()) {
      setTurno(turnoAnterior); // Revertir cambio
      return false;
    }

    registrarNovedad(new RegistroNovedad(
        LocalDateTime.now(),
        "CAMBIO_TURNO",
        "Turno cambiado de " + turnoAnterior + " a " + nuevoTurno,
        getNombre()));
    return true;
  }

  @Override
  public String generarReporte() {
    return String.format("""
        ══════════════════════════════════
        REPORTE DE VIGILANTE
        ══════════════════════════════════
        Nombre: %s
        Documento: %s
        Puesto: %d
        Tipo Arma: %s
        Turno: %s
        Configuración Válida: %s
        Salario Total: $%,.2f
        Equipos Asignados: %d
        ══════════════════════════════════
        """,
        getNombre(), getDocumento(), numeroPuesto, tipoArma, getTurno(),
        esConfiguracionValida() ? "SÍ" : "NO",
        calcularSalarioTotal(), getEquiposAsignados().size());
  }

  public int getNumeroPuesto() {
    return numeroPuesto;
  }

  public void setNumeroPuesto(int numeroPuesto) {
    this.numeroPuesto = numeroPuesto;
  }

  public TipoArma getTipoArma() {
    return tipoArma;
  }

  public void setTipoArma(TipoArma tipoArma) {
    this.tipoArma = tipoArma;
  }
}
