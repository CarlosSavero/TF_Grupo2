
package util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 * Métodos de ayuda para leer datos del teclado con validación.
 * Cada método repite la pregunta hasta recibir un dato válido.
 */

public class Entrada {

    private static final Scanner SC = new Scanner(System.in);

    // STRICT: rechaza fechas imposibles como 31/02/2026
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    // Evita que se creen objetos de esta clase (solo tiene métodos estáticos)
    private Entrada() {
    }

    public static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = SC.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("  ✖ Este dato no puede quedar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ✖ Ingresa un número entero válido (ejemplo: 25).");
            }
        }
    }

    public static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                // Aceptamos coma o punto como separador decimal
                return Double.parseDouble(SC.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("  ✖ Ingresa un número válido (ejemplo: 4.50).");
            }
        }
    }

    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return LocalDate.parse(SC.nextLine().trim(), FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("  ✖ Fecha no válida. Usa el formato dd/MM/yyyy (ejemplo: 15/12/2026).");
            }
        }
    }

    /**
     * Pregunta de sí/no. Devuelve true si responde S.
     */
    public static boolean confirmar(String mensaje) {
        while (true) {
            String r = leerTexto(mensaje + " (S/N): ").toUpperCase();
            if (r.equals("S")) {
                return true;
            }
            if (r.equals("N")) {
                return false;
            }
            System.out.println("  ✖ Responde S o N.");
        }
    }
}
