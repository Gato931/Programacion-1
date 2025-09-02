package co.edu.uniquindio.poo;

import javax.swing.JOptionPane;

public class App {

  public static void main(String[] args) {
    // Veterinaria incial
    Veterinaria veterinaria = new Veterinaria("Amigos peludos", "Universidad del Quindio", "123321", "33333");

    // Propietarios iniciales
    Propietario propietario1 = new Propietario("Mateo", "123", "8923", "Calle 8", "Gomez");
    Propietario propietario2 = new Propietario("Luisa", "321", "2389", "Calle 9", "Galan");

    // Mascotas iniciales
    Mascota mascota1 = new Mascota("111", "Lucas", "Perro", "Labrador", 5, "Dorado", 19.5);
    mascota1.setPropietario(propietario1);
    mascota1.setVeterinaria(veterinaria);
    mascota1.agregarEnfermedad("Rabia");
    veterinaria.agregarMascota(mascota1);

    Mascota mascota2 = new Mascota("222", "Misu", "Gato", "Siames", 3, "Blanco", 4.3);
    mascota2.setPropietario(propietario2);
    mascota2.setVeterinaria(veterinaria);
    mascota2.agregarEnfermedad("Alergia");
    veterinaria.agregarMascota(mascota2);

    int opcion = 0;

    while (opcion != 5) {
      String menu = "Bienvenido a la veterinaria Amigos peludos, ubicada en la universidad del Quindio (NIT:123321)\n"
          + "1. Agregar mascota\n"
          + "2. Eliminar mascota\n"
          + "3. Actualizar mascota\n"
          + "4. Listar mascotas\n"
          + "5. Salir\n"
          + "Seleccione una opcion:";
      String entrada = JOptionPane.showInputDialog(menu);
      if (entrada == null || entrada.isBlank())
        continue;
      if (!entrada.matches("\\d+")) {
        JOptionPane.showMessageDialog(null, "Ingrese una opcion valida");
        continue;
      }
      opcion = Integer.parseInt(entrada);

      if (opcion == 1) { // Agregar mascota
        String id = solicitarId("Ingrese el id de la mascota: ");
        if (id == null)
          continue;
        Mascota mascotaExistente = veterinaria.buscarMascota(id);

        if (mascotaExistente != null) {
          JOptionPane.showMessageDialog(null, "La mascota con id " + id + " ya existe. Operacion cancelada.");
        } else {
          String nombre = solicitarTexto("Ingrese el nombre: ");
          if (nombre == null)
            continue;
          String especie = solicitarTexto("Ingrese la especie: ");
          if (especie == null)
            continue;
          String raza = solicitarTexto("Ingrese la raza: ");
          if (raza == null)
            continue;
          Integer edad = solicitarEntero("Ingrese la edad: ");
          if (edad == null)
            continue;
          String color = solicitarTexto("Ingrese el color: ");
          if (color == null)
            continue;
          Double peso = solicitarDecimal("Ingrese el peso: ");
          if (peso == null)
            continue;
          String enfermedad = solicitarTexto("Ingrese la(s) enfermedad(es) de la mascota: ");
          if (enfermedad == null)
            continue;

          Mascota mascota = new Mascota(id, nombre, especie, raza, edad, color, peso);
          mascota.agregarEnfermedad(enfermedad);
          JOptionPane.showMessageDialog(null, veterinaria.agregarMascota(mascota));
        }
      } else if (opcion == 2) { // Eliminar mascota
        String id = solicitarId("Ingrese el id de la mascota a eliminar: ");
        if (id == null)
          continue;
        Mascota mascotaExistente = veterinaria.buscarMascota(id);

        if (mascotaExistente == null) {
          JOptionPane.showMessageDialog(null, "La mascota con id " + id + " no existe. Operacion cancelada.");
        } else {
          JOptionPane.showMessageDialog(null, veterinaria.eliminarMascota(id));
        }
      } else if (opcion == 3) { // Actualizar mascota
        String id = solicitarId("Ingrese el id de la mascota a actualizar: ");
        if (id == null)
          continue;
        Mascota mascotaExistente = veterinaria.buscarMascota(id);

        if (mascotaExistente == null) {
          JOptionPane.showMessageDialog(null, "La mascota con id " + id + " no existe. Operacion cancelada.");
        } else {
          String nombre = solicitarTexto("Ingrese el nuevo nombre: ");
          if (nombre == null)
            continue;
          Integer edad = solicitarEntero("Ingrese la nueva edad: ");
          if (edad == null)
            continue;
          Double peso = solicitarDecimal("Ingrese el nuevo peso: ");
          if (peso == null)
            continue;
          String enfermedad = solicitarTexto("Ingrese la(s) nueva(s) enfermedad(es)");
          if (enfermedad == null)
            continue;

          String especie = "No se puede modificar la especie";
          JOptionPane.showMessageDialog(null, especie);
          String raza = "No se puede modificar la raza";
          JOptionPane.showMessageDialog(null, raza);
          String color = "No se puede modificar el color";
          JOptionPane.showMessageDialog(null, color);

          Mascota nuevaMascota = new Mascota(id, nombre, especie, raza, edad, color, peso);
          nuevaMascota.agregarEnfermedad(enfermedad);
          JOptionPane.showMessageDialog(null, veterinaria.actualizarMascota(id, nuevaMascota, enfermedad));
        }
      } else if (opcion == 4) { // Listar mascotas
        JOptionPane.showMessageDialog(null, veterinaria.listarMascotas());

      } else if (opcion == 5) {
        JOptionPane.showMessageDialog(null, "Cerrando aplicacion c:");

      } else {
        JOptionPane.showMessageDialog(null, "Opcion no valida :c");
      }
    }
  }

