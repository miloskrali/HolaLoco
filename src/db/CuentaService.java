package db;

import java.util.List;

import entidades.Cuenta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.ServicioException;

public class CuentaService {
    
    public List<Cuenta> listarCuentas() throws ServicioException {
        CuentaDAO d = new CuentaDAOH2();
        List<Cuenta> usuarios = d.listarTodasLasCuentas();
        return usuarios;
    }

    public void agregarCuenta(Cuenta c) throws ServicioException, DuplicadoException {
        CuentaDAO d = new CuentaDAOH2();
        try {
            d.crearCuenta(c);
        } catch (DAOException e) {
            throw new ServicioException(e);
        }
    }

}
