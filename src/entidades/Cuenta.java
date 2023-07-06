package entidades;

import db.CuentaDAO;
import excepciones.DAOException;
import excepciones.SaldonegativoException;

public class Cuenta {
    private int dni;
    private String tipo;
    private int numeroCuenta;
    private double saldo;

    public Cuenta(int dni, String tipo, int numeroCuenta, double saldo) {
        this.dni = dni;
        this.tipo = tipo;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    public Cuenta() {
	}

	public int getDni() {
        return dni;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }
    
    public void setDni(int dni) {
		this.dni = dni;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public void depositarSaldo(double saldo, CuentaDAO cuentaDAO)
            throws DAOException {
        this.saldo += saldo;
        cuentaDAO.actualizarCuenta(this);
    }

    public void retirarSaldo(double saldo, CuentaDAO cuentaDAO)
            throws SaldonegativoException, DAOException {
        if (this.saldo < saldo) {
            throw new SaldonegativoException("Saldo insuficiente en la cuenta");
        }
        this.saldo -= saldo;
        cuentaDAO.actualizarCuenta(this);
    }

    public void agregarMovimiento(Movimiento movimiento) {
        if (movimiento != null) {
        } else {
            throw new IllegalArgumentException("El movimiento es nulo");
        }
    }

	@Override
	public String toString() {
		return "Cuenta [dni=" + dni + ", tipo=" + tipo + ", numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + "]";
	}
    
    
}