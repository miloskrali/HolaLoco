package entidades;

public class Movimiento {
    private int id;
    private int numeroTarjeta;
    private double monto;
    private String descripcion;
    private int dni;

    public Movimiento(int id, int numeroTarjeta, double monto, String descripcion, int dni) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.monto = monto;
        this.descripcion = descripcion;
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public double getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDni() {
        return dni;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Movimiento [id=" + id + ", numeroTarjeta=" + numeroTarjeta + ", monto=" + monto + ", descripcion="
                + descripcion + ", dni=" + dni + "]";
    }
}