public abstract class ProcesadorPago {

    // Aca esta el Factory Method: cada subclase decide que pasarela crear.
    protected abstract PasarelaPago crearPasarela();

    // Flujo comun, se escribe una sola vez
    // No contiene if/switch por tipo de pago
    public void procesar(Pedido pedido) {
        PasarelaPago pasarela = crearPasarela();
        double total = pedido.calcularTotal();

        System.out.println("Procesando " + pedido.getId() + " con " + pasarela.nombre() + " por $" + (long) total);

        boolean aprobado = pasarela.cobrar(total);

        System.out.println("  -> Pago " + (aprobado ? "APROBADO" : "RECHAZADO"));
    }
    
}