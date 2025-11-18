package co.edu.uniquindio.poo.model;

public interface Transaccionable {
  boolean depositar(double monto, String descripcion);

  boolean retirar(double monto, String descripcion);

  boolean transferir(Transaccionable destino, double monto, String descripcion);

  double getSaldo();
}