package co.edu.uniquindio.poo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SistemaGestionSeguridad {
    private List<Empleado> empleados;
    private List<Servicio> servicios;
    private List<Equipo> equipos;

    public SistemaGestionSeguridad() {
        this.empleados = new ArrayList<>();
        this.servicios = new ArrayList<>();
        this.equipos = new ArrayList<>();
    }

    // Ejercicio 2: Transferir equipo entre empleados
    public boolean transferirEquipo(String codigoEquipo, Empleado origen, Empleado destino) {
        // Buscar el equipo en el origen
        Optional<Equipo> equipoOpt = origen.getEquiposAsignados().stream()
                .filter(e -> e.getCodigo().equals(codigoEquipo) && e.getEstado() == EstadoEquipo.EN_USO)
                .findFirst();

        if (equipoOpt.isEmpty()) {
            System.out.println("✗ Equipo no encontrado o no está EN_USO en el origen");
            return false;
        }

        Equipo equipo = equipoOpt.get();

        // Retirar del origen
        origen.retirarEquipoPorCodigo(codigoEquipo);
        origen.registrarNovedad(new RegistroNovedad(
                LocalDateTime.now(),
                "TRANSFERENCIA_EQUIPO",
                "Equipo " + codigoEquipo + " transferido a " + destino.getNombre(),
                origen.getNombre()));

        // Asignar al destino
        destino.asignarEquipo(equipo);
        destino.registrarNovedad(new RegistroNovedad(
                LocalDateTime.now(),
                "RECEPCION_EQUIPO",
                "Equipo " + codigoEquipo + " recibido de " + origen.getNombre(),
                destino.getNombre()));

        System.out.println("✓ Equipo transferido exitosamente");
        return true;
    }

    // Ejercicio 3: Generar reporte consolidado heterogéneo
    public String generarReporteConsolidado(List<Reporteable> elementos) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n╔════════════════════════════════════════════╗\n");
        reporte.append("║       REPORTE CONSOLIDADO SISTEMA         ║\n");
        reporte.append("╚════════════════════════════════════════════╝\n\n");

        for (Reporteable elemento : elementos) {
            reporte.append(elemento.generarReporte()).append("\n");
        }

        return reporte.toString();
    }

    // Ejercicio 4: Contar vigilantes por tipo de arma
    public int contarVigilantesPorTipoArma(TipoArma tipoArma) {
        return (int) empleados.stream()
                .filter(e -> e instanceof Vigilante)
                .map(e -> (Vigilante) e)
                .filter(v -> v.getTipoArma() == tipoArma)
                .count();
    }

    // Ejercicio 5: Sumar costos heterogéneos polimórficamente
    public double sumarCostosHeterogeneos(List<Costeable> costeables) {
        return costeables.stream()
                .mapToDouble(Costeable::obtenerCostoMensual)
                .sum();
    }

    // Ejercicio 6: Buscar vigilante por número de puesto
    public Optional<Vigilante> buscarVigilantePorPuesto(int numeroPuesto) {
        return empleados.stream()
                .filter(e -> e instanceof Vigilante)
                .map(e -> (Vigilante) e)
                .filter(v -> v.getNumeroPuesto() == numeroPuesto)
                .findFirst();
    }

    // Ejercicio 10: Obtener contratos activos de un cliente
    public List<String> obtenerContratosActivosPorCliente(String nombreCliente) {
        return servicios.stream()
                .filter(s -> s.getEstado() == EstadoServicio.ACTIVO)
                .filter(s -> s.getCliente().equalsIgnoreCase(nombreCliente))
                .map(Servicio::getCodigoContrato)
                .collect(Collectors.toList());
    }

    // Ejercicio 17: Validar asignación de empleado a servicio
    public boolean validarAsignacionEmpleadoServicio(Empleado empleado, Servicio servicio) {
        // 1. El servicio debe estar ACTIVO
        if (servicio.getEstado() != EstadoServicio.ACTIVO) {
            System.out.println("✗ El servicio no está activo");
            return false;
        }

        // 2. El empleado debe existir (estar registrado)
        if (!empleados.contains(empleado)) {
            System.out.println("✗ El empleado no está registrado en el sistema");
            return false;
        }

        // 3. Si es vigilante con arma letal, su turno debe ser noche
        if (empleado instanceof Vigilante) {
            Vigilante vigilante = (Vigilante) empleado;
            if (!vigilante.esConfiguracionValida()) {
                System.out.println("✗ Vigilante con arma letal debe tener turno nocturno");
                return false;
            }
        }

        return true;
    }

    // Métodos de gestión básica
    public void registrarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        System.out.println("✓ Empleado registrado: " + empleado.getNombre());
    }

    public List<Vigilante> listarVigilantes() {
        return empleados.stream()
                .filter(e -> e instanceof Vigilante)
                .map(e -> (Vigilante) e)
                .collect(Collectors.toList());
    }

    public List<Supervisor> listarSupervisores() {
        return empleados.stream()
                .filter(e -> e instanceof Supervisor)
                .map(e -> (Supervisor) e)
                .collect(Collectors.toList());
    }

    public List<OperadorMonitoreo> listarOperadores() {
        return empleados.stream()
                .filter(e -> e instanceof OperadorMonitoreo)
                .map(e -> (OperadorMonitoreo) e)
                .collect(Collectors.toList());
    }

    public void asignarPersonalAServicio(String codigoContrato, Empleado empleado) {
        Servicio servicio = buscarServicio(codigoContrato);
        if (servicio != null && validarAsignacionEmpleadoServicio(empleado, servicio)) {
            servicio.asignarPersonal(empleado);
            System.out.println("✓ Personal asignado al servicio " + codigoContrato);
        }
    }

    public void asignarEquipoAServicio(String codigoContrato, Equipo equipo) {
        Servicio servicio = buscarServicio(codigoContrato);
        if (servicio != null && servicio.getEstado() == EstadoServicio.ACTIVO) {
            servicio.asignarEquipo(equipo);
            System.out.println("✓ Equipo asignado al servicio " + codigoContrato);
        } else {
            System.out.println("✗ Servicio no encontrado o no activo");
        }
    }

    public double calcularCostoTotalServicios() {
        return servicios.stream()
                .filter(s -> s.getEstado() == EstadoServicio.ACTIVO)
                .mapToDouble(Servicio::calcularCostoMensual)
                .sum();
    }

    public void generarInformeDotacion(String documento) {
        Empleado empleado = buscarEmpleadoPorDocumento(documento);
        if (empleado != null) {
            System.out.println("\n═══ INFORME DE DOTACIÓN ═══");
            System.out.println("Empleado: " + empleado.getNombre());
            System.out.println("Documento: " + empleado.getDocumento());
            System.out.println("\nEquipos asignados:");

            List<Equipo> equipos = empleado.getEquiposAsignados();
            if (equipos.isEmpty()) {
                System.out.println("  - Sin equipos asignados");
            } else {
                equipos.forEach(e -> System.out.println("  - " + e));
            }

            System.out.println("\nValor total equipos: $" +
                    String.format("%,.2f", empleado.calcularValorEquipos()));
            System.out.println("═══════════════════════════\n");
        } else {
            System.out.println("✗ Empleado no encontrado");
        }
    }

    public void calcularGastoTotal() {
        double totalNomina = empleados.stream()
                .mapToDouble(Empleado::calcularSalarioTotal)
                .sum();

        double totalEquipos = empleados.stream()
                .mapToDouble(Empleado::calcularValorEquipos)
                .sum();

        System.out.println("\n═══ RESUMEN FINANCIERO ═══");
        System.out.println("Total nómina mensual: $" + String.format("%,.2f", totalNomina));
        System.out.println("Total valor equipos: $" + String.format("%,.2f", totalEquipos));
        System.out.println("TOTAL GASTOS: $" + String.format("%,.2f", totalNomina + totalEquipos));
        System.out.println("═══════════════════════════\n");
    }

    public void registrarServicio(Servicio servicio) {
        servicios.add(servicio);
        System.out.println("✓ Servicio registrado: " + servicio.getCodigoContrato());
    }

    public void registrarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    private Servicio buscarServicio(String codigoContrato) {
        return servicios.stream()
                .filter(s -> s.getCodigoContrato().equals(codigoContrato))
                .findFirst()
                .orElse(null);
    }

    private Empleado buscarEmpleadoPorDocumento(String documento) {
        return empleados.stream()
                .filter(e -> e.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    public List<Servicio> listarServiciosActivos() {
        return servicios.stream()
                .filter(s -> s.getEstado() == EstadoServicio.ACTIVO)
                .collect(Collectors.toList());
    }

    public List<Empleado> getEmpleados() {
        return new ArrayList<>(empleados);
    }

    public List<Servicio> getServicios() {
        return new ArrayList<>(servicios);
    }

    public List<Equipo> getEquipos() {
        return new ArrayList<>(equipos);
    }
}