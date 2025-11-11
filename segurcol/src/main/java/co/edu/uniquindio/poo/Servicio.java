package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Servicio implements Auditable, Agendable, Reporteable, Costeable {
  private String codigoContrato;
  private String cliente;
  private double tarifaMensual;
  private EstadoServicio estado;
  private List<Empleado> personalAsignado;
  private List<Equipo> equiposAsignados;
  private List<RegistroNovedad> novedades;
  private List<AgendaItem> agenda;

  public Servicio(String codigoContrato, String cliente, double tarifaMensual) {
    this.codigoContrato = codigoContrato;
    this.cliente = cliente;
    this.tarifaMensual = tarifaMensual;
    this.estado = EstadoServicio.ACTIVO;
    this.personalAsignado = new ArrayList<>();
    this.equiposAsignados = new ArrayList<>();
    this.novedades = new ArrayList<>();
    this.agenda = new ArrayList<>();
  }

  // Método abstracto - Polimorfismo
  public abstract double calcularCostoMensual();

  // Implementación de Costeable
  @Override
  public double obtenerCostoMensual() {
    return calcularCostoMensual();
  }

  // Gestión de personal
  public void asignarPersonal(Empleado empleado) {
    personalAsignado.add(empleado);
    registrarNovedad(new RegistroNovedad(
        LocalDateTime.now(),
        "ASIGNACION_PERSONAL",
        "Personal asignado: " + empleado.getNombre(),
        "SISTEMA"));
  }

  public void removerPersonal(Empleado empleado) {
    personalAsignado.remove(empleado);
  }

  // Ejercicio 14: Reemplazar vigilante en servicio
  public boolean reemplazarVigilante(Vigilante incapacitado, Vigilante reemplazo,
      LocalDate desde, LocalDate hasta) {
    // Verificar que el incapacitado está asignado
    if (!personalAsignado.contains(incapacitado)) {
      return false;
    }

    // Verificar que tienen la misma configuración
    if (incapacitado.getTipoArma() != reemplazo.getTipoArma() ||
        incapacitado.getTurno() != reemplazo.getTurno()) {
      return false;
    }

    // Remover incapacitado y agregar reemplazo
    removerPersonal(incapacitado);
    asignarPersonal(reemplazo);

    // Copiar agenda del rango
    List<AgendaItem> agendaIncapacitado = incapacitado.obtenerAgenda(desde, hasta);
    for (AgendaItem item : agendaIncapacitado) {
      reemplazo.programar(item.fecha(), item.descripcion());
    }

    // Registrar novedades
    registrarNovedad(new RegistroNovedad(
        LocalDateTime.now(),
        "REEMPLAZO_VIGILANTE",
        String.format("Vigilante %s reemplazado por %s",
            incapacitado.getNombre(), reemplazo.getNombre()),
        "SISTEMA"));

    return true;
  }

  // Gestión de equipos
  public void asignarEquipo(Equipo equipo) {
    equiposAsignados.add(equipo);
    registrarNovedad(new RegistroNovedad(
        LocalDateTime.now(),
        "ASIGNACION_EQUIPO_SERVICIO",
        "Equipo asignado al servicio: " + equipo.getTipo(),
        "SISTEMA"));
  }

  public void removerEquipo(Equipo equipo) {
    equiposAsignados.remove(equipo);
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
    agenda.add(new AgendaItem(fecha, descripcion, this.cliente));
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
        REPORTE DE SERVICIO
        ══════════════════════════════════
        Tipo: %s
        Código Contrato: %s
        Cliente: %s
        Estado: %s
        Tarifa Mensual: $%,.2f
        Costo Mensual: $%,.2f
        Personal Asignado: %d
        Equipos Asignados: %d
        ══════════════════════════════════
        """,
        getClass().getSimpleName(), codigoContrato, cliente, estado,
        tarifaMensual, calcularCostoMensual(),
        personalAsignado.size(), equiposAsignados.size());
  }

  // Getters y Setters
  public String getCodigoContrato() {
    return codigoContrato;
  }

  public void setCodigoContrato(String codigoContrato) {
    this.codigoContrato = codigoContrato;
  }

  public String getCliente() {
    return cliente;
  }

  public void setCliente(String cliente) {
    this.cliente = cliente;
  }

  public double getTarifaMensual() {
    return tarifaMensual;
  }

  public void setTarifaMensual(double tarifaMensual) {
    this.tarifaMensual = tarifaMensual;
  }

  public EstadoServicio getEstado() {
    return estado;
  }

  public void setEstado(EstadoServicio estado) {
    this.estado = estado;
  }

  public List<Empleado> getPersonalAsignado() {
    return new ArrayList<>(personalAsignado);
  }

  public List<Equipo> getEquiposAsignados() {
    return new ArrayList<>(equiposAsignados);
  }

  @Override
  public String toString() {
    return String.format("%s - Contrato: %s - Cliente: %s - Estado: %s",
        getClass().getSimpleName(), codigoContrato, cliente, estado);
  }
}
