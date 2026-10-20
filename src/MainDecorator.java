// 1. Interfaz del Componente Base
interface Cafe {
    double getCosto();
    String getDescripcion();
}
// 2. Componente Concreto
class CafeSimple implements Cafe {
    @Override
    public double getCosto() { return 2.0; }
    @Override
    public String getDescripcion() { return "Café Simple"; }}
// 3. Decorador Base (Abstracto)
abstract class DecoradorCafe implements Cafe {
    protected Cafe cafeDecorado;

    public DecoradorCafe(Cafe cafe) {
        this.cafeDecorado = cafe;
    }}
// 4. Decoradores Concretos
class ConLeche extends DecoradorCafe {
    public ConLeche(Cafe cafe) { super(cafe); }

    @Override
    public double getCosto() { return cafeDecorado.getCosto() + 0.5; }
    @Override
    public String getDescripcion() { return cafeDecorado.getDescripcion() + ", Leche"; }}
class ConChocolate extends DecoradorCafe {
    public ConChocolate(Cafe cafe) { super(cafe); }
    @Override
    public double getCosto() { return cafeDecorado.getCosto() + 0.7; }
    @Override
    public String getDescripcion() { return cafeDecorado.getDescripcion() + ", Chocolate"; }}
// Uso práctico
public class MainDecorator {
    public static void main(String[] args) {
        Cafe miCafe = new CafeSimple();
        miCafe = new ConLeche(miCafe);
        miCafe = new ConChocolate(miCafe);

        System.out.println("Pedido: " + miCafe.getDescripcion() + " | Total: $" + miCafe.getCosto());
        // Salida: Pedido: Café Simple, Leche, Chocolate | Total: $3.2
    }
}