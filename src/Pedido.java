import java.util.ArrayList;
import java.util.List;

public class Pedido {

    
    /*  
    Atributos 
    ! (Final para que una vez se cree el objeto, este sea inmutable)
    * Un pedido no puede cambiar de datos una vez creado
    * Solo puede cambiar de datos en el builder
    */
    private final String id;
    private final String cliente;
    private final TipoEntrega tipoEntrega;
    private final String direccion;
    private final List<ItemPedido> items;
    private final String notas;
    private final int cupon;
    private final double propina;

    //Constructor privado: Solo el Builder puede crear un pedido.
    private Pedido (Builder b) {
        
        this.id = GeneradorConsecutivo.obtenerInstancia().siguiente();
        this.cliente = b.cliente;
        this.tipoEntrega = b.tipoEntrega;
        this.direccion = b.direccion;
        this.items = new ArrayList<>(b.items);
        this.notas = b.notas;
        this.cupon = b.cupon;
        this.propina = b.propina;

    }

    // Constructor de clonado: se usa en clonar(), tambien privado
    private Pedido (Pedido original) {

        this.id = GeneradorConsecutivo.obtenerInstancia().siguiente();
        this.cliente = original.cliente;
        this.tipoEntrega = original.tipoEntrega;
        this.direccion = original.direccion;
        this.items = new ArrayList<>(original.items);
        this.notas = original.notas;
        this.cupon = 0;
        this.propina = original.propina;

    }
    
    //? Getters
    public String getId() {return id;}
    public String getCliente() {return this.cliente;}
    public TipoEntrega getTipoEntrega() {return this.tipoEntrega;}
    public String getDireccion() {return this.direccion;}
    public List<ItemPedido> getItem() {return this.items;}
    public String getNotas() {return this.notas;}
    public int getCupon() {return this.cupon;}
    public double getPropina() {return this.propina;}

    //* Metodos

    public Pedido clonar() {
        return new Pedido(this);
    }

    public void agregarItem(ItemPedido item) {
        items.add(item);
    }
    
    public double calcularTotal() {

        double subtotal = 0;

        for (ItemPedido item : items) {
            subtotal += item.getSubtotal(); 
        }

        double total = subtotal * (100 - cupon) / 100 + propina; 
        return total;

    }

    public void mostrarResumen() {
        System.out.print("Pedido " + id + " | Cliente: " + cliente
                + " | Entrega: " + tipoEntrega);
        if (tipoEntrega == TipoEntrega.DOMICILIO) {
            System.out.print(" (" + direccion + ")");
        }
        System.out.println();
        for (ItemPedido item : items) {
            System.out.println("  - " + item.getCantidad() + " x " + item.getNombre()
                    + " ($" + (long) item.getPrecioUnitario() + ")");
        }
        if (!notas.isEmpty()) {
            System.out.println("  Notas: " + notas);
        }
        System.out.println("  Cupon: " + cupon + "% | Propina: $" + (long) propina
                + " | Total: $" + (long) calcularTotal());
    }


    public static class Builder {

        /*
        Atributos
        Tambien privados, pero estos si son mutables
        puesto que es quien construye el molde para el objeto
        como tal no es el objeto, viene siendo su adn
        */
        // Obligatorios (No tienen valor por defecto)
        private String cliente;
        private final List<ItemPedido> items = new ArrayList<>();

        // Opcionales (Con valor por defecto)
        private TipoEntrega tipoEntrega = TipoEntrega.RECOGER;
        private String direccion = "";
        private String notas = "";
        private int cupon = 0;
        private double propina = 0;

        public Builder conCliente(String cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder conTipoEntrega(TipoEntrega tipoEntrega) {
            this.tipoEntrega = tipoEntrega;
            return this;
        }

        public Builder conDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder agregarItem(ItemPedido item) {
            this.items.add(item);
            return this;
        }

        public Builder conNotas(String notas) {
            this.notas = notas;
            return this;
        }

        public Builder conCupon(int cupon) {
            if(cupon < 0 || cupon > 100) {
                throw new IllegalStateException("El cupon debe estar entre 0 y 100");
            }
            this.cupon = cupon;
            return this;
        }

        public Builder conPropina(double propina) {
            this.propina = propina;
            return this;
        }

        public Pedido construir() {
            // Se valida en el orden exigido antes de pedir el numero.
            if (cliente == null || cliente.isBlank()) {
                throw new IllegalStateException("El cliente es obligatorio");
            }
            if (items.isEmpty()) {
                throw new IllegalStateException("El pedido debe tener al menos un item");
            }
            if (tipoEntrega == TipoEntrega.DOMICILIO && direccion.isBlank()) {
                throw new IllegalStateException("El domicilio requiere direccion");
            }
            return new Pedido(this); // Aca es donde se consume el numero
        }

}

}
