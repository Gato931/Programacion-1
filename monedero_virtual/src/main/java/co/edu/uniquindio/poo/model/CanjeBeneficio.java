package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class CanjeBeneficio {

  private final String id;
  private final Cliente cliente;
  private final Beneficio beneficio;
  private final LocalDateTime fechaCanje;
  private LocalDateTime fechaExpiracion;
  private boolean aplicado;
  private int vecesUsado;
  private int usosMaximos;

  public CanjeBeneficio(Cliente cliente, Beneficio beneficio) {
    this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    this.cliente = cliente;
    this.beneficio = beneficio;
    this.fechaCanje = LocalDateTime.now();
    this.aplicado = false;
    this.vecesUsado = 0;

    configurarBeneficio();
  }

  private void configurarBeneficio() {
    switch (beneficio.getTipo()) {
      case DESCUENTO_COMISION -> {

        this.usosMaximos = 10;
        this.fechaExpiracion = LocalDateTime.now().plusDays(30);
      }
      case SIN_CARGOS -> {

        this.usosMaximos = Integer.MAX_VALUE;
        this.fechaExpiracion = LocalDateTime.now().plusMonths(1);
      }
      case BONO_SALDO -> {

        this.usosMaximos = 1;
        this.fechaExpiracion = null;
      }
      case PUNTOS_EXTRA -> {

        this.usosMaximos = 20;
        this.fechaExpiracion = LocalDateTime.now().plusDays(60);
      }
    }
  }

  public boolean puedeUsarse() {
    if (aplicado && beneficio.getTipo() == Beneficio.TipoBeneficio.BONO_SALDO) {
      return false;
    }

    if (vecesUsado >= usosMaximos) {
      return false;
    }

    if (fechaExpiracion != null && LocalDateTime.now().isAfter(fechaExpiracion)) {
      return false;
    }

    return true;
  }

  public void usar() {
    this.vecesUsado++;
    if (vecesUsado >= usosMaximos) {
      this.aplicado = true;
    }
  }

  public String obtenerEstado() {
    if (!puedeUsarse()) {
      return "Expirado/Agotado";
    }

    if (fechaExpiracion != null) {
      long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(
          LocalDateTime.now(), fechaExpiracion);
      return String.format("Activo (%d dias restantes, %d/%d usos)",
          diasRestantes, vecesUsado, usosMaximos);
    }

    return String.format("Activo (%d/%d usos)", vecesUsado, usosMaximos);
  }

  public String getId() {
    return id;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public Beneficio getBeneficio() {
    return beneficio;
  }

  public LocalDateTime getFechaCanje() {
    return fechaCanje;
  }

  public LocalDateTime getFechaExpiracion() {
    return fechaExpiracion;
  }

  public boolean isAplicado() {
    return aplicado;
  }

  public void setAplicado(boolean aplicado) {
    this.aplicado = aplicado;
  }

  public int getVecesUsado() {
    return vecesUsado;
  }

  public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
    this.fechaExpiracion = fechaExpiracion;
  }

  public void setVecesUsado(int vecesUsado) {
    this.vecesUsado = vecesUsado;
  }

  public int getUsosMaximos() {
    return usosMaximos;
  }

  public void setUsosMaximos(int usosMaximos) {
    this.usosMaximos = usosMaximos;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s canjeo %s - %s",
        id,
        cliente.getNombre(),
        beneficio.getNombre(),
        obtenerEstado());
  }
}