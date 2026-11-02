
package app;
import java.time.LocalDate;
import modelo.Producto;
import servicio.Inventario;
public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        cargarDatosDePrueba(inventario);   
        new Menu(inventario).iniciar();
    }

    /**
     * Carga productos de ejemplo con fechas relativas a hoy,
     * para poder probar vencimientos sin importar el día.
     */
    private static void cargarDatosDePrueba(Inventario inv) {
        LocalDate hoy = LocalDate.now();
        inv.registrar(new Producto("P001", "Leche 1L", 20, 4.50, hoy.plusDays(10)));
        inv.registrar(new Producto("P002", "Yogurt 1L", 8, 6.80, hoy.plusDays(3)));
        inv.registrar(new Producto("P003", "Atún 170g", 40, 5.20, hoy.plusMonths(8)));
        inv.registrar(new Producto("P004", "Pan de molde", 5, 7.90, hoy.minusDays(2)));
        inv.registrar(new Producto("P005", "Queso fresco 500g", 3, 12.00, hoy.plusDays(6)));
        inv.registrar(new Producto("P006", "Galletas soda", 2, 1.50, hoy.minusDays(10)));
    }
}
