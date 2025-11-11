package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Empleado implements Auditable, Agendable, Reporteable {
  private String nombre;
  private String documento;
  private Turno turno;
  private double salarioBase;
  private double tarifaHoraExtra;
  private double horasExtras;
  private List<Equipo> equiposAsignados;
  private List<RegistroNovedad> novedades;
  private List<AgendaItem> agenda;

  public Empleado(String nombre, String documento, Turno turno, double salarioBase) {
    this.nombre = nombre;
    this.documento = documento;
    this.turno = turno;
    this.salarioBase = salarioBase;
    this.tarifaHoraExtra = salarioBase / 240; // Tarifa por hora
    this.horasExtras = 0;
    this.equiposAsignados = new ArrayList<>();
    this.novedades = new ArrayList<>();
    this.agenda = new ArrayList<>();
  }

  // Método abstracto que cada tipo de empleado implementará
  public abstract double calcularSalarioTotal();

  // Ejercicio 1: Verificar si tiene turno nocturno
  public boolean tieneTurnoNocturno() {
    return this.turno == Turno.NOCHE;
  }

  // Ejercicio 9: Calcular salario con horas extras validadas
  public double calcularSalarioConHorasExtras(double horasExtras) {
    // Si las horas son negativas, se toma como cero
    double horasValidas = horasExtras < 0 ? 0 : horasExtras;
    this.horasExtras = horasValidas;
    return calcularSalarioTotal();
  }

  // Método común para calcular pago por horas extras
  protected double calcularPagoHorasExtras() {
    return horasExtras * tarifaHoraExtra * 1.5;
  }

  // Gestión de equipos
  public void asignarEquipo(Equipo equipo) {
    equiposAsignados.add(equipo);
    equipo.setEstado(EstadoEquipo.EN_USO);
    registrarNovedad(new RegistroNovedad(
        LocalDateTime.now(),
        "ASIGNACION_EQUIPO",
        "Equipo asignado: " + equipo.getTipo() + " [" + equipo.getCodigo() + "]",
        this.nombre));
  }

  // Ejercicio 12: Retirar equipo por código con resultado booleano
  public boolean retirarEquipoPorCodigo(String codigoEquipo) {
    Optional<Equipo> equipoOpt = equiposAsignados.stream()
        .filter(e -> e.getCodigo().equals(codigoEquipo))
        .findFirst();

    if (equipoOpt.isPresent()) {
      Equipo equipo = equipoOpt.get();
      equiposAsignados.remove(equipo);
      equipo.setEstado(EstadoEquipo.ALMACEN);
      registrarNovedad(new RegistroNovedad(
          LocalDateTime.now(),
          "DEVOLUCION_EQUIPO",
          "Equipo devuelto: " + equipo.getTipo() + " [" + equipo.getCodigo() + "]",
          this.nombre));
      return true;
    }
    return false;
  }

  // Ejercicio 7: Calcular valor total de equipos por tipo
  public double calcularValorEquiposPorTipo(TipoEquipo tipo) {
    return equiposAsignados.stream()
        .filter(e -> e.getTipo() == tipo && e.getEstado() == EstadoEquipo.EN_USO)
        .mapToDouble(Equipo::getValorReposicion)
        .sum();
  }

  public double calcularValorEquipos() {
    return equiposAsignados.stream()
        .mapToDouble(Equipo::getValorReposicion)
        .sum();
  }

  // Implementación de Auditable
  @Override
  public void registrarNovedad(RegistroNovedad novedad) {
    novedades.add(novedad);
  }

  @Override
  public List<RegistroNovedad> obtenerNovedades(LocalDateTime desde, LocalDateTime hasta) {
    return novedades.stream()
        .filter(n -> !n.fechaHora().isBefore(desde) && !n.fechaHora().isAfter(hasta))
        .collect(Collectors.toList());
  }

  // Implementación de Agendable
  @Override
  public void programar(LocalDate fecha, String descripcion) {
    agenda.add(new AgendaItem(fecha, descripcion, this.nombre));
  }

  @Override
  public List<AgendaItem> obtenerAgenda(LocalDate desde, LocalDate hasta) {
    return agenda.stream()
        .filter(a -> !a.fecha().isBefore(desde) && !a.fecha().isAfter(hasta))
        .collect(Collectors.toList());
  }

  // Ejercicio 11: Filtrar agenda por palabra clave
  public List<AgendaItem> filtrarAgendaPorPalabra(String palabraClave) {
    return agenda.stream()
        .filter(a -> a.descripcion().toLowerCase().contains(palabraClave.toLowerCase()))
        .collect(Collectors.toList());
  }

  // Implementación de Reporteable
  @Override
  public String generarReporte() {
    return String.format("""
        ══════════════════════════════════
        REPORTE DE EMPLEADO
        ══════════════════════════════════
        Tipo: %s
        Nombre: %s
        Documento: %s
        Turno: %s
        Salario Base: $%,.2f
        Salario Total: $%,.2f
        Equipos Asignados: %d
        Valor Equipos: $%,.2f
        ══════════════════════════════════
        """,
        getClass().getSimpleName(), nombre, documento, turno,
        salarioBase, calcularSalarioTotal(),
        equiposAsignados.size(), calcularValorEquipos());
  }

  // Getters y Setters
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public Turno getTurno() {
    return turno;
  }

  public void setTurno(Turno turno) {
    this.turno = turno;
  }

  public double getSalarioBase() {
    return salarioBase;
  }

  public void setSalarioBase(double salarioBase) {
    this.salarioBase = salarioBase;
  }

  public double getHorasExtras() {
    return horasExtras;
  }

  public void setHorasExtras(double horasExtras) {
    this.horasExtras = horasExtras;
  }

  public List<Equipo> getEquiposAsignados() {
    return new ArrayList<>(equiposAsignados);
  }

  public List<AgendaItem> getAgendaCompleta() {
    return new ArrayList<>(agenda);
  }

  @Override
  public String toString() {
    return String.format("%s - %s [%s] - Turno: %s",
        getClass().getSimpleName(), nombre, documento, turno);
  }
}