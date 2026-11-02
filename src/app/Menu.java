
package app;
import java.time.LocalDate;
import java.util.List;
import modelo.Producto;
import servicio.Inventario;
import util.Entrada;

/**
 * Interfaz de consola de StockGuard.
 * Muestra el menú principal y delega el trabajo en Inventario.
 */
public class Menu {
    private final Inventario inventario;

    public Menu(Inventario inventario) {
        this.inventario = inventario;
    }

    /**
     * Bucle principal: se repite hasta que el usuario elige 0.
     */
    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            System.out.println("7. Control de vencimientos");
            opcion = Entrada.leerEntero("Elige una opción: ");
            ejecutar(opcion);
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println();
        System.out.println("========== STOCKGUARD ==========");
        System.out.println("1. Registrar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto por código");
        System.out.println("4. Editar producto");
        System.out.println("5. Actualizar stock");
        System.out.println("6. Eliminar producto");
        System.out.println("0. Salir");
    }

    private void ejecutar(int opcion) {
        switch (opcion) {
            case 1: registrarProducto(); break;
            case 2: listarProductos(); break;
            case 3: buscarProducto(); break;
            case 4: editarProducto(); break;
            case 5: actualizarStock(); break;
            case 6: eliminarProducto(); break;
            case 7: controlVencimientos(); break;
            case 0: System.out.println("¡Hasta pronto!"); break;
            default: System.out.println("✖ Opción no válida. Elige un número del menú.");
        }
    }

    private void registrarProducto() {
        System.out.println("--- Registrar producto ---");
        try {
            String codigo = Entrada.leerTexto("Código: ");
            String nombre = Entrada.leerTexto("Nombre: ");
            int cantidad = Entrada.leerEntero("Cantidad: ");
            double precio = Entrada.leerDecimal("Precio unitario (S/): ");
            LocalDate fecha = Entrada.leerFecha("Fecha de vencimiento (dd/MM/yyyy): ");

            inventario.registrar(new Producto(codigo, nombre, cantidad, precio, fecha));
            System.out.println("✔ Producto registrado correctamente.");
        } catch (IllegalArgumentException e) {
            // Aquí caen: cantidad/precio negativo, código repetido, etc.
            System.out.println("✖ No se pudo registrar: " + e.getMessage());
        }
    }

    private void listarProductos() {
        System.out.println("--- Lista de productos ---");
        List<Producto> lista = inventario.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
    }

    private void buscarProducto() {
        System.out.println("--- Buscar producto ---");
        String codigo = Entrada.leerTexto("Código a buscar: ");
        Producto p = inventario.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("✖ No existe un producto con el código " + codigo + ".");
        } else {
            System.out.println(p);
        }
    }

    private void editarProducto() {
        System.out.println("--- Editar producto ---");
        String codigo = Entrada.leerTexto("Código del producto a editar: ");
        Producto actual = inventario.buscarPorCodigo(codigo);
        if (actual == null) {
            System.out.println("✖ No existe un producto con el código " + codigo + ".");
            return;
        }
        System.out.println("Datos actuales: " + actual);
        System.out.println("Ingresa los nuevos datos:");
        try {
            String nombre = Entrada.leerTexto("Nombre: ");
            int cantidad = Entrada.leerEntero("Cantidad: ");
            double precio = Entrada.leerDecimal("Precio unitario (S/): ");
            LocalDate fecha = Entrada.leerFecha("Fecha de vencimiento (dd/MM/yyyy): ");

            inventario.editar(codigo, nombre, cantidad, precio, fecha);
            System.out.println("✔ Producto actualizado.");
        } catch (IllegalArgumentException e) {
            System.out.println("✖ No se pudo editar: " + e.getMessage());
        }
    }

    private void actualizarStock() {
        System.out.println("--- Actualizar stock ---");
        String codigo = Entrada.leerTexto("Código del producto: ");
        if (inventario.buscarPorCodigo(codigo) == null) {
            System.out.println("✖ No existe un producto con el código " + codigo + ".");
            return;
        }
        try {
            int cantidad = Entrada.leerEntero("Nueva cantidad en stock: ");
            inventario.editar(codigo, cantidad);   // usa la versión sobrecargada
            System.out.println("✔ Stock actualizado.");
        } catch (IllegalArgumentException e) {
            System.out.println("✖ No se pudo actualizar: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        System.out.println("--- Eliminar producto ---");
        String codigo = Entrada.leerTexto("Código del producto a eliminar: ");
        Producto p = inventario.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("✖ No existe un producto con el código " + codigo + ".");
            return;
        }
        System.out.println(p);
        if (Entrada.confirmar("¿Seguro que deseas eliminarlo?")) {
            inventario.eliminar(codigo);
            System.out.println("✔ Producto eliminado.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private void controlVencimientos() {
    int opcion;
    do {
        System.out.println();
        System.out.println("--- Control de vencimientos ---");
        System.out.println("1. Ver productos vencidos");
        System.out.println("2. Ver productos próximos a vencer");
        System.out.println("0. Volver al menú principal");
        opcion = Entrada.leerEntero("Elige una opción: ");
        switch (opcion) {
            case 1: mostrarVencidos(); break;
            case 2: mostrarPorVencer(); break;
            case 0: break;
            default: System.out.println("✖ Opción no válida.");
        }
    } while (opcion != 0);
}

private void mostrarVencidos() {
    System.out.println("--- Productos vencidos ---");
    List<Producto> vencidos = inventario.obtenerVencidos();
    if (vencidos.isEmpty()) {
        System.out.println("✔ No hay productos vencidos.");
        return;
    }
    for (Producto p : vencidos) {
        System.out.println(p + "  -> vencido hace " + Math.abs(p.diasParaVencer()) + " día(s)");
    }
}

private void mostrarPorVencer() {
    System.out.println("--- Productos próximos a vencer ---");
    int dias = Entrada.leerEntero("Días de anticipación (0 = usar "
            + Inventario.DIAS_ALERTA_POR_DEFECTO + " por defecto): ");

    List<Producto> lista;
    if (dias == 0) {
        lista = inventario.obtenerPorVencer();       // versión sin parámetro
    } else if (dias > 0) {
        lista = inventario.obtenerPorVencer(dias);   // versión con parámetro
    } else {
        System.out.println("✖ Los días no pueden ser negativos.");
        return;
    }

    if (lista.isEmpty()) {
        System.out.println("✔ No hay productos próximos a vencer en ese plazo.");
        return;
    }
    for (Producto p : lista) {
        long d = p.diasParaVencer();
        String aviso = (d == 0) ? "vence HOY" : "vence en " + d + " día(s)";
        System.out.println(p + "  -> " + aviso);
    }
}
}
