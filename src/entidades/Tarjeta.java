package entidades;

public class Tarjeta {
    private int dni;
    private int numero;
    private double disponible;
    private double saldoPagar;
    
    public Tarjeta(int dni, int numero, double disponible, double saldoPagar) {
        this.dni = dni;
        this.numero = numero;
        this.disponible = disponible;
        this.saldoPagar = saldoPagar;
    }

    public Tarjeta() {
    }

    public int getDni() {
        return dni;
    }

    public int getNumero() {
        return numero;
    }

    public double getDisponible() {
        return disponible;
    }

    public double getSaldoPagar() {
        return saldoPagar;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDisponible(double disponible) {
        this.disponible = disponible;
    }

    public void setSaldoPagar(double saldoPagar) {
        this.saldoPagar = saldoPagar;
    }
    
    public String toString() {
        return "Tarjeta [numero=" + numero + ", disponible=" + disponible + ", saldoPagar=" + saldoPagar + "]";
    }

    public void realizarCompra(double monto) {
        if (monto <= disponible) {
            disponible -= monto;
            saldoPagar += monto;
            System.out.println("Compra realizada correctamente.");
        } else {
            System.out.println("No hay suficiente saldo disponible en la tarjeta.");
        }
    }

    public void realizarPago(double monto) {
        saldoPagar -= monto;
        if (saldoPagar < 0) {
            disponible += Math.abs(saldoPagar);
            saldoPagar = 0;
        }
        System.out.println("Pago realizado correctamente.");
    }

    public void generarResumen(int mes, int anio) {
        System.out.println("Resumen de movimientos de la tarjeta para el mes " + mes + " del año " + anio);
    }
}