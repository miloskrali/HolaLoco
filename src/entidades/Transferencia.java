package entidades;

import db.CuentaDAO;
import excepciones.DAOException;
import excepciones.SaldoInsuficienteException;
import excepciones.SaldonegativoException;

public class Transferencia {
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private CuentaDAO cuentaDAO;

    public Transferencia() {
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public void realizarTransferencia(double monto, Cuenta cuentaOrigen, Cuenta cuentaDestino, CuentaDAO cuentaDAO)
            throws SaldonegativoException, SaldoInsuficienteException, DAOException {
        double saldo = cuentaOrigen.getSaldo();

        if (saldo < 0) {
            throw new SaldonegativoException("No se puede realizar una transferencia desde una cuenta con saldo negativo");
        }
        if (saldo == 0) {
            throw new SaldoInsuficienteException("No se puede realizar una transferencia desde una cuenta con saldo cero");
        }

        cuentaOrigen.retirarSaldo(saldo,cuentaDAO);
        cuentaDestino.depositarSaldo(saldo,cuentaDAO);
    }
}