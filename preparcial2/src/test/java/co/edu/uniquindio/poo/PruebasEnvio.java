package co.edu.uniquindio.poo;

import enums.ZonaEntrega;
import modelo.*;

import java.util.Date;

public class PruebasEnvio {
  public static void main(String[] args) {
    Cliente cliente = new Cliente("Carlos", "123456789", "carlos@mail.com", "Calle 123");
    Ruta ruta = new Ruta(2, 15);
    Vehiculo camion = new Camion("ABC123", "Chevrolet", "NQR", 1000);
    Envio envio = new Envio("E001", ZonaEntrega.URBANA, new Date(), cliente, ruta, camion);

    envio.agregarPaquete(new Paquete("P1", 30));
    envio.agregarPaquete(new Paquete("P2", 25));

    System.out.println("Costo del Envío: " + envio.calcularCostoEnvio());

    System.out.println("¿Hay envíos con peso > 50 kg?");
    for (Envio e : cliente.obtenerEnviosPesoMayor50()) {
      System.out.println("- Envío " + e);
    }
  }
}
