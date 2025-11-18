package co.edu.uniquindio.poo.model;

import java.util.List;

public interface Notificable {
  void enviarNotificacion(String mensaje, TipoNotificacion tipo);

  List<String> obtenerNotificaciones();

  void marcarNotificacionComoLeida(int indice);
}