  /**
   * Metodo para solicitar cadenas de numeros a traves del JOption, comprobar que
   * no sea nulo ni este vacio
   * 
   * @param mensaje
   * @return
   */
  private static String solicitarId(String mensaje) {
    String texto = JOptionPane.showInputDialog(mensaje);
    if (texto == null)
      return null;

    while (!texto.matches("\\d+")) {
      texto = JOptionPane.showInputDialog("El ID debe contener solo números. " + mensaje);
      if (texto == null)
        return null;
    }
    return texto;
  }

  /**
   * Metodo para solicitar cadenas de letras a traves del JOption, comprobar que
   * no sea nulo ni este vacio
   * 
   * @param mensaje
   * @return
   */
  private static String solicitarTexto(String mensaje) {
    String texto = JOptionPane.showInputDialog(mensaje);
    if (texto == null)
      return null;

    while (texto.isBlank() || !texto.matches(".*[a-zA-Z].*")) {
      texto = JOptionPane.showInputDialog("Debe contener letras " + mensaje);
      if (texto == null)
        return null;
    }
    return texto;
  }

  /**
   * Metodo para solicitar un entero a traves del JOption, comprobar que no sea
   * nulo ni este vacio y convertirlo en entero
   * 
   * @param mensaje
   * @return
   */
  private static Integer solicitarEntero(String mensaje) {
    String entrada = JOptionPane.showInputDialog(mensaje);
    if (entrada == null)
      return null;
    while (!entrada.matches("\\d+")) {
      entrada = JOptionPane.showInputDialog("Debe ser un numero entero " + mensaje);
      if (entrada == null)
        return null;
    }
    return Integer.parseInt(entrada);
  }

  /**
   * Metodo para solicitar un decimal a traves del JOption, comprobar que no sea
   * nulo ni este vacio y convertirlo en decimal
   * 
   * @param mensaje
   * @return
   */
  private static Double solicitarDecimal(String mensaje) {
    String entrada = JOptionPane.showInputDialog(mensaje);
    if (entrada == null)
      return null;
    while (!entrada.matches("\\d+(\\.\\d+)?")) {
      entrada = JOptionPane.showInputDialog("Debe ser un numero decimal " + mensaje);
      if (entrada == null)
        return null;
    }
    return Double.parseDouble(entrada);
  }
}