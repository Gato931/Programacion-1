package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Peaje peaje = new Peaje("Peaje Quindio", "Quindio");

        Recaudador recaudador1 = new Recaudador("Ana", "Rodriguez", "111", LocalDate.of(1995, 8, 20), 1200.0);
        Recaudador recaudador2 = new Recaudador("Carlos", "Vargas", "222", LocalDate.of(1990, 3, 10), 1350.50);
        peaje.agregarRecaudador(recaudador1);
        peaje.agregarRecaudador(recaudador2);

        Carro carro1 = new Carro("AAA321", 3, true, false);
        Carro carro2 = new Carro("BBB123", 1, false, true);
        Moto moto1 = new Moto("CCC333", 0, 150);
        Moto moto2 = new Moto("DDD111", 2, 300);
        Camion camion1 = new Camion("EEE222", 5, 3, 12);
        Camion camion2 = new Camion("FFF213", 2, 2, 8);

        Conductor conductor1 = new Conductor("Juan", "Gomez", "123456789", LocalDate.of(1990, 5, 20));
        conductor1.asignarVehiculo(carro1);
        conductor1.asignarVehiculo(moto1);
        conductor1.asignarVehiculo(camion1);

        Conductor conductor2 = new Conductor("Maria", "Rojas", "987654321", LocalDate.of(1988, 11, 10));
        conductor2.asignarVehiculo(carro2);
        conductor2.asignarVehiculo(moto2);

        peaje.procesarVehiculo(carro1, conductor1);
        peaje.procesarVehiculo(carro2, conductor2);
        peaje.procesarVehiculo(moto1, conductor1);
        peaje.procesarVehiculo(moto2, conductor2);
        peaje.procesarVehiculo(camion1, conductor1);
        peaje.procesarVehiculo(camion2, null);

        System.out.println();
        peaje.imprimirResumen();

        System.out.println();
        List<Conductor> conductoresAltoTonelajePeaje = peaje.conductoresConCamionAltoTonelaje();
        boolean conductor1TieneCamionAltoTonelaje = false;
        for (Conductor conductor : conductoresAltoTonelajePeaje) {
            if (conductor.equals(conductor1)) {
                conductor1TieneCamionAltoTonelaje = true;
                break;
            }
        }

        if (conductor1TieneCamionAltoTonelaje) {
            System.out.println(conductor1.getNombre() + " tiene un camion de alto tonelaje");
        } else {
            System.out.println(conductor1.getNombre() + " no tiene un camion de alto tonelaje");
        }

        System.out.println("\nVehículos asignados a " + conductor1.getNombre() + ":");
        for (Vehiculo vehiculo : conductor1.getVehiculos()) {
            System.out.println("- " + vehiculo.obtenerDescripcion());
        }

        System.out.println();
        conductor1.imprimirTotalPeajesPorVehiculo();

        System.out.println("\nBuscando recaudador por nombre");
        String nombreABuscar = "Juan Carlos Meza";
        Recaudador recaudadorEncontrado = peaje.buscarRecaudadorPorNombreCompleto(nombreABuscar);
        if (recaudadorEncontrado != null) {
            System.out.println("Recaudador encontrado: " + recaudadorEncontrado.getNombre() + " "
                    + recaudadorEncontrado.getApellidos());
        } else {
            System.out.println("No se encontro ningun recaudador con el nombre: " + nombreABuscar);
        }

        System.out.println("\nConductores que pasaron con un camion de alto tonelaje:");
        List<Conductor> conductoresAltoTonelaje = peaje.conductoresConCamionAltoTonelaje();
        if (conductoresAltoTonelaje.isEmpty()) {
            System.out.println("No hay conductores que hayan pasado con camiones de alto tonelaje.");
        } else {
            for (Conductor conductor : conductoresAltoTonelaje) {
                System.out.println("- " + conductor.getNombre() + " " + conductor.getApellidos());
            }
        }

        System.out.println("\nVehiculos tipo Carro de " + conductor1.getNombre() + ":");
        List<Vehiculo> carrosJuan = conductor1.obtenerVehiculosPorTipo("Carro");
        for (Vehiculo carro : carrosJuan) {
            System.out.println("- " + carro.obtenerDescripcion());
        }
    }
}