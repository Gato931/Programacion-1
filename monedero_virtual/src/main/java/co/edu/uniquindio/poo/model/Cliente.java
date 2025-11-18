package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Cliente implements Puntuable, Notificable, Reporteable {

  private final String id;
  private String nombre;
  private String email;
  private String telefono;
  private int puntosAcumulados;
  private RangoCliente rangoActual;
  private final List<Monedero> monederos;
  private final List<String> notificaciones;
  private final LocalDateTime fechaRegistro;
  private boolean activo;
  private List<CanjeBeneficio> beneficiosActivos;

  public Cliente(String nombre, String email, String telefono) {
    if (nombre == null || nombre.trim().isEmpty()) {
      throw new IllegalArgumentException("El nombre es requerido");
    }
    if (email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("El email es requerido");
    }

    this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.nombre = nombre;
    this.email = email;
    this.telefono = telefono;
    this.puntosAcumulados = 0;
    this.rangoActual = RangoCliente.BRONCE;
    this.monederos = new ArrayList<>();
    this.notificaciones = new ArrayList<>();
    this.fechaRegistro = LocalDateTime.now();
    this.activo = true;
    this.beneficiosActivos = new ArrayList<>();

    crearMonedero(TipoMonedero.PRINCIPAL);
  }

  public Monedero crearMonedero(TipoMonedero tipo) {

    Optional<Monedero> existente = monederos.stream()
        .filter(m -> m.getTipo() == tipo)
        .findFirst();

    if (existente.isPresent()) {
      throw new IllegalStateException("Ya existe un monedero de tipo " + tipo.getNombre());
    }

    Monedero nuevoMonedero = new Monedero(tipo, this);
    monederos.add(nuevoMonedero);

    enviarNotificacion(
        "Nuevo monedero creado: " + tipo.getNombre(),
        TipoNotificacion.TRANSACCION_COMPLETADA);

    return nuevoMonedero;
  }

  public Optional<Monedero> obtenerMonedero(TipoMonedero tipo) {
    return monederos.stream()
        .filter(m -> m.getTipo() == tipo)
        .findFirst();
  }

  public Monedero getMonederoPrincipal() {
    return obtenerMonedero(TipoMonedero.PRINCIPAL)
        .orElseThrow(() -> new IllegalStateException("No existe monedero principal"));
  }

  public double calcularSaldoTotal() {
    return monederos.stream()
        .mapToDouble(Monedero::getSaldo)
        .sum();
  }

  public int calcularTotalTransacciones() {
    return monederos.stream()
        .mapToInt(m -> m.obtenerHistorial().size())
        .sum();
  }

  public ResumenClienteDTO generarResumenDTO() {
    return new ResumenClienteDTO(
        this.nombre,
        this.puntosAcumulados,
        this.rangoActual,
        this.monederos.size(),
        calcularSaldoTotal(),
        calcularTotalTransacciones());
  }

  @Override
  public void acumularPuntos(int puntos) {
    if (puntos > 0) {
      int puntosAnteriores = this.puntosAcumulados;
      this.puntosAcumulados += puntos;

      RangoCliente rangoAnterior = this.rangoActual;
      this.rangoActual = calcularRango();

      if (rangoAnterior != rangoActual) {
        enviarNotificacion(
            String.format("Felicidades! Has alcanzado el rango %s",
                rangoActual.getNombre()),
            TipoNotificacion.NUEVO_RANGO);
      }

      enviarNotificacion(
          String.format("Puntos antes: %d, puntos ahora: %d",
              puntosAnteriores, puntosAcumulados),
          TipoNotificacion.PUNTOS_ACUMULADOS);
    }
  }

  @Override
  public boolean canjearPuntos(int puntos) {
    if (puntos > 0 && puntosAcumulados >= puntos) {
      this.puntosAcumulados -= puntos;
      this.rangoActual = calcularRango();
      return true;
    }
    return false;
  }

  @Override
  public RangoCliente calcularRango() {
    return RangoCliente.obtenerRangoPorPuntos(puntosAcumulados);
  }

  @Override
  public void enviarNotificacion(String mensaje, TipoNotificacion tipo) {

    LocalDateTime ahora = LocalDateTime.now();
    String hora = String.format("%02d:%02d", ahora.getHour(), ahora.getMinute());

    String notificacion = String.format("[%s] %s %s: %s",
        hora,
        tipo.getIcono(),
        tipo.getDescripcion(),
        mensaje);

    notificaciones.add(notificacion);
  }

  @Override
  public List<String> obtenerNotificaciones() {
    return new ArrayList<>(notificaciones);
  }

  @Override
  public void marcarNotificacionComoLeida(int indice) {
    if (indice >= 0 && indice < notificaciones.size()) {
      notificaciones.remove(indice);
    }
  }

  public void verificarSaldoBajo() {
    monederos.stream()
        .filter(m -> m.getSaldo() < m.getSaldoMinimo() + 100)
        .forEach(m -> enviarNotificacion(
            String.format("Saldo bajo en monedero %s: %s",
                m.getTipo().getNombre(),
                formatearCOP(m.getSaldo())),
            TipoNotificacion.SALDO_BAJO));
  }


  public static String formatearCOP(double monto) {
    return String.format("$%,.0f COP", monto);
  }

  @Override
  public String generarReporte() {
    return String.format("%s - Puntos: %d - Rango: %s %s",
        nombre,
        puntosAcumulados,
        rangoActual.getIcono(),
        rangoActual.getNombre());
  }

  @Override
  public String generarReporteDetallado() {
    return String.format("""
        ═══════════════════════════════════
        REPORTE DE CLIENTE
        ═══════════════════════════════════
        ID: %s
        Nombre: %s
        Email: %s
        Telefono: %s
        Rango: %s %s
        Puntos Acumulados: %d
        Monederos: %d
        Saldo Total: %s
        Transacciones Totales: %d
        Fecha Registro: %s
        Estado: %s
        ═══════════════════════════════════
        """,
        id,
        nombre,
        email,
        telefono,
        rangoActual.getIcono(),
        rangoActual.getNombre(),
        puntosAcumulados,
        monederos.size(),
        formatearCOP(calcularSaldoTotal()),
        calcularTotalTransacciones(),
        fechaRegistro.toLocalDate(),
        activo ? "Activo" : "Inactivo");
  }

  public List<CanjeBeneficio> getBeneficiosActivos() {
    return new ArrayList<>(beneficiosActivos);
  }

  public boolean tieneBeneficioActivo(String tipoBeneficio) {
    return beneficiosActivos.stream()
        .anyMatch(c -> c.getBeneficio().getTipo().name().equals(tipoBeneficio)
            && !c.isAplicado());
  }

  public Optional<CanjeBeneficio> obtenerBeneficioActivo(String tipoBeneficio) {
    return beneficiosActivos.stream()
        .filter(c -> c.getBeneficio().getTipo().name().equals(tipoBeneficio)
            && !c.isAplicado())
        .findFirst();
  }

  public String getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getEmail() {
    return email;
  }

  public String getTelefono() {
    return telefono;
  }

  @Override
  public int getPuntosAcumulados() {
    return puntosAcumulados;
  }

  public void setPuntosAcumulados(int puntosAcumulados) {
    this.puntosAcumulados = puntosAcumulados;
  }

  public void setRangoActual(RangoCliente rangoActual) {
    this.rangoActual = rangoActual;
  }

  public List<String> getNotificaciones() {
    return notificaciones;
  }

  public RangoCliente getRangoActual() {
    return rangoActual;
  }

  public List<Monedero> getMonederos() {
    return new ArrayList<>(monederos);
  }

  public LocalDateTime getFechaRegistro() {
    return fechaRegistro;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }

  @Override
  public String toString() {
    return generarReporte();
  }

  public void setBeneficiosActivos(List<CanjeBeneficio> beneficiosActivos) {
    this.beneficiosActivos = beneficiosActivos;
  }

  public void agregarBeneficioActivo(CanjeBeneficio canje) {
    this.beneficiosActivos.add(canje);
  }
}