package db;

import java.util.List;

import entidades.Usuario;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.ServicioException;

public class UsuarioService {

    public List<Usuario> listarUsuarios() throws ServicioException {
        UsuarioDAO d = new UsuarioDAOH2();
        List<Usuario> usuarios = d.listaTodosLosUsuarios();
        return usuarios;
    }

    public void agregarUsuario(Usuario u) throws ServicioException, DuplicadoException {
        UsuarioDAO d = new UsuarioDAOH2();
        try {
            d.crearUsuario(u);
        } catch (DAOException e) {
            throw new ServicioException(e);
        }
    }
}
