import java.util.ArrayList;
import java.util.List;

// 1. Interfaz del Observador (Suscripción)
interface ObservadorStock {
    void actualizar(String nombreProducto, int nuevoStock);
}

// 2. Observadores Concretos (Sistemas que reaccionan al cambio)
class SistemaClientes implements ObservadorStock {
    @Override
    public void actualizar(String nombreProducto, int nuevoStock) {
        if (nuevoStock > 0) {
            System.out.println("[Email Clientes] ¡Buenas noticias! '"
                    + nombreProducto + "' vuelve a estar disponible ("
                    + nuevoStock + " unidades).");
        }
    }
}

class SistemaMarketing implements ObservadorStock {
    @Override
    public void actualizar(String nombreProducto, int nuevoStock) {
        if (nuevoStock > 20) {
            System.out.println("[Marketing Ads] Activando campaña automática en redes para '"
                    + nombreProducto + "'.");
        }
    }
}

// 3. El Sujeto (Clase que es observada)
class ProductoInventario {
    private String nombre;
    private int stock;
    private List<ObservadorStock> observadores = new ArrayList<>();

    public ProductoInventario(String nombre, int stockInicial) {
        this.nombre = nombre;
        this.stock = stockInicial;
    }

    public void agregarObservador(ObservadorStock obs) {
        observadores.add(obs);
    }

    public void removerObservador(ObservadorStock obs) {
        observadores.remove(obs);
    }

    // Cuando el stock cambia, se dispara la notificación automática
    public void setStock(int nuevoStock) {
        this.stock = nuevoStock;
        System.out.println("\n--- Stock de '" + this.nombre
                + "' actualizado a " + nuevoStock + " unidades ---");
        notificarObservadores();
    }

    private void notificarObservadores() {
        for (ObservadorStock obs : observadores) {
            obs.actualizar(this.nombre, this.stock);
        }
    }
}

// Clase de ejecución
class MainObserver {
    public static void main(String[] args) {
        // Creamos el producto (Sujeto)
        ProductoInventario consola = new ProductoInventario("PlayStation 5", 0);

        // Creamos los módulos interesados (Observadores)
        ObservadorStock moduloClientes = new SistemaClientes();
        ObservadorStock moduloMarketing = new SistemaMarketing();

        // Suscribimos los módulos al producto
        consola.agregarObservador(moduloClientes);
        consola.agregarObservador(moduloMarketing);

        // Simulamos la llegada de un camión con mercancía al almacén
        consola.setStock(50);
    }
}