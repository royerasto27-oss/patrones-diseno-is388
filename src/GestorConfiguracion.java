import java.util.HashMap;
import java.util.Map;

public class GestorConfiguracion {
    // La única instancia se crea de forma perezosa (Lazy Initialization)
    // volatile asegura que múltiples hilos manejen la variable correctamente
    private static volatile GestorConfiguracion instancia;

    private Map<String, String> configuraciones;

    // El constructor privado evita la instanciación externa con 'new'
    private GestorConfiguracion() {
        this.configuraciones = new HashMap<>();
        // Simulamos la carga pesada desde un archivo físico
        System.out.println("Cargando 'config.properties' desde el disco por única vez...");
        this.configuraciones.put("db.url", "jdbc:postgresql://localhost:5432/produccion");
        this.configuraciones.put("db.usuario", "admin_sistema");
        this.configuraciones.put("api.timeout", "5000");
    }

    // Método de acceso global con doble verificación de bloqueo (Double-Checked Locking)
    public static GestorConfiguracion getInstancia() {
        if (instancia == null) {
            synchronized (GestorConfiguracion.class) {
                if (instancia == null) {
                    instancia = new GestorConfiguracion();
                }
            }
        }
        return instancia;
    }

    public String getConfig(String clave) {
        return this.configuraciones.get(clave);
    }
}

// Clase de ejecución (Prueba de hilos concurrentes)
class MainSingleton {
    public static void main(String[] args) {
        // Creamos dos tareas que intentan acceder al gestor al mismo tiempo
        Runnable tarea1 = () -> {
            GestorConfiguracion config = GestorConfiguracion.getInstancia();
            System.out.println("Hilo 1 - DB URL: " + config.getConfig("db.url"));
        };

        Runnable tarea2 = () -> {
            GestorConfiguracion config = GestorConfiguracion.getInstancia();
            System.out.println("Hilo 2 - DB User: " + config.getConfig("db.usuario"));
        };

        // Ejecución concurrente
        new Thread(tarea1).start();
        new Thread(tarea2).start();
    }
}