// 1. Interfaz de la Estrategia
interface EstrategiaPago {
    String pagar(double monto);
}

// 2. Estrategias Concretas
class PagoTarjeta implements EstrategiaPago {
    @Override
    public String pagar(double monto) {
        return "Pagando $" + monto + " con Tarjeta de Crédito.";
    }
}

class PagoPayPal implements EstrategiaPago {
    @Override
    public String pagar(double monto) {
        return "Pagando $" + monto + " mediante PayPal.";
    }
}

// 3. Contexto que utiliza la estrategia
class CarritoCompras {
    private double monto;

    public CarritoCompras(double monto) {
        this.monto = monto;
    }

    public String procesarPago(EstrategiaPago estrategia) {
        return estrategia.pagar(this.monto);
    }
}

// Uso práctico
public class MainStrategy {
    public static void main(String[] args) {
        CarritoCompras carrito = new CarritoCompras(150.0);

        // El cliente selecciona PayPal dinámicamente
        String resultado = carrito.procesarPago(new PagoPayPal());
        System.out.println(resultado);
        // Salida: Pagando $150.0 mediante PayPal.
    }
}