public class GeneradorConsecutivo {

    //* La unica instancia existente
    private static GeneradorConsecutivo contador;

    //* Contador de pedidos
    private int numero;

    private GeneradorConsecutivo() {
        
        this.numero = 0;
        System.out.println("Creando la unica instancia de GeneradorConsecutivo...");

    }

    public static GeneradorConsecutivo obtenerInstancia() {

        if(contador == null) {return new GeneradorConsecutivo();

        }

        return contador;

    }

    // Metodo para aumentar el numero del pedido
    public int siguienteNumero() {

        ++numero;
        return numero;

    }
}
