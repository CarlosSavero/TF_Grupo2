package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
/**
 * Representa un producto del inventario de la bodega.
 * Guarda los datos necesarios para controlar stock y vencimientos.
 */
public class Producto {

    // Atributos privados: nadie fuera de la clase puede tocarlos directamente
    private final String codigo;
    private String nombre;
    private int cantidad;
    private double precioUnitario;
    private LocalDate fechaVencimiento;

    /**
     * Constructor: se ejecuta al crear un producto con "new".
     * Reutiliza los setters para que las validaciones se apliquen
     * también al momento de crear el objeto.
     */
    public Producto(String codigo, String nombre, int cantidad,
        double precioUnitario, LocalDate fechaVencimiento) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        this.codigo = codigo.trim();
        setNombre(nombre);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        setFechaVencimiento(fechaVencimiento);
    }

    // ---------- Getters ----------
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    // ---------- Setters con validación ----------
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precioUnitario = precioUnitario;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Calcula el valor total de este producto en el inventario.
     * Valor = cantidad x precio unitario.
     */
    public double calcularValorTotal() {
        return cantidad * precioUnitario;
    }
/**
 * Días que faltan para el vencimiento.
 * Positivo: aún no vence. 0: vence hoy. Negativo: ya venció.
 */
public long diasParaVencer() {
    return ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
}

/**
 * Un producto está vencido si su fecha es anterior a hoy.
 * Si vence hoy, todavía se considera vigente.
 */
public boolean estaVencido() {
    return diasParaVencer() < 0;
}

/**
 * Está por vencer si NO está vencido y faltan 'dias' días o menos.
 */
public boolean estaPorVencer(int dias) {
    long restantes = diasParaVencer();
    return restantes >= 0 && restantes <= dias;
}
    /**
     * Texto legible del producto, útil para mostrarlo en consola.
     */
    @Override
public String toString() {
    return String.format("[%s] %s | Stock: %d | S/ %.2f | Vence: %s",
            codigo, nombre, cantidad, precioUnitario,
            fechaVencimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
}
    }

