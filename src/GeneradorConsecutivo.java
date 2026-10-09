public class GeneradorConsecutivo {

    //* La unica instancia existente
    private static GeneradorConsecutivo contador;

    //* Contador de pedidos
    private int numero;

    private GeneradorConsecutivo() {
        
        this.numero = 0;
        System.out.println("[Consecutivo] Instancia creada.");

    }

    public static GeneradorConsecutivo obtenerInstancia() {

        if(contador == null) {
            
            contador = new GeneradorConsecutivo();

        }

        return contador;

    }

    // Metodo para aumentar el numero del pedido, cumpliendo con el formato requerido
    public String siguiente() {
    numero++;
    return String.format("PED-%04d", numero);
}
}
