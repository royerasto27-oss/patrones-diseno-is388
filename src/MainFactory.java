// 1. Interfaz común (Producto)
interface Notificador {
    String enviar(String mensaje);
}
// 2. Productos concretos
class NotificadorEmail implements Notificador {
    @Override
    public String enviar(String mensaje) {
        return "Enviando Email: " + mensaje;
    }
}
class NotificadorSMS implements Notificador {
    @Override
    public String enviar(String mensaje) {
        return "Enviando SMS: " + mensaje;
    }
}
// 3. Fábrica (Factory)
class FabricaNotificaciones {
    public static Notificador crearNotificador(String tipo) {
        if (tipo == null) {
            return null;
        }
        if (tipo.equalsIgnoreCase("EMAIL")) {
            return new NotificadorEmail();
        } else if (tipo.equalsIgnoreCase("SMS")) {
            return new NotificadorSMS();
        }
        throw new IllegalArgumentException("Tipo de notificación no soportado.");
    }
}
// Uso práctico
public class MainFactory {
    public static void main(String[] args) {
        Notificador clienteNotificador = FabricaNotificaciones.crearNotificador("EMAIL");
        System.out.println(clienteNotificador.enviar("Tu pedido ha sido enviado"));
        // Salida: Enviando Email: Tu pedido ha sido enviado
    }
}