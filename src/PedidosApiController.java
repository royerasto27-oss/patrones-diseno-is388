// 1. Los subsistemas complejos del Backend
class ServicioInventario {
    public boolean verificarYReservarStock(String productoId, int cantidad) {
        System.out.println("[Inventario] Stock reservado para el producto: " + productoId);
        return true;
    }
}

class ServicioFinanzas {
    public boolean cobrarCliente(String clienteId, double total) {
        System.out.println("[Finanzas] Transacción exitosa para el cliente: " + clienteId);
        return true;
    }
}

class ServicioLogistica {
    public void generarGuiaEnvio(String productoId, String direccion) {
        System.out.println("[Logística] Guía de envío generada para: " + direccion);
    }
}

class ServicioNotificaciones {
    public void enviarConfirmacionEmail(String clienteId) {
        System.out.println("[Notificaciones] Correo electrónico de confirmación enviado.");
    }
}

// 2. La Fachada (Facade) que simplifica y coordina a todos los subsistemas
class OrdenesFacade {
    private ServicioInventario inventario = new ServicioInventario();
    private ServicioFinanzas finanzas = new ServicioFinanzas();
    private ServicioLogistica logistica = new ServicioLogistica();
    private ServicioNotificaciones notificaciones = new ServicioNotificaciones();

    // El backend reduce líneas de código coordinando dependencias a un solo método
    public boolean procesarNuevaOrden(String clienteId, String productoId, int cantidad,
                                      double total, String direccion) {
        System.out.println("=== Iniciando flujo de orden unificado ===");

        if (!inventario.verificarYReservarStock(productoId, cantidad)) {
            return false;
        }

        if (!finanzas.cobrarCliente(clienteId, total)) {
            return false;
        }

        logistica.generarGuiaEnvio(productoId, direccion);
        notificaciones.enviarConfirmacionEmail(clienteId);

        System.out.println("=== Orden completada exitosamente ===");
        return true;
    }
}

// Uso práctico en el API Controller o Handler del Backend
class PedidosApiController {
    public static void main(String[] args) {
        // El controlador solo conoce la fachada, manteniéndose ultra-delgado (Skinny Controller)
        OrdenesFacade tiendaFacade = new OrdenesFacade();

        boolean resultado = tiendaFacade.procesarNuevaOrden(
                "cli_987", "prod_laptop_01", 1, 1200.00, "Av. Siempre Viva 742"
        );

        System.out.println("Respuesta HTTP para el cliente: "
                + (resultado ? "201 Created" : "400 Bad Request"));
    }
}