package co.edu.uniquindio.poo;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        Cliente cliente1 = new ClienteFrecuente("Juan Pérez", "12345", "Calle 10", 200);
        Cliente cliente2 = new ClienteCorporativo("Empresa XYZ", "N/A", "Cra 20", "890123456-7", 0.10);

        Producto leche = new ProductoAlimenticio("A001", "Leche", 4000, LocalDate.of(2025, 11, 10));
        Producto licuadora = new ProductoElectrodomestico("E001", "Licuadora", 150000, 12);

        Factura factura1 = new Factura(1, cliente1);
        factura1.agregarDetalle(leche, 3);
        factura1.agregarDetalle(licuadora, 1);
        factura1.mostrarFactura();

        System.out.println("-----------------------------");

        Factura factura2 = new Factura(2, cliente2);
        factura2.agregarDetalle(licuadora, 5);
        factura2.mostrarFactura();
    }
}
