package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.controller.TransaccionController;
import co.edu.uniquindio.poo.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardViewController {

  @FXML
  private Label nombreLabel;
  @FXML
  private Label rangoLabel;
  @FXML
  private Label puntosLabel;
  @FXML
  private Label saldoTotalLabel;
  @FXML
  private ListView<String> monederosListView;
  @FXML
  private ListView<String> transaccionesListView;
  @FXML
  private ListView<String> notificacionesListView;
  @FXML
  private ListView<String> contactosListView;
  @FXML
  private ComboBox<String> tipoTransaccionCombo;
  @FXML
  private ComboBox<String> monederoOrigenCombo;
  @FXML
  private TextField montoField;
  @FXML
  private TextArea descripcionArea;
  @FXML
  private TextField emailDestinoField;
  @FXML
  private ComboBox<String> monederoDestinoCombo;
  @FXML
  private Button ejecutarButton;

  private Cliente clienteActual;
  private TransaccionController transaccionController;
  private ScheduledExecutorService scheduler;

  private String formatearCOP(double monto) {
    return String.format("$%,.0f COP", monto);
  }

  @FXML
  public void initialize() {
    System.out.println("Dashboard inicializado");
    transaccionController = new TransaccionController();
    configurarComboBoxes();
    iniciarProcesadorTransacciones();
  }

  public void setCliente(Cliente cliente) {
    this.clienteActual = cliente;
    System.out.println("Cliente configurado: " + cliente.getNombre());
    cargarDatosCliente();
  }

  private void configurarComboBoxes() {
    tipoTransaccionCombo.setItems(FXCollections.observableArrayList(
        "Deposito", "Retiro", "Transferencia"));
    tipoTransaccionCombo.setValue("Deposito");

    monederoOrigenCombo.setItems(FXCollections.observableArrayList(
        "PRINCIPAL", "AHORRO", "GASTOS_DIARIOS", "EMERGENCIA", "INVERSION"));
    monederoOrigenCombo.setValue("PRINCIPAL");

    monederoDestinoCombo.setItems(FXCollections.observableArrayList(
        "PRINCIPAL", "AHORRO", "GASTOS_DIARIOS", "EMERGENCIA", "INVERSION"));
    monederoDestinoCombo.setValue("PRINCIPAL");

    tipoTransaccionCombo.setOnAction(e -> {
      String seleccion = tipoTransaccionCombo.getValue();
      boolean esTransferencia = seleccion.equals("Transferencia");
      emailDestinoField.setVisible(esTransferencia);
      monederoDestinoCombo.setVisible(esTransferencia);
    });

    emailDestinoField.setVisible(false);
    monederoDestinoCombo.setVisible(false);
  }

  private void cargarDatosCliente() {
    nombreLabel.setText(clienteActual.getNombre());
    rangoLabel.setText(clienteActual.getRangoActual().getNombre());
    puntosLabel.setText(String.valueOf(clienteActual.getPuntosAcumulados()) + " pts");
    saldoTotalLabel.setText(formatearCOP(clienteActual.calcularSaldoTotal()));

    cargarMonederos();
    cargarTransacciones();
    cargarNotificaciones();
    cargarContactos();
  }

  private void cargarMonederos() {
    monederosListView.getItems().clear();
    clienteActual.getMonederos().forEach(m -> monederosListView.getItems().add(
        m.getTipo().getNombre() + ": " + formatearCOP(m.getSaldo())));
  }

  private void cargarTransacciones() {
    transaccionesListView.getItems().clear();

    clienteActual.getMonederos().forEach(m -> m.obtenerHistorial().stream()
        .sorted((t1, t2) -> t2.getFechaCreacion().compareTo(t1.getFechaCreacion()))
        .limit(15)
        .map(t -> new TransaccionDTO(
            t.getId(),
            t.getTipo(),
            t.getMonto(),
            t.getFechaCreacion(),
            t.getEstado(),
            m.getTipo().getNombre(),
            t instanceof Transferencia ? ((Transferencia) t).getMonederoDestino().getTipo().getNombre() : "-",
            t.getDescripcion(),
            t.getPuntosGenerados()))
        .forEach(dto -> {
          String hora = dto.fecha().toString().substring(11, 16);
          transaccionesListView.getItems().add(
              String.format("[%s] %s - %s - %s (+%d pts)",
                  hora,
                  dto.tipo().getDescripcion(),
                  formatearCOP(dto.monto()),
                  dto.descripcion(),
                  dto.puntosGenerados()));
        }));
  }

  private void cargarNotificaciones() {
    notificacionesListView.getItems().clear();
    List<String> notificaciones = clienteActual.obtenerNotificaciones();

    if (notificaciones.isEmpty()) {
      notificacionesListView.getItems().add("No hay notificaciones");
    } else {

      int inicio = Math.max(0, notificaciones.size() - 15);
      notificacionesListView.getItems().addAll(
          notificaciones.subList(inicio, notificaciones.size()));

      if (!notificacionesListView.getItems().isEmpty()) {
        notificacionesListView.scrollTo(notificacionesListView.getItems().size() - 1);
      }
    }
  }

  private void cargarContactos() {
    contactosListView.getItems().clear();

    SistemaMonedero sistema = SistemaMonedero.getInstance();
    List<Cliente> todosClientes = sistema.obtenerTodosClientes();

    todosClientes.stream()
        .filter(c -> !c.getId().equals(clienteActual.getId()))
        .forEach(c -> {

          String monederos = c.getMonederos().stream()
              .map(m -> m.getTipo().getNombre())
              .collect(java.util.stream.Collectors.joining(", "));

          String info = String.format("%s | %s | Rango: %s | Monederos: [%s]",
              c.getNombre(),
              c.getEmail(),
              c.getRangoActual().getNombre(),
              monederos);
          contactosListView.getItems().add(info);
        });

    if (contactosListView.getItems().isEmpty()) {
      contactosListView.getItems().add("No hay otros usuarios registrados");
    }

    contactosListView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        String seleccion = contactosListView.getSelectionModel().getSelectedItem();
        if (seleccion != null && !seleccion.contains("No hay")) {

          String[] partes = seleccion.split(" \\| ");
          if (partes.length >= 2) {
            String email = partes[1];
            emailDestinoField.setText(email);
            tipoTransaccionCombo.setValue("Transferencia");

            mostrarInfo("Contacto seleccionado\n\n" + seleccion.replace(" | ", "\n"));
          }
        }
      }
    });
  }

  private void mostrarInfo(String mensaje) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Informacion");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  @FXML
  private void handleEjecutarTransaccion() {
    String tipo = tipoTransaccionCombo.getValue();
    String monederoOrigen = monederoOrigenCombo.getValue();
    String montoStr = montoField.getText().trim();
    String descripcion = descripcionArea.getText().trim();

    if (montoStr.isEmpty()) {
      mostrarAlerta("Por favor ingrese el monto", AlertType.WARNING);
      return;
    }

    try {

      montoStr = montoStr.replace(".", "").replace(",", ".");

      double monto = Double.parseDouble(montoStr);

      if (monto <= 0) {
        mostrarAlerta("El monto debe ser mayor a cero", AlertType.WARNING);
        return;
      }

      TipoMonedero tipoMonedero = TipoMonedero.valueOf(monederoOrigen);

      if (tipo.equals("Deposito")) {
        realizarDeposito(tipoMonedero, monto, descripcion);
      } else if (tipo.equals("Retiro")) {
        realizarRetiro(tipoMonedero, monto, descripcion);
      } else if (tipo.equals("Transferencia")) {
        realizarTransferencia(tipoMonedero, monto, descripcion);
      }

    } catch (NumberFormatException e) {
      mostrarAlerta("El monto ingresado no es valido\n\n" +
          "Ejemplos validos:\n" +
          "- 50000 (cincuenta mil)\n" +
          "- 1000000 (un millon)\n" +
          "- 1.000.000 (un millon con separadores)",
          AlertType.ERROR);
    } catch (Exception e) {
      mostrarAlerta("Error: " + e.getMessage(), AlertType.ERROR);
    }
  }

  private void realizarDeposito(TipoMonedero tipo, double monto, String descripcion) {
    if (descripcion.isEmpty()) {
      descripcion = "Deposito en " + tipo.getNombre();
    }

    boolean exitoso = transaccionController.realizarDeposito(
        clienteActual, tipo, monto, descripcion);

    if (exitoso) {
      mostrarExito("Deposito realizado exitosamente\nNuevo saldo: " +
          formatearCOP(clienteActual.calcularSaldoTotal()));
      limpiarFormulario();
      cargarDatosCliente();
    } else {
      mostrarAlerta("Error al realizar el deposito", AlertType.ERROR);
    }
  }

  private void realizarRetiro(TipoMonedero tipo, double monto, String descripcion) {
    if (descripcion.isEmpty()) {
      descripcion = "Retiro de " + tipo.getNombre();
    }

    Optional<Monedero> monederoOpt = clienteActual.obtenerMonedero(tipo);

    if (monederoOpt.isEmpty()) {
      mostrarAlerta("No se encontro el monedero de tipo " + tipo.getNombre(), AlertType.ERROR);
      return;
    }

    Monedero monedero = monederoOpt.get();
    double saldoActual = monedero.getSaldo();
    double saldoMinimo = monedero.getSaldoMinimo();
    double limiteRetiroDiario = monedero.getLimiteRetiroDiario();
    double retirosHoy = calcularRetirosHoy(monedero);

    if (saldoActual < monto) {
      mostrarAlerta(
          "Saldo insuficiente en el monedero " + tipo.getNombre() + "\n\n" +
              "Saldo actual: " + formatearCOP(saldoActual) + "\n" +
              "Monto a retirar: " + formatearCOP(monto) + "\n" +
              "Faltan: " + formatearCOP(monto - saldoActual),
          AlertType.ERROR);
      return;
    }

    if (saldoActual - monto < saldoMinimo) {
      mostrarAlerta(
          "El retiro dejaria el saldo por debajo del minimo\n\n" +
              "Saldo actual: " + formatearCOP(saldoActual) + "\n" +
              "Saldo minimo: " + formatearCOP(saldoMinimo) + "\n" +
              "Maximo a retirar: " + formatearCOP(saldoActual - saldoMinimo),
          AlertType.ERROR);
      return;
    }

    if (retirosHoy + monto > limiteRetiroDiario) {
      mostrarAlerta(
          "Limite de retiro diario excedido\n\n" +
              "Limite diario: " + formatearCOP(limiteRetiroDiario) + "\n" +
              "Ya has retirado hoy: " + formatearCOP(retirosHoy) + "\n" +
              "Disponible para retirar: " + formatearCOP(limiteRetiroDiario - retirosHoy),
          AlertType.ERROR);
      return;
    }

    boolean exitoso = transaccionController.realizarRetiro(
        clienteActual, tipo, monto, descripcion);

    if (exitoso) {
      mostrarExito("Retiro realizado exitosamente\n" +
          "Monto retirado: " + formatearCOP(monto) + "\n" +
          "Nuevo saldo en " + tipo.getNombre() + ": " +
          formatearCOP(monedero.getSaldo()) + "\n" +
          "Saldo total: " + formatearCOP(clienteActual.calcularSaldoTotal()));
      limpiarFormulario();
      cargarDatosCliente();
    } else {
      mostrarAlerta("Error al realizar el retiro", AlertType.ERROR);
    }
  }

  private double calcularRetirosHoy(Monedero monedero) {
    LocalDateTime inicioDia = LocalDateTime.now().toLocalDate().atStartOfDay();

    return monedero.obtenerHistorial().stream()
        .filter(t -> t.getTipo() == TipoTransaccion.RETIRO ||
            t.getTipo() == TipoTransaccion.TRANSFERENCIA)
        .filter(t -> t.getEstado() == EstadoTransaccion.COMPLETADA)
        .filter(t -> t.getFechaEjecucion() != null &&
            t.getFechaEjecucion().isAfter(inicioDia))
        .mapToDouble(Transaccion::getMonto)
        .sum();
  }

  private void realizarTransferencia(TipoMonedero monederoOrigen, double monto, String descripcion) {
    String emailDestino = emailDestinoField.getText().trim();
    String monederoDestinoStr = monederoDestinoCombo.getValue();

    if (emailDestino.isEmpty()) {
      mostrarAlerta("Por favor ingrese el email del destinatario", AlertType.WARNING);
      return;
    }

    if (emailDestino.equals(clienteActual.getEmail())) {
      mostrarAlerta("No puede transferir a su propia cuenta", AlertType.WARNING);
      return;
    }

    Optional<Cliente> destinatarioOpt = transaccionController.buscarClientePorEmail(emailDestino);

    if (destinatarioOpt.isEmpty()) {
      mostrarAlerta("Destinatario no encontrado\nVerifique el email ingresado", AlertType.ERROR);
      return;
    }

    if (descripcion.isEmpty()) {
      descripcion = "Transferencia a " + destinatarioOpt.get().getNombre();
    }

    double comisionBase = monto * 0.01;
    double descuentoRango = clienteActual.getRangoActual().getDescuentoComision();
    double comisionConRango = comisionBase * descuentoRango;
    double comisionFinal = comisionConRango;

    String infoBeneficio = "";
    boolean tieneSinCargos = clienteActual.tieneBeneficioActivo("SIN_CARGOS");
    boolean tieneDescuento = clienteActual.tieneBeneficioActivo("DESCUENTO_COMISION");

    if (tieneSinCargos) {
      Optional<CanjeBeneficio> beneficioOpt = clienteActual.obtenerBeneficioActivo("SIN_CARGOS");
      if (beneficioOpt.isPresent() && beneficioOpt.get().puedeUsarse()) {
        comisionFinal = 0.0;
        int usosRestantes = beneficioOpt.get().getUsosMaximos() - beneficioOpt.get().getVecesUsado();
        infoBeneficio = String.format("\n\nBENEFICIO ACTIVO:\nMes Sin Cargos - Comision eliminada\nUsos restantes: %d",
            usosRestantes);
      }
    } else if (tieneDescuento) {
      Optional<CanjeBeneficio> beneficioOpt = clienteActual.obtenerBeneficioActivo("DESCUENTO_COMISION");
      if (beneficioOpt.isPresent() && beneficioOpt.get().puedeUsarse()) {
        double descuento = beneficioOpt.get().getBeneficio().getValorDescuento();
        comisionFinal = comisionConRango * (1.0 - descuento);
        int usosRestantes = beneficioOpt.get().getUsosMaximos() - beneficioOpt.get().getVecesUsado();
        infoBeneficio = String.format(
            "\n\nBENEFICIO ACTIVO:\nDescuento %.0f%% en comisiones\nAhorro: %s\nUsos restantes: %d",
            descuento * 100,
            formatearCOP(comisionConRango - comisionFinal),
            usosRestantes);
      }
    }

    double montoTotal = monto + comisionFinal;

    Alert confirmacion = new Alert(AlertType.CONFIRMATION);
    confirmacion.setTitle("Confirmar Transferencia");
    confirmacion.setHeaderText("Detalles de la transferencia");

    String mensaje = String.format(
        "Destinatario: %s\n" +
            "Monto: %s\n" +
            "Comision base (1%%): %s\n" +
            "Descuento por rango %s: %.0f%%\n" +
            "Comision final: %s\n" +
            "───────────────────────\n" +
            "TOTAL A COBRAR: %s%s\n\n" +
            "¿Desea continuar?",
        destinatarioOpt.get().getNombre(),
        formatearCOP(monto),
        formatearCOP(comisionBase),
        clienteActual.getRangoActual().getNombre(),
        (1.0 - descuentoRango) * 100,
        formatearCOP(comisionFinal),
        formatearCOP(montoTotal),
        infoBeneficio);

    confirmacion.setContentText(mensaje);

    Optional<ButtonType> resultado = confirmacion.showAndWait();

    if (resultado.isEmpty() || resultado.get() != ButtonType.OK) {
      return;
    }

    TipoMonedero tipoMonederoDestino = TipoMonedero.valueOf(monederoDestinoStr);

    boolean exitoso = transaccionController.realizarTransferencia(
        clienteActual, monederoOrigen,
        destinatarioOpt.get(), tipoMonederoDestino,
        monto, descripcion);

    if (exitoso) {
      mostrarExito(String.format(
          "Transferencia realizada exitosamente\n\n" +
              "Destinatario: %s\n" +
              "Monto transferido: %s\n" +
              "Comision: %s\n" +
              "Total cobrado: %s\n\n" +
              "Nuevo saldo: %s",
          destinatarioOpt.get().getNombre(),
          formatearCOP(monto),
          formatearCOP(comisionFinal),
          formatearCOP(montoTotal),
          formatearCOP(clienteActual.calcularSaldoTotal())));
      limpiarFormulario();
      cargarDatosCliente();
    } else {
      mostrarAlerta("Error al realizar la transferencia\nVerifique el saldo",
          AlertType.ERROR);
    }
  }

  @FXML
  private void handleCrearMonedero() {
    List<String> tiposDisponibles = new java.util.ArrayList<>();

    boolean tieneAhorro = clienteActual.getMonederos().stream()
        .anyMatch(m -> m.getTipo() == TipoMonedero.AHORRO);
    boolean tieneGastosDiarios = clienteActual.getMonederos().stream()
        .anyMatch(m -> m.getTipo() == TipoMonedero.GASTOS_DIARIOS);
    boolean tieneEmergencia = clienteActual.getMonederos().stream()
        .anyMatch(m -> m.getTipo() == TipoMonedero.EMERGENCIA);
    boolean tieneInversion = clienteActual.getMonederos().stream()
        .anyMatch(m -> m.getTipo() == TipoMonedero.INVERSION);

    if (!tieneAhorro)
      tiposDisponibles.add("AHORRO");
    if (!tieneGastosDiarios)
      tiposDisponibles.add("GASTOS_DIARIOS");
    if (!tieneEmergencia)
      tiposDisponibles.add("EMERGENCIA");
    if (!tieneInversion)
      tiposDisponibles.add("INVERSION");

    if (tiposDisponibles.isEmpty()) {
      mostrarAlerta("Ya tiene todos los tipos de monederos disponibles", AlertType.INFORMATION);
      return;
    }

    ChoiceDialog<String> dialog = new ChoiceDialog<>(tiposDisponibles.get(0), tiposDisponibles);
    dialog.setTitle("Crear Monedero");
    dialog.setHeaderText("Seleccione el tipo de monedero a crear\n\n" +
        "AHORRO:\n" +
        "  - Limite diario: $2.000.000 COP\n" +
        "  - Saldo minimo: $50.000 COP\n\n" +
        "GASTOS_DIARIOS:\n" +
        "  - Limite diario: $3.000.000 COP\n" +
        "  - Sin saldo minimo\n\n" +
        "EMERGENCIA:\n" +
        "  - Limite diario: $1.000.000 COP\n" +
        "  - Saldo minimo: $100.000 COP\n\n" +
        "INVERSION:\n" +
        "  - Limite diario: $10.000.000 COP\n" +
        "  - Saldo minimo: $500.000 COP");
    dialog.setContentText("Tipo:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(tipo -> {
      try {
        TipoMonedero tipoMonedero = TipoMonedero.valueOf(tipo);
        clienteActual.crearMonedero(tipoMonedero);
        mostrarExito("Monedero creado exitosamente\n\n" +
            "Tipo: " + tipoMonedero.getNombre() + "\n" +
            "Saldo inicial: " + formatearCOP(0));
        cargarMonederos();
        cargarDatosCliente();
      } catch (Exception e) {
        mostrarAlerta("Error al crear monedero: " + e.getMessage(), AlertType.ERROR);
      }
    });
  }

  @FXML
  private void handleVerBeneficios() {
    SistemaMonedero sistema = SistemaMonedero.getInstance();
    List<Beneficio> beneficios = sistema.obtenerBeneficiosCanjeables(clienteActual);

    if (beneficios.isEmpty()) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Beneficios");
      alert.setHeaderText("Tus puntos: " + clienteActual.getPuntosAcumulados());
      alert.setContentText("No tienes suficientes puntos para canjear beneficios.\n\n" +
          "Acumula puntos realizando transacciones:\n" +
          "- Depositos: 1 punto cada $10.000 COP\n" +
          "- Retiros: 2 puntos cada $10.000 COP\n" +
          "- Transferencias: 3 puntos cada $10.000 COP\n\n" +
          "BENEFICIOS DISPONIBLES:\n" +
          "- Descuento 10% Comisiones (100 pts): 10 usos o 30 dias\n" +
          "- Mes Sin Cargos (500 pts): Valido por 1 mes\n" +
          "- Bono de Saldo (1000 pts): $50.000 COP inmediatos");
      alert.showAndWait();
      return;
    }

    List<BeneficioDTO> beneficiosDTO = beneficios.stream()
        .map(b -> new BeneficioDTO(
            b.getId(),
            b.getNombre(),
            b.getDescripcion(),
            b.getPuntosRequeridos(),
            b.puedeSerCanjeado(clienteActual)))
        .toList();

    List<String> opcionesBeneficios = beneficiosDTO.stream()
        .map(BeneficioDTO::getDescripcionCompleta)
        .toList();

    ChoiceDialog<String> dialog = new ChoiceDialog<>(opcionesBeneficios.get(0), opcionesBeneficios);
    dialog.setTitle("Canjear Beneficios");
    dialog.setHeaderText("Beneficios disponibles\nTus puntos: " + clienteActual.getPuntosAcumulados());
    dialog.setContentText("Selecciona un beneficio:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(seleccion -> {
      int indice = opcionesBeneficios.indexOf(seleccion);
      if (indice >= 0 && indice < beneficios.size()) {
        Beneficio beneficio = beneficios.get(indice);
        boolean canjeado = sistema.canjearBeneficio(clienteActual, beneficio.getId());

        if (canjeado) {
          mostrarExito("Beneficio canjeado exitosamente\n\n" +
              beneficio.getNombre() + "\n" +
              beneficio.getDescripcion() + "\n\n" +
              "Puntos restantes: " + clienteActual.getPuntosAcumulados());
          cargarDatosCliente();
        } else {
          mostrarAlerta("No se pudo canjear el beneficio", AlertType.ERROR);
        }
      }
    });
  }

  @FXML
  private void handleVerBeneficiosActivos() {
    List<CanjeBeneficio> beneficiosActivos = clienteActual.getBeneficiosActivos()
        .stream()
        .filter(CanjeBeneficio::puedeUsarse)
        .toList();

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Mis Beneficios Activos");
    alert.setHeaderText("Beneficios que tienes activos ahora:");

    if (beneficiosActivos.isEmpty()) {
      alert.setContentText("No tienes beneficios activos en este momento.\n\n" +
          "Canjea puntos en 'Ver Beneficios' para obtener descuentos y bonos.");
    } else {
      StringBuilder sb = new StringBuilder();
      beneficiosActivos.forEach(b -> {
        sb.append(String.format("✓ %s\n  %s\n  Estado: %s\n\n",
            b.getBeneficio().getNombre(),
            b.getBeneficio().getDescripcion(),
            b.obtenerEstado()));
      });
      alert.setContentText(sb.toString());
    }

    alert.showAndWait();
  }

  @FXML
  private void handleVerReporte() {
    SistemaMonedero sistema = SistemaMonedero.getInstance();

    ResumenClienteDTO resumen = clienteActual.generarResumenDTO();

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Resumen del Cliente");
    alert.setHeaderText(null);

    TextArea textArea = new TextArea(
        resumen.generarReporte() + "\n" +
            sistema.generarReporteCliente(clienteActual));
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setPrefHeight(400);
    textArea.setPrefWidth(600);

    alert.getDialogPane().setContent(textArea);
    alert.getDialogPane().setPrefSize(650, 500);
    alert.showAndWait();
  }

  @FXML
  private void handleVerAnalisisGastos() {
    AnalizadorPatrones.EstadisticasGasto stats = AnalizadorPatrones.analizarGastos(clienteActual);

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Análisis de Patrones de Gasto");
    alert.setHeaderText("Estadísticas de " + clienteActual.getNombre());
    alert.setContentText(stats.generarReporte());
    alert.showAndWait();
  }

  private void iniciarProcesadorTransacciones() {
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(() -> {
      Platform.runLater(() -> {
        SistemaMonedero.getInstance().procesarTransaccionesProgramadas();
        cargarDatosCliente();
      });
    }, 0, 1, TimeUnit.MINUTES);
  }

  private void limpiarFormulario() {
    montoField.clear();
    descripcionArea.clear();
    emailDestinoField.clear();
    tipoTransaccionCombo.setValue("Deposito");
    monederoOrigenCombo.setValue("PRINCIPAL");
  }

  private void mostrarAlerta(String mensaje, AlertType tipo) {
    Alert alert = new Alert(tipo);
    alert.setTitle(tipo == AlertType.ERROR ? "Error" : "Aviso");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  private void mostrarExito(String mensaje) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Operacion Exitosa");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }
}