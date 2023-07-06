package db;

import java.util.List;

import entidades.Cuenta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.ServicioException;
import excepciones.UsuarioNoExistenteException;

public class CuentaService {
    
    public List<Cuenta> listarCuentas() throws ServicioException {
        CuentaDAO d = new CuentaDAOH2();
        List<Cuenta> usuarios = d.listarTodasLasCuentas();
        return usuarios;
    }

    public void agregarCuenta(Cuenta c) throws ServicioException, DuplicadoException {
        CuentaDAO d = new CuentaDAOH2();
        try {
            try {
				d.crearCuenta(c);
			} catch (DuplicadoException e) {
				e.printStackTrace();
			} catch (UsuarioNoExistenteException e) {
				e.printStackTrace();
			}
        } catch (DAOException e) {
            throw new ServicioException(e);
        }
    }

}
