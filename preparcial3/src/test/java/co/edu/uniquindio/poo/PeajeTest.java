package co.edu.uniquindio.poo;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PeajeTest {

  @Test
  void asignarVehiculosAConductor() {
    Conductor conductor = new Conductor("Juan", "Gomez", "123", LocalDate.now());
    Carro carro = new Carro("ABC-123", 0, false, false);
    Moto moto = new Moto("DEF-456", 0, 150);
    Camion camion = new Camion("GHI-789", 0, 2, 5.0);

    conductor.asignarVehiculo(carro);
    conductor.asignarVehiculo(moto);
    conductor.asignarVehiculo(camion);

    List<Vehiculo> vehiculosAsignados = conductor.getVehiculos();
    assertEquals(3, vehiculosAsignados.size());
    assertTrue(vehiculosAsignados.contains(carro));
    assertTrue(vehiculosAsignados.contains(moto));
    assertTrue(vehiculosAsignados.contains(camion));
  }

  @Test
  void imprimirResumenPeaje() {
    Peaje peaje = new Peaje("Peaje Prueba", "Quindio");
    Carro carro = new Carro("JKL-012", 0, true, false);
    Moto moto = new Moto("MNO-345", 0, 250);
    Conductor conductor1 = new Conductor("Ana", "Perez", "456", LocalDate.now());

    peaje.procesarVehiculo(carro, conductor1);
    peaje.procesarVehiculo(moto, null);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    peaje.imprimirResumen();
    String expectedOutput = "Resumen Peaje - Peaje Prueba\n" +
        "Carro - Placa: JKL-012, Electrico: true, Servicio Publico: false - Peaje pagado: 8000.0 (Calculo: Base: 10000, Eléctrico (x0.8) = 8000.0)\n"
        +
        "Moto - Placa: MNO-345, Cilindraje: 250cc - Peaje pagado: 7000.0 (Calculo: Base: 5000, Cilindraje > 200 (+2000) = 7000.0)\n"
        +
        "Total recaudado: 15000.0\n";
    assertEquals(expectedOutput.trim().replace("\r\n", "\n"), outContent.toString().trim().replace("\r\n", "\n"));
    System.setOut(System.out); // Restablecer la salida estándar
  }

  @Test
  void obtenerDescripcionVehiculo() {
    Carro carro = new Carro("PQR-678", 0, true, true);
    Moto moto = new Moto("STU-901", 0, 180);
    Camion camion = new Camion("VWX-234", 0, 4, 12.5);

    assertEquals("Carro - Placa: PQR-678, Eléctrico: true, Servicio Público: true", carro.obtenerDescripcion());
    assertEquals("Moto - Placa: STU-901, Cilindraje: 180cc", moto.obtenerDescripcion());
    assertEquals("Camion - Placa: VWX-234, Ejes: 4, Carga: 12.5t", camion.obtenerDescripcion());
  }

  @Test
  void imprimirTotalPeajesConductor() {
    Conductor conductor = new Conductor("Laura", "Diaz", "789", LocalDate.now());
    Carro carro = new Carro("YZA-567", 0, false, false);
    Moto moto = new Moto("BCD-890", 0, 220);
    conductor.asignarVehiculo(carro);
    conductor.asignarVehiculo(moto);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    conductor.imprimirTotalPeajesPorVehiculo();
    String expectedOutput = "Reporte de peajes pagados por Laura Diaz:\n" +
        "- Carro - Placa: YZA-567, Electrico: false, Servicio Publico: false: 10000.0 (Cálculo: Base: 10000 = 10000.0)\n"
        +
        "- Moto - Placa: BCD-890, Cilindraje: 220cc: 7000.0 (Cálculo: Base: 5000, Cilindraje > 200 (+2000) = 7000.0)\n";
    assertEquals(expectedOutput.trim().replace("\r\n", "\n"), outContent.toString().trim().replace("\r\n", "\n"));
    System.setOut(System.out);
  }

  @Test
  void obtenerVehiculosPorTipoConductor() {
    Conductor conductor = new Conductor("Pedro", "Suarez", "012", LocalDate.now());
    Carro carro1 = new Carro("EFG-123", 0, false, false);
    Carro carro2 = new Carro("HIJ-456", 0, true, false);
    Moto moto = new Moto("KLM-789", 0, 100);
    Camion camion = new Camion("NOP-012", 0, 2, 6.0);
    conductor.asignarVehiculo(carro1);
    conductor.asignarVehiculo(carro2);
    conductor.asignarVehiculo(moto);
    conductor.asignarVehiculo(camion);

    List<Vehiculo> carros = conductor.obtenerVehiculosPorTipo("Carro");
    assertEquals(2, carros.size());
    assertTrue(carros.contains(carro1));
    assertTrue(carros.contains(carro2));

    List<Vehiculo> motos = conductor.obtenerVehiculosPorTipo("Moto");
    assertEquals(1, motos.size());
    assertTrue(motos.contains(moto));

    List<Vehiculo> camiones = conductor.obtenerVehiculosPorTipo("Camion");
    assertEquals(1, camiones.size());
    assertTrue(camiones.contains(camion));

    List<Vehiculo> buses = conductor.obtenerVehiculosPorTipo("Bus");
    assertTrue(buses.isEmpty());
  }

  @Test
  void procesarVehiculoEnPeaje() {
    Peaje peaje = new Peaje("Peaje Central", "Antioquia");
    Carro carro = new Carro("QRS-345", 0, false, true);
    Conductor conductor = new Conductor("Sofia", "Vargas", "654", LocalDate.now());

    peaje.procesarVehiculo(carro, conductor);

    assertEquals(11500.0, peaje.getValorTotalRecaudado());
    assertEquals(1, peaje.getVehiculosProcesados().size());
    assertEquals(1, peaje.getValoresPeajeProcesados().size());
    assertEquals(1, peaje.getConductoresVehiculosProcesados().size());
    assertEquals(carro, peaje.getVehiculosProcesados().get(0));
    assertEquals(11500.0, peaje.getValoresPeajeProcesados().get(0));
    assertEquals(conductor, peaje.getConductoresVehiculosProcesados().get(0));
  }

  @Test
  void buscarRecaudadorPorNombre() {
    Peaje peaje = new Peaje("Peaje Norte", "Caldas");
    Recaudador recaudador1 = new Recaudador("Andres", "Perez", "111", LocalDate.now(), 1500.0);
    Recaudador recaudador2 = new Recaudador("Maria", "Lopez Gomez", "222", LocalDate.now(), 1600.0);
    peaje.agregarRecaudador(recaudador1);
    peaje.agregarRecaudador(recaudador2);

    Recaudador encontrado1 = peaje.buscarRecaudadorPorNombreCompleto("andres perez");
    assertEquals(recaudador1, encontrado1);

    Recaudador encontrado2 = peaje.buscarRecaudadorPorNombreCompleto(" MARIA  lopez   gomez ");
    assertEquals(recaudador2, encontrado2);

    Recaudador noEncontrado = peaje.buscarRecaudadorPorNombreCompleto("pedro ramirez");
    assertNull(noEncontrado);
  }

  @Test
  void conductoresConCamionAltoTonelaje() {
    Peaje peaje = new Peaje("Peaje Sur", "Risaralda");
    Conductor conductor1 = new Conductor("Ricardo", "Castro", "333", LocalDate.now());
    Conductor conductor2 = new Conductor("Elena", "Ruiz", "444", LocalDate.now());
    Camion camionAlto1 = new Camion("UUU-111", 0, 3, 11.0);
    Camion camionAlto2 = new Camion("VVV-222", 0, 5, 15.0);
    Carro carro = new Carro("WWW-333", 0, false, false);

    peaje.procesarVehiculo(camionAlto1, conductor1);
    peaje.procesarVehiculo(carro, conductor2);
    peaje.procesarVehiculo(camionAlto2, conductor1);
    peaje.procesarVehiculo(new Camion("XXX-444", 0, 2, 9.0), conductor2);

    List<Conductor> conductoresAltoTonelaje = peaje.conductoresConCamionAltoTonelaje();
    assertEquals(1, conductoresAltoTonelaje.size());
    assertTrue(conductoresAltoTonelaje.contains(conductor1));

    Peaje peajeSinAltoTonelaje = new Peaje("Peaje Este", "Tolima");
    peajeSinAltoTonelaje.procesarVehiculo(carro, conductor1);
    assertTrue(peajeSinAltoTonelaje.conductoresConCamionAltoTonelaje().isEmpty());
  }
}