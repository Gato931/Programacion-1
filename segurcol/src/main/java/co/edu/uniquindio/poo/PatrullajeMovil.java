package co.edu.uniquindio.poo;
import java.math.BigDecimal;

public class PatrullajeMovil extends Servicio {
    private int numeroRutas;
    private double kilometrosRecorridos;

    public PatrullajeMovil(String codigoContrato, String cliente, double tarifaMensual,
            int numeroRutas, double kilometrosRecorridos) {
        super(codigoContrato, cliente, tarifaMensual);
        this.numeroRutas = numeroRutas;
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    @Override
    public double calcularCostoMensual() {
        double costo = getTarifaMensual();

        // Costo por ruta
        costo += numeroRutas * 500000; // $500,000 por ruta

        // Costo por kilómetro
        costo += kilometrosRecorridos * 3000; // $3,000 por km

        return costo;
    }

    // Ejercicio 18: Calcular costo con BigDecimal
    public BigDecimal calcularCostoMensualPreciso() {
        BigDecimal tarifaBase = BigDecimal.valueOf(getTarifaMensual());
        BigDecimal costoRutas = BigDecimal.valueOf(numeroRutas).multiply(BigDecimal.valueOf(80000));
        BigDecimal costoKm = BigDecimal.valueOf(kilometrosRecorridos).multiply(BigDecimal.valueOf(2000));

        return tarifaBase.add(costoRutas).add(costoKm);
    }

    public int getNumeroRutas() {
        return numeroRutas;
    }

    public void setNumeroRutas(int numeroRutas) {
        this.numeroRutas = numeroRutas;
    }

    public double getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(double kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }
}