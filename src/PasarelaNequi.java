public class PasarelaNequi implements PasarelaPago{

    @Override
    public String nombre() {return "Nequi";}

    @Override
    public boolean cobrar(double monto) {return monto <= 300000;}
    
}