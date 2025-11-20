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

  /**
   * Crea un nuevo monedero para el cliente
   * Valida que no exista ya un monedero del mismo tipo
   * 
   * @param tipo El tipo de monedero a crear
   * @return El monedero creado
   * @throws IllegalStateException si ya existe un monedero de ese tipo
   */
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

  /**
   * Busca y retorna un monedero específico del cliente
   * 
   * @param tipo El tipo de monedero a buscar
   * @return Optional con el monedero si existe
   */
  public Optional<Monedero> obtenerMonedero(TipoMonedero tipo) {
    return monederos.stream()
        .filter(m -> m.getTipo() == tipo)
        .findFirst();
  }

  /**
   * Obtiene el monedero principal del cliente
   * 
   * @return El monedero principal
   * @throws IllegalStateException si no existe el monedero principal
   */
  public Monedero getMonederoPrincipal() {
    return obtenerMonedero(TipoMonedero.PRINCIPAL)
        .orElseThrow(() -> new IllegalStateException("No existe monedero principal"));
  }

  /**
   * Calcula la suma de saldos de todos los monederos del cliente
   * 
   * @return El saldo total en todos los monederos
   */
  public double calcularSaldoTotal() {
    return monederos.stream()
        .mapToDouble(Monedero::getSaldo)
        .sum();
  }

  /**
   * Cuenta el total de transacciones realizadas en todos los monederos
   * 
   * @return Número total de transacciones
   */
  public int calcularTotalTransacciones() {
    return monederos.stream()
        .mapToInt(m -> m.obtenerHistorial().size())
        .sum();
  }

  /**
   * Genera un DTO con el resumen del cliente para mostrar en reportes
   * 
   * @return ResumenClienteDTO con los datos principales
   */
  public ResumenClienteDTO generarResumenDTO() {
    return new ResumenClienteDTO(
        this.nombre,
        this.puntosAcumulados,
        this.rangoActual,
        this.monederos.size(),
        calcularSaldoTotal(),
        calcularTotalTransacciones());
  }

  /**
   * Acumula puntos al cliente y actualiza su rango si corresponde
   * Envía notificaciones si cambia de rango
   * 
   * @param puntos Cantidad de puntos a acumular (debe ser positivo)
   */
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

  /**
   * Canjea (resta) puntos del cliente si tiene suficientes
   * Actualiza el rango después del canje
   * 
   * @param puntos Cantidad de puntos a canjear
   * @return true si el canje fue exitoso, false si no tiene suficientes puntos
   */
  @Override
  public boolean canjearPuntos(int puntos) {
    if (puntos > 0 && puntosAcumulados >= puntos) {
      this.puntosAcumulados -= puntos;
      this.rangoActual = calcularRango();
      return true;
    }
    return false;
  }

  /**
   * Calcula el rango del cliente basado en sus puntos acumulados
   * 
   * @return El rango correspondiente a los puntos actuales
   */
  @Override
  public RangoCliente calcularRango() {
    return RangoCliente.obtenerRangoPorPuntos(puntosAcumulados);
  }

  /**
   * Envía una notificación al cliente con formato de hora y tipo
   * 
   * @param mensaje El mensaje de la notificación
   * @param tipo    El tipo de notificación (define icono y descripción)
   */
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

  /**
   * Obtiene todas las notificaciones del cliente
   * 
   * @return Lista con las notificaciones
   */
  @Override
  public List<String> obtenerNotificaciones() {
    return new ArrayList<>(notificaciones);
  }

  /**
   * Marca una notificación como leída eliminándola de la lista
   * 
   * @param indice El índice de la notificación a marcar
   */
  @Override
  public void marcarNotificacionComoLeida(int indice) {
    if (indice >= 0 && indice < notificaciones.size()) {
      notificaciones.remove(indice);
    }
  }

  /**
   * Verifica todos los monederos y envía alertas si el saldo está bajo
   * Se considera saldo bajo cuando está cerca del mínimo + 100
   */
  public void verificarSaldoBajo() {
    monederos.stream()
        .filter(m -> m.getSaldo() < m.getSaldoMinimo() + 100)
        .forEach(m -> enviarNotificacion(
            String.format("Saldo bajo en monedero %s: %s",
                m.getTipo().getNombre(),
                formatearCOP(m.getSaldo())),
            TipoNotificacion.SALDO_BAJO));
  }

  /**
   * Formatea un monto en pesos colombianos con separadores de miles
   * 
   * @param monto El monto a formatear
   * @return String con formato "$1,000 COP"
   */
  public static String formatearCOP(double monto) {
    return String.format("$%,.0f COP", monto);
  }

  /**
   * Genera un reporte resumido de una línea del cliente
   * 
   * @return String con nombre, puntos y rango
   */
  @Override
  public String generarReporte() {
    return String.format("%s - Puntos: %d - Rango: %s %s",
        nombre,
        puntosAcumulados,
        rangoActual.getIcono(),
        rangoActual.getNombre());
  }

  /**
   * Genera un reporte completo y detallado del cliente
   * Incluye todos los datos: ID, contacto, puntos, monederos, etc.
   * 
   * @return String con el reporte detallado formateado
   */
  @Override
  public String generarReporteDetallado() {
    return String.format("""

        REPORTE DE CLIENTE

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

  /**
   * Verifica si el cliente tiene un beneficio activo de un tipo específico
   * 
   * @param tipoBeneficio El tipo de beneficio a buscar (nombre del enum)
   * @return true si tiene ese beneficio activo y no ha sido completamente usado
   */
  public boolean tieneBeneficioActivo(String tipoBeneficio) {
    return beneficiosActivos.stream()
        .anyMatch(c -> c.getBeneficio().getTipo().name().equals(tipoBeneficio)
            && !c.isAplicado());
  }

  /**
   * Obtiene un beneficio activo específico del cliente
   * 
   * @param tipoBeneficio El tipo de beneficio a buscar
   * @return Optional con el CanjeBeneficio si existe y está activo
   */
  public Optional<CanjeBeneficio> obtenerBeneficioActivo(String tipoBeneficio) {
    return beneficiosActivos.stream()
        .filter(c -> c.getBeneficio().getTipo().name().equals(tipoBeneficio)
            && !c.isAplicado())
        .findFirst();
  }

  /**
   * Agrega un beneficio canjeado a la lista de beneficios activos del cliente
   * 
   * @param canje El canje de beneficio a agregar
   */
  public void agregarBeneficioActivo(CanjeBeneficio canje) {
    this.beneficiosActivos.add(canje);
  }

  // Getters básicos
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

  public List<CanjeBeneficio> getBeneficiosActivos() {
    return new ArrayList<>(beneficiosActivos);
  }

  public List<String> getNotificaciones() {
    return notificaciones;
  }

  // Setters básicos
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

  public void setPuntosAcumulados(int puntosAcumulados) {
    this.puntosAcumulados = puntosAcumulados;
  }

  public void setRangoActual(RangoCliente rangoActual) {
    this.rangoActual = rangoActual;
  }

  public void setBeneficiosActivos(List<CanjeBeneficio> beneficiosActivos) {
    this.beneficiosActivos = beneficiosActivos;
  }

  @Override
  public String toString() {
    return generarReporte();
  }
}