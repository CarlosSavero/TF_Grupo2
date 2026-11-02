
package servicio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

/**
 * Administra la colección de productos del inventario.
 * Los datos se guardan en memoria usando un ArrayList.
 */

public class Inventario {
    // Lista interna: privada para que solo esta clase la modifique
    public static final int DIAS_ALERTA_POR_DEFECTO = 7;
    private final List<Producto> productos;
    public Inventario() {
        this.productos = new ArrayList<>();
    }

    /**
     * Registra un producto nuevo. No permite códigos repetidos.
     */
    public void registrar(Producto nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (buscarPorCodigo(nuevo.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el código " + nuevo.getCodigo() + ".");
        }
        productos.add(nuevo);
    }

    /**
     * Busca un producto por su código (sin distinguir mayúsculas).
     * @return el producto, o null si no existe
     */
    public Producto buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Edita todos los datos de un producto (el código no cambia).
     * @return true si lo encontró y editó, false si no existe
     */
    public boolean editar(String codigo, String nombre, int cantidad,
                          double precio, LocalDate fechaVencimiento) {
        Producto existente = buscarPorCodigo(codigo);
        if (existente == null) {
            return false;
        }
        // Producto temporal: si algún dato es inválido, se lanza la excepción aquí,
        // antes de modificar el producto real.
        new Producto(existente.getCodigo(), nombre, cantidad, precio, fechaVencimiento);

        existente.setNombre(nombre);
        existente.setCantidad(cantidad);
        existente.setPrecioUnitario(precio);
        existente.setFechaVencimiento(fechaVencimiento);
        return true;
    }

    /**
     * Sobrecarga: edita únicamente la cantidad en stock.
     */
    public boolean editar(String codigo, int nuevaCantidad) {
        Producto existente = buscarPorCodigo(codigo);
        if (existente == null) {
            return false;
        }
        existente.setCantidad(nuevaCantidad);
        return true;
    }

    /**
     * Elimina un producto por su código.
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(String codigo) {
        Producto existente = buscarPorCodigo(codigo);
        if (existente == null) {
            return false;
        }
        return productos.remove(existente);
    }

    /**
     * Devuelve una COPIA de la lista, para proteger la lista interna.
     */
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    public int totalProductos() {
        return productos.size();
    }
    /**
 * Devuelve los productos cuya fecha de vencimiento ya pasó.
 */
public List<Producto> obtenerVencidos() {
    List<Producto> resultado = new ArrayList<>();
    for (Producto p : productos) {
        if (p.estaVencido()) {
            resultado.add(p);
        }
    }
    return resultado;
}

/**
 * Devuelve los productos que vencen dentro de los próximos 'dias' días.
 */
public List<Producto> obtenerPorVencer(int dias) {
    List<Producto> resultado = new ArrayList<>();
    for (Producto p : productos) {
        if (p.estaPorVencer(dias)) {
            resultado.add(p);
        }
    }
    return resultado;
}

/**
 * Sobrecarga: usa el umbral por defecto (7 días).
 */
public List<Producto> obtenerPorVencer() {
    return obtenerPorVencer(DIAS_ALERTA_POR_DEFECTO);
}
}

