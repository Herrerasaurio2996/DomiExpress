public class PasarelaTarjeta implements PasarelaPago{

    @Override
    public String nombre() {return "Tarjeta";}

    @Override
    public boolean cobrar(double monto) {return monto <= 500000;}
    
}