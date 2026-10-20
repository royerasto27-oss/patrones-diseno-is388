// 1. Interfaz moderna y estandarizada de tu Backend
interface ProcesadorPago {
    void procesarPagoConToken(String tokenCliente, double monto);
}

// 2. Clase externa / Legacy (No la puedes modificar porque es de un SDK externo)
class GatewayPagoExterno {
    public void enviarTransaccion(String idTarjeta, String fechaExp,
                                  double total, String codigoMoneda) {
        System.out.println("[Gateway Externo] Procesando cobro manual de " + codigoMoneda
                + " " + total + " a tarjeta " + idTarjeta);
    }
}

// 3. El Adaptador (Implementa tu interfaz y traduce las peticiones al formato externo)
class AdaptadorPagoExterno implements ProcesadorPago {
    private GatewayPagoExterno gatewayExterno;

    public AdaptadorPagoExterno(GatewayPagoExterno gatewayExterno) {
        this.gatewayExterno = gatewayExterno;
    }

    @Override
    public void procesarPagoConToken(String tokenCliente, double monto) {
        // El adaptador resuelve la complejidad: "Descompone" el token interno
        // para mapearlo a los parámetros que pide el SDK externo viejo.
        String idTarjetaSimulado = "4111-XXXX-XXXX-1234";
        String fechaExpSimulada = "12/29";

        gatewayExterno.enviarTransaccion(idTarjetaSimulado, fechaExpSimulada, monto, "USD");
    }
}

// Uso en el Controlador o Servicio del Backend
class PagoController {
    public static void main(String[] args) {
        // Tu backend interactúa estrictamente con la interfaz estandarizada
        GatewayPagoExterno sdkExterno = new GatewayPagoExterno();
        ProcesadorPago procesador = new AdaptadorPagoExterno(sdkExterno);

        // El flujo del backend es limpio y agnóstico de quién procesa el pago por detrás
        procesador.procesarPagoConToken("tok_customer_abc123", 49.99);
    }
